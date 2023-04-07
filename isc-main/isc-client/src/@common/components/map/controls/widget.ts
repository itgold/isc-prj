/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable class-methods-use-this */
import OlBaseControl from './base.control';
import { INativeControlOptions, IOlControlOptions, OlControlOnCloseListener, OlControlOnOpenListener } from './types';

/*
Example:

const exampleButton = new OlButton({
    label: '<i class="material-icons" style="font-size: 14px;">build</i> &nbsp; Edit Tools &nbsp;',
    position: { top: 10, right: 10 },
    size: { height: 24 },
    onClick: (map: PluggableMap) => this.showSearch()
});

const text = document.createElement('span');
text.innerHTML = '== Under development ==';
const widget = new OlWidget({
    title: 'Test Popup',
    content: text,
    position: { top: 40, right: 10 },
    size: { width: 200, height: 300 },
    hidden: true
});
this.widget = widget;
*/

interface IOlWidgetParams extends IOlControlOptions {
    title?: string;
    content: HTMLElement;
    hidden?: boolean;
    hideCloseBtn?: boolean;

    onOpen?: OlControlOnOpenListener;
    onClose?: OlControlOnCloseListener;
}

export default class OlWidget extends OlBaseControl<IOlWidgetParams> {
    closeBtn?: HTMLButtonElement;

    protected createControl(options: IOlWidgetParams): INativeControlOptions {
        const element = document.createElement('div');
        element.className = `ol-unselectable ol-control isc-ol-widget${options.hidden ? ' isc-ol-hidden' : ''}`;
        if (options.position.top) element.style.top = `${options.position.top}px`;
        if (options.position.left) element.style.left = `${options.position.left}px`;
        if (options.position.bottom) element.style.bottom = `${options.position.bottom}px`;
        if (options.position.right) element.style.right = `${options.position.right}px`;

        if (options.size?.width) element.style.width = `${options.size.width}px`;
        if (options.size?.height) element.style.height = `${options.size.height}px`;

        const header = this.createHeader(options);
        const body = this.createBody(options);
        body.appendChild(this.options.content);

        element.appendChild(header);
        element.appendChild(body);

        return {
            element,
        };
    }

    protected createHeader(options: IOlWidgetParams): HTMLElement {
        const element = document.createElement('div');
        element.className = 'isc-ol-widget-header';
        if (options.title) {
            const title = document.createElement('span');
            title.className = 'isc-ol-title';
            title.innerHTML = options.title;
            element.appendChild(title);
        }
        if (!options.hideCloseBtn) {
            this.closeBtn = document.createElement('button');
            this.closeBtn.className = 'isc-ol-closebox';
            this.closeBtn.type = 'button';
            element.appendChild(this.closeBtn);
        }

        return element;
    }

    protected createBody(options: IOlWidgetParams): HTMLElement {
        const element = document.createElement('div');
        element.className = 'isc-ol-widget-body';
        return element;
    }

    protected initializeEventHandlers(): void {
        if (this.closeBtn) {
            this.closeBtn.addEventListener('click', () => this.onClose());
        }
    }

    private onClose(): void {
        this.hide();
        if (this.options.onClose) {
            this.options.onClose();
        }
    }
}
