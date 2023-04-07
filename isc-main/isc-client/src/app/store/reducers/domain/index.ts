import { combineReducers } from 'redux';
import { Entities, Composites, SchoolEvents, RootState, EntityMap, Alerts } from 'app/store/appState';
import chainReducers from '@common/utils/chain.reducers';

import { handleActions } from 'redux-actions';

import * as Actions from 'app/store/actions';
import { SearchResult } from 'app/domain/search';
import DeviceEvent, { Alert, isAlert, resolveAlertType, TYPE_ALERT } from 'app/domain/deviceEvent';
import { EMPTY_LIST, EntityType } from 'app/utils/domain.constants';
import CodeDictionary from 'app/domain/codeDictionary';
import compositeEntitiesReducer, { INIT_STATE_DOMAIN_ENTITIES } from './entities.reducer';
import compositeTreeReducer from './tree.reducer';
import deviceStateReducer from './state.events.reducer';

const domainFlagsReducer = handleActions<boolean, any>(
    {
        [Actions.commonModelsLoaded.toString()]: (state, action) => action.payload as boolean,
    },
    false
);

const domainLoadErrorReducer = handleActions<boolean, any>(
    {
        [Actions.queryRegionTreeFailure.toString()]: () => true,
    },
    false
);

const DEFAULT_STATE: SchoolEvents = {
    currentPage: null,
    newEvents: [],
};
const DEFAULT_ALERTS_STATE: Alerts = {
    currentPage: null,
    newEvents: [],
};

function handleNewDeviceEvent(state: SchoolEvents, newEvent: DeviceEvent): SchoolEvents {
    const alertType = resolveAlertType(newEvent);
    if (alertType) newEvent.deviceType = alertType;

    return {
        ...state,
        newEvents: [newEvent, ...state.newEvents],
    };
}
function handleNewDeviceAlertEvent(state: Alerts, newEvent: DeviceEvent): Alerts {
    const newAlert = isAlert(newEvent) ? newEvent : undefined;
    return newAlert
        ? {
              ...state,
              newEvents: [newAlert, ...state.newEvents],
          }
        : state;
}

const deviceCodesReducer = handleActions<EntityMap<CodeDictionary>, any>(
    {
        [Actions.queryDeviceStateCodesSuccess.toString()]: (state, action) => {
            return action.payload.reduce(
                (result: EntityMap<CodeDictionary>, deviceCodeTuple: { name: string; value: CodeDictionary }) => {
                    return {
                        ...result,
                        [deviceCodeTuple.name]: deviceCodeTuple.value,
                    };
                },
                undefined
            );
        },
    },
    {}
);

const eventsReducer = handleActions<SchoolEvents, any>(
    {
        [Actions.queryEvents.toString()]: () => ({
            currentPage: null,
            newEvents: [],
        }),
        [Actions.queryEventsSuccess.toString()]: (state, action) => ({
            currentPage: action.payload as SearchResult<DeviceEvent>,
            newEvents: [],
        }),
        [Actions.newDeviceEvent.toString()]: (state, action) =>
            handleNewDeviceEvent(state, action.payload as DeviceEvent),
    },
    DEFAULT_STATE
);
const alertsReducer = handleActions<Alerts, any>(
    {
        [Actions.queryAlerts.toString()]: () => ({
            currentPage: null,
            newEvents: [],
        }),
        [Actions.queryAlertsSuccess.toString()]: (state, action) => ({
            currentPage: action.payload as SearchResult<Alert>,
            newEvents: [],
        }),
        [Actions.newDeviceEvent.toString()]: (state, action) =>
            handleNewDeviceAlertEvent(state, action.payload as DeviceEvent),
    },
    DEFAULT_ALERTS_STATE
);

export default combineReducers<Composites>({
    commonLoaded: domainFlagsReducer,
    domainLoadError: domainLoadErrorReducer,
    root: compositeTreeReducer,
    entities: chainReducers<Entities, any>(INIT_STATE_DOMAIN_ENTITIES, compositeEntitiesReducer, deviceStateReducer),
    events: eventsReducer,
    alerts: alertsReducer,
    deviceCodes: deviceCodesReducer,
});
