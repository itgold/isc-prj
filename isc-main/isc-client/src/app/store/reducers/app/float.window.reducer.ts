import { FloatingWindow } from 'app/components/map/FloatingWindow';
import * as Actions from 'app/store/actions';
import { handleActions } from 'redux-actions';

const removeFloatingWindow = (floatingWindow: FloatingWindow, state: FloatingWindow[]): FloatingWindow[] => {
    return state.filter(wnd => wnd.entityId !== floatingWindow.entityId);
};

const mapReducer = handleActions<FloatingWindow[], FloatingWindow>(
    {
        // set the floor to a specific building
        [Actions.openFloatingWindow.toString()]: (state, action) => {
            return [...removeFloatingWindow(action.payload as FloatingWindow, state), action.payload as FloatingWindow];
        },
        // removes the building which contains the selected floor
        [Actions.closeFloatingWindow.toString()]: (state, action) => {
            return removeFloatingWindow(action.payload as FloatingWindow, state);
        },
        [Actions.setCurrentSchool.toString()]: () => [],
    },
    [] as FloatingWindow[]
);

export default mapReducer;
