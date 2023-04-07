import { Action } from 'redux-actions';
import { createRestReducer, restCallState } from '@common/utils/action.utils';
import * as Actions from 'app/store/actions/admin/model.queries';

const initialState = {
    querySchools: restCallState,
    queryDistricts: restCallState,
    createSchool: restCallState,
    updateSchool: restCallState,
    deleteSchool: restCallState,
};

// Query Schools REST call progress
const querySchoolsCallReducer = createRestReducer(
    Actions.querySchools,
    Actions.querySchoolsSuccess,
    Actions.querySchoolsFailure
);
const querySchoolDistrictsCallReducer = createRestReducer(
    Actions.querySchoolDistricts,
    Actions.querySchoolDistrictsSuccess,
    Actions.querySchoolDistrictsFailure
);

export default function modelsReducer(state = initialState, action: Action<any>) {
    return {
        ...state,
        querySchools: querySchoolsCallReducer(state.querySchools, action),
        queryDistricts: querySchoolDistrictsCallReducer(state.queryDistricts, action),
    };
}
