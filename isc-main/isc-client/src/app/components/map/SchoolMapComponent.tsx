import React, { LegacyRef, RefObject, createRef } from 'react';
import _ from '@lodash';

import { Collection, MapBrowserEvent, MapEvent } from 'ol';
import { Coordinate } from 'ol/coordinate';
import { Control } from 'ol/control';
import { Interaction, DefaultsOptions as InteractionDefaultsOptions } from 'ol/interaction';
import VectorSource from 'ol/source/Vector';
import MapComponent, { IMapComponent } from '@common/components/map/map';
import { IContextMenuView, IDefaultAction } from 'app/main/mapsEditor/contextMenus/ContextMenu';
import ISchoolElement from 'app/domain/school.element';
import School from 'app/domain/school';
import Region from 'app/domain/region';
import VectorLayer from 'ol/layer/Vector';
import { connect } from 'react-redux';
import { RootState } from 'app/store/appState';
import KeyboardEventInteraction from '@common/components/map/KeyboardEventInteraction';
import { EntityType, RegionType } from 'app/utils/domain.constants';
import { CompositeNode, CompositeNodesMap } from 'app/domain/composite';
import * as AppActions from 'app/store/actions/app';
import { Action, Dispatch } from 'redux';
import { buildingFloors } from './utils/MapUtils';
import MapFloorSelector from './MapFloorSelector';
import SchoolMapFloatWindows from './SchoolMapFloatWindows';
import SchoolMarkerComponent from './SchoolMarkerComponent';
import { ICurrentBuildingContext } from './MapBuildingContext';

interface SchoolMapComponentProps {
    id: string;
    school?: School;
    schoolRegion?: Region;

    center?: Coordinate;
    zoom?: number;
    rotation?: number;

    mapRef: RefObject<IMapComponent>;
    contextMenuViews?: Array<IContextMenuView>;
    defaultContextMenuActions?: Array<IDefaultAction>;

    source: VectorSource<any>;
    controls?: Collection<Control> | Control[];
    interactions?: Interaction[];
    interactionOptions?: InteractionDefaultsOptions;
    isEditing?: boolean;

    /**
     * Determines if the map is being consumed from Map Editor
     */
    isOnEditMode?: boolean;

    /**
     * Callback triggered when a map zoomed/moved/pitched
     */
    onMapMoveStart?(event: MapEvent): void;
    onMapMoved?(event: MapEvent): void;
    onMapClick?(event: MapBrowserEvent<UIEvent>): void;

    onPointerMove?(contextEvent: MapBrowserEvent<UIEvent>): boolean;
    onKeyDown?(event: React.KeyboardEvent): boolean;

    trackDeviceChanges?: boolean;
    currentEntity?: ISchoolElement;
}

interface SchoolMapComponentState {
    school?: School;
    schoolRegion?: Region;
}

/**
 * Determines the information to be supported by each layerhash
 */
export interface ILayerHash {
    /**
     * layer where the features will live on
     */
    layer: VectorLayer<any>;
    /**
     * Source of information for all markers
     */
    source: VectorSource<any>;
}

export interface ILayerHashmapChildren {
    [key: string]: ILayerHash;
}

/**
 * Determines the structure of the hashmap which serves as reference for each layer and its vector
 * For example:
 * {
 *    BUILDING: {
 *      layer: Object,
 *      source: Object
 *    }
 * }
 */
export interface ILayersHashmap {
    [key: string]: ILayerHash | ILayerHashmapChildren;
}

interface DispatchProps {
    dispatch(payload: Action): void;
}
interface ReduxStateProps {
    layerHash: ILayersHashmap;
}

type Props = SchoolMapComponentProps & ReduxStateProps & DispatchProps & ICurrentBuildingContext;

export interface IKeydownHandler {
    onKeyDownHandler(event: React.KeyboardEvent): boolean;
}

class SchoolMapComponent extends React.Component<Props, SchoolMapComponentState> implements IKeydownHandler {
    interactions?: Interaction[];

    static getDerivedStateFromProps(
        nextProps: Props,
        prevState: SchoolMapComponentState
    ): SchoolMapComponentState | null {
        if (
            !_.isEqual(prevState.school?.id, nextProps.school?.id) ||
            !_.isEqual(prevState.schoolRegion?.id, nextProps.schoolRegion?.id)
        ) {
            return {
                ...prevState,
                school: nextProps.school,
                schoolRegion: nextProps.schoolRegion,
            };
        }

        return null;
    }

    constructor(props: Props) {
        super(props);

        this.interactions = [
            ...(props.interactions || []),
            // new KeyboardEventInteraction({
            //     onKeyDown: event => {
            //         return this.onKeyDown(event);
            //     },
            // }),
        ];

        this.state = {
            school: props.school,
            schoolRegion: props.schoolRegion,
        };
    }

    componentDidMount(): void {
        this.cleanup();
    }

    onKeyDownHandler(event: React.KeyboardEvent): boolean {
        let stopPropogation = false;
        if (this.props.currentBuilding?.id) {
            const floors = buildingFloors(this.props.currentBuilding as CompositeNode);
            if (!floors?.length) {
                return false;
            }

            let selectedIndex = -1;
            for (let idx = 0; idx < floors.length; idx += 1) {
                if (floors[idx].selected) {
                    selectedIndex = idx;
                    break;
                }
            }

            let floor;
            const keyCode = event.code?.toLowerCase();
            if (keyCode === 'pageup' || keyCode === 'keyw') {
                stopPropogation = true;
                // minus
                if (selectedIndex < 0) {
                    floor = floors[floors.length - 1].floor;
                } else {
                    selectedIndex -= 1;
                    if (selectedIndex < 0) {
                        selectedIndex = -1;
                    }
                    floor = selectedIndex >= 0 ? floors[selectedIndex].floor : undefined;
                }
            } else if (keyCode === 'pagedown' || keyCode === 'keys') {
                // plus
                stopPropogation = true;
                if (selectedIndex < 0) {
                    floor = floors[0].floor;
                } else {
                    selectedIndex += 1;
                    if (selectedIndex >= floors.length) {
                        selectedIndex = -1;
                    }
                    floor = selectedIndex >= 0 ? floors[selectedIndex].floor : undefined;
                }
            } else if (keyCode.startsWith('digit')) {
                const floorIndex = parseInt(event.code.substring('digit'.length), 10);
                if (floorIndex > 0 || floorIndex <= floors.length) {
                    selectedIndex = floorIndex - 1;
                }
                floor = selectedIndex >= 0 && selectedIndex < floors.length ? floors[selectedIndex].floor : undefined;
                stopPropogation = true;
            }

            if (stopPropogation) {
                // we've got floor change
                const mapComponent = this.props?.mapRef?.current;
                mapComponent?.closeContextMenu();

                if (floor) {
                    const floorPayload: CompositeNodesMap = {};
                    floorPayload[this.props.currentBuilding?.id] = floor as CompositeNode;
                    this.props.dispatch(AppActions.setFloorSelection(floorPayload));
                } else {
                    this.props.dispatch(AppActions.clearFloorSelection(this.props.currentBuilding?.id));
                }
            }
        }

        return stopPropogation;
    }

    onKeyDown(event: React.KeyboardEvent): boolean {
        let stopPropogation = false;
        let process = true;
        if (this.props.onKeyDown) {
            process = !this.props.onKeyDown(event);
        }

        if (process) {
            stopPropogation = this.onKeyDownHandler(event);
        }

        return stopPropogation;
    }

    onPointerMove(event: MapBrowserEvent<UIEvent>): boolean {
        let process = true;
        if (this.props.onPointerMove) {
            process = this.props.onPointerMove(event);
        }

        if (process) {
            const mapComponent = this.props?.mapRef?.current;
            const buildingRegion = mapComponent
                ?.getMap()
                ?.getFeaturesAtPixel(event.pixel)
                .find(
                    f =>
                        f.getProperties().entityType === EntityType.REGION &&
                        f.getProperties().data?.type === RegionType.BUILDING
                );
            const building = buildingRegion ? ({ ...buildingRegion?.getProperties().data } as Region) : undefined;
            if (building) {
                if (this.props.currentBuilding?.id !== building.id) {
                    this.props.setCurrentBuilding(building);
                }
            } else if (this.props.currentBuilding) {
                this.props.setCurrentBuilding(undefined);
            }
        }

        return process;
    }

    /*

    /**
     *  Return all VectorLayer generated based on the data
     * @param hash
     * @returns
     */
    getLayersFromHash = (hash: ILayersHashmap): VectorLayer<any>[] => {
        // Important!!! The order of the layers is important as well as the zIndex!
        const layers: VectorLayer<any>[] = [];
        if (Object.keys(hash).length === 0) return layers;
        layers.push(hash.DEFAULT.layer as VectorLayer<any>);
        if (hash.SCHOOL) {
            layers.push(hash.SCHOOL.layer as VectorLayer<any>);
        }
        if (hash.ZONE) {
            layers.push(hash.ZONE.layer as VectorLayer<any>);
        }

        // search for all the types introduced dynamically
        Object.keys(hash)
            .filter(key => key !== 'MAP_EDITOR' && ['DEFAULT', 'SCHOOL', 'ZONE'].indexOf(key) === -1)
            .forEach(dynaKeys => {
                layers.push(hash[dynaKeys].layer as VectorLayer<any>);
            });
        return layers;
    };

    resolveLayers = (isEdit: boolean): VectorLayer<any>[] => {
        const layerMapKeys = Object.keys(this.props.layerHash).length > 0;
        if (!layerMapKeys) {
            return [];
        }

        return isEdit
            ? [this.props.layerHash.MAP_EDITOR.layer as VectorLayer<any>]
            : this.getLayersFromHash(this.props.layerHash as ILayersHashmap);
    };

    cleanup(): void {
        const mapComponent = this.props?.mapRef?.current;
        mapComponent?.closeContextMenu();
        mapComponent?.updateFeatures(this.props.source, []);
    }

    render(): React.ReactNode {
        const mapComponent = this.props?.mapRef?.current;
        const defaultContextMenuActions: Array<IDefaultAction> = this.props.defaultContextMenuActions || [];
        const layers = this.resolveLayers(this.props.isOnEditMode || false);

        return (
            <MapComponent
                layers={layers}
                ref={this.props.mapRef}
                id={this.props.id}
                controls={this.props.controls}
                interactions={this.interactions}
                interactionOptions={this.props.interactionOptions}
                contextMenuViews={this.props.contextMenuViews}
                defaultContextMenuActions={defaultContextMenuActions}
                onMapMoved={event => this.props.onMapMoved?.(event)}
                onMapMoveStart={event => this.props.onMapMoveStart?.(event)}
                onMapClick={event => {
                    if (this.props.onMapClick) {
                        this.props.onMapClick(event);
                    }
                }}
                onPointerMove={event => this.onPointerMove(event)}
                center={this.props.center}
                zoom={this.props.zoom}
                rotation={this.props.rotation}
                isEditing={this.props.isEditing}
            >
                {mapComponent && (
                    <SchoolMarkerComponent
                        trackDeviceChanges={this.props.trackDeviceChanges}
                        data={this.state.schoolRegion as ISchoolElement}
                        map={mapComponent}
                        source={this.props.source}
                        isOnEditMode={this.props.isOnEditMode}
                        currentEntity={this.props.currentEntity}
                    />
                )}
                <SchoolMapFloatWindows map={mapComponent} />
                <MapFloorSelector building={this.props.currentBuilding} map={mapComponent} />
            </MapComponent>
        );
    }
}

const mapStateToProps = (state: RootState): ReduxStateProps => ({
    layerHash: state.app.map.layersHashmap,
});

function mapDispatchToProps(dispatch: Dispatch<Action>): DispatchProps {
    return {
        dispatch: action => dispatch(action),
    };
}

export default connect(mapStateToProps, mapDispatchToProps, null, { forwardRef: true })(SchoolMapComponent);
