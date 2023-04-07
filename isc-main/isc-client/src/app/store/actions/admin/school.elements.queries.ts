import { createActions } from 'redux-actions';

// ------------- Doors ---------------
export const { queryDoors, queryDoorsSuccess, queryDoorsFailure, addDoor, deleteDoorSuccess } = createActions(
    'QUERY_DOORS',
    'QUERY_DOORS_SUCCESS',
    'QUERY_DOORS_FAILURE',
    'ADD_DOOR',
    'DELETE_DOOR_SUCCESS'
);
export const { updateDoor, updateDoorSuccess, updateDoorFailure } = createActions(
    'UPDATE_DOOR',
    'UPDATE_DOOR_SUCCESS',
    'UPDATE_DOOR_FAILURE'
);

// ------------- Cameras ---------------
export const { queryCameras, queryCamerasSuccess, queryCamerasFailure, addCamera, deleteCameraSuccess } = createActions(
    'QUERY_CAMERAS',
    'QUERY_CAMERAS_SUCCESS',
    'QUERY_CAMERAS_FAILURE',
    'ADD_CAMERA',
    'DELETE_CAMERA_SUCCESS'
);

export const { updateCamera, updateCameraSuccess, updateCameraFailure } = createActions(
    'UPDATE_CAMERA',
    'UPDATE_CAMERA_SUCCESS',
    'UPDATE_CAMERA_FAILURE'
);

// ------------- Speakers ---------------
export const {
    querySpeakers,
    querySpeakersSuccess,
    querySpeakersFailure,
    addSpeaker,
    deleteSpeakerSuccess,
} = createActions(
    'QUERY_SPEAKERS',
    'QUERY_SPEAKERS_SUCCESS',
    'QUERY_SPEAKERS_FAILURE',
    'ADD_SPEAKER',
    'DELETE_SPEAKER_SUCCESS'
);

export const { updateSpeaker, updateSpeakerSuccess, updateSpeakerFailure } = createActions(
    'UPDATE_SPEAKER',
    'UPDATE_SPEAKER_SUCCESS',
    'UPDATE_SPEAKER_FAILURE'
);

// ------------- Drones ---------------
export const { queryDrones, queryDronesSuccess, queryDronesFailure, addDrone, deleteDroneSuccess } = createActions(
    'QUERY_DRONES',
    'QUERY_DRONES_SUCCESS',
    'QUERY_DRONES_FAILURE',
    'ADD_DRONE',
    'DELETE_DRONE_SUCCESS'
);

export const { updateDrone, updateDroneSuccess, updateDroneFailure } = createActions(
    'UPDATE_DRONE',
    'UPDATE_DRONE_SUCCESS',
    'UPDATE_DRONE_FAILURE'
);

// ------------- Regions ---------------
export const { addRegion, deleteRegionSuccess } = createActions('ADD_REGION', 'DELETE_REGION_SUCCESS');
export const { queryRegions, queryRegionsSuccess, queryRegionsFailure, queryRegionsDropdown } = createActions(
    'QUERY_REGIONS',
    'QUERY_REGIONS_SUCCESS',
    'QUERY_REGIONS_FAILURE',
    'QUERY_REGIONS_DROPDOWN'
);
export const { updateRegion, updateRegionSuccess, updateRegionFailure } = createActions(
    'UPDATE_REGION',
    'UPDATE_REGION_SUCCESS',
    'UPDATE_REGION_FAILURE'
);

export const { querySchoolElements, querySchoolElementsSuccess, querySchoolElementsFailure } = createActions(
    'QUERY_SCHOOL_ELEMENTS',
    'QUERY_SCHOOL_ELEMENTS_SUCCESS',
    'QUERY_SCHOOL_ELEMENTS_FAILURE'
);

// ------------- Utilities ---------------
export const {
    queryUtilities,
    queryUtilitiesSuccess,
    queryUtilitiesFailure,
    addUtility,
    deleteUtilitySuccess,
} = createActions(
    'QUERY_UTILITIES',
    'QUERY_UTILITIES_SUCCESS',
    'QUERY_UTILITIES_FAILURE',
    'ADD_UTILITY',
    'DELETE_UTILITY_SUCCESS'
);
export const { updateUtility, updateUtilitySuccess, updateUtilityFailure } = createActions(
    'UPDATE_UTILITY',
    'UPDATE_UTILITY_SUCCESS',
    'UPDATE_UTILITY_FAILURE'
);
// ------------- Safeties ---------------
export const {
    querySafeties,
    querySafetiesSuccess,
    querySafetiesFailure,
    addSafety,
    deleteSafetySuccess,
} = createActions(
    'QUERY_SAFETIES',
    'QUERY_SAFETIES_SUCCESS',
    'QUERY_SAFETIES_FAILURE',
    'ADD_SAFETY',
    'DELETE_SAFETY_SUCCESS'
);
export const { updateSafety, updateSafetySuccess, updateSafetyFailure } = createActions(
    'UPDATE_SAFETY',
    'UPDATE_SAFETY_SUCCESS',
    'UPDATE_SAFETY_FAILURE'
);

// ------------- Radios ---------------
export const { queryRadios, queryRadiosSuccess, queryRadiosFailure, addRadio, deleteRadioSuccess } = createActions(
    'QUERY_RADIOS',
    'QUERY_RADIOS_SUCCESS',
    'QUERY_RADIOS_FAILURE',
    'ADD_RADIO',
    'DELETE_RADIO_SUCCESS'
);
export const { updateRadio, updateRadioSuccess, updateRadioFailure } = createActions(
    'UPDATE_RADIO',
    'UPDATE_RADIO_SUCCESS',
    'UPDATE_RADIO_FAILURE'
);
