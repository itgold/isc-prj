import { Control } from 'ol/control';
import { INativeControlOptions, IOlControlOptions } from './types';

export default abstract class OlBaseControl<O extends IOlControlOptions> extends Control {
    element: HTMLElement;

    constructor(protected options: O) {
        super({});

        const controlOptions = this.createControl(options);
        this.element = controlOptions.element ? controlOptions.element : document.createElement('span');

        Control.call(this, controlOptions);
        this.initializeEventHandlers();
    }

    protected abstract createControl(options: O): INativeControlOptions;

    protected abstract initializeEventHandlers(): void;

    show(): void {
        this.element.classList.remove('isc-ol-hidden');
    }

    hide(): void {
        this.element.classList.add('isc-ol-hidden');
    }

    toggle(): void {
        this.element.classList.toggle('isc-ol-hidden');
    }
}
