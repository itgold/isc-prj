import { createAsyncCallSaga } from '@common/utils/sagas.utils';
import { DeviceActionPayload, DeviceActionResult } from 'app/components/map/deviceActions/IDeviceAction';
import queryService from 'app/services/query.service';
import * as MessageActions from 'app/store/actions/fuse/message.actions';
import * as Actions from 'app/store/actions/admin';
import * as MapActions from 'app/store/actions/app/map.actions';
import { select, put, take, call, fork } from 'redux-saga/effects';

/**
 * Incremental school entities load.
 * Note: this Saga left here for the future to avoid loading ALL entities on the page load time but load them by school
 *
 * @param schoolId School id
 */
async function querySchoolElements(schoolId: string): Promise<void> {
    console.log('!!! INCREMENTAL SCHOOL ENTITIES optimization is not implemented !!!', schoolId);

    /*
    if (schoolId) {
        const payload = gql`
            query SchoolEntities($schoolId: String, $page: PageRequest, $sort: [SortOrder]) {
                regionsBySchool(schoolId: $schoolId, page: $page, sort: $sort) {
                    ...regionFragment
                }

                doorsBySchool(schoolId: $schoolId, page: $page, sort: $sort) {
                    ...doorFragment
                }

                speakersBySchool(schoolId: $schoolId, page: $page, sort: $sort) {
                    ...speakerFragment
                }

                camerasBySchool(schoolId: $schoolId, page: $page, sort: $sort) {
                    ...cameraFragment
                }

                dronesBySchool(schoolId: $schoolId, page: $page, sort: $sort) {
                    ...droneFragment
                }
            }
            ${fragments.regionFragment}
            ${fragments.doorFragment}
            ${fragments.cameraFragment}
            ${fragments.speakerFragment}
            ${fragments.droneFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    schoolId,
                    page: {
                        page: 0,
                        size: 1000,
                    },
                    sort: [
                        {
                            property: 'id',
                            direction: 'ASC',
                        },
                    ],
                },
                query: payload,
            })
            .then(response => response.data)
            .then(serverData => {
                const doors: any[] = [];
                serverData.doorsBySchool?.forEach((d: any) => {
                    doors.push({
                        ...d,
                        id: resolveId(d),
                        status: d.status || DEACTIVATED,
                        entityType: EntityType.DOOR,
                    });
                });

                const cameras: any[] = [];
                serverData.camerasBySchool?.forEach((d: any) => {
                    cameras.push({
                        ...d,
                        id: resolveId(d),
                        status: d.status || DEACTIVATED,
                        entityType: EntityType.CAMERA,
                    });
                });

                const speakers: any[] = [];
                serverData.speakersBySchool?.forEach((d: any) => {
                    speakers.push({
                        ...d,
                        id: resolveId(d),
                        status: d.status || DEACTIVATED,
                        entityType: EntityType.SPEAKER,
                    });
                });

                const drones: any[] = [];
                serverData.dronesBySchool?.forEach((d: any) => {
                    drones.push({
                        ...d,
                        id: resolveId(d),
                        status: d.status || DEACTIVATED,
                        entityType: EntityType.DRONE,
                    });
                });

                const regions: any[] = [];
                serverData.regionsBySchool?.forEach((d: any) => {
                    regions.push({
                        ...d,
                        id: resolveId(d),
                        name: d.name,
                        status: d.status || DEACTIVATED,
                        entityType: EntityType.REGION,
                    });
                });

                return {
                    schoolId,
                    doors,
                    cameras,
                    speakers,
                    drones,
                    regions,
                };
            });
    }

    return new Promise<any>(resolve =>
        resolve({
            schoolId: undefined,
            doors: EMPTY_LIST,
            cameras: EMPTY_LIST,
            speakers: EMPTY_LIST,
            drones: EMPTY_LIST,
            regions: EMPTY_LIST,
        })
    );
    */

    return new Promise<void>(resolve => resolve());
}

export const watchSchoolElementsQueryRequest = createAsyncCallSaga(
    Actions.querySchoolElements,
    Actions.querySchoolElementsSuccess,
    Actions.querySchoolElementsFailure,
    (schoolId: string) => querySchoolElements(schoolId)
);

export const watchDeviceActionRequest = createAsyncCallSaga(
    MapActions.deviceAction,
    MapActions.deviceActionSuccess,
    MapActions.deviceActionFailure,
    (payload: DeviceActionPayload) => queryService.deviceAction(payload)
);

export const watchDeviceCodesQueryRequest = createAsyncCallSaga(
    Actions.queryDeviceStateCodes,
    Actions.queryDeviceStateCodesSuccess,
    Actions.queryDeviceStateCodesFailure,
    () => queryService.queryDeviceCodes()
);

export function* watchDeviceActionResponse() {
    while (true) {
        const action: { payload: DeviceActionResult } = yield take(MapActions.deviceActionFailure);
        const errorMessage = action.payload.errors
            ?.map(error => error.message || error.code)
            .reduce((a, b, i, array) => a + (i < array.length - 1 ? ', ' : ' and ') + b);

        yield put(
            MessageActions.showMessage({
                message: errorMessage,
                autoHideDuration: 5000, // ms
                anchorOrigin: {
                    vertical: 'bottom', // top bottom
                    horizontal: 'right', // left center right
                },
                variant: 'error', // success error info warning null
            })
        );
    }
}
