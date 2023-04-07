import { handleActions } from 'redux-actions';
import { Entities } from 'app/store/appState';
import { convertToStateType } from 'app/utils/domain.constants';
import * as Actions from '../../actions/app/events.actions';
import { DeviceStateUpdate } from '../../actions/app/events.actions';
import { INIT_STATE_DOMAIN_ENTITIES } from './entities.reducer';

const handleServerEvent = (state: Entities, deviceStateUpdate: DeviceStateUpdate): Entities => {
    const newState = { ...state } as any;

    // Override current state - we have always full state from server
    const newDeviceState = deviceStateUpdate.state;
    Object.freeze(newDeviceState);

    const entityId = deviceStateUpdate.deviceId;
    const type = convertToStateType(deviceStateUpdate.type);
    const oldEntity = state[type][entityId] || {};
    newState[type] = { ...state[type] };
    newState[type][entityId] = { ...oldEntity, state: newDeviceState };

    return newState;
};

/**
 * Defines the title, message and callbacks for buttons on Dialog
 */
const eventsReducer = handleActions<Entities, DeviceStateUpdate>(
    {
        [Actions.updateDeviceState.toString()]: (state, action) => {
            return handleServerEvent(state, action.payload);
        },
    },
    INIT_STATE_DOMAIN_ENTITIES
);

export default eventsReducer;
