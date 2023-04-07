import { call, put, take } from 'redux-saga/effects';

/*
import {
  setLoadingState,
  resetLoadingState,
  clearError,
  setError
} from "../state/ui";

//  * Generates a saga effect that automatically manages the loading
//  * states and the errors.
//  *
//  * @param loadingKey - String - Action pattern to look for
//  * @param saga - function*(){} - Child saga to run

export const withLoadingAndErrors = (loadingKey, saga, ...sagaArgs) => {
    return function* (action) {
        yield put(setLoadingState(loadingKey));
        yield put(clearError());
        try {
            yield call(saga, action, ...sagaArgs);
        } catch (error) {
            yield put(setError(error.message));
        } finally {
            yield put(resetLoadingState(loadingKey));
        }
    };
}

export function* getRateSaga({ baseCurrency, quoteCurrency, baseAmount }) {
    const rate = yield call(api.getRate, baseCurrency, quoteCurrency, baseAmount);
    yield put(actions.fetchRateSuccess(rate));
}
  
export function* root() {
    yield takeLatest(
      actions.FETCH_RATE_START,
      withLoadingAndErrors("FETCH-RATE", getRateSaga)
    );
}
*/

const createAsyncCallSaga = (callAction, successAction, failureAction, asynFunction) => {
    function* makeCall({ payload }) {
        try {
            const response = yield call(asynFunction, payload);
            yield put(successAction(response));
        } catch (e) {
            yield put(failureAction(e));
        }
    }

    return function* func() {
        while (true) {
            const action = yield take(callAction);
            yield call(makeCall, action);
        }
    };
};

export default createAsyncCallSaga;
