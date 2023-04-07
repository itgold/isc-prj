import { createActions } from 'redux-actions';

export const { queryTags, queryTagsSuccess, queryTagsFailure } = createActions(
    'QUERY_TAGS',
    'QUERY_TAGS_SUCCESS',
    'QUERY_TAGS_FAILURE'
);
export const { addTag, updateTagSuccess, deleteTagSuccess } = createActions(
    'ADD_TAG',
    'UPDATE_TAG_SUCCESS',
    'DELETE_TAG_SUCCESS'
);
// ------------- Users ---------------
export const { queryUsersSuccess, addUser, updateUserSuccess, deleteUserSuccess } = createActions(
    'QUERY_USERS_SUCCESS',
    'ADD_USER',
    'UPDATE_USER_SUCCESS',
    'DELETE_USER_SUCCESS'
);

// ------------- Roles ---------------
export const { queryRoles, queryRolesSuccess, queryRolesFailure } = createActions(
    'QUERY_ROLES',
    'QUERY_ROLES_SUCCESS',
    'QUERY_ROLES_FAILURE'
);

// ------------- Device state codes ---------------
export const { queryDeviceStateCodes, queryDeviceStateCodesSuccess, queryDeviceStateCodesFailure } = createActions(
    'QUERY_DEVICE_STATE_CODES',
    'QUERY_DEVICE_STATE_CODES_SUCCESS',
    'QUERY_DEVICE_STATE_CODES_FAILURE'
);
