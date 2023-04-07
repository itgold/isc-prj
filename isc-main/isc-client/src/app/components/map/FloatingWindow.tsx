import { Rect } from '@common/components/layout/window/IscWindow';
import { EntityType } from 'app/utils/domain.constants';

export interface FloatingWindow {
    entityType: EntityType;
    entityId: string;
    data: unknown;

    initialWidth?: number;
    initialHeight?: number;
    initialTop?: number;
    initialLeft?: number;
}

export interface FloatingWindowProps {
    entityId: string;
    initialTop: number;
    initialLeft: number;
    initialWidth: number;
    initialHeight: number;
    data: unknown;

    onClose?: () => void;
    onMove?: (currentFrame: Rect) => void;
    onResize?: (currentFrame: Rect) => void;

    // This is special flag which used to enforce re-render of the window component
    updateFlag: number;
}
