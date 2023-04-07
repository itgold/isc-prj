import { handleActions } from 'redux-actions';
import * as Actions from 'app/store/actions/admin';
import { CompositeNode, CompositeType } from 'app/domain/composite';
import { convertToStateType, EntityCollectionType, EntityType } from 'app/utils/domain.constants';
import { Entities, EntityMap } from 'app/store/appState';
import { CrudResponse } from 'app/services/query.service';
import { RadioCrudResponse } from 'app/services/device.radio.service';
import { IEntity } from '../../../domain/entity';

function loadTree(state: Entities, payload: CompositeNode): Entities {
    const entitiesMap = { ...state };

    function traverseTree(node: CompositeNode, map: Entities): void {
        if (node && node.id) {
            const entityType = CompositeNode.entityType(node);
            if (entityType) {
                const type = convertToStateType(entityType);
                map[type][node.id] = node as any;
                if (node.children) {
                    Object.values(node.children).forEach(child => traverseTree(child, map));
                }
            }
        }
    }

    try {
        traverseTree(payload, entitiesMap);
    } catch (e) {
        console.log('Unable to load composite tree data', e);
    }

    return entitiesMap;
}

function loadEntities(state: Entities, nodes: CompositeNode[] | undefined, entityType: EntityType): Entities {
    const type = convertToStateType(entityType);
    const newState = { ...state };
    nodes?.forEach(node => {
        newState[type][node.id || ''] = node as any;
    });

    return newState;
}

function propogateCompositeInfo(entity: CompositeNode, oldEntity?: CompositeNode): CompositeNode {
    if (oldEntity) {
        if (oldEntity.compositeType) {
            entity.compositeType = entity.compositeType || oldEntity.compositeType;
            entity.children = entity.children || oldEntity.children;
        }
        entity.status = entity.status || (oldEntity as CompositeNode).status || 'ACTIVATED';
    } else {
        // guess the composite info
        entity.compositeType =
            !entity.entityType || entity.entityType === EntityType.REGION
                ? CompositeType.CONTAINER
                : CompositeType.LEAF;
        entity.children = {};
        entity.status = entity.status || 'ACTIVATED';
    }

    return entity;
}

function createEntity(state: Entities, treeNode: CompositeNode | undefined, entityTypeX: EntityType): Entities {
    const entityId = treeNode?.id;
    if (treeNode && entityId) {
        const newState = { ...state };
        if (treeNode) {
            treeNode.entityType = treeNode.entityType || entityTypeX;
        }
        const entityType = (treeNode ? CompositeNode.entityType(treeNode) : entityTypeX) || entityTypeX;
        newState[convertToStateType(entityType)][entityId] = propogateCompositeInfo(treeNode) as any;
        return newState;
    }

    return state;
}

function updateEntity(state: Entities, treeNode: CompositeNode | undefined, entityTypeX: EntityType): Entities {
    const entityId = treeNode?.id;
    if (treeNode && entityId) {
        const newState = { ...state };
        if (treeNode) {
            treeNode.entityType = treeNode.entityType || entityTypeX;
        }
        const entityType = (treeNode ? CompositeNode.entityType(treeNode) : entityTypeX) || entityTypeX;
        const type = convertToStateType(entityType);
        const oldEntity = (state[type][entityId] || {}) as CompositeNode;

        newState[type][entityId] = propogateCompositeInfo(
            {
                ...treeNode,
                entityType,
            },
            oldEntity
        ) as any;
        return newState;
    }

    return state;
}

function deleteEntity(state: Entities, treeNode: CompositeNode | undefined, entityTypeX: EntityType): Entities {
    const entityId = treeNode?.id;
    if (entityId) {
        if (treeNode) {
            treeNode.entityType = treeNode.entityType || entityTypeX;
        }
        const entityType = (treeNode ? CompositeNode.entityType(treeNode) : entityTypeX) || entityTypeX;
        const type: EntityCollectionType = convertToStateType(entityType);
        const collection = state[type];
        const updated: EntityMap<IEntity> = {};
        Object.keys(collection).forEach(id => {
            if (id !== entityId) {
                updated[entityId] = collection[entityId];
            }
        });

        const newState = { ...state } as any;
        newState[type] = updated;

        return newState;
    }

    return state;
}

export const INIT_STATE_DOMAIN_ENTITIES: Entities = {
    doors: {},
    cameras: {},
    speakers: {},
    safeties: {},
    drones: {},
    regions: {},
    radios: {},

    schools: {},
    districts: {},
    tags: {},
    users: {},
    roles: {},
    utilities: {},
    compositecams: {},
};

/**
 * This is to handle updates for the Composite entities map state.
 * We do handle changes in the entities details here.
 */
const compositeEntitiesReducer = handleActions(
    {
        [Actions.queryRegionTreeSuccess.toString()]: (state, action) =>
            loadTree(state, action.payload as CompositeNode),
        // =========== Utilities updates ======================
        [Actions.addUtility.toString()]: (state, action: any) =>
            createEntity(state, (action.payload as CrudResponse).newUtility, EntityType.UTILITY),
        [Actions.updateUtilitySuccess.toString()]: (state, action: any) =>
            updateEntity(state, (action.payload as CrudResponse).updateUtility, EntityType.UTILITY),
        [Actions.deleteUtilitySuccess.toString()]: (state, action: any) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteUtility, EntityType.UTILITY),
        // =========== Doors updates ======================
        [Actions.addDoor.toString()]: (state, action: any) =>
            createEntity(state, (action.payload as CrudResponse).newDoor, EntityType.DOOR),
        [Actions.updateDoorSuccess.toString()]: (state, action: any) =>
            updateEntity(state, (action.payload as CrudResponse).updateDoor, EntityType.DOOR),
        [Actions.deleteDoorSuccess.toString()]: (state, action: any) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteDoor, EntityType.DOOR),
        // =========== Camera updates ======================
        [Actions.addCamera.toString()]: (state, action: any) =>
            createEntity(state, (action.payload as CrudResponse).newCamera, EntityType.CAMERA),
        [Actions.updateCameraSuccess.toString()]: (state, action: any) =>
            updateEntity(state, (action.payload as CrudResponse).updateCamera, EntityType.CAMERA),
        [Actions.deleteCameraSuccess.toString()]: (state, action: any) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteCamera, EntityType.CAMERA),
        // =========== Safeties updates ======================
        [Actions.addSafety.toString()]: (state, action: any) =>
            createEntity(state, (action.payload as CrudResponse).newSafety, EntityType.SAFETY),
        [Actions.updateSafetySuccess.toString()]: (state, action: any) =>
            updateEntity(state, (action.payload as CrudResponse).updateSafety, EntityType.SAFETY),
        [Actions.deleteSafetySuccess.toString()]: (state, action: any) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteSafety, EntityType.SAFETY),
        // =========== Drone updates ======================
        [Actions.addDrone.toString()]: (state, action: any) =>
            createEntity(state, (action.payload as CrudResponse).newDrone, EntityType.DRONE),
        [Actions.updateDroneSuccess.toString()]: (state, action: any) =>
            updateEntity(state, (action.payload as CrudResponse).updateDrone, EntityType.DRONE),
        [Actions.deleteDroneSuccess.toString()]: (state, action: any) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteDrone, EntityType.DRONE),
        // =========== Speaker updates ======================
        [Actions.addSpeaker.toString()]: (state, action: any) =>
            createEntity(state, (action.payload as CrudResponse).newSpeaker, EntityType.SPEAKER),
        [Actions.updateSpeakerSuccess.toString()]: (state, action: any) =>
            updateEntity(state, (action.payload as CrudResponse).updateSpeaker, EntityType.SPEAKER),
        [Actions.deleteSpeakerSuccess.toString()]: (state, action: any) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteSpeaker, EntityType.SPEAKER),
        // =========== Region updates ======================
        [Actions.addRegion.toString()]: (state, action: any) =>
            createEntity(state, (action.payload as CrudResponse).newRegion, EntityType.REGION),
        [Actions.updateRegionSuccess.toString()]: (state, action: any) =>
            updateEntity(state, (action.payload as CrudResponse).updateRegion, EntityType.REGION),
        [Actions.deleteRegionSuccess.toString()]: (state, action: any) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteRegion, EntityType.REGION),
        // =========== Radios updates ======================
        [Actions.addRadio.toString()]: (state, action: any) =>
            createEntity(state, (action.payload as RadioCrudResponse).newRadio, EntityType.RADIO),
        [Actions.updateRadioSuccess.toString()]: (state, action: any) =>
            updateEntity(state, (action.payload as RadioCrudResponse).updateRadio, EntityType.RADIO),
        [Actions.deleteRadioSuccess.toString()]: (state, action: any) =>
            deleteEntity(state, (action.payload as RadioCrudResponse).deleteRadio, EntityType.RADIO),

        // -----------------------------------------
        [Actions.querySchoolsSuccess.toString()]: (state, action: any) =>
            loadEntities(state, (action.payload as CrudResponse).schools, EntityType.SCHOOL),
        [Actions.addSchool.toString()]: (state, action: any) =>
            createEntity(state, (action.payload as CrudResponse).newSchool, EntityType.SCHOOL),
        [Actions.updateSchoolSuccess.toString()]: (state, action: any) =>
            updateEntity(state, (action.payload as CrudResponse).updateSchool, EntityType.SCHOOL),
        [Actions.deleteSchoolSuccess.toString()]: (state, action: any) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteSchool, EntityType.SCHOOL),
        // -----------------------------------------
        [Actions.querySchoolDistrictsSuccess.toString()]: (state, action: any) =>
            loadEntities(state, (action.payload as CrudResponse).districts, EntityType.DISTRICT),
        [Actions.addSchoolDistrict.toString()]: (state, action: any) =>
            createEntity(state, (action.payload as CrudResponse).newDistrict, EntityType.DISTRICT),
        [Actions.updateSchoolDistrictSuccess.toString()]: (state, action: any) =>
            updateEntity(state, (action.payload as CrudResponse).updateDistrict, EntityType.DISTRICT),
        [Actions.deleteSchoolDistrictSuccess.toString()]: (state, action: any) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteDistrict, EntityType.DISTRICT),

        // -----------------------------------------
        [Actions.queryUsersSuccess.toString()]: (state, action: any) =>
            loadEntities(state, (action.payload as CrudResponse).users, EntityType.USER),
        [Actions.addUser.toString()]: (state, action: any) =>
            createEntity(state, (action.payload as CrudResponse).newUser, EntityType.USER),
        [Actions.updateUserSuccess.toString()]: (state, action: any) =>
            updateEntity(state, (action.payload as CrudResponse).updateUser, EntityType.USER),
        [Actions.deleteUserSuccess.toString()]: (state, action: any) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteUser, EntityType.USER),
        // -----------------------------------------
        [Actions.queryRolesSuccess.toString()]: (state, action: any) =>
            loadEntities(state, (action.payload as CrudResponse).roles, EntityType.ROLE),
        // -----------------------------------------
        [Actions.queryTagsSuccess.toString()]: (state, action: any) =>
            loadEntities(state, action.payload as CompositeNode[], EntityType.TAG),
        [Actions.addTag.toString()]: (state, action: any) =>
            createEntity(state, (action.payload as CrudResponse).newTag, EntityType.TAG),
        [Actions.updateTagSuccess.toString()]: (state, action: any) =>
            updateEntity(state, (action.payload as CrudResponse).updateTag, EntityType.TAG),
        [Actions.deleteTagSuccess.toString()]: (state, action: any) =>
            deleteEntity(state, (action.payload as CrudResponse).deleteTag, EntityType.TAG),
        // -----------------------------------------
    },
    INIT_STATE_DOMAIN_ENTITIES
);

export default compositeEntitiesReducer;
