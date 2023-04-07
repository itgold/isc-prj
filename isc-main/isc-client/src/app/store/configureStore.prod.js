import * as reduxModule from 'redux';
import { applyMiddleware, createStore } from 'redux';
import createSagaMiddleware from 'redux-saga';
import sagaMonitor from '@redux-saga/simple-saga-monitor';

export default function configureStore(initialState, createReducer) {
    /*
	Fix for Firefox redux dev tools extension
	https://github.com/zalmoxisus/redux-devtools-instrument/pull/19#issuecomment-400637274
	*/
    reduxModule.__DO_NOT_USE__ActionTypes.REPLACE = '@@redux/INIT';
    const sagaMiddleware = createSagaMiddleware({ sagaMonitor });

    return {
        ...createStore(createReducer(), initialState, applyMiddleware(sagaMiddleware)),
        runSaga: sagaMiddleware.run,
    };
}
