import { take, fork, cancel, call, put, cancelled } from 'redux-saga/effects';
import getHistory from '@history/GlobalHistory';
import { AUTH_STORAGE, LOGIN_URL, login as loginRequest } from 'app/utils/auth.utils';
import storageService from 'app/services/storage.service';
import * as Actions from '../../actions/auth';

function* loginFlow(username, password) {
    let token;
    try {
        // try to call to our loginApi() function.  Redux Saga
        // will pause here until we either are successful or
        // receive an error
        token = yield call(loginRequest, username, password);
        if (token.status && token.status === 'SUCCEEDED') {
            storageService.updateProperty(AUTH_STORAGE, JSON.stringify(token));

            // inform Redux to set our client token, this is non blocking so...
            yield put(Actions.authSet(token));

            const userData = {
                role: token.roles,
                data: {
                    displayName: token.username,
                    photoURL: 'assets/images/avatars/profile.jpg',
                    email: token.username,
                    shortcuts: ['map', 'social'],
                },
            };
            yield put(Actions.setUserData(userData));

            // .. also inform redux that our login was successful
            yield put(Actions.loginSuccess(token));

            // redirect them to index page
            // getHistory().push(INDEX_URL);
        } else {
            yield put(Actions.loginError(token.msg));
        }
    } catch (error) {
        // error? send it to redux
        yield put(Actions.loginError(error));
    } finally {
        // No matter what, if our `forked` `task` was cancelled
        // we will then just redirect them to login
        if (yield cancelled()) {
            console.log('Fail to login. Redirecting to login page ...');
            getHistory().push(LOGIN_URL);
        }
    }

    // return the token for health and wealth
    return token;
}

// Our watcher (saga).  It will watch for many things.
export default function* loginWatcher() {
    // Generators halt execution until their next step is ready/occurring
    // So it's not like this loop is firing in the background 1000/sec
    // Instead, it says, "okay, true === true", and hits the first step...
    while (true) {
        //
        // ... and in this first it sees a yield statement with `take` which
        // pauses the loop.  It will sit here and WAIT for this action.
        //
        // yield take(ACTION) just says, when our generator sees the ACTION
        // it will pull from that ACTION's payload that we send up, its
        // username and password.  ONLY when this happens will the loop move
        // forward...
        const loginAction = yield take(Actions.submitLogin);
        const { username, password } = loginAction.payload;

        // ... and pass the username and password to our loginFlow() function.
        // The fork() method spins up another "process" that will deal with
        // handling the loginFlow's execution in the background!
        // Think, "fork another process".
        //
        // It also passes back to us, a reference to this forked task
        // which is stored in our const task here.  We can use this to manage
        // the task.
        //
        // However, fork() does not block our loop.  It's in the background
        // therefore as soon as our loop executes this it mores forward...
        const task = yield fork(loginFlow, username, password);

        // ... and begins looking for either CLIENT_UNSET or LOGIN_ERROR!
        // That's right, it gets to here and stops and begins watching
        // for these tasks only.  Why would it watch for login any more?
        // During the life cycle of this generator, the user will login once
        // and all we need to watch for is either logging out, or a login
        // error.  The moment it does grab either of these though it will
        // once again move forward...
        const action = yield take([Actions.authUnset, Actions.logout, Actions.loginError]);

        // ... if, for whatever reason, we decide to logout during this
        // cancel the current action.  i.e. the user is being logged
        // in, they get impatient and start hammering the logout button.
        // this would result in the above statement seeing the CLIENT_UNSET
        // action, and down here, knowing that we should cancel the
        // forked `task` that was trying to log them in.  It will do so
        // and move forward...
        if (action.type === Actions.authUnset || action.type === Actions.logout) {
            yield cancel(task);
        }
        if (action.type === Actions.loginError) {
            // ... finally we'll just log them out.  This will unset the client
            // access token ... -> follow this back up to the top of the while loop
            yield put(Actions.logout());
        }
    }
}
