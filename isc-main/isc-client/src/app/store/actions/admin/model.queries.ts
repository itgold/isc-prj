import { createActions } from 'redux-actions';

// ------------- School Districts ---------------
export const { querySchoolDistricts, querySchoolDistrictsSuccess, querySchoolDistrictsFailure } = createActions(
    'QUERY_SCHOOL_DISTRICTS',
    'QUERY_SCHOOL_DISTRICTS_SUCCESS',
    'QUERY_SCHOOL_DISTRICTS_FAILURE'
);
export const { addSchoolDistrict, updateSchoolDistrictSuccess, deleteSchoolDistrictSuccess } = createActions(
    'ADD_SCHOOL_DISTRICT',
    'UPDATE_SCHOOL_DISTRICT_SUCCESS',
    'DELETE_SCHOOL_DISTRICT_SUCCESS'
);

// ------------- Schools ---------------
export const { querySchools, querySchoolsSuccess, querySchoolsFailure } = createActions(
    'QUERY_SCHOOLS',
    'QUERY_SCHOOLS_SUCCESS',
    'QUERY_SCHOOLS_FAILURE'
);
export const { updateSchool, updateSchoolSuccess, updateSchoolFailure } = createActions(
    'UPDATE_SCHOOL',
    'UPDATE_SCHOOL_SUCCESS',
    'UPDATE_SCHOOL_FAILURE'
);
export const { addSchool, deleteSchoolSuccess } = createActions('ADD_SCHOOL', 'DELETE_SCHOOL_SUCCESS');

export const { commonModelsLoaded } = createActions('COMMON_MODELS_LOADED');

// ------------ Events -----------------
export const { queryEvents, queryEventsSuccess, queryEventsFailure } = createActions(
    'QUERY_EVENTS',
    'QUERY_EVENTS_SUCCESS',
    'QUERY_EVENTS_FAILURE'
);
export const { queryAlerts, queryAlertsSuccess, queryAlertsFailure } = createActions(
    'QUERY_ALERTS',
    'QUERY_ALERTS_SUCCESS',
    'QUERY_ALERTS_FAILURE'
);
