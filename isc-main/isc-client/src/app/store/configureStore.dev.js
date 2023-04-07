import * as reduxModule from 'redux';
import { applyMiddleware, compose, createStore } from 'redux';
import createSagaMiddleware from 'redux-saga';
import sagaMonitor from '@redux-saga/simple-saga-monitor';
import { createLogger } from 'redux-logger';

export default function configureStore(initialState, createReducer) {
    /*
	Fix for Firefox redux dev tools extension
	https://github.com/zalmoxisus/redux-devtools-instrument/pull/19#issuecomment-400637274
	*/
    reduxModule.__DO_NOT_USE__ActionTypes.REPLACE = '@@redux/INIT';
    const composeSetup =
        process.env.NODE_ENV !== 'production' &&
        typeof window === 'object' &&
        window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
            ? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({
                  // Specify extension’s options like name, actionsBlacklist, actionsCreators, serialize...
              })
            : compose;

    const sagaMiddleware = createSagaMiddleware({ sagaMonitor });

    return {
        ...createStore(createReducer(), initialState, composeSetup(applyMiddleware(sagaMiddleware, createLogger()))),
        runSaga: sagaMiddleware.run,
    };
}
