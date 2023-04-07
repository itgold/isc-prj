import { combineReducers } from 'redux';
import fuse from './fuse';
import auth from './auth';
import domain from './domain';
import rest from './admin/rest.calls.reducer';
import app from './app';
import dashboards from './admin/dashboards.reducer';

const createReducer = asyncReducers =>
    combineReducers({
        auth,
        fuse,
        domain,
        rest,
        app,
        dashboards,
        ...asyncReducers,
    });

export default createReducer;
