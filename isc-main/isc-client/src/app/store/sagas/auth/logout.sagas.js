import { takeLatest, put, take } from 'redux-saga/effects';
import { resolveToken, crearToken, updateToken, logout as logoutRequest, LOGIN_URL } from 'app/utils/auth.utils';
import * as Actions from 'app/store/actions/auth';
import * as FuseActions from 'app/store/actions/fuse';
import getHistory from '@history/GlobalHistory';

function* logout() {
    const token = resolveToken();

    const getCurrentHistory = getHistory();
    getCurrentHistory?.push({
        pathname: LOGIN_URL,
    });

    yield put(Actions.authUnset());

    logoutRequest(token).then(() => {
        console.log('Logged out from back end');
    });

    // remove our token
    crearToken();

    yield put(FuseActions.setInitialSettings());

    yield put(Actions.userLoggedOut());
}

export function* logoutWatcher() {
    yield takeLatest(Actions.logout, logout);
}

export function* tokenWatcher() {
    const action = yield take(Actions.authSet);
    updateToken(action.payload);
}
