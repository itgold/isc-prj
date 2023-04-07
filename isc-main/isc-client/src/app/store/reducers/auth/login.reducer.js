import { handleActions } from 'redux-actions';
import { JWTToken } from '@common/utils/api.utils';
import * as Actions from '../../actions/auth';

const initialState = {
    rest: {
        success: false,
        error: null,
    },
    token: new JWTToken({
        access_token: null,
        expires_in: 1800,
        refresh_token: null,
        token_type: 'Bearer',
    }),
};

const authentication = handleActions(
    {
        [Actions.authSet]: (state, action) => ({ ...state, token: action.payload }),
        [Actions.authUnset]: state => ({ ...state, token: initialState.token }),
        [Actions.loginSuccess]: state => ({ ...state, rest: { success: true, error: null } }),
        [Actions.loginError]: (state, action) => ({ ...state, rest: { success: false, error: action.payload } }),
    },
    initialState
);

export default authentication;
