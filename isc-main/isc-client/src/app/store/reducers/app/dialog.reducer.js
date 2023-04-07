import { handleActions } from 'redux-actions';

import * as Actions from '../../actions/app';

const initialState = {
    title: '',
    message: '',
    // callbacks
    onConfirm: null,
    onCancel: null,
    onClose: null,
    showDialog: false,
};

/**
 * Defines the title, message and callbacks for buttons on Dialog
 */
const dialogReducer = handleActions(
    {
        [Actions.showDialog]: (state, action) => {
            return { ...action.payload };
        },
        [Actions.hideDialog]: (state, action) => {
            return {
                ...state,
                showDialog: !action.payload,
            };
        },
    },
    initialState
);

export default dialogReducer;
