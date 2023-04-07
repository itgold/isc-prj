import { ContextMenuAction } from '@common/components/layout/TreePanel';
import { IMapComponent } from '@common/components/map/map';
import { CompositeNode } from 'app/domain/composite';
import ISchoolElement from 'app/domain/school.element';
import { EntityType } from 'app/utils/domain.constants';
import { OnHighlightComplete } from '../SchoolMapBase';
import { PanelLocation } from '../SchoolMapSplitPanel';

export interface MapControl {
    getMapComponent(): IMapComponent | undefined | null;

    resetMap(): void;
    highlight(row: ISchoolElement | null, completeListener?: OnHighlightComplete): void;
    togglePanel(location: PanelLocation, activeIndex: number, minimized: boolean): void;
}

export default interface IDeviceAction extends ContextMenuAction {
    execute: (device: CompositeNode, map?: MapControl) => void;
    requireConfirmation?: () => boolean;
}

export type ActionMap = {
    [key: string]: IDeviceAction[] | null;
};

export interface DeviceActionPayload {
    entityType: EntityType;
    deviceId: string;
    action: string;
}

export interface DeviceActionError {
    code: string;
    message?: string;
}

export enum ActionResult {
    SUCCESS = 'SUCCESS',
    WARNING = 'WARNING',
    FAILURE = 'FAILURE',
}

export interface DeviceActionResult {
    status: ActionResult;
    errors?: DeviceActionError[];
}
