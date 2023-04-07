import { handleActions } from 'redux-actions';
import * as Actions from 'app/store/actions/admin/dashboards';
import * as MapActions from 'app/store/actions/app/map.actions';
import { DashboardsState, MapContextStates } from 'app/store/appState';
import { IscTableContext } from 'app/components/table/IscTableContext';
import { DeviceActionPayload, DeviceActionResult } from 'app/components/map/deviceActions/IDeviceAction';

const initialState: DashboardsState = {
    mapDashboard: {},
    currentSchoolId: '',
    deviceAction: undefined,
};

const updateState = (state: DashboardsState, payload: any): DashboardsState => {
    const { schoolId } = payload;
    const contexts: MapContextStates = { ...state.mapDashboard };
    if (schoolId) {
        contexts[schoolId] = { ...payload.context } as IscTableContext;
        console.log('CONTEXT UPDATED for school', schoolId);
    }

    return {
        ...state,
        mapDashboard: contexts,
    };
};

const adminReducer = handleActions(
    {
        [Actions.updateMapDashboard.toString()]: (state, action) => updateState(state, action.payload),
        [Actions.setCurrentSchool.toString()]: (state, action) => ({
            ...state,
            currentSchoolId: (action.payload as unknown) as string,
        }),
        [MapActions.deviceAction.toString()]: (state, action) => ({
            ...state,
            deviceAction: {
                action: (action.payload as unknown) as DeviceActionPayload,
                result: undefined,
            },
        }),
        [MapActions.deviceActionSuccess.toString()]: (state, action) => ({
            ...state,
            deviceAction: {
                ...(state.deviceAction || {}),
                result: (action.payload as unknown) as DeviceActionResult,
            },
        }),
        [MapActions.deviceActionFailure.toString()]: (state, action) => ({
            ...state,
            deviceAction: {
                ...(state.deviceAction || {}),
                result: (action.payload as unknown) as DeviceActionResult,
            },
        }),
    },
    initialState
);

export default adminReducer;
