/* eslint-disable prefer-destructuring */
import { MapBrowserEvent, Map } from 'ol';
import { Coordinate } from 'ol/coordinate';
import BaseEvent from 'ol/events/Event';
import { FeatureLike } from 'ol/Feature';
import Point from 'ol/geom/Point';
import { fromLonLat } from 'ol/proj';
import { EntityType, RegionType } from 'app/utils/domain.constants';
import { Icon, Style } from 'ol/style';
import { CompositeNode } from 'app/domain/composite';

export const DEFAULT_COORDINATES = fromLonLat([-99.644788, 40.473614]);
export const DEFAULT_ZOOM = 5;

export function coordinates(c?: Coordinate): Coordinate {
    return c || DEFAULT_COORDINATES;
}

export function zoom(z?: number): number {
    return z || DEFAULT_ZOOM;
}

export type MapEventName =
    | 'change'
    | 'error'
    | 'propertychange'
    | 'postrender'
    | 'change:layergroup'
    | 'change:size'
    | 'change:target'
    | 'change:view'
    | 'singleclick'
    | 'click'
    | 'dblclick'
    | 'pointerdrag'
    | 'rendercomplete'
    | 'moveend'
    | 'movestart'
    | 'pointermove';

/**
 * Determines the structure for events for map
 */
export interface IMapEventListener {
    /**
     * The name of the event. For reference: https://openlayers.org/en/latest/apidoc/module-ol_Map-Map.html
     */
    eventName: MapEventName;
    callback: (evt: Event | BaseEvent) => unknown;
}

const PRIORITY_LIST = [
    RegionType.SCHOOL_DISTRICT,
    RegionType.SCHOOL,
    RegionType.BUILDING,
    RegionType.FLOOR,
    RegionType.UNKNOWN,
    RegionType.ROOM,
    RegionType.ELEVATOR,
    RegionType.STAIRS,
    RegionType.WALL,
    RegionType.ZONE,
];

export function featureFromCoordinates(position: Coordinate, map?: Map, regionsOnly = false): FeatureLike | undefined {
    let feature: FeatureLike | undefined;

    if (map) {
        // get the map feature for the current coordinate
        const features: FeatureLike[] | undefined = map.getFeaturesAtPixel(map.getPixelFromCoordinate(position));
        if (features) {
            feature = !regionsOnly
                ? features?.find(f => f.getProperties().entityType && f.getGeometry() instanceof Point)
                : undefined;
            if (!feature) {
                const prioritizedList = features
                    .filter(f => {
                        const entityType = CompositeNode.entityType(f.getProperties().data);

                        const matched =
                            entityType === EntityType.REGION &&
                            f.getProperties().data?.type &&
                            PRIORITY_LIST.indexOf(f.getProperties().data.type) >= 0;
                        return matched;
                    })
                    .sort((a, b) => {
                        return (
                            PRIORITY_LIST.indexOf(b.getProperties().data.type) -
                            PRIORITY_LIST.indexOf(a.getProperties().data.type)
                        );
                    });
                [feature] = prioritizedList;
            }
        }
    }

    return feature;
}

function getVectorImage(src: string, width: number, height: number, color: [number, number, number, number]) {
    const canvas = document.createElement('canvas');
    canvas.width = width;
    canvas.height = height;
    canvas.setAttribute('width', `${width}px`);
    canvas.setAttribute('height', `${height}px`);
    const ctx = canvas.getContext('2d');
    if (ctx) {
        const img = new Image();
        img.onload = () => {
            const svgCanvas = document.createElement('canvas');
            const svgCtx = svgCanvas.getContext('2d');
            svgCanvas.width = width;
            svgCanvas.height = height;
            // draw the actual svg image to temporary canvas
            svgCtx?.drawImage(img, 0, 0, width, height);

            const svgData = svgCtx?.getImageData(0, 0, canvas.width, canvas.height);
            if (svgData) {
                // get pixel data
                const { data } = svgData;
                // loop through data & update color
                for (let i = 0; i < data.length; i += 4) {
                    if (data[i + 3] !== 0) {
                        data[i] = color[0];
                        data[i + 1] = color[1];
                        data[i + 2] = color[2];
                        data[i + 3] *= color[3];
                    }
                }
                // put the data back to the temporary svg canvas
                svgCtx?.putImageData(svgData, 0, 0);
                // draw temporary canvas to the real canvas
                ctx.drawImage(svgCanvas, 0, 0, width, height);
            }
        };
        img.src = src;
    }

    return canvas;
}

// example: getWfsStyle(18, 18, `${publicPath}/assets/images/mapElements/securityCamera.svg`, [245, 76, 76]),
export function getWfsStyle(
    width: number,
    height: number,
    imgSrc: string,
    color: [number, number, number, number]
): Style {
    const img = getVectorImage(imgSrc, width, height, color);
    const style = new Style({
        image: new Icon({
            img,
            imgSize: [width, height],
        }),
        zIndex: 2,
    });
    return style;
}
