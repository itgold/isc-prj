import { Map } from 'ol';
import { upAndDown } from 'ol/easing';
import VectorLayer from 'ol/layer/Vector';
import Animation from './featureAnimation';

/**
 * Simulate an Up and Down effect
 */
export default class UpAndDownEffect extends Animation<UpAndDownEffect> {
    constructor(map: Map, source: VectorLayer<any>) {
        super(map, source);

        this.effect(upAndDown);
    }
}
