import { createActions } from 'redux-actions';

export const { setUserData, updateUserData, removeUserData } = createActions(
    'SET_USER_DATA',
    'UPDATE_USER_DATA',
    'REMOVE_USER_DATA'
);
export const { updateUserSettings, updateUserShortcuts, userLoggedOut } = createActions(
    'UPDATE_USER_SETTINGS',
    'UPDATE_USER_SHORTCUTS',
    'USER_LOGGED_OUT'
);
