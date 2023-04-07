import { DeviceState } from 'app/store/appState';
import { EntityType } from 'app/utils/domain.constants';
import { createActions } from 'redux-actions';

export const { updateDeviceState, newDeviceEvent } = createActions('UPDATE_DEVICE_STATE', 'NEW_DEVICE_EVENT');
export const { toggleZone, hideAllZones, showZone } = createActions('TOGGLE_ZONE', 'HIDE_ALL_ZONES', 'SHOW_ZONE');

export interface DeviceStateUpdate {
    deviceId: string;
    type: EntityType;
    state: DeviceState;
}
