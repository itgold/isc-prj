import { DeviceState } from 'app/store/appState';
import { EntityType } from 'app/utils/domain.constants';
import { IEntity } from './entity';

export interface EventPayload extends Record<string, string | number | boolean | DeviceState | undefined> {
    type: EntityType | string;
}

export interface DeviceEventPayload extends EventPayload {
    deviceId?: string;
    code: string;
    description: string;
    state?: DeviceState;
}

export interface ApplicationEvent<T extends EventPayload> extends IEntity {
    eventId: string;
    correlationId: string;
    referenceId?: string;
    type?: string;

    eventTime: Date;
    receivedTime: Date;

    payload?: T;

    origin?: string;
    schoolId?: string;
    districtId?: string;
}

export default interface DeviceEvent extends ApplicationEvent<DeviceEventPayload> {
    deviceId?: string;
    deviceType?: string;
}

export interface AlertEvent extends DeviceEvent {
    type?: string;
}

export enum AlertSeverity {
    MINOR = 'MINOR',
    REPORTABLE = 'REPORTABLE',
    MAJOR = 'MAJOR',
    CRITICAL = 'CRITICAL',
    DISASTER = 'DISASTER',
}
export enum AlertStatus {
    ACTIVE = 'ACTIVE',
    MUTED = 'MUTED',
    ACKED = 'ACKED',
    IGNORE = 'IGNORE',
}
export interface Alert extends IEntity {
    triggerId: string;
    deviceId?: string;
    deviceType: EntityType;
    severity: AlertSeverity;
    count: number;
    status: AlertStatus;
    eventId?: string;
    schoolId: string;
    districtId: string;
    code: string;
    description: string;
    updated: Date;
}

export const TYPE_ALERT = 'ALERT';
export const TYPE_ALERT_ACK = 'ALERT ACK';
export const TYPE_ALERT_IGNORE = 'ALERT DEL';

export interface AlertUpdateRequest {
    alertId: string;
    alertStatus?: AlertStatus;
    alertSeverity?: AlertSeverity;
    notes?: string;
}

export function resolveAlertType(event: any): string | undefined {
    const alertType = event.type || event.eventPath;
    if (alertType?.startsWith('alert.incrementalUpdate.')) {
        if (alertType.indexOf('.newAlert') > 0) {
            return TYPE_ALERT;
        }
        if (alertType.indexOf('.ack') > 0) {
            return TYPE_ALERT_ACK;
        }
        if (alertType.indexOf('.ignore') > 0) {
            return TYPE_ALERT_IGNORE;
        }
    }

    return undefined;
}

export function isAlert(event: any): boolean {
    const alertType = resolveAlertType(event);
    return (
        !!alertType ||
        event.deviceType === TYPE_ALERT ||
        event.deviceType === TYPE_ALERT_ACK ||
        event.deviceType === TYPE_ALERT_IGNORE
    );
}
