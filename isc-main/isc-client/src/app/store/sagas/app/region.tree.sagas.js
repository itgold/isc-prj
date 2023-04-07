import { select, put, take, call, fork } from 'redux-saga/effects';
import regionHierarchyService from 'app/services/region.hierarchy.service';
import * as Selectors from 'app/store/selectors';
import * as Actions from '../../actions/admin';
import { NONE_ENTITY } from '../../../utils/domain.constants';

function* queryCompositesFlow(nodeId) {
    try {
        const response = yield call(payload => regionHierarchyService.getComposite(payload), nodeId);
        yield put(Actions.queryRegionTreeSuccess(response));
        yield put(Actions.commonModelsLoaded(true));
    } catch (e) {
        yield put(Actions.queryRegionTreeFailure(e));
    }
}

// ------------ Regions Tree -----------
export function* watchRegionsTreeQueryRequest() {
    while (true) {
        const action = yield take(Actions.queryRegionTree);
        yield fork(queryCompositesFlow, action.payload);
    }
}

export function* loadRegionsTreeWatcher() {
    while (true) {
        const action = yield take(Actions.loadRegionTree);
        const root = yield select(Selectors.compositeRootSelector);
        if (!root?.id || action.payload) {
            // yield put(Actions.commonModelsLoaded(false));
            yield fork(queryCompositesFlow, NONE_ENTITY);
        }
    }
}
