import React, { createRef, RefObject } from 'react';
import { createStyles, withStyles } from '@material-ui/core';
import { OSM, Source } from 'ol/source';
import { Collection, Feature, Map as MapImpl, MapBrowserEvent, MapEvent, Overlay, View } from 'ol';
import * as olExtent from 'ol/extent';
import { getCenter } from 'ol/extent';
import { Coordinate } from 'ol/coordinate';
import {
    defaults as interactionDefaults,
    DefaultsOptions as InteractionDefaultsOptions,
    Interaction,
} from 'ol/interaction';
import { Control, defaults as controlsDefaults, DefaultsOptions as ControlDefaultsOptions } from 'ol/control';

import BaseLayer from 'ol/layer/Base';
import { Group as LayerGroup, Tile as TileLayer } from 'ol/layer';
import ContextMenu, { IContextMenuView, IDefaultAction } from 'app/main/mapsEditor/contextMenus/ContextMenu';
import OverlayPositioning from 'ol/OverlayPositioning';
import VectorLayer from 'ol/layer/Vector';
import { FeatureLike } from 'ol/Feature';
import Point from 'ol/geom/Point';
import Style from 'ol/style/Style';
import VectorSource from 'ol/source/Vector';
import Geometry from 'ol/geom/Geometry';
import { resolveEntityTool } from 'app/components/map/markers/toolsList';
import RenderEvent from 'ol/render/Event';
import { ClassNameMap, StyleRules } from '@material-ui/styles';
import SimpleGeometry from 'ol/geom/SimpleGeometry';
import { fromExtent } from 'ol/geom/Polygon';
import LineString from 'ol/geom/LineString';
import { Stroke } from 'ol/style';
import { coordinates, featureFromCoordinates, IMapEventListener, MapEventName, zoom } from './map.utils';
import { extend } from '../../utils/dom.utils';
import UpAndDownEffect from './animation/upAndDownEffect';
import ISchoolElement from '../../../app/domain/school.element';
import { prettifyName } from '../../utils/prettyName';

const styles = (): StyleRules =>
    createStyles({
        root: {
            width: '100%',
            height: '100%',
        },
    });

/*
// ------------------------------------------
Example custom control:

customControl = function(opt_options) {
    var element = document.createElement('div');
    element.className = 'custom-control ol-unselectable ol-control';
    ol.control.Control.call(this, {
    element: element
    });
};
ol.inherits(customControl, ol.control.Control);

var map = new ol.Map({
    layers: [new ol.layer.Tile({source: new ol.source.OSM()})],
    controls: [new customControl],
    target: 'map',
    view: new ol.View({
    center: [-11000000, 4600000],
    zoom: 4
    })
});

.custom-control {
    top: 20px;
    right: 20px;
    width: 70px;
    height: 70px;
    background: no-repeat url('http://openlayers.org/en/latest/examples/resources/logo-70x70.png')
}

// ------------------------------------------
*/

export interface HighligtOptions {
    padding?: number[];
    parentId?: string;
}

interface IMapProps {
    id: string;
    center?: Coordinate;
    zoom?: number;
    rotation?: number;
    classes: Partial<ClassNameMap<string>>;
    children: React.ReactNode | React.ReactNode[];

    interactionOptions?: InteractionDefaultsOptions;
    interactions?: Collection<Interaction> | Interaction[];
    controlOptions?: ControlDefaultsOptions;
    controls?: Collection<Control> | Control[];

    overlays?: Collection<Overlay> | Overlay[];
    layers?: BaseLayer[] | Collection<BaseLayer> | LayerGroup;

    /**
     * Define whether the map is on edit mode on not
     */
    isEditing?: boolean;

    /**
     * Determines the timestamp of the last feature inserted
     */
    lastInsertEventTime?: number;

    /**
     * Add listener for a certain type of event.
     */
    mapEventsListener?: Array<IMapEventListener>;

    /**
     * Set the context menus that should be applied for the current using of the map
     */
    contextMenuViews?: Array<IContextMenuView>;

    /**
     * Beside the context menus that should be displayed for each mark, it allows to define default (or basic) actions displayed as
     * contextMenus. For example: Delete, Details, Edit, etc
     */
    defaultContextMenuActions?: Array<IDefaultAction>;

    /**
     * Callback triggered when a mark is selected
     */
    onMapClick?(event: MapBrowserEvent<UIEvent>): void;

    /**
     * Callback triggered when a map zoomed/moved/pitched
     */
    onMapMoved?(event: MapEvent): void;

    /**
     * Callback triggered when a map zoomed/moved/pitched started
     */
    onMapMoveStart?(event: MapEvent): void;

    onPointerMove?(event: MapBrowserEvent<UIEvent>): void;

    /* eslint-disable react/no-unused-prop-types */
    ref?: RefObject<IMapComponent>;
}

export interface IMapPosition {
    center: Coordinate;
    zoom: number;
    rotation: number;
}

export type OnActionComplete = (feature?: Feature<Geometry>, event?: RenderEvent) => void;

interface Rect {
    x: number;
    y: number;
    width: number;
    height: number;
}

export interface IMapComponent {
    updateFeatures(source: VectorSource<any>, features: Feature<Geometry>[]): void;
    /**
     * Removes all markers from a specified source
     * @param list array of `Feature<Geometry>`
     * @param source VectorSource which the markers lives
     */
    removeFeatures(source: VectorSource<any>, list: Feature<Geometry>[]): void;
    getView(): View | null;
    getMap(): MapImpl | undefined;
    resizeMap(): void;
    features: Feature<Geometry>[];

    animateFeature(featureId: string, callback?: OnActionComplete): void;
    createLayer(source: VectorSource<any>): VectorLayer<any>;
    createLayers(source: VectorSource<any>): VectorLayer<any>[];
    highlightFeature(featureId: string, options: HighligtOptions, callback?: OnActionComplete): void;
    closeContextMenu(): void;

    hintLine(featureId: string, action: 'create' | 'update' | 'delete', hintBoxLocation?: Rect): void;
}

export interface IMapState {
    // Defines whether the context should display or not
    showContextMenu: boolean;
    // Defines which feature from the map is selected
    selectedFeature: Feature<Geometry> | null;
}

function mapInteractions(
    extraOptions?: any,
    interactions?: Collection<Interaction> | Interaction[]
): Collection<Interaction> | Interaction[] {
    const defaultOptions = {
        altShiftDragRotate: true,
        dragPan: true,
        pinchRotate: true,
        zoomDuration: 0,
    } as InteractionDefaultsOptions;

    const customInteraction: Interaction[] = [];
    if (interactions) {
        interactions.forEach(interaction => customInteraction.push(interaction));
    }

    /*
        Default interactions:

     * {@link module:ol/interaction/DragRotate~DragRotate}
     * {@link module:ol/interaction/DoubleClickZoom~DoubleClickZoom}
     * {@link module:ol/interaction/DragPan~DragPan}
     * {@link module:ol/interaction/PinchRotate~PinchRotate}
     * {@link module:ol/interaction/PinchZoom~PinchZoom}
     * {@link module:ol/interaction/KeyboardPan~KeyboardPan}
     * {@link module:ol/interaction/KeyboardZoom~KeyboardZoom}
     * {@link module:ol/interaction/MouseWheelZoom~MouseWheelZoom}
     * {@link module:ol/interaction/DragZoom~DragZoom}
     */
    const options = extend({}, defaultOptions, extraOptions !== undefined ? extraOptions : {});
    return interactionDefaults(options).extend(customInteraction);
}

function mapControls(extraOptions?: any, controls?: Collection<Control> | Control[]): Collection<Control> | Control[] {
    const defaultOptions = {
        rotate: false,
    } as ControlDefaultsOptions;

    const customControls: Control[] = [];
    if (controls) {
        controls.forEach(control => customControls.push(control));
    }

    const options = extend({}, defaultOptions, extraOptions !== undefined ? extraOptions : {});
    return controlsDefaults(options).extend(customControls);
}

function mapOverlays(extraOptions?: any, overlays?: Collection<Overlay> | Overlay[]): Collection<Overlay> | Overlay[] {
    const customOverlays: Overlay[] = [];
    if (overlays) {
        overlays.forEach(overlay => customOverlays.push(overlay));
    }

    return customOverlays;
}

class Map extends React.Component<IMapProps, IMapState> implements IMapComponent {
    map?: MapImpl;

    contextMenuRef: RefObject<any>;

    mapLabel?: Overlay | undefined;

    contextMenu?: Overlay | undefined;

    constructor(props: IMapProps) {
        super(props);
        this.contextMenuRef = createRef<any>();

        this.state = {
            showContextMenu: false,
            selectedFeature: null,
        };
    }

    componentDidMount(): void {
        const osmLayer = new TileLayer({
            source: new OSM(),
            visible: true,
        });

        const allLayers: BaseLayer[] = [osmLayer];
        if (this.props.layers) {
            const layers = this.props.layers instanceof LayerGroup ? this.props.layers.getLayers() : this.props.layers;
            layers.forEach(layer => allLayers.push(layer));
        }

        const rotation: number = this.props.rotation ? this.props.rotation : 0.0;
        this.map = new MapImpl({
            // added mark tolerance to avoid changing the position by mistake
            moveTolerance: 20,
            interactions: mapInteractions(this.props.interactionOptions, this.props.interactions),
            controls: mapControls(this.props.controlOptions, this.props.controls),
            layers: allLayers,
            overlays: mapOverlays(this.props.overlays),
            target: this.props.id,
            view: new View({
                center: coordinates(this.props.center),
                zoom: zoom(this.props.zoom),
                rotation,
            }),
        });

        // add event listeners on the map. Events to be triggered like on 'change', 'click', 'postrender', etc.
        if (this.props.mapEventsListener && this.props.mapEventsListener.length > 0) {
            this.props.mapEventsListener.forEach(event => this.map?.on([event.eventName], event.callback));
        }

        this.map?.on('click', this.onMapClick);
        this.map?.on('moveend', (evt: MapEvent) => {
            if (this.props.onMapMoved) {
                this.props.onMapMoved(evt);
            }
        });
        this.map?.on('movestart', (evt: MapEvent) => {
            if (this.props.onMapMoveStart) {
                this.props.onMapMoveStart(evt);
            }
        });
        this.map?.on('pointermove', (event: MapBrowserEvent<UIEvent>) => {
            this.onPointerMove(event);
        });
    }

    componentDidUpdate(): void {
        const view = this.map?.getView();
        if (view) {
            if (this.props.rotation) view.setRotation(this.props.rotation);
            if (this.props.zoom) view.setZoom(this.props.zoom);
            if (this.props.center) view.setCenter(this.props.center);
        }
    }

    componentWillUnmount(): void {
        this.map?.dispose();
    }

    getView(): View | null {
        if (this.map) {
            return this.map?.getView();
        }

        return null;
    }

    getMap(): MapImpl | undefined {
        return this.map;
    }

    get layers(): VectorLayer<any>[] {
        const allLayers: VectorLayer<any>[] = [];

        let layers = this.props.layers as any;
        if (layers) {
            // If it is LayerGroup
            if (layers.getLayers) {
                layers = layers.getLayers();
            }

            // This way is handled both BaseLayer[] | Collection<BaseLayer>
            if (layers.forEach) {
                layers.forEach((layer: BaseLayer) => {
                    if (layer instanceof VectorLayer) {
                        allLayers.push(layer as VectorLayer<any>);
                    }
                });
            }
        }

        return allLayers;
    }

    get features(): Feature<Geometry>[] {
        const allFeatures: Feature<Geometry>[] = [];

        this.layers.forEach((layer: VectorLayer<any>) => {
            const source: Source = layer.getSource();
            if ((source as any).getFeatures) {
                const features: Feature<Geometry>[] = (source as any).getFeatures() || [];
                features.forEach(feature => allFeatures.push(feature));
            }
        });

        return allFeatures;
    }

    /**
     * Add features from the parent source having some data which contains geoLocation as reference
     * @param data
     */
    updateFeatures = (source: VectorSource<any>, features: Feature<Geometry>[]): void => {
        // clear all current features
        source.clear();

        // inject them by the array of elementss
        source.addFeatures(features);
    };

    /**
     * Removes all markers from a specified source
     * @param source VectorSource which the markers lives
     * @param list array of `Feature<Geometry>`
     */
    removeFeatures = (source: VectorSource<any>, featureList: Feature<Geometry>[]): void => {
        featureList.forEach(feature => feature && source.removeFeature(feature));
    };

    /**
     * Animate a specific feature according the info got from the row
     * @param featureId Feature id to animate
     */
    animateFeature = (featureId: string, callback?: OnActionComplete): void => {
        const feature = this.features.find((f: Feature<Geometry>) => f.getProperties()?.data?.id === featureId);
        this._animateFeature(feature, callback);
    };

    _animateFeature = (feature?: Feature<Geometry>, callback: OnActionComplete | undefined = undefined): void => {
        // get the current vector which is responsible to be rendering the marks
        const vectorLayer = this.resolveAnimationLayer(feature);

        if (this.map && feature && vectorLayer) {
            const toolType = feature?.getProperties()?.entityType
                ? resolveEntityTool(feature.getProperties()?.entityType)
                : null;

            const featureProperties = feature.getProperties() || {};
            featureProperties.currentAnimation?.cancel();

            const effect = new UpAndDownEffect(this.map, vectorLayer);
            feature.setProperties({
                ...featureProperties,
                currentAnimation: effect,
            });

            const animationStyle = toolType?.getAnimationStyle;
            effect
                .styleProvider(animationStyle ? animationStyle.bind(toolType) : undefined)
                .animate(feature)
                .then((event: RenderEvent) => callback && callback(feature, event));
        } else if (callback) {
            callback(feature, undefined);
        }
    };

    createLayer = (source: VectorSource<any>): VectorLayer<any> => {
        return new VectorLayer<any>({
            source,
            style: (feature: FeatureLike): Style | Style[] => {
                // returns the style according the type
                const currentTool = resolveEntityTool(feature.getProperties()?.entityType);
                return currentTool?.style || [];
            },
        });
    };

    /**
     * @returns Array of layers.
     */
    createLayers = (source: VectorSource<any>): VectorLayer<any>[] => {
        return [this.createLayer(source)];
    };

    /**
     * Highlight specific feature by id
     * @param row
     */
    highlightFeature = (featureId: string, options: HighligtOptions, callback?: OnActionComplete): void => {
        // find the element o check if the element was already added into the map
        const selectedElement = this.features.find(
            (feature: Feature<Geometry>) => feature.getProperties()?.data?.id === featureId
        );
        // if it was added to map...
        if (selectedElement) {
            const parentFeature = options.parentId
                ? this.features.find(
                      (feature: Feature<Geometry>) => feature.getProperties()?.data?.id === options.parentId
                  )
                : null;
            const geometry = parentFeature ? parentFeature.getGeometry() : selectedElement.getGeometry();

            if (geometry && this.map) {
                const view = this.map.getView();

                if (geometry instanceof Point) {
                    const center = getCenter(geometry.getExtent());
                    const viewBoxExtent = olExtent.boundingExtent([
                        [center[0] - 30, center[1] - 30],
                        [center[0] + 30, center[1] + 30],
                    ]);
                    const viewBox = fromExtent(viewBoxExtent);
                    view.fit(viewBox, {
                        duration: 800,
                    });
                } else {
                    view.fit(geometry as SimpleGeometry, {
                        duration: 800,
                        padding: options.padding ? options.padding : [60, 60, 60, 60],
                    });
                }

                this._animateFeature(selectedElement, callback);
            }
        }
    };

    hintLine = (featureId: string, action: 'create' | 'update' | 'delete', hintBoxLocation?: Rect): void => {
        const theMap = this.map;
        if (theMap) {
            const layer = this.layers.find(l => l.get('name') === 'DEFAULT') || this.layers[0];
            let lineFeature = (layer.getSource().getFeatures() || []).find(
                (f: Feature<Geometry>) => f.getProperties().hintLineFor === featureId
            );

            if (action === 'delete') {
                if (lineFeature) {
                    layer.getSource().removeFeature(lineFeature);
                }
            } else {
                const feature = this.features.find((f: Feature<Geometry>) => f.getProperties()?.data?.id === featureId);
                const geometry = feature?.getGeometry();
                const geometryCoordinates: Coordinate | undefined = geometry
                    ? getCenter(geometry.getExtent())
                    : undefined;

                const hintBox = hintBoxLocation
                    ? theMap.getCoordinateFromPixel([
                          hintBoxLocation.x + hintBoxLocation.width / 2,
                          hintBoxLocation.y + hintBoxLocation.height / 2,
                      ])
                    : null;

                if (hintBox && geometryCoordinates) {
                    let lineCoordinates = [geometryCoordinates, hintBox];
                    const style = new Style({
                        stroke: new Stroke({
                            color: '#ff7f16',
                            width: 1,
                            lineDash: [8, 5],
                            lineCap: 'butt',
                        }),
                    });

                    if (action === 'create' || !lineFeature) {
                        if (lineFeature) {
                            lineCoordinates = (lineFeature.getGeometry() as LineString).getCoordinates();
                        }

                        lineFeature = new Feature<Geometry>();
                        lineFeature.setGeometry(new LineString(lineCoordinates));
                        lineFeature.setStyle(style);
                        lineFeature.setProperties({
                            hintLineFor: featureId,
                        });

                        // add line to layer
                        const features = (layer.getSource().getFeatures() || []).filter(
                            (f: Feature<any>) => f.getProperties().hintLineFor !== featureId
                        );
                        layer.getSource().clear();
                        layer.getSource().addFeatures([...features, lineFeature]);
                    } else if (action === 'update') {
                        if (lineFeature) {
                            lineFeature.setStyle(style);
                            lineFeature.setGeometry(new LineString(lineCoordinates));
                        }
                    }
                }
            }
        }
    };

    calculateCenter = (featureId: string): Point | null => {
        const selectedElement = this.features.find(
            (feature: Feature<Geometry>) => feature.getProperties()?.data?.id === featureId
        );
        // if it was added to map...
        if (selectedElement) {
            const geometry = selectedElement.getGeometry();
            if (geometry) {
                if (geometry instanceof Point) {
                    return geometry as Point;
                }

                // const extent = geometry.getExtent();
                // const x = extent[0] + (extent[2] - extent[0]) / 2;
                // const y = extent[1] + (extent[3] - extent[1]) / 2;
                // return new Point([x, y]);
                return new Point(getCenter(geometry.getExtent()));
            }
        }

        return null;
    };

    /**
     * Handle a context menu
     */
    onMapClick = (contextEvent: MapBrowserEvent<UIEvent>): void => {
        const removeOverlay = (): void => {
            if (this.contextMenu) {
                this.map?.removeOverlay(this.contextMenu);
                this.contextMenu = undefined;
            }
        };

        const { map } = this;
        if (map && !this.props.isEditing) {
            const clickedElement = contextEvent?.originalEvent?.target as HTMLElement;
            const isContextItem = this.state.showContextMenu;

            // get the map coordinate as reference for the feature coordinate
            let mapCoordinate: Coordinate = contextEvent.coordinate;
            const aFeature = featureFromCoordinates(mapCoordinate, this.map);

            if (aFeature && !isContextItem) {
                removeOverlay();

                // shows up the context menu passing as reference the selected feature
                this.setState({
                    showContextMenu: true,
                    selectedFeature: aFeature as Feature<Geometry>,
                });
                if (aFeature?.getGeometry() instanceof Point) {
                    const point = aFeature?.getGeometry() as Point;
                    mapCoordinate = point.getCoordinates();
                }

                const contextMenu = new Overlay({
                    element: this.contextMenuRef?.current,
                    positioning: OverlayPositioning.BOTTOM_CENTER,
                    stopEvent: false,
                    offset: [0, -25],
                    position: mapCoordinate,
                });

                map.addOverlay(contextMenu);
                this.contextMenu = contextMenu;
            } else {
                const isOverlay = clickedElement?.closest('.ol-overlay-container');
                if (!isOverlay) {
                    this.setState({ showContextMenu: false });
                    removeOverlay();
                }
            }

            if (this.props.onMapClick) {
                this.props.onMapClick(contextEvent);
            }
        }
    };

    onPointerMove = (contextEvent: MapBrowserEvent<UIEvent>): void => {
        const mapCoordinate = contextEvent.coordinate;
        const feature = featureFromCoordinates(mapCoordinate, this.map, this.props.isEditing);
        if (feature) {
            this.openEntityLabel(feature as Feature<Geometry>, mapCoordinate);
        } else {
            this.closeEntityLabel();
        }

        if (this.props.onPointerMove) {
            this.props.onPointerMove(contextEvent);
        }
    };

    openEntityLabel = (feature: Feature<Geometry>, mapCoordinate: Coordinate): void => {
        const entity = feature.getProperties()?.data;
        if (!this.contextMenu && entity) {
            const labelId = 'label-info';
            let elem: HTMLElement | null;
            elem = document.getElementById(labelId);

            if (!elem) {
                elem = document.createElement('div');
                elem.setAttribute('id', labelId);
                elem.setAttribute('class', 'mapEntityLabel');
            }

            const tool = resolveEntityTool(feature.getProperties()?.entityType);
            switch (feature.getProperties()?.entityType) {
                case 'UTILITY':
                case 'SAFETY':
                    elem.innerHTML = `${prettifyName(entity.type)}: ${entity.name}`;
                    break;
                default:
                    elem.innerHTML = `${tool?.getLabel?.(entity) || entity.entityType || entity.type} ${entity.name}`;
            }

            const contextMenu = new Overlay({
                positioning: OverlayPositioning.CENTER_LEFT,
                element: elem,
                offset: [10, 0],
            });

            if (feature?.getGeometry() instanceof Point) {
                const point = feature?.getGeometry() as Point;
                mapCoordinate = point.getCoordinates();
            }

            contextMenu.setPosition(mapCoordinate);

            this?.map?.addOverlay(contextMenu);
            this.mapLabel = contextMenu;
        }
    };

    closeEntityLabel = (): void => {
        if (this.mapLabel) {
            this?.map?.removeOverlay(this.mapLabel);
            this.mapLabel = undefined;
        }
    };

    closeContextMenu = (): void => {
        this.closeEntityLabel();
        this.setState({
            showContextMenu: false,
        });
    };

    private resolveAnimationLayer(feature?: Feature<Geometry>): VectorLayer<any> | undefined {
        const tool = feature?.getProperties()?.entityType
            ? resolveEntityTool(feature.getProperties()?.entityType)
            : null;
        if (this.layers.length) {
            const model = feature?.getProperties?.()?.data as ISchoolElement;
            const layerName = tool?.getLayerName?.(model) || 'DEFAULT';
            const layer = this.layers.find(l => l.get('name') === layerName);
            return layer || this.layers[0];
        }

        return undefined;
    }

    resizeMap(): void {
        this.map?.updateSize();
    }

    render(): JSX.Element {
        const { classes } = this.props;
        const now = Date.now();
        // only display the context menu half second after adding a new element into the map
        const shouldDisplayMenu =
            this.props.lastInsertEventTime === undefined ? true : now - (this.props.lastInsertEventTime || now) > 500;

        return (
            <>
                <div ref={this.contextMenuRef}>
                    {this.state.showContextMenu && !this.props.isEditing && shouldDisplayMenu && (
                        <ContextMenu
                            contextMenuViews={this.props.contextMenuViews}
                            map={this.map}
                            selectedFeature={this.state.selectedFeature}
                            defaultContextMenuActions={this.props.defaultContextMenuActions}
                            closeContextMenu={this.closeContextMenu}
                        />
                    )}
                </div>
                <div
                    id={this.props.id}
                    role="menu"
                    tabIndex={-1}
                    className={classes.root}
                    onMouseLeave={() => this.closeEntityLabel()}
                />
                {this.props.children}
            </>
        );
    }
}

export default withStyles(styles)(Map);
