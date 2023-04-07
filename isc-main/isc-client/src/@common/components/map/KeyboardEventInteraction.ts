/* eslint-disable */
import { MapBrowserEvent } from 'ol';
import { Interaction } from 'ol/interaction';

interface KeyboardEventInteractionProps {
    onKeyDown: (event: React.KeyboardEvent) => boolean;
}

/* eslint-disable */
export default class KeyboardEventInteraction extends Interaction {
    props: KeyboardEventInteractionProps;

    constructor(props: KeyboardEventInteractionProps) {
        super();

        this.props = props;
    }

    handleEvent(mapBrowserEvent: MapBrowserEvent<KeyboardEvent>): boolean {
        let stopEvent = false;

        if (mapBrowserEvent.type === 'keypress' || mapBrowserEvent.type === 'keydown') {
            const keyEvent = mapBrowserEvent.originalEvent;

            stopEvent = this.props.onKeyDown
                ? this.props.onKeyDown((keyEvent as unknown) as React.KeyboardEvent)
                : false;

            // add other keys
            if (stopEvent) {
                keyEvent.preventDefault();
                keyEvent.stopPropagation();
            }
        }

        return !stopEvent;
    }
}
