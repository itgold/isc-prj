import { END } from 'redux-saga';
import configureStoreProd from './configureStore.prod';
import configureStoreDev from './configureStore.dev';
import createReducer from './reducers';
import rootSaga from './sagas';

function createStore(defaultState) {
    let configureStore;
    if (process.env.NODE_ENV === 'production') {
        configureStore = configureStoreProd;
    } else {
        configureStore = configureStoreDev;
    }

    const storeInstance = configureStore(defaultState, createReducer);
    storeInstance.close = () => storeInstance.dispatch(END);
    storeInstance.asyncReducers = {};
    storeInstance.runSaga(rootSaga);
    return storeInstance;
}

const initialState = {};
const store = createStore(initialState);

export const injectReducer = (key, reducer) => {
    if (store.asyncReducers[key]) {
        return false;
    }
    store.asyncReducers[key] = reducer;
    store.replaceReducer(createReducer(store.asyncReducers));
    return store;
};

export default store;
