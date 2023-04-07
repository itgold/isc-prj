/* eslint-disable prefer-destructuring */
import { Map, Feature } from 'ol';
import RenderEvent from 'ol/render/Event';
import { getVectorContext } from 'ol/render';
import { EventsKey } from 'ol/events';
import { easeOut } from 'ol/easing';
import { unByKey } from 'ol/Observable';
import { Circle, Stroke, Style } from 'ol/style';
import { Color } from 'ol/color';
import { ColorLike } from 'ol/colorlike';
import VectorLayer from 'ol/layer/Vector';
import Geometry from 'ol/geom/Geometry';
import Point from 'ol/geom/Point';
import { Effect, StyleProvider, StyleProviderOptions } from './animation.utils';

const ANIMATION_DURATION = 2000;

export default class Animation<T> {
    _map: Map;

    _source: VectorLayer<any>;

    _effect?: Effect;

    _styleProvider?: StyleProvider;

    _color?: Color | ColorLike;

    _canceled = false;

    constructor(map: Map, source: VectorLayer<any>) {
        this._map = map;
        this._source = source;
    }

    effect(effect: Effect): T {
        this._effect = effect;
        return (this as unknown) as T;
    }

    styleProvider(styleProvider?: StyleProvider): T {
        this._styleProvider = styleProvider;
        return (this as unknown) as T;
    }

    animate(feature: Feature<Geometry>, duration: number = ANIMATION_DURATION): Promise<RenderEvent> {
        return new Promise<RenderEvent>(resolve => {
            const start = +new Date();
            let listenerKey: EventsKey | EventsKey[]; // to remove the listener after the duration

            // This is original animate function !!!
            const animateInternal = (event: RenderEvent): void => {
                // canvas context where the effect will be drawn
                const vectorContext = getVectorContext(event);
                const { frameState } = event;

                // create a clone of the original ol.Feature
                // on each browser frame a new style will be applied
                const flashGeom = feature.getGeometry()?.clone();
                const elapsed = (frameState?.time || start) - start;
                const elapsedRatio = elapsed / duration;
                const opacity = this.currentEffect(1 - elapsedRatio);

                // you can customize here the style
                // like color, width
                const style = this.currentStyleProvider(feature, { effect: this.currentEffect, elapsedRatio, opacity });
                vectorContext.setStyle(style);
                if (flashGeom) vectorContext.drawGeometry(flashGeom);
                if (elapsed > duration || this._canceled) {
                    // stop the effect
                    unByKey(listenerKey);

                    resolve(event);
                    return;
                }
                // tell OL3 to continue postcompose animation
                this._map.render();
            };

            listenerKey = this._source.on('postrender', animateInternal);
            this._map.render();
        });
    }

    cancel(): void {
        this._canceled = true;
    }

    private get currentEffect(): Effect {
        return this._effect || easeOut;
    }

    private get currentStyleProvider(): StyleProvider {
        const defaultProvider = (feature: Feature<Geometry>, options?: StyleProviderOptions): Style => {
            // radius will be 5 at start and 30 at end.
            const effect = this.currentEffect;
            const elapsedRatio = options?.elapsedRatio || 1;
            const radius = effect(elapsedRatio) * 25 + 5;
            const opacity = effect(1 - elapsedRatio);

            return new Style({
                image: new Circle({
                    radius,
                    stroke: new Stroke({
                        color: [51, 51, 51, opacity],
                        width: 0.25 + opacity,
                    }),
                }),
            });
        };

        return this._styleProvider || defaultProvider;
    }
}
