import { createAsyncCallSaga } from '@common/utils/sagas.utils';
import { select, put, take } from 'redux-saga/effects';
import * as Selectors from 'app/store/selectors';
import * as Actions from '../../actions/admin';
import queryService from '../../../services/query.service';
import radioDeviceService from '../../../services/device.radio.service';

// ----------- School Districts ---------------
export const watchSchoolDistrictsQueryRequest = createAsyncCallSaga(
    Actions.querySchoolDistricts,
    Actions.querySchoolDistrictsSuccess,
    Actions.querySchoolDistrictsFailure,
    payload => queryService.querySchoolDistricts(payload)
);

// ----------- Schools ---------------
export const watchSchoolsQueryRequest = createAsyncCallSaga(
    Actions.querySchools,
    Actions.querySchoolsSuccess,
    Actions.querySchoolsFailure,
    payload => queryService.querySchools(payload)
);
export const watchSchoolUpdateRequest = createAsyncCallSaga(
    Actions.updateSchool,
    Actions.updateSchoolSuccess,
    Actions.updateSchoolFailure,
    payload => queryService.updateSchool(payload)
);

// ----------- Doors ---------------
export const watchUpdateDoorRequest = createAsyncCallSaga(
    Actions.updateDoor,
    Actions.updateDoorSuccess,
    Actions.updateDoorFailure,
    payload => queryService.updateDoor(payload)
);
export const watchDoorsQueryRequest = createAsyncCallSaga(
    Actions.queryDoors,
    Actions.queryDoorsSuccess,
    Actions.queryDoorsFailure,
    payload => queryService.queryDoors(payload)
);
// ----------- Camera ---------------
export const watchUpdateCameraRequest = createAsyncCallSaga(
    Actions.updateCamera,
    Actions.updateCameraSuccess,
    Actions.updateCameraFailure,
    payload => queryService.updateCamera(payload)
);
export const watchCamerasQueryRequest = createAsyncCallSaga(
    Actions.queryCameras,
    Actions.queryCamerasSuccess,
    Actions.queryCamerasFailure,
    payload => queryService.queryCameras(payload)
);
// ----------- Drone ---------------
export const watchUpdateDroneRequest = createAsyncCallSaga(
    Actions.updateDrone,
    Actions.updateDroneSuccess,
    Actions.updateDroneFailure,
    payload => queryService.updateDrone(payload)
);
export const watchDronesQueryRequest = createAsyncCallSaga(
    Actions.queryDrones,
    Actions.queryDronesSuccess,
    Actions.queryDronesFailure,
    payload => queryService.queryDrones(payload)
);
// ----------- Region ---------------
export const watchRegionsQueryRequest = createAsyncCallSaga(
    Actions.queryRegions,
    Actions.queryRegionsSuccess,
    Actions.queryRegionsFailure,
    payload => queryService.queryRegions(payload)
);
export function* watchRegionsDropdownQueryRequest() {
    while (true) {
        yield take(Actions.queryRegionsDropdown);
        const isTreeLoaded = yield select(Selectors.isTreeLoadedSelector);
        if (!isTreeLoaded) {
            yield put(Actions.queryRegionTree);
        }
    }
}

export const watchUpdateRegionRequest = createAsyncCallSaga(
    Actions.updateRegion,
    Actions.updateRegionSuccess,
    Actions.updateRegionFailure,
    payload => queryService.updateRegion(payload)
);
// ----------- Speaker ---------------
export const watchUpdateSpeakerRequest = createAsyncCallSaga(
    Actions.updateSpeaker,
    Actions.updateSpeakerSuccess,
    Actions.updateSpeakerFailure,
    payload => queryService.updateSpeaker(payload)
);
export const watchSpeakersQueryRequest = createAsyncCallSaga(
    Actions.querySpeakers,
    Actions.querySpeakersSuccess,
    Actions.querySpeakersFailure,
    payload => queryService.querySpeakers(payload)
);

// ----------- Radio ---------------
export const watchUpdateRadioRequest = createAsyncCallSaga(
    Actions.updateRadio,
    Actions.updateRadioSuccess,
    Actions.updateRadioFailure,
    payload => queryService.updateRadio(payload)
);
export const watchRadiosQueryRequest = createAsyncCallSaga(
    Actions.queryRadios,
    Actions.queryRadiosSuccess,
    Actions.queryRadiosFailure,
    payload => radioDeviceService.queryRadios(payload)
);
