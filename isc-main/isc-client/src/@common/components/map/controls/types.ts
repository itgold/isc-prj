import { MapEvent, PluggableMap } from 'ol';

export interface INativeControlOptions {
    element?: HTMLElement;
    render?: (p0: MapEvent) => void;
    target?: HTMLElement | string;
}

export type OlControlClickListener = (map: PluggableMap | null | undefined) => void;
export type OlControlOnCloseListener = () => void;
export type OlControlOnOpenListener = () => void;

export interface OlControlPosition {
    top?: number;
    left?: number;
    bottom?: number;
    right?: number;
}

export interface OlControlSize {
    width?: number;
    height?: number;
}

export interface IOlControlOptions {
    position: OlControlPosition;
    size?: OlControlSize;
}
