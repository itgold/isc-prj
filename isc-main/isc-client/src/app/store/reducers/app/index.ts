import { combineReducers } from 'redux';
import { handleActions } from 'redux-actions';
import * as Actions from 'app/store/actions';
import dialog from './dialog.reducer';
import map from './map.reducer';
import floatingWindows from './float.window.reducer';

function toggleZone(state: string[], zoneId: string): string[] {
    const newState = state.filter(id => id !== zoneId);
    return newState.length === state.length ? [...newState, zoneId] : newState;
}

function showZone(state: string[], zoneId: string): string[] {
    const enabledZone = state.find(id => id === zoneId);
    return enabledZone ? state : [...state, zoneId];
}

const zones = handleActions<string[], string>(
    {
        [Actions.toggleZone.toString()]: (state, action) => toggleZone(state, action.payload),
        [Actions.showZone.toString()]: (state, action) => showZone(state, action.payload),
        [Actions.hideAllZones.toString()]: () => [],
    },
    [] as string[]
);

const appReducer = combineReducers({
    dialog,
    map,
    zones,
    floatingWindows,
});

export default appReducer;
