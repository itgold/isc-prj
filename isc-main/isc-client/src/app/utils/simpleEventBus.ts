export interface UIBusEvent {
    name: () => string;
}

export type UIBusEventListener<E extends UIBusEvent> = (event: E) => void;

export interface UIBusEventSubscription {
    unsubscribe: () => void;
}

class SimpleEventBus {
    subscribe = <E extends UIBusEvent>(event: E, listener: UIBusEventListener<E>): UIBusEventSubscription => {
        const eventType = (event.name() as unknown) as keyof WindowEventMap;
        const eventListener = (customEvent: any): void => {
            listener(customEvent.detail);
        };
        window.addEventListener(eventType, eventListener);

        return {
            unsubscribe: (): void => {
                window.removeEventListener(eventType, eventListener);
            },
        };
    };

    post = (event: UIBusEvent): void => {
        const customEvent = new CustomEvent(event.name(), { detail: event });
        window.dispatchEvent(customEvent);
    };
}

const EventBus = new SimpleEventBus();
export default EventBus;
