/* eslint-disable class-methods-use-this */
import React, { createRef, RefObject, LegacyRef } from 'react';
import _ from '@lodash';
import { Coordinate } from 'ol/coordinate';
import { Control } from 'ol/control';
import { DragRotateAndZoom, Interaction } from 'ol/interaction';
import VectorSource from 'ol/source/Vector';
import { IContextMenuView, IDefaultAction } from 'app/main/mapsEditor/contextMenus/ContextMenu';
import { fromLonLat } from 'ol/proj';

import { HighligtOptions, IMapComponent } from '@common/components/map/map';
import OlButton from '@common/components/map/controls/button';

import * as AppActions from 'app/store/actions/app';
import * as MessageActions from 'app/store/actions/fuse/message.actions';
import Region from 'app/domain/region';
import School from 'app/domain/school';
import storageService from 'app/services/storage.service';

import { ClassNameMap } from '@material-ui/styles';
import MapBrowserEvent from 'ol/MapBrowserEvent';
import { CompositeNode, CompositeNodesMap, CompositeType } from 'app/domain/composite';
import { Action } from 'redux';
import { MAP_STATE_COOKIE } from 'app/store/reducers/app/map.reducer';
import { TabPanel, TabPanelView } from '@common/components/layout/tabpanel';
import { ViewStyle } from '@common/components/layout/splitpanel';
import { ObjectMap, RegionType, EntityType } from 'app/utils/domain.constants';
import { MapEvent } from 'ol';
import EventBus, { UIBusEventSubscription } from 'app/utils/simpleEventBus';
import SelectInTreeEvent from 'app/store/events/SelectInTreeEvent';
import { ICellCallback } from '../table/IscTableHead';
import { IscTableContext } from '../table/IscTableContext';
import SchoolMapSplitPanel, { PanelLocation } from './SchoolMapSplitPanel';
import SchoolMapComponent, { IKeydownHandler, ILayersHashmap } from './SchoolMapComponent';
import ISchoolElement from '../../domain/school.element';
import { CompositeTreeDataProvider, ICompositeTreeDataProvider } from './providers/CompositeTreeDataProvider';
import { findParentFloor } from './utils/MapUtils';
import DeviceTreeView from './DeviceTreeView';
import { ActionMap, MapControl } from './deviceActions/IDeviceAction';
import FindOnTree from './deviceActions/FindOnTree';
import { CurrentBuildingContext } from './MapBuildingContext';

export const DEFAULT_CENTER = fromLonLat([-101.449347, 40.181145]);
export const DEFAULT_ZOOM = 5;
export const DEFAULT_ROTATION = 0.0;

export type OnHighlightComplete = () => void;

export interface SchoolMapActionProps {
    querySchoolElements(schoolId: string): void;
    dispatch(payload: Action): void;
}
export interface ReduxStoreProps {
    schoolRegion?: Region;
    selectedFloors: Region[];
    layersHashmap?: ILayersHashmap;
}

export interface DefaultProps {
    classes: Partial<ClassNameMap<string>>;
}

export interface SchoolMapComponentProps {
    pageId: string;
    school?: School;
    context: IscTableContext;

    contextMenuViews?: Array<IContextMenuView>;
    defaultContextMenuActions?: Array<IDefaultAction>;
    toolBarItems?: React.ReactNode;

    extraActions?: ActionMap;
}
type SchoolMapProps = SchoolMapComponentProps & SchoolMapActionProps & ReduxStoreProps & DefaultProps;

export interface SchoolMapState {
    showFilters: boolean;
    dataContext: IscTableContext;

    center: Coordinate;
    zoom: number;
    rotation: number;

    currentSchool?: School;
    currentSchoolRegion?: Region;
    mapDataProvider: ICompositeTreeDataProvider;

    // left panel
    leftPanelWidth: number;
    leftPanelMinimized: boolean;
    leftPanelIndex: number;
    // right panel
    rightPanelWidth: number;
    rightPanelMinimized: boolean;
    rightPanelIndex: number;
    // bottom panel
    bottomPanelWidth: number;
    bottomPanelMinimized: boolean;
    bottomPanelIndex: number;

    isEditing: boolean;
    editingModel?: ISchoolElement;
}

export default class SchoolMapBase<
    Props extends SchoolMapProps = SchoolMapProps,
    State extends SchoolMapState = SchoolMapState
> extends React.Component<Props, State> {
    mapRef: RefObject<IMapComponent>;

    schoolMap: RefObject<any>;

    controls?: Control[];

    interactions?: Interaction[];

    defaultContextMenuActions: Array<IDefaultAction>;

    cellCallback: ICellCallback;

    mapControl: MapControl;

    mapIsMoving: boolean;

    selectOnTreeSubscription?: UIBusEventSubscription;

    onKeyDownHandler: (ev: KeyboardEvent) => void;

    static getDerivedStateFromProps(nextProps: SchoolMapProps, prevState: SchoolMapState): SchoolMapState | null {
        if (
            !_.isEqual(prevState.currentSchool?.id, nextProps.school?.id) ||
            !_.isEqual(prevState.currentSchoolRegion?.id, nextProps.schoolRegion?.id)
        ) {
            const { school } = nextProps;
            if (school?.id) {
                nextProps.querySchoolElements(school.id);
            }

            return {
                ...prevState,
                currentSchool: nextProps.school,
                currentSchoolRegion: nextProps.schoolRegion,
                mapDataProvider: new CompositeTreeDataProvider(nextProps.schoolRegion as CompositeNode),
                dataContext: nextProps.context,
                center: nextProps.schoolRegion?.geoLocation
                    ? [nextProps.schoolRegion?.geoLocation.x, nextProps.schoolRegion?.geoLocation.y]
                    : DEFAULT_CENTER,
                zoom: nextProps.schoolRegion?.geoZoom || DEFAULT_ZOOM,
                rotation: nextProps.schoolRegion?.geoRotation || DEFAULT_ROTATION,
            };
        }

        return null;
    }

    constructor(props: Props & SchoolMapProps) {
        super(props);

        this.mapRef = createRef<IMapComponent>();
        this.schoolMap = createRef<any>();
        this.mapIsMoving = false;

        // Init map component
        this.controls = this.createControls();
        this.interactions = this.createInteractions();

        // Init rest
        this.defaultContextMenuActions = [];

        this.cellCallback = {
            onAction: (action: string, row: ISchoolElement): void => {
                this.onCellAction(action, row);
            },
            context: {},
        };

        const { pageId } = this.props;
        (this.state as SchoolMapState) = {
            showFilters: false,
            center: DEFAULT_CENTER,
            zoom: DEFAULT_ZOOM,
            rotation: DEFAULT_ROTATION,

            currentSchoolRegion: undefined,
            currentSchool: undefined,
            dataContext: this.props.context,
            mapDataProvider: new CompositeTreeDataProvider(undefined),

            // left panel
            leftPanelWidth: storageService.readIntProperty(`${pageId}-left-width`, 320),
            leftPanelMinimized: storageService.readBooleanProperty(`${pageId}-left-min`, true),
            leftPanelIndex: storageService.readIntProperty(`${pageId}-left-idx`, 0),
            // right panel
            rightPanelWidth: storageService.readIntProperty(`${pageId}-right-width`, 260),
            rightPanelMinimized: storageService.readBooleanProperty(`${pageId}-right-min`, true),
            rightPanelIndex: storageService.readIntProperty(`${pageId}-right-idx`, 0),
            // bottom panel
            bottomPanelWidth: storageService.readIntProperty(`${pageId}-bottom-width`, 260),
            bottomPanelMinimized: storageService.readBooleanProperty(`${pageId}-bottom-min`, false),
            bottomPanelIndex: storageService.readIntProperty(`${pageId}-bottom-idx`, 0),

            isEditing: false,
            editingModel: undefined,
        };

        const self = this;
        this.mapControl = {
            getMapComponent(): IMapComponent | undefined | null {
                return self.mapRef.current;
            },
            resetMap(): void {
                self.resetMap();
            },
            highlight(row: ISchoolElement | null, completeListener?: OnHighlightComplete): void {
                self.highlightEntity(row, completeListener);
            },
            togglePanel(location: PanelLocation, activeIndex: number, minimized: boolean): void {
                self.handleActiveTabChange(location, activeIndex);
                self.handleToggleGrid(location, minimized);
            },
        };

        this.onKeyDownHandler = this.onKeyPressed.bind(this);
    }

    componentDidMount(): void {
        super.componentDidMount?.();
        this.centralizeMap();

        if (!this.props.selectedFloors?.length) {
            const buildings = storageService.readProperty(MAP_STATE_COOKIE);
            if (buildings) {
                const selectedFloors = this.findSelectedFloors(
                    this.props.schoolRegion as CompositeNode,
                    JSON.parse(buildings)
                );
                this.props.dispatch(AppActions.setFloorSelection(selectedFloors));
            }
        }
        this.mapIsMoving = false;
        this.selectOnTreeSubscription = EventBus.subscribe(new SelectInTreeEvent(), (event: SelectInTreeEvent) => {
            const minimized = (this.state as ObjectMap).leftPanelMinimized;
            const tabIndex = (this.state as ObjectMap).leftPanelIndex;

            this.mapControl.togglePanel(PanelLocation.left, 0, false);
            if (minimized || tabIndex !== 0) {
                // In that case the DeviceTreeView component is not rendered yet so will not listen for selection event
                setTimeout(() => EventBus.post(new SelectInTreeEvent(event.nodeId)), 100);
            }
        });
        window.addEventListener('keydown', this.onKeyDownHandler);
    }

    shouldComponentUpdate(nextProps: SchoolMapProps, nextState: SchoolMapState): boolean {
        if (_.isEqual(this.state, nextState) && _.isEqual(this.props, nextProps)) {
            return false;
        }

        return true;
    }

    componentDidUpdate(): void {
        this.centralizeMap();
    }

    componentWillUnmount(): void {
        this.selectOnTreeSubscription?.unsubscribe();
        this.selectOnTreeSubscription = undefined;
        window.removeEventListener('keydown', this.onKeyDownHandler);
    }

    handleResizeEnd(panelLocation: PanelLocation, sizes: number[]): void {
        let gridHeight = -1;
        const [firstPanel, secondPanel] = sizes;
        if (PanelLocation.left === panelLocation) gridHeight = firstPanel;
        else if (PanelLocation.right === panelLocation) gridHeight = secondPanel;
        else if (PanelLocation.bottom === panelLocation) gridHeight = secondPanel;

        if (gridHeight >= 0) {
            this.setState(
                state => {
                    const newState = { ...state };
                    (newState as ObjectMap)[`${panelLocation}PanelWidth`] = gridHeight;
                    return newState;
                },
                () => {
                    storageService.updateProperty(`${this.props.pageId}-${panelLocation}-width`, `${gridHeight}`);
                    this.updateMapSize();
                }
            );
        }
    }

    handleToggleGrid(panelLocation: PanelLocation, minimized: boolean): void {
        this.setState(
            state => {
                const newState = { ...state };
                (newState as ObjectMap)[`${panelLocation}PanelMinimized`] = minimized;
                return newState;
            },
            () => {
                storageService.updateProperty(`${this.props.pageId}-${panelLocation}-min`, `${minimized}`);
                this.updateMapSize();
            }
        );
    }

    handleActiveTabChange(panelLocation: PanelLocation, tabIndex: number): void {
        this.setState(
            state => {
                const newState = { ...state };
                (newState as ObjectMap)[`${panelLocation}PanelIndex`] = tabIndex;
                return newState;
            },
            () => {
                storageService.updateProperty(`${this.props.pageId}-${panelLocation}-idx`, `${tabIndex}`);
            }
        );
    }

    handleToggleFilters(show: boolean): void {
        const map = this.mapRef?.current;
        if (map) {
            this.setState(
                state => {
                    return {
                        ...state,
                        showFilters: show,
                    };
                },
                () => map.resizeMap()
            );
        }
    }

    protected onKeyPressed(ev: KeyboardEvent): boolean {
        let stopPropogation = false;
        const event = (ev as unknown) as React.KeyboardEvent;
        if (!this.onKeyDown(event)) {
            const schoolMapComponent = this.schoolMap.current as IKeydownHandler;
            if (schoolMapComponent) {
                stopPropogation = schoolMapComponent.onKeyDownHandler(event);
            }
        }

        if (stopPropogation) {
            ev.preventDefault();
            ev.stopPropagation();
        }

        return stopPropogation;
    }

    onCellAction(action: string, row: ISchoolElement): void {
        if (action === 'highlight') {
            this.highlightEntity(row);
        } else if (action === 'findInTree') {
            new FindOnTree().execute(row as CompositeNode);
        }
    }

    onMapMoveStart = (event: MapEvent): void => {
        this.mapIsMoving = true;
    };

    onMapMoved = (event: MapEvent): void => {
        this.mapIsMoving = false;
        this.updateMapCenterFromView();
    };

    onMapClick = (event: MapBrowserEvent<UIEvent>): void => {};

    onPointerMove = (event: MapBrowserEvent<UIEvent>): boolean => {
        if (this.mapIsMoving) {
            return false;
        }

        return true;
    };

    protected onKeyDown(event: React.KeyboardEvent): boolean {
        return false;
    }

    findBuildingOrFloor = (currentRegion: CompositeNode, id: string): CompositeNode | null => {
        let node: CompositeNode | null = null;
        if (currentRegion.id === id) {
            return currentRegion;
        }

        if (currentRegion.children && Object.keys(currentRegion.children).length > 0) {
            Object.values(currentRegion.children)
                .filter((child: CompositeNode) => ['FLOOR', 'BUILDING'].indexOf(child.type as string) > -1)
                .forEach(child => {
                    if (!node) {
                        node = this.findBuildingOrFloor(child, id);
                    }
                });
        }
        return node;
    };

    findSelectedFloors = (
        currentRegion: CompositeNode,
        selectedFloors: { [key: string]: unknown }
    ): { [key: string]: unknown } => {
        const selectedFloorsObj: { [key: string]: unknown } = {};

        // first find the building from selectedFloors
        Object.entries(selectedFloors).forEach(([buildingId, persistedFloor]) => {
            const buildingObj: CompositeNode | null = this.findBuildingOrFloor(currentRegion, buildingId);
            if (buildingObj) {
                const floorObj = this.findBuildingOrFloor(buildingObj, (persistedFloor as { id: string }).id);
                selectedFloorsObj[buildingObj.id as string] = floorObj;
            }
        });
        return selectedFloorsObj;
    };

    centralizeMap = (): void => {
        const view = this.mapRef.current?.getView();
        if (view) {
            view.setCenter(this.state.center);
            view.setRotation(this.state.rotation);
            view.setZoom(this.state.zoom);
            this.updateMapSize();
        }
    };

    private updateMapSize(): void {
        setTimeout(() => this.mapRef?.current?.resizeMap(), 10);
    }

    public highlightEntity(row: ISchoolElement | null, completeListener?: OnHighlightComplete): void {
        const map = this.mapRef?.current;
        const rowId = row?.id;
        if (map && rowId) {
            this.props.dispatch(AppActions.showMapLayer(row));

            if (
                EntityType.REGION === CompositeNode.entityType(row as CompositeNode) &&
                (row as Region).type === RegionType.ZONE
            ) {
                this.props.dispatch(AppActions.showZone(rowId));
            }

            const highlight = (featureId: string, options: HighligtOptions): void => {
                setTimeout(() => {
                    map.highlightFeature(featureId, options, () => {
                        if (completeListener) {
                            completeListener();
                        }
                    });
                }, 100);
            };

            const { floor, building } = findParentFloor(row as CompositeNode, this.props.selectedFloors);
            if (floor?.id || building?.id) {
                let highlightOptions: HighligtOptions = {};
                let useParent = (row as CompositeNode).compositeType === CompositeType.LEAF;
                if (!useParent) {
                    const region = row as Region;
                    if (
                        region.type &&
                        region.type !== RegionType.SCHOOL &&
                        region.type !== RegionType.BUILDING &&
                        region.type !== RegionType.FLOOR
                    ) {
                        useParent = true;
                    }
                }

                if (useParent && floor?.id) {
                    highlightOptions = {
                        parentId: floor?.id,
                        // padding: [30, 30, 30, 30],
                    };
                }

                const selectedFloor = floor?.id ? this.props.selectedFloors?.find(f => f.id === floor?.id) : undefined;
                if (!selectedFloor && floor && building) {
                    const floorPayload: CompositeNodesMap = {};
                    floorPayload[building.id || ''] = floor as CompositeNode;

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

                    highlight(rowId, highlightOptions);
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

                    highlight(rowId, highlightOptions);
                } else {
                    highlight(rowId, highlightOptions);
                }
            } else {
                highlight(rowId, {});
            }
        }
    }

    updateMapCenter(center: Coordinate, zoom: number, rotation: number): void {
        this.setState(prevState => ({
            ...prevState,
            center,
            zoom,
            rotation,
        }));
    }

    updateMapCenterFromView(): void {
        const view = this.mapRef?.current?.getView();
        if (view) {
            this.updateMapCenter(
                view.getCenter() || DEFAULT_CENTER,
                view.getZoom() || DEFAULT_ZOOM,
                view.getRotation() || DEFAULT_ROTATION
            );
        }
    }

    resetMap(callback?: () => void): void {
        this.setState(
            prevState => ({
                ...prevState,
                center: prevState.currentSchoolRegion?.geoLocation
                    ? [prevState.currentSchoolRegion.geoLocation.x, prevState.currentSchoolRegion.geoLocation.y]
                    : DEFAULT_CENTER,
                zoom: prevState.currentSchoolRegion?.geoZoom || DEFAULT_ZOOM,
                rotation: prevState.currentSchoolRegion?.geoRotation || DEFAULT_ROTATION,
            }),
            () => {
                this.mapRef?.current?.closeContextMenu();
                this.centralizeMap();
                if (callback) {
                    callback();
                }
            }
        );
    }

    // --------------------------------------------------------------
    // Some methods can be overridden to extend the SchoolMap component
    // --------------------------------------------------------------

    /**
     * @returns Array of map controls to show.
     * */
    createControls(): Control[] {
        const controls: Control[] = [];

        const resetViewButton = new OlButton({
            label: '<i class="material-icons" style="font-size: 14px;">open_with</i>',
            position: { top: 10, right: 7 },
            size: { width: 22, height: 22 },
            onClick: () => this.resetMap(),
        });
        controls.push(resetViewButton);

        return controls;
    }

    /**
     * @returns Array of map interactions to apply.
     */
    createInteractions(): Interaction[] {
        return [new DragRotateAndZoom()]; // [selectPointerMove, selectClick];
    }

    /**
     * @returns Create SchoolMapComponent component
     */
    createSchoolMapComponent(dataProvider: ICompositeTreeDataProvider): React.ReactNode {
        return (
            <CurrentBuildingContext.Consumer>
                {context => (
                    <SchoolMapComponent
                        id="school-map"
                        ref={this.schoolMap}
                        trackDeviceChanges
                        school={this.state.currentSchool}
                        schoolRegion={dataProvider.root as Region}
                        mapRef={this.mapRef}
                        source={this.props.layersHashmap?.DEFAULT.source as VectorSource<any>}
                        controls={this.controls}
                        interactions={this.interactions}
                        onMapMoved={event => this.onMapMoved(event)}
                        onMapMoveStart={event => this.onMapMoveStart(event)}
                        onMapClick={event => this.onMapClick(event)}
                        onPointerMove={event => this.onPointerMove(event)}
                        onKeyDown={event => this.onKeyDown(event)}
                        center={this.state.center}
                        zoom={this.state.zoom}
                        rotation={this.state.rotation}
                        contextMenuViews={this.props.contextMenuViews}
                        defaultContextMenuActions={this.props.defaultContextMenuActions}
                        currentBuilding={context.currentBuilding}
                        setCurrentBuilding={context.setCurrentBuilding}
                    />
                )}
            </CurrentBuildingContext.Consumer>
        );
    }

    createLeftPanel(treeProvider: ICompositeTreeDataProvider): React.ReactNode {
        return (
            <TabPanelView
                align={ViewStyle.vertical}
                selectedIndex={this.state.leftPanelIndex}
                onToggle={minimized => this.handleToggleGrid(PanelLocation.left, minimized)}
                onSelect={activeIndex => this.handleActiveTabChange(PanelLocation.left, activeIndex)}
            >
                <TabPanel name="Overview">
                    <DeviceTreeView
                        schoolId={this.state.currentSchool?.id}
                        map={this.mapControl}
                        extraActions={this.props.extraActions}
                    />
                </TabPanel>
            </TabPanelView>
        );
    }

    createBottomPanel(treeProvider: ICompositeTreeDataProvider): React.ReactNode {
        return null;
    }

    createRightPanel(treeProvider: ICompositeTreeDataProvider): React.ReactNode {
        // return (
        //     <TabPanelView
        //          horizontalAlign="right"
        //          align={ViewStyle.vertical}
        //          onToggle={minimized => this.handleToggleGrid(PanelLocation.right, minimized)}
        //      >
        //         <TabPanel name="Panel #1">Panel #1</TabPanel>
        //         <TabPanel name="Panel #2">Panel #2</TabPanel>
        //     </TabPanelView>
        // );

        return null;
    }

    /**
     * Render the component
     */
    render(): React.ReactNode {
        return (
            <SchoolMapSplitPanel
                onResize={(panelLocation, sizes) => this.handleResizeEnd(panelLocation, sizes)}
                // central map area
                mapPanel={this.createSchoolMapComponent(this.state.mapDataProvider)}
                // left panel
                leftPanel={this.createLeftPanel(this.state.mapDataProvider)}
                leftPanelWidth={this.state.leftPanelWidth}
                leftPanelMinimized={this.state.leftPanelMinimized}
                // bottom panel
                bottomPanel={this.createBottomPanel(this.state.mapDataProvider)}
                bottomPanelHeight={this.state.bottomPanelWidth}
                bottomPanelMinimized={this.state.bottomPanelMinimized}
                // right panel
                rightPanel={this.createRightPanel(this.state.mapDataProvider)}
                rightPanelWidth={this.state.rightPanelWidth}
                rightPanelMinimized={this.state.rightPanelMinimized}
            />
        );
    }
}
