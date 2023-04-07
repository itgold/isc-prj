import { Action, ReduxCompatibleReducer } from 'redux-actions';

export default function chainReducers<State, Payload>(
    initialState: State,
    ...reducers: ReduxCompatibleReducer<State, Payload>[]
): ReduxCompatibleReducer<State, Payload> {
    return (state: State | undefined, action: Action<Payload>): State => {
        let newState = state;
        reducers.forEach(reducer => {
            newState = reducer(newState, action);
        });

        return newState || initialState;
    };
}
