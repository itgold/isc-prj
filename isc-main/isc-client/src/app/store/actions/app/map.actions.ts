import { createActions } from 'redux-actions';

export const { setFloorSelection, clearFloorSelection, setMapLayers, showMapLayer } = createActions(
    'SET_FLOOR_SELECTION',
    'CLEAR_FLOOR_SELECTION',
    'SET_MAP_LAYERS',
    'SHOW_MAP_LAYER'
);

export const { deviceAction, deviceActionSuccess, deviceActionFailure } = createActions(
    'DEVICE_ACTION',
    'DEVICE_ACTION_SUCCESS',
    'DEVICE_ACTION_FAILURE'
);

export const { openFloatingWindow, closeFloatingWindow } = createActions(
    'OPEN_FLOATING_WINDOW',
    'CLOSE_FLOATING_WINDOW'
);
