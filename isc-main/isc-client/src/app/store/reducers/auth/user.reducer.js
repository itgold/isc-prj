import { handleActions } from 'redux-actions';
import * as Actions from '../../actions/auth';

const initialState = {
    role: [], // guest
    data: {
        displayName: '',
        photoURL: 'assets/images/avatars/profile.jpg',
        email: '',
        shortcuts: ['map', 'social'],
    },
};

const user = handleActions(
    {
        [Actions.setUserData]: (state, action) => ({ ...initialState, ...action.payload }),
        [Actions.removeUserData]: () => initialState,
        [Actions.userLoggedOut]: () => initialState,
    },
    initialState
);

export default user;
