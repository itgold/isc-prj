import React from 'react';
import { Action, Dispatch, bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import MomentUtils from '@date-io/moment';
import { createStyles, StyleRules, Theme, withStyles } from '@material-ui/core/styles';
import { ClassNameMap } from '@material-ui/styles';
import { Button, IconButton, TableCell, Tooltip, Typography } from '@material-ui/core';
import { FaEye, FaMapPin } from 'react-icons/fa';

import { fromLonLat, toLonLat } from 'ol/proj';
import { Draw, Interaction, Modify } from 'ol/interaction';
import MapBrowserEvent from 'ol/MapBrowserEvent';
import { Control } from 'ol/control';
import VectorSource from 'ol/source/Vector';

import Geometry from 'ol/geom/Geometry';
import Point from 'ol/geom/Point';
import Polygon from 'ol/geom/Polygon';
import { Coordinate } from 'ol/coordinate';
import Feature from 'ol/Feature';

import GeoSearchDialog from '@common/components/map/search/search.dialog';
import { IGeoData } from '@common/components/map/search/providers/search';
import OlButton from '@common/components/map/controls/button';

import * as AppActions from 'app/store/actions/app';
import * as Actions from 'app/store/actions/admin';
import * as MessageActions from 'app/store/actions/fuse/message.actions';
import * as Selectors from 'app/store/selectors';
import { RootState } from 'app/store/appState';
import { EntityType, hasGeoData, RegionType } from 'app/utils/domain.constants';

import SchoolMapBase, {
    SchoolMapActionProps,
    ReduxStoreProps,
    SchoolMapComponentProps,
    SchoolMapState,
} from 'app/components/map/SchoolMapBase';
import {
    extractGeoData,
    extractRegionGeoData,
    filterRegion,
    findParentFloor,
    pathsToRoot,
    purifyEntityData,
} from 'app/components/map/utils/MapUtils';
import ITool from 'app/components/map/markers/ITool';
import GeoPoint from 'app/domain/geo.location';
import School from 'app/domain/school';
import ISchoolElement from 'app/domain/school.element';
import GeoPolygon from 'app/domain/geo.polygon';
import { resolveEntityTool } from 'app/components/map/markers/toolsList';
import queryService from 'app/services/query.service';
import deviceRadioService from 'app/services/device.radio.service';
import SchoolMapComponent, { IKeydownHandler } from 'app/components/map/SchoolMapComponent';
import { createSelector } from 'reselect';
import Region from 'app/domain/region';
import { CompositeNode, CompositeNodesMap } from 'app/domain/composite';
import { ICompositeTreeDataProvider } from 'app/components/map/providers/CompositeTreeDataProvider';
import { TabPanel, TabPanelView } from '@common/components/layout/tabpanel';
import { PanelLocation } from 'app/components/map/SchoolMapSplitPanel';
import { ViewStyle } from '@common/components/layout/splitpanel';
import RegionsGridComponent from 'app/components/map/regionGrid/RegionsGridComponent';
import { ICellCallback, ITableHeadColumn } from 'app/components/table/IscTableHead';
import EntityNameCell, { ParentPathComponent } from 'app/components/EntityNameCell';
import { CgListTree } from 'react-icons/cg';
import { featureFromCoordinates } from '@common/components/map/map.utils';
import { ModifyEvent } from 'ol/interaction/Modify';
import BaseEvent from 'ol/events/Event';
import { CurrentBuildingContext } from 'app/components/map/MapBuildingContext';
import DevicesGridComponent from '../../components/map/deviceGrid/DevicesGridComponent';

const momentUtils: MomentUtils = new MomentUtils();

const styles = (theme: Theme): StyleRules =>
    createStyles({
        defaultButtons: {
            '& > *': {
                margin: theme.spacing(1),
            },
        },
        root: {
            display: 'flex',
            minHeight: '100%',
            marginTop: 0,
            marginBottom: 0,
        },
        widgetBody: {
            height: '100%',
            width: '100%',
        },
        formControl: {
            margin: theme.spacing(1),
            minWidth: 120,
        },
        title: {
            flexGrow: 1,
        },
        hide: {
            display: 'none',
        },
        content: {
            position: 'relative',
            display: 'flex',
            height: '100%',
            flexDirection: 'column',
            flex: 1,
            outline: 'none',
            overflow: 'hidden',
            userSelect: 'text',
        },
        tableRoot: {
            flexGrow: 1,
            marginLeft: theme.spacing(2),
            marginRight: theme.spacing(2),
        },
        tableContainer: {
            height: '100%',
        },
        updateParentBtn: {
            marginLeft: 60,
        },
    });

const DEVICES_GRID: Array<ITableHeadColumn> = [
    {
        id: 'name',
        align: 'left',
        disablePadding: false,
        label: 'Name',
        sort: true,
        filter: 'string',
    },
    {
        id: 'entityType',
        align: 'left',
        disablePadding: false,
        label: 'Type',
        width: 160,
        sort: true,
        filter: {
            filterType: 'string',
            filter: (value: ISchoolElement, filterValue: string): boolean => {
                return `${value}`.toLowerCase().indexOf(filterValue) >= 0;
            },
        },
    },
    {
        id: 'updated',
        align: 'center',
        disablePadding: false,
        label: 'Updated',
        width: 120,
        sort: true,
        filter: {
            filterType: 'date',
            format: 'YYYY-MM-DD',
        },
        formatter: (row: ISchoolElement, column: ITableHeadColumn, index: number): React.ReactNode => (
            <TableCell padding="default" key={index}>
                {momentUtils.format(momentUtils.date(row.updated), 'YYYY-MM-DD')}
            </TableCell>
        ),
    },
    {
        id: 'region',
        alias: 'id',
        align: 'center',
        disablePadding: false,
        label: 'Region',
        filter: {
            filterType: 'string',
            filter: filterRegion,
        },
        formatter: (row: ISchoolElement, column: ITableHeadColumn, index: number): React.ReactNode => (
            <EntityNameCell entity={row} skipEntityName skipSchoolName key={index} />
        ),
    },
    {
        id: 'actions',
        align: 'left',
        disablePadding: false,
        label: 'Actions',
        width: 100,
        formatter: (
            row: ISchoolElement,
            column: ITableHeadColumn,
            index: number,
            cellCallback?: ICellCallback
        ): React.ReactNode => {
            return (
                <TableCell padding="default" key={index}>
                    <Tooltip title="Find on Tree" aria-label="show-filters">
                        <IconButton
                            size="small"
                            style={{ fontSize: '1.4rem' }}
                            onClick={(event: React.MouseEvent) => {
                                event.stopPropagation();
                                if (cellCallback?.onAction) cellCallback?.onAction('findInTree', row);
                                return false;
                            }}
                        >
                            <CgListTree />
                        </IconButton>
                    </Tooltip>

                    {hasGeoData(row) ? (
                        <>
                            <Tooltip title="Find On Map">
                                <IconButton
                                    aria-label="highlight"
                                    size="small"
                                    onClick={() =>
                                        cellCallback?.onAction ? cellCallback?.onAction('highlight', row) : undefined
                                    }
                                >
                                    <FaEye />
                                </IconButton>
                            </Tooltip>
                            <Tooltip title="Edit">
                                <IconButton
                                    aria-label="edit"
                                    size="small"
                                    onClick={e => {
                                        e.stopPropagation();
                                        if (cellCallback?.onAction) cellCallback?.onAction('edit', row);
                                        return false;
                                    }}
                                >
                                    <FaMapPin />
                                </IconButton>
                            </Tooltip>
                        </>
                    ) : (
                        <Tooltip title="Edit">
                            <IconButton
                                style={{ color: 'rgba(0, 0, 0, 0.26)' }}
                                aria-label="highlight"
                                size="small"
                                onClick={e => {
                                    e.stopPropagation();
                                    if (cellCallback?.onAction) cellCallback?.onAction('edit', row);
                                    return false;
                                }}
                            >
                                <FaMapPin />
                            </IconButton>
                        </Tooltip>
                    )}
                </TableCell>
            );
        },
    },
];

const REGIONS_GRID: Array<ITableHeadColumn> = [
    {
        id: 'name',
        align: 'left',
        disablePadding: false,
        label: 'Name',
        sort: true,
        filter: 'string',
    },
    {
        id: 'type',
        align: 'left',
        disablePadding: false,
        label: 'Type',
        width: 160,
        sort: true,
        filter: {
            filterType: 'string',
            filter: (value: ISchoolElement, filterValue: string): boolean =>
                `${value}`.toLowerCase().indexOf(filterValue) >= 0,
        },
    },
    {
        id: 'updated',
        align: 'center',
        disablePadding: false,
        label: 'Updated',
        width: 120,
        sort: true,
        filter: {
            filterType: 'date',
            format: 'YYYY-MM-DD',
        },
        formatter: (row: ISchoolElement, column: ITableHeadColumn, index: number): React.ReactNode => (
            <TableCell padding="default" key={index}>
                {momentUtils.format(momentUtils.date(row.updated), 'YYYY-MM-DD')}
            </TableCell>
        ),
    },
    {
        id: 'region',
        alias: 'id',
        align: 'center',
        disablePadding: false,
        label: 'Parent',
        filter: {
            filterType: 'string',
            filter: filterRegion,
        },
        formatter: (row: ISchoolElement, column: ITableHeadColumn, index: number): React.ReactNode => (
            <EntityNameCell entity={row} skipEntityName skipSchoolName key={index} />
        ),
    },
    {
        id: 'actions',
        align: 'left',
        disablePadding: false,
        label: 'Actions',
        width: 100,
        formatter: (
            row: ISchoolElement,
            column: ITableHeadColumn,
            index: number,
            cellCallback?: ICellCallback
        ): React.ReactNode => {
            return (
                <TableCell padding="default" key={index}>
                    <Tooltip title="Find on Tree" aria-label="show-filters">
                        <IconButton
                            size="small"
                            style={{ fontSize: '1.4rem' }}
                            onClick={(event: React.MouseEvent) => {
                                event.stopPropagation();
                                if (cellCallback?.onAction) cellCallback?.onAction('findInTree', row);
                                return false;
                            }}
                        >
                            <CgListTree />
                        </IconButton>
                    </Tooltip>

                    {hasGeoData(row) ? (
                        <>
                            <Tooltip title="Find On Map">
                                <IconButton
                                    aria-label="highlight"
                                    size="small"
                                    onClick={() =>
                                        cellCallback?.onAction ? cellCallback?.onAction('highlight', row) : undefined
                                    }
                                >
                                    <FaEye />
                                </IconButton>
                            </Tooltip>
                            <Tooltip title="Edit">
                                <IconButton
                                    aria-label="edit"
                                    size="small"
                                    onClick={e => {
                                        e.stopPropagation();
                                        if (cellCallback?.onAction) cellCallback?.onAction('edit', row);
                                        return false;
                                    }}
                                >
                                    <FaMapPin />
                                </IconButton>
                            </Tooltip>
                        </>
                    ) : (
                        <Tooltip title="Edit">
                            <IconButton
                                style={{ color: 'rgba(0, 0, 0, 0.26)' }}
                                aria-label="highlight"
                                size="small"
                                onClick={e => {
                                    e.stopPropagation();
                                    if (cellCallback?.onAction) cellCallback?.onAction('edit', row);
                                    return false;
                                }}
                            >
                                <FaMapPin />
                            </IconButton>
                        </Tooltip>
                    )}
                </TableCell>
            );
        },
    },
];

interface MapEditorComponentActionProps extends SchoolMapActionProps {
    updateRegion(school?: School): void;
}
type MapEditorComponentReduxStoreProps = ReduxStoreProps;
interface MapEditorComponentProps extends SchoolMapComponentProps {
    onEntityUpdated?: (row: ISchoolElement, isDeleteAction: boolean) => void;
}

export interface DefaultProps {
    classes: Partial<ClassNameMap<string>>;
}
type Props = MapEditorComponentProps & MapEditorComponentActionProps & MapEditorComponentReduxStoreProps & DefaultProps;

export interface IMapEditorComponent extends React.Component<Props> {
    updateRegionEntity(schoolEntity: ISchoolElement, fullEntity: boolean, deleteGeoData: boolean): void;
}

interface MapEditorComponentState extends SchoolMapState {
    currentDraw?: Draw | null;
    selectedMark: any | null;
    lastInsertEventTime?: number;
    openFindLocationDialog?: boolean;
}

class MapEditorComponent extends SchoolMapBase<Props, MapEditorComponentState> implements IMapEditorComponent {
    constructor(props: Props) {
        super(props);

        this.state = {
            ...this.state,
            openFindLocationDialog: false,
            selectedMark: null,
        };
    }

    protected updateChangedFeature(updatedFeature: Feature<Geometry>): void {
        const row = updatedFeature?.getProperties()?.data || null;
        let newParentRegion: Region | null = null;

        if (CompositeNode.entityType(row) === EntityType.REGION && (row as Region).type !== RegionType.POINT_REGION) {
            row.geoBoundaries = this.getRegionFeatureCoordinates(updatedFeature);
        } else {
            row.geoLocation = this.getFeatureCoordinates(updatedFeature);

            const point: Point = updatedFeature.getGeometry() as Point;
            const coordinates = point.getCoordinates() as Coordinate;
            const feature = featureFromCoordinates(coordinates, this.mapRef.current?.getMap(), true);
            const entityType = feature?.getProperties()?.entityType;
            if (entityType === EntityType.REGION) {
                const parent = feature?.getProperties()?.data as Region;
                if (parent?.id && row.parentIds?.indexOf(parent.id) === -1) {
                    newParentRegion = parent;
                }
            } else if (!entityType) {
                // dropped to school territory
                const schoolRegionId = this.props.school?.region?.id;
                if (schoolRegionId && row.parentIds?.indexOf(schoolRegionId) === -1) {
                    newParentRegion = this.props.school?.region || null;
                }
            }
        }

        this.updateRegionEntity(row).then(() => {
            if (newParentRegion) {
                this.updateParentRegion(row, newParentRegion);
            } else {
                this.props.dispatch(Actions.loadRegionTree(true));
            }
        });

        this.setState({
            isEditing: false,
            editingModel: undefined,
        });
    }

    protected onKeyDown(ev: React.KeyboardEvent): boolean {
        // is Escape pressed?
        if ((ev.which === 27 || ev.keyCode === 27 || ev.key === 'Escape') && this.state.isEditing) {
            // cancel drawing event
            this.props.dispatch(
                AppActions.showDialog(
                    'Cancel drawing',
                    'Would you like to cancel the drawing event?',
                    this.cancelDrawingEvent,
                    null,
                    null
                )
            );

            return true;
        }

        return super.onKeyDown(ev);
    }

    editRow = (row: ISchoolElement | null): void => {
        // if it's an unselection event from the table
        if (!row) {
            // cancel the latest drawing event and exit from this procedure
            this.cancelDrawingEvent();
            return;
        }

        const selectRowTool = (rowId: string, entity: ISchoolElement): void => {
            this.mapRef.current?.animateFeature(rowId, (feature?: Feature<Geometry>) => {
                if (feature) {
                    this.cancelDrawingEvent();
                }
                const tool = resolveEntityTool(CompositeNode.entityType(entity));
                if (tool) {
                    this.selectTool(tool, entity);
                }
            });
        };

        if (row?.id) {
            if (
                EntityType.REGION === CompositeNode.entityType(row as CompositeNode) &&
                (row as Region).type === RegionType.ZONE
            ) {
                this.props.dispatch(AppActions.showZone(row.id));
            }

            const { floor, building } = findParentFloor(row as CompositeNode, this.props.selectedFloors);
            if (floor?.id || building?.id) {
                const selectedFloor = this.props.selectedFloors?.find(f => f.id === floor?.id);
                if (!selectedFloor && floor && building?.id) {
                    const floorPayload: CompositeNodesMap = {};
                    floorPayload[building.id] = floor as CompositeNode;

                    this.props.dispatch(AppActions.setFloorSelection(floorPayload));
                    this.props.dispatch(
                        MessageActions.showMessage({
                            message: `Building '${building.name}' Floor '${floor.name}' selected.`, // text or html
                            autoHideDuration: 3000, // ms
                            anchorOrigin: {
                                vertical: 'bottom', // top bottom
                                horizontal: 'right', // left center right
                            },
                            variant: 'success', // success error info warning null
                        })
                    );

                    setTimeout(() => {
                        if (row.id) selectRowTool(row.id, row);
                    }, 10);
                } else if (!floor?.id && building?.id) {
                    this.props.dispatch(AppActions.clearFloorSelection(building.id));
                    this.props.dispatch(
                        MessageActions.showMessage({
                            message: `Building '${building.name}' floors unselected.`, // text or html
                            autoHideDuration: 3000, // ms
                            anchorOrigin: {
                                vertical: 'bottom', // top bottom
                                horizontal: 'right', // left center right
                            },
                            variant: 'success', // success error info warning null
                        })
                    );

                    setTimeout(() => {
                        if (row.id) selectRowTool(row.id, row);
                    }, 10);
                } else {
                    selectRowTool(row.id, row);
                }
            } else {
                selectRowTool(row.id, row);
            }
        }
    };

    onSelectElement = (row: ISchoolElement | null): void => {
        if (row?.id) {
            if (row?.id) {
                this.mapRef?.current?.animateFeature(row.id);
            }
        } else {
            // cancel the latest drawing event and exit from this procedure
            this.cancelDrawingEvent();
        }
    };

    onCellAction(action: string, row: ISchoolElement): void {
        this.cancelDrawingEvent();
        if (action === 'edit') {
            this.editRow(row);
        } else {
            super.onCellAction(action, row);
        }
    }

    updateRegionEntity(schoolEntity: ISchoolElement, fullEntity = false, deleteGeoData = false): Promise<any> {
        const showMessage = this?.props?.onEntityUpdated;
        switch (CompositeNode.entityType(schoolEntity)) {
            case EntityType.DOOR:
                if (deleteGeoData) schoolEntity.geoLocation = null;
                return queryService
                    .updateDoor(fullEntity ? purifyEntityData(schoolEntity) : extractGeoData(schoolEntity))
                    .then((payload: any) => {
                        showMessage?.(schoolEntity, deleteGeoData);
                        this.props.dispatch(Actions.updateDoorSuccess(payload));
                    });
                break;
            case EntityType.CAMERA:
                if (deleteGeoData) schoolEntity.geoLocation = null;
                return queryService
                    .updateCamera(fullEntity ? purifyEntityData(schoolEntity) : extractGeoData(schoolEntity))
                    .then((payload: any) => {
                        showMessage?.(schoolEntity, deleteGeoData);
                        this.props.dispatch(Actions.updateCameraSuccess(payload));
                    });
                break;
            case EntityType.SPEAKER:
                if (deleteGeoData) schoolEntity.geoLocation = null;
                return queryService
                    .updateSpeaker(fullEntity ? purifyEntityData(schoolEntity) : extractGeoData(schoolEntity))
                    .then((payload: any) => {
                        showMessage?.(schoolEntity, deleteGeoData);
                        this.props.dispatch(Actions.updateSpeakerSuccess(payload));
                    });
                break;
            case EntityType.DRONE:
                if (deleteGeoData) schoolEntity.geoLocation = null;
                return queryService
                    .updateDrone(fullEntity ? purifyEntityData(schoolEntity) : extractGeoData(schoolEntity))
                    .then((payload: any) => {
                        showMessage?.(schoolEntity, deleteGeoData);
                        this.props.dispatch(Actions.updateDroneSuccess(payload));
                    });
                break;
            case EntityType.REGION:
            case EntityType.COMPOSITECAM:
                if (deleteGeoData) {
                    schoolEntity.geoBoundaries = null;
                    schoolEntity.geoLocation = null;
                }

                return queryService
                    .updateRegion(fullEntity ? purifyEntityData(schoolEntity) : extractRegionGeoData(schoolEntity))
                    .then((payload: any) => {
                        showMessage?.(schoolEntity, deleteGeoData);
                        this.props.dispatch(Actions.updateRegionSuccess(payload));
                        if ((schoolEntity as Region).type === RegionType.ZONE) {
                            console.log(`!!! show zone ${schoolEntity.name}`);
                            this.props.dispatch(AppActions.showZone(schoolEntity.id));
                        }
                    });
                break;
            case EntityType.RADIO:
                if (deleteGeoData) schoolEntity.geoLocation = null;
                return deviceRadioService
                    .updateRadio(fullEntity ? purifyEntityData(schoolEntity) : extractGeoData(schoolEntity))
                    .then((payload: any) => {
                        showMessage?.(schoolEntity, deleteGeoData);
                        this.props.dispatch(Actions.updateRadioSuccess(payload));
                    });
                break;
            case EntityType.UTILITY:
                if (deleteGeoData) schoolEntity.geoLocation = null;
                return queryService
                    .updateUtility(fullEntity ? purifyEntityData(schoolEntity) : extractGeoData(schoolEntity))
                    .then((payload: any) => {
                        showMessage?.(schoolEntity, deleteGeoData);
                        this.props.dispatch(Actions.updateUtilitySuccess(payload));
                    });
                break;
            case EntityType.SAFETY:
                if (deleteGeoData) schoolEntity.geoLocation = null;
                return queryService
                    .updateSafety(fullEntity ? purifyEntityData(schoolEntity) : extractGeoData(schoolEntity))
                    .then((payload: any) => {
                        showMessage?.(schoolEntity, deleteGeoData);
                        this.props.dispatch(Actions.updateSafetySuccess(payload));
                    });
                break;
            default:
                console.log('ERROR: Unknown entity type:', schoolEntity);
                return Promise.reject();
        }
    }

    // Customize map controls
    createControls(): Control[] {
        const controls = super.createControls();

        const searchButton = new OlButton({
            label: '<i class="material-icons" style="font-size: 14px;">room</i>',
            position: { top: 64, left: 7 },
            size: { width: 22, height: 22 },
            onClick: () => this.openSearchDialog(),
        });
        const schoolGeoSaveButton = new OlButton({
            label: '<i class="material-icons" style="font-size: 14px;">save</i>',
            position: { top: 88, left: 7 },
            size: { width: 22, height: 22 },
            onClick: () => this.saveSchoolGeo(),
        });
        controls.push(searchButton);
        controls.push(schoolGeoSaveButton);

        return controls;
    }

    onPointerMove = (event: MapBrowserEvent<UIEvent>): boolean => {
        const entityType = CompositeNode.entityType(this.state.editingModel as CompositeNode);
        if (this.mapIsMoving || (this.state.isEditing && !CompositeNode.isDeviceEntity(entityType))) {
            return false;
        }

        return true;
    };

    createSchoolMapComponent(dataProvider: ICompositeTreeDataProvider): React.ReactNode {
        return (
            <CurrentBuildingContext.Consumer>
                {context => (
                    <SchoolMapComponent
                        id="school-map-editor"
                        ref={this.schoolMap}
                        school={this.state.currentSchool}
                        schoolRegion={dataProvider.root as Region}
                        mapRef={this.mapRef}
                        source={this.props.layersHashmap?.MAP_EDITOR.source as VectorSource<any>}
                        controls={this.controls}
                        interactions={this.interactions}
                        interactionOptions={{ doubleClickZoom: false }}
                        onMapMoved={event => this.onMapMoved(event)}
                        onMapMoveStart={event => this.onMapMoveStart(event)}
                        onMapClick={event => this.onMapClick(event)}
                        center={this.state.center}
                        zoom={this.state.zoom}
                        rotation={this.state.rotation}
                        contextMenuViews={this.props.contextMenuViews}
                        defaultContextMenuActions={this.props.defaultContextMenuActions}
                        isEditing={this.state.isEditing}
                        isOnEditMode
                        onPointerMove={event => this.onPointerMove(event)}
                        onKeyDown={event => this.onKeyDown(event)}
                        currentBuilding={context.currentBuilding}
                        setCurrentBuilding={context.setCurrentBuilding}
                        currentEntity={this.state.editingModel}
                    />
                )}
            </CurrentBuildingContext.Consumer>
        );
    }

    // customize interactions
    createInteractions(): Interaction[] {
        const interactions = super.createInteractions();

        const modifyMarkersInteraction = new Modify({
            source: this.props.layersHashmap?.MAP_EDITOR.source as VectorSource<any>,
            // Make possible to drug markers on the map only when Ctrl button pressed
            condition: (mapBrowserEvent: MapBrowserEvent<UIEvent>) => {
                const { originalEvent }: any = mapBrowserEvent;
                const allow =
                    (originalEvent.ctrlKey || originalEvent.metaKey) &&
                    !originalEvent.altKey &&
                    !originalEvent.shiftKey;
                if (allow) {
                    // modifications starting .. so hide the context menu
                    setTimeout(() => {
                        this.mapRef.current?.closeContextMenu();
                    }, 10);
                }
                return allow;
            },
        });
        modifyMarkersInteraction.on(['modifystart'], (evt: Event | BaseEvent) => {
            const modifyEvent = evt as ModifyEvent;
            const features = modifyEvent.features.getArray();
            if (features?.length) {
                const entity = features[0].getProperties()?.data;

                // start marker modification
                this.setState({
                    isEditing: true,
                    editingModel: entity,
                });
            }
        });

        modifyMarkersInteraction.on(['modifyend'], (evt: Event | BaseEvent) => {
            const modifyEvent = evt as ModifyEvent;
            const features = modifyEvent.features.getArray();
            if (features?.length) {
                const feature = features[0] as Feature<Geometry>;
                this.updateChangedFeature(feature);
            }
        });
        interactions.push(modifyMarkersInteraction);

        return interactions;
    }

    onMapClick = (event: MapBrowserEvent<UIEvent>): void => {
        const selectedMark = this.mapRef.current
            ?.getMap()
            ?.getFeaturesAtPixel(event.pixel)
            .find(f => f.getProperties().data);

        if (selectedMark)
            this.setState({
                selectedMark: selectedMark.getProperties().data,
            });
    };

    /**
     * Cancel the draw event
     */
    private cancelDrawingEvent = (state?: MapEditorComponentState): void => {
        const { currentDraw } = this.state;
        if (currentDraw) {
            currentDraw.abortDrawing();
            this.mapRef?.current?.getMap()?.removeInteraction(currentDraw);

            const stateProps = state || this.state;
            this.setState({ ...stateProps, currentDraw: null, isEditing: false, editingModel: undefined });
        }
        this.mapRef.current?.closeContextMenu();
    };

    private get showFindLocationDialog(): boolean {
        return !!this.state?.openFindLocationDialog;
    }

    private openSearchDialog = (): void => {
        this.setState(state => {
            return {
                ...state,
                openFindLocationDialog: true,
            };
        });
    };

    private closeSearchDialog(value: IGeoData | null): void {
        this.setState(prevState => {
            let { center, zoom, rotation } = prevState;

            if (value && value.lon && value.lat) {
                center = fromLonLat([value.lon, value.lat]);
                zoom = 16;
                rotation = 0;
            }

            return {
                ...prevState,
                center,
                zoom,
                rotation,
                openFindLocationDialog: false,
            };
        });
    }

    private saveSchoolGeo = (): void => {
        this.updateMapCenterFromView();

        const { currentSchool } = this.state;
        if (currentSchool) {
            currentSchool.region = this.props.schoolRegion || currentSchool.region;
            const mapCenter = this.state.center;
            (currentSchool.region as Region).geoLocation = new GeoPoint({ x: mapCenter[0], y: mapCenter[1] });
            (currentSchool.region as Region).geoZoom = this.state.zoom;
            (currentSchool.region as Region).geoRotation = this.state.rotation;
            this.props.updateRegion(currentSchool.region);
        }
    };

    private updateParentRegion = (device: ISchoolElement, parent: Region): void => {
        const updateParent = () => {
            this.props.dispatch(MessageActions.hideMessage());

            const parentPaths = pathsToRoot(device.parentIds, true);
            const parentChains = parentPaths.map(chain => chain[0].id).filter(id => !!id);
            const parentIds: string[] = [];
            device.parentIds?.forEach(parentId => {
                if (parentChains.indexOf(parentId) === -1) {
                    parentIds.push(parentId);
                }
            });
            if (parent.id && parentIds.indexOf(parent.id) === -1) {
                parentIds.push(parent.id);
            }
            device.parentIds = parentIds;
            (device as any).regions = parentIds;
            this.updateRegionEntity(device, true).then(() => {
                this.props.dispatch(Actions.loadRegionTree(true));
            });
        };

        const { updateParentBtn } = this.props.classes;
        const Message = () => (
            <div style={{ textAlign: 'center' }}>
                <div>
                    <Typography className="mx-8">Do you want to update parent region to:</Typography>
                    <ParentPathComponent entity={parent as ISchoolElement} />
                </div>
                <br />
                <Button className={updateParentBtn} size="small" variant="contained" onClick={() => updateParent()}>
                    Update parent
                </Button>
            </div>
        );

        this.props.dispatch(
            MessageActions.showMessage({
                messageComponent: Message,
                autoHideDuration: 60000, // ms
                anchorOrigin: {
                    vertical: 'bottom', // top bottom
                    horizontal: 'right', // left center right
                },
                variant: 'info', // success error info warning null
            })
        );
    };

    /**
     * Defines which tool is going to be drawn in the map
     * @param tool
     */
    private selectTool = (tool: ITool, row: ISchoolElement): void => {
        // by default, always remove the latest tool selected
        this.cancelDrawingEvent();

        // only add the tool if in fact, there's one selected
        const map = this.mapRef?.current;
        if (tool?.value) {
            const draw = new Draw({
                clickTolerance: 1,
                snapTolerance: 1,
                // FIXME: get the source from the context (based on its type), not from this global source
                source: this.props.layersHashmap?.MAP_EDITOR.source as VectorSource<any>,
                type: tool.value,
                geometryFunction: tool.geometryFunction,
                style: tool.style,
            });

            draw.on('drawend', e => {
                if (this.state.currentDraw) {
                    let newParentRegion: Region | null = null;

                    if (tool.entityType === EntityType.REGION) {
                        row.geoBoundaries = this.getRegionFeatureCoordinates(e.feature);
                    } else {
                        row.geoLocation = this.getFeatureCoordinates(e.feature);

                        const point: Point = e.feature.getGeometry() as Point;
                        const coordinates = point.getCoordinates() as Coordinate;
                        const feature = featureFromCoordinates(coordinates, map?.getMap(), true);
                        const entityType = feature?.getProperties()?.entityType;
                        if (entityType === EntityType.REGION) {
                            const parent = feature?.getProperties()?.data as Region;
                            if (parent?.id && row.parentIds?.indexOf(parent.id) === -1) {
                                newParentRegion = parent;
                            }
                        } else if (!entityType) {
                            // dropped to school territory
                            const schoolRegionId = this.props.school?.region?.id;
                            if (schoolRegionId && row.parentIds?.indexOf(schoolRegionId) === -1) {
                                newParentRegion = this.props.school?.region || null;
                            }
                        }
                    }

                    e.feature.setProperties({
                        entityType: tool.entityType,
                        data: row,
                    });

                    const completeUpdate = () => {
                        this.cancelDrawingEvent({ ...this.state, lastInsertEventTime: Date.now() });
                        if (newParentRegion) {
                            this.updateParentRegion(row, newParentRegion as Region);
                        }
                    };
                    this.updateRegionEntity(row)
                        .then(() => completeUpdate())
                        .catch(() => completeUpdate());
                }
            });

            map?.getMap()?.addInteraction(draw);
            this.setState({
                isEditing: true,
                editingModel: row,
                currentDraw: draw,
            });
        } else {
            this.cancelDrawingEvent();
        }
    };

    private getFeatureCoordinates = (feature: Feature<Geometry>): GeoPoint | null | undefined => {
        const point: Point = feature.getGeometry() as Point;
        let result = null;

        const coordinates: Coordinate = toLonLat(point.getCoordinates() as Coordinate);
        if (coordinates) {
            result = new GeoPoint({
                x: coordinates[0],
                y: coordinates[1],
            });
        }

        return result;
    };

    private getRegionFeatureCoordinates = (feature: Feature<Geometry>): GeoPolygon | null | undefined => {
        let result = null;

        const coordinates = (feature.getGeometry() as Polygon).getCoordinates()[0];
        if (coordinates) {
            result = {
                points: coordinates.map((coordinate: Coordinate) => {
                    const lonLatCoordinate = toLonLat(coordinate);
                    return new GeoPoint({
                        x: lonLatCoordinate[0],
                        y: lonLatCoordinate[1],
                    });
                }),
            };
        }

        return result;
    };

    customizeToolbar(): React.ReactNode {
        const { classes } = this.props;

        return null;
        /*
        return (
            <>
                <Tooltip title="Location Search" aria-label="location-search">
                    <IconButton
                        className={classes.menuButtonSmall}
                        size="small"
                        value="check"
                        onClick={() => this.openSearchDialog()}
                        color="secondary"
                    >
                        <FaMapMarkerAlt />
                    </IconButton>
                </Tooltip>
                <Button
                    className="whitespace-no-wrap normal-case m-2"
                    variant="contained"
                    color="secondary"
                    onClick={() => this.saveSchoolGeo()}
                >
                    <span className="hidden sm:flex">Save Map</span>
                </Button>
            </>
        );
        */
    }

    createBottomPanel(/* treeProvider: ICompositeTreeDataProvider */): React.ReactNode {
        return (
            <TabPanelView
                align={ViewStyle.horisontal}
                selectedIndex={this.state.bottomPanelIndex}
                onToggle={minimized => this.handleToggleGrid(PanelLocation.bottom, minimized)}
                onSelect={activaIndex => this.handleActiveTabChange(PanelLocation.bottom, activaIndex)}
            >
                <TabPanel name="Devices">
                    <DevicesGridComponent
                        school={this.props.school}
                        mapControl={this.mapControl}
                        columns={DEVICES_GRID}
                        onSelectElement={(row: ISchoolElement | null) => this.onSelectElement(row)}
                        customizeToolbar={() => this.customizeToolbar()}
                        cellCallback={this.cellCallback}
                    />
                </TabPanel>
                <TabPanel name="Regions">
                    <RegionsGridComponent
                        school={this.props.school}
                        mapControl={this.mapControl}
                        columns={REGIONS_GRID}
                        customizeToolbar={() => this.customizeToolbar()}
                        cellCallback={this.cellCallback}
                    />
                </TabPanel>
            </TabPanelView>
        );
    }

    render(): React.ReactNode {
        const theComponent = super.render();
        return (
            <>
                {theComponent}
                <GeoSearchDialog
                    open={this.showFindLocationDialog}
                    onClose={coordinates => this.closeSearchDialog(coordinates)}
                />
            </>
        );
    }
}

type StatePropsSelectorCreator = (state: RootState) => MapEditorComponentReduxStoreProps;
function statePropsSelectorCreator(schoolId: string): StatePropsSelectorCreator {
    return createSelector(
        Selectors.createSchoolRegionsTreeSelector(schoolId),
        Selectors.selectSelectedFloors,
        (schoolRegion: Region, selectedFloors: Region[]) => {
            return {
                schoolRegion,
                selectedFloors,
            };
        }
    );
}
const mapStateToProps = (state: RootState, ownProps: SchoolMapComponentProps): MapEditorComponentReduxStoreProps => ({
    ...statePropsSelectorCreator(ownProps.school?.id || '')(state),
    layersHashmap: state.app.map.layersHashmap,
});

function mapDispatchToProps(dispatch: Dispatch<Action>): MapEditorComponentActionProps {
    return {
        ...bindActionCreators(
            {
                querySchoolElements: Actions.querySchoolElements,
                updateRegion: Actions.updateRegion,
            },
            dispatch
        ),
        dispatch: action => dispatch(action),
    };
}

export default connect(mapStateToProps, mapDispatchToProps, null, { forwardRef: true })(
    withStyles(styles)(MapEditorComponent)
);
