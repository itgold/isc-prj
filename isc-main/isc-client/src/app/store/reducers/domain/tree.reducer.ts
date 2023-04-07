import { handleActions } from 'redux-actions';
import * as Actions from 'app/store/actions/admin';
import { CompositeNode } from 'app/domain/composite';

// ======================================================================
//  Handle Composite Tree updates
// =================
/*
function cloneChildren(children: CompositeNodesMap | undefined, skipId: string | null = null): CompositeNodesMap {
    const oldChildren = children || {};
    const clone: CompositeNodesMap = {};
    Object.keys(oldChildren)
        .filter(childId => childId !== skipId)
        .forEach(childId => {
            clone[childId] = oldChildren[childId];
        });

    return clone;
}

function updateTree(
    rootNode: CompositeNode,
    updatedNode: CompositeNode,
    processedNodes: CompositeNodesMap
): CompositeNode {
    if (updatedNode.parentIds?.length) {
        let newState = { ...rootNode };
        updatedNode.parentIds?.forEach(parentId => {
            if (!processedNodes[parentId]) {
                const parent = findComposite(newState, parentId);
                if (parent && updatedNode.id) {
                    const children = cloneChildren(parent.children, updatedNode.id);
                    children[updatedNode.id] = updatedNode;

                    const updatedParent = { ...parent, children } as CompositeNode;
                    processedNodes[parentId] = updatedParent;
                    newState = updateTree(newState, updatedParent, processedNodes);
                }
            }
        });

        return newState;
    }

    // top level
    return updatedNode;
}

function createNode(state: CompositeNode, treeNode: CompositeNode | undefined): CompositeNode {
    const entityId = treeNode?.id;
    if (treeNode && entityId) {
        if (treeNode.parentIds && treeNode.parentIds.length) {
            let newState = {
                ...state,
            } as CompositeNode;

            treeNode.parentIds.forEach(parentId => {
                const parent = findComposite(newState, parentId);
                if (parent) {
                    const children = cloneChildren(parent.children);
                    children[entityId] = treeNode;
                    const updated = {
                        ...parent,
                        children,
                    } as CompositeNode;

                    const processedNodes: CompositeNodesMap = {};
                    newState = updateTree(newState, updated, processedNodes);
                }
            });

            return newState;
        }
    }

    return state;
}

function updateNode(state: CompositeNode, treeNode: CompositeNode | undefined): CompositeNode {
    const entityId = treeNode?.id;
    if (treeNode && entityId) {
        let newState = {
            ...state,
        } as CompositeNode;

        const node = findComposite(newState, entityId);
        if (node && node.parentIds && node.parentIds.length) {
            node.parentIds.forEach(parentId => {
                const parent = findComposite(newState, parentId);
                if (parent) {
                    const children = cloneChildren(parent.children, entityId);
                    const childNode = parent.children?.[entityId];
                    children[entityId] = { ...(childNode || {}), ...treeNode } as CompositeNode;

                    const updated = {
                        ...parent,
                        children,
                    } as CompositeNode;

                    const processedNodes: CompositeNodesMap = {};
                    newState = updateTree(newState, updated, processedNodes);
                }
            });
        }

        return newState;
    }

    return state;
}

function deleteNode(state: CompositeNode, treeNode: CompositeNode | undefined): CompositeNode {
    const entityId = treeNode?.id;
    if (entityId) {
        let newState = {
            ...state,
        } as CompositeNode;

        const node = findComposite(newState, entityId);
        if (node && node.parentIds && node.parentIds.length) {
            node.parentIds.forEach(parentId => {
                const parent = findComposite(newState, parentId);
                if (parent) {
                    const children = cloneChildren(parent.children, entityId);
                    const updated = {
                        ...parent,
                        children,
                    } as CompositeNode;

                    const processedNodes: CompositeNodesMap = {};
                    newState = updateTree(newState, updated, processedNodes);
                }
            });
        }

        return newState;
    }

    return state;
}
*/

export const INIT_STATE_DOMAIN_ROOT = new CompositeNode();

/**
 * This is to handle updates for the Composite Tree state.
 * We do handle only changes in hierarchy here
 */
const compositeTreeReducer = handleActions(
    {
        [Actions.queryRegionTreeSuccess.toString()]: (state, action) => action.payload as CompositeNode,
        /*
        // =========== Doors updates ======================
        [Actions.addDoor.toString()]: (state, action) =>
            createEntity(state, (action.payload as CrudResponse).newDoor),
        [Actions.updateDoorSuccess.toString()]: (state, action) =>
            updateEntity(state, (action.payload as CrudResponse).updateDoor),
        [Actions.deleteDoorSuccess.toString()]: (state, action) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteDoor),
        // =========== Camera updates ======================
        [Actions.addCamera.toString()]: (state, action) =>
            createEntity(state, (action.payload as CrudResponse).newCamera),
        [Actions.updateCameraSuccess.toString()]: (state, action) =>
            updateEntity(state, (action.payload as CrudResponse).updateCamera),
        [Actions.deleteCameraSuccess.toString()]: (state, action) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteCamera),
        // =========== Drone updates ======================
        [Actions.addDrone.toString()]: (state, action) =>
            createEntity(state, (action.payload as CrudResponse).newDrone),
        [Actions.updateDroneSuccess.toString()]: (state, action) =>
            updateEntity(state, (action.payload as CrudResponse).updateDrone),
        [Actions.deleteDroneSuccess.toString()]: (state, action) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteDrone),
        // =========== Speaker updates ======================
        [Actions.addSpeaker.toString()]: (state, action) =>
            createEntity(state, (action.payload as CrudResponse).newSpeaker),
        [Actions.updateSpeakerSuccess.toString()]: (state, action) =>
            updateEntity(state, (action.payload as CrudResponse).updateSpeaker),
        [Actions.deleteSpeakerSuccess.toString()]: (state, action) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteSpeaker),
        // =========== Region updates ======================
        [Actions.addRegion.toString()]: (state, action) =>
            createEntity(state, (action.payload as CrudResponse).newRegion),
        [Actions.updateRegionSuccess.toString()]: (state, action) =>
            updateEntity(state, (action.payload as CrudResponse).updateRegion),
        [Actions.deleteRegionSuccess.toString()]: (state, action) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteRegion),
        // =========== Radios updates ======================
        [Actions.addRadio.toString()]: (state, action) =>
            createEntity(state, (action.payload as CrudResponse).newRadio),
        [Actions.updateRadioSuccess.toString()]: (state, action) =>
            updateEntity(state, (action.payload as CrudResponse).updateRadio),
        [Actions.deleteRadioSuccess.toString()]: (state, action) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteRadio),
        */
    },
    INIT_STATE_DOMAIN_ROOT
);

export default compositeTreeReducer;
