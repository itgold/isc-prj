import { createActions } from 'redux-actions';

export const { loadRegionTree, queryRegionTree, queryRegionTreeSuccess, queryRegionTreeFailure } = createActions(
    'LOAD_REGION_TREE',
    'QUERY_REGION_TREE',
    'QUERY_REGION_TREE_SUCCESS',
    'QUERY_REGION_TREE_FAILURE'
);
