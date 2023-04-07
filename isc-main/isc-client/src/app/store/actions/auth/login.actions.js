import { createActions } from 'redux-actions';

export const { submitLogin, loginSuccess, loginError } = createActions('SUBMIT_LOGIN', 'LOGIN_SUCCESS', 'LOGIN_ERROR');
export const { authSet, authUnset } = createActions('AUTH_SET', 'AUTH_UNSET');
export const { logout } = createActions('LOGOUT');
