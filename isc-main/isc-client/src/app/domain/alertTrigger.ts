import { IEntity } from './entity';

export enum AlertTriggerMatcherType {
    DATE_TIME = 'DATE_TIME',
    CUSTOM = 'CUSTOM',
}

export interface AlertTriggerMatcher {
    type: AlertTriggerMatcherType;

    body: string;

    updated?: Date;
}

export default class AlertTrigger implements IEntity {
    id?: string;

    name: string;

    active: boolean;

    processorType: string;

    matchers: AlertTriggerMatcher[];

    updated?: Date;

    constructor(rec?: AlertTrigger) {
        this.id = rec?.id;
        this.name = rec?.name || '';
        this.active = rec?.active || true;
        this.processorType = rec?.processorType || '';
        this.matchers = rec?.matchers || [];
        this.updated = rec?.updated;
    }
}
