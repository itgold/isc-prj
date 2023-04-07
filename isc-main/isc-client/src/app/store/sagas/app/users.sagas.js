import { call, put, take } from 'redux-saga/effects';
import * as Actions from 'app/store/actions/auth';
import * as FuseActions from 'app/store/actions/fuse';
import * as MessageActions from 'app/store/actions/fuse/message.actions';

export function* setUserDataWatcher() {
    const action = yield take(Actions.setUserData);
    const user = action.payload;

    // You can redirect the logged-in user to a specific route depending on his role

    // history.location.state = {
    //     redirectUrl: user.redirectUrl // for example 'apps/academy'
    // }
    // getHistory().push(INDEX_URL);

    // Set User Settings
    yield put(FuseActions.setDefaultSettings(user.data.settings));
}

async function updateUserData() {
    return new Promise(resolve => {
        setTimeout(() => resolve('TODO: implement save user data on server'), 500);
    });
}

export function* updateUserDataWatcher() {
    const action = yield take(Actions.updateUserData);

    try {
        yield call(updateUserData, action.payload);
        yield put(MessageActions.showMessage({ message: 'User data saved with api' }));
    } catch (error) {
        yield put(MessageActions.showMessage({ message: error.message }));
    }
}
