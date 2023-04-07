import { Map } from 'ol';
import { easeOut } from 'ol/easing';
import VectorLayer from 'ol/layer/Vector';
import Animation from './featureAnimation';

/**
 * Simulate an explode effect
 */
export default class ExplodeEffect extends Animation<ExplodeEffect> {
    constructor(map: Map, source: VectorLayer<any>) {
        super(map, source);

        this.effect(easeOut);
    }
}
