import { handleActions } from 'redux-actions';

export const restCallState = {
    requesting: false,
    successful: false,
    response: null,
    error: null,
    completeTime: null,
};

export function createRestReducer(call, success, failure) {
    return handleActions(
        {
            [call]: () => ({
                requesting: true,
                successful: false,
                response: null,
                errors: null,
                completeTime: null,
            }),
            [success]: (state, action) => ({
                errors: null,
                response: action.payload,
                requesting: false,
                successful: true,
                completeTime: new Date(),
            }),
            [failure]: (state, action) => ({
                error: action.payload,
                response: null,
                requesting: false,
                successful: false,
                completeTime: new Date(),
            }),
        },
        restCallState // default state
    );
}

export function createRootReducer(DefaultState, ...args) {
    const reducers = args;
    return (state, action) => {
        let resultState = state || DefaultState;
        reducers.forEach(reducer => {
            resultState = { ...resultState, ...reducer(resultState, action) };
        });

        return resultState;
    };
}
