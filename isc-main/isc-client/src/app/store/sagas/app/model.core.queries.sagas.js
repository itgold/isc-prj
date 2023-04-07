import { createAsyncCallSaga } from '@common/utils/sagas.utils';
import * as Actions from '../../actions/admin';
import queryService from '../../../services/query.service';
import eventsService from '../../../services/events.service';

// ----------- Tags ---------------
export const watchTagsQueryRequest = createAsyncCallSaga(
    Actions.queryTags,
    Actions.queryTagsSuccess,
    Actions.queryTagsFailure,
    payload => queryService.queryTags(payload)
);

// ----------- Roles ---------------
export const watchRolesQueryRequest = createAsyncCallSaga(
    Actions.queryRoles,
    Actions.queryRolesSuccess,
    Actions.queryRolesFailure,
    payload => queryService.queryRoles(payload)
);

export const watchEventsQueryRequest = createAsyncCallSaga(
    Actions.queryEvents,
    Actions.queryEventsSuccess,
    Actions.queryEventsFailure,
    payload => eventsService.queryEvents(payload.filter, payload.pagination, payload.sort)
);

export const watchAlertsQueryRequest = createAsyncCallSaga(
    Actions.queryAlerts,
    Actions.queryAlertsSuccess,
    Actions.queryAlertsFailure,
    payload => eventsService.queryAlerts(payload.filter, payload.pagination, payload.sort)
);
