import OlBaseControl from './base.control';
import { INativeControlOptions, OlControlClickListener, IOlControlOptions } from './types';

interface IOlButtonParams extends IOlControlOptions {
    label?: string;
    onClick?: OlControlClickListener;
}

export default class OlButton extends OlBaseControl<IOlButtonParams> {
    button?: HTMLButtonElement;

    protected createControl(options: IOlButtonParams): INativeControlOptions {
        this.button = document.createElement('button');
        this.button.type = 'button';

        if (options.size?.width) this.button.style.width = `${options.size.width}px`;
        else this.button.style.width = 'auto';
        if (options.size?.height) this.button.style.height = `${options.size.height}px`;

        this.button.style.padding = '4px';
        this.button.innerHTML = this.options.label ? this.options.label : '???';

        const element = document.createElement('div');
        element.className = 'ol-unselectable ol-control';

        if (options.position.top) element.style.top = `${options.position.top}px`;
        if (options.position.left) element.style.left = `${options.position.left}px`;
        if (options.position.bottom) element.style.bottom = `${options.position.bottom}px`;
        if (options.position.right) element.style.right = `${options.position.right}px`;

        element.appendChild(this.button);

        return {
            element,
        };
    }

    protected initializeEventHandlers(): void {
        if (this.button) {
            this.button.addEventListener('click', () => this.click());
        }
    }

    click() {
        if (this.options.onClick) {
            if (this.button) this.button.blur();
            const elements = document.getElementsByTagName('canvas');
            if (elements && elements.length) {
                const viewport = elements[0] as HTMLElement;
                viewport.focus();
            }

            this.options.onClick(this.getMap());
            // setTimeout(() => {
            //     debugger;
            //     if (buttonEl) buttonEl.blur();
            //     const elements = document.getElementsByTagName('canvas');
            //     if (elements && elements.length) {
            //         const viewport = elements[0] as HTMLElement;
            //         viewport.focus();
            //     }
            // }, 10);
        }
    }
}
