import { Feature, Map } from 'ol';
import { Icon, Style } from 'ol/style';
import { getVectorContext } from 'ol/render';
import { toSize } from 'ol/size';
import IconOrigin from 'ol/style/IconOrigin';
import RenderEvent from 'ol/render/Event';
import Geometry from 'ol/geom/Geometry';

export type Effect = (t: number) => number;

export interface StyleProviderOptions {
    effect: Effect;

    elapsedRatio?: number;
    opacity?: number;
}

export type StyleProvider = (feature: Feature<Geometry>, options?: StyleProviderOptions) => Style;

/**
 * Allow to set an icon above a mark
 * @param feature feature to receive the icon
 * @param event what event will be triggered and will be used as reference for context
 * @param image Image address
 * @param map Map to be called for re-rendering
 */
export const setIconEffect = (feature: Feature<Geometry>, event: RenderEvent, image: string, map: Map): void => {
    const vectorContext = getVectorContext(event);

    const flashGeom = feature?.getGeometry()?.clone();
    const style = new Style({
        image: new Icon({
            src: image,
            scale: 0.1,
            imgSize: toSize(500),
            anchorOrigin: IconOrigin.BOTTOM_RIGHT,
            anchor: [1.3, 0.6],
        }),
    });
    style.getImage().load();
    vectorContext.setStyle(style);
    if (flashGeom) vectorContext.drawGeometry(flashGeom);
    map.render();
};
