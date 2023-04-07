import { createSelector } from 'reselect';

import Camera from 'app/domain/camera';
import { CompositeNode, CompositeNodesMap, CompositeType } from 'app/domain/composite';
import Door from 'app/domain/door';
import Drone from 'app/domain/drone';
import Region from 'app/domain/region';
import School from 'app/domain/school';
import Speaker from 'app/domain/speaker';
import { Alerts, EntityMap, MapContextStates, RootState, SchoolEvents } from 'app/store/appState';
import { EMPTY_LIST, EntityType, RegionType } from 'app/utils/domain.constants';
import Tag from 'app/domain/tag';
import User from 'app/domain/user';
import Role from 'app/domain/role';
import Radio from 'app/domain/radio';
import Zone, { DEFAULT_ZONE_COLOR } from 'app/domain/zone';
import { ILayersHashmap } from 'app/components/map/SchoolMapComponent';
import CodeDictionary from 'app/domain/codeDictionary';
import ISchoolElement from '../domain/school.element';
import { IEntity } from '../domain/entity';
import SchoolDistrict from '../domain/school.district';
import Utility from '../domain/utility';
import Safety from '../domain/safety';

export const compositeRootSelector = (state: RootState): CompositeNode => state.domain?.root;
export const dashboardContextSelector = (state: RootState): MapContextStates => state.dashboards?.mapDashboard || {};
export const currentSchoolSelector = (state: RootState): string | undefined => state.dashboards?.currentSchoolId;
export const eventsSelector = (state: RootState): SchoolEvents => state.domain?.events;
export const alertsSelector = (state: RootState): Alerts => state.domain?.alerts;
export const deviceCodeSelector = (state: RootState): EntityMap<CodeDictionary> => state.domain?.deviceCodes;

type RegionSelector = (state: RootState) => Region;

export function findComposite(root: CompositeNode, regionId: string): CompositeNode | undefined {
    let node: CompositeNode | undefined;
    if (root?.id === regionId) {
        node = root;
    } else {
        node = root.children?.[regionId];
        if (!node && root.children) {
            Object.values(root.children).forEach(subnode => {
                if (!node) {
                    node = findComposite(subnode, regionId);
                }
            });
        }
    }

    return node;
}

type EntitySelector = (state: RootState) => IEntity | undefined;
type EntitySelectorCreator = (entityId: string) => EntitySelector;
export function createDoorEntitySelector(doorId: string): EntitySelector {
    return (state: RootState): ISchoolElement | undefined => {
        return state.domain.entities.doors[doorId];
    };
}
export const doorsSelector = (state: RootState): Door[] => {
    const doors = state.domain.entities.doors || {};
    return Object.values(doors);
};

export function createCameraEntitySelector(cameraId: string): EntitySelector {
    return (state: RootState): ISchoolElement | undefined => {
        return state.domain.entities.cameras[cameraId];
    };
}
export const camerasSelector = (state: RootState): Camera[] => {
    const cameras = state.domain.entities.cameras || {};
    return Object.values(cameras);
};

export function createDroneEntitySelector(droneId: string): EntitySelector {
    return (state: RootState): ISchoolElement | undefined => {
        return state.domain.entities.drones[droneId];
    };
}
export const dronesSelector = (state: RootState): Drone[] => {
    const drones = state.domain.entities.drones || {};
    return Object.values(drones);
};

export function createSpeakerEntitySelector(speakerId: string): EntitySelector {
    return (state: RootState): ISchoolElement | undefined => {
        return state.domain.entities.speakers[speakerId];
    };
}
export const speakersSelector = (state: RootState): Speaker[] => {
    const speakers = state.domain.entities.speakers || {};
    return Object.values(speakers);
};

export function createRegionEntitySelector(regionId: string): EntitySelector {
    return (state: RootState): ISchoolElement | undefined => {
        return state.domain.entities.regions[regionId] || state.domain.entities.compositecams[regionId];
    };
}

export function createCompositeCameraEntitySelector(regionId: string): EntitySelector {
    return (state: RootState): ISchoolElement | undefined => {
        return state.domain.entities.compositecams[regionId];
    };
}

export function createUtilityEntitySelector(utilityId: string): EntitySelector {
    return (state: RootState): IEntity | undefined => {
        return state.domain.entities.utilities[utilityId];
    };
}

export const utilitiesSelector = (state: RootState): Utility[] => {
    const utilities = state.domain.entities.utilities || {};
    return Object.values(utilities);
};

export const regionsSelector = (state: RootState): Region[] => {
    const regions = state.domain.entities.regions || {};
    return Object.values(regions);
};
export function createSafetyEntitySelector(safetyId: string): EntitySelector {
    return (state: RootState): IEntity | undefined => {
        return state.domain.entities.safeties[safetyId];
    };
}
export const safetiesSelector = (state: RootState): Safety[] => {
    const safeties = state.domain.entities.safeties || {};
    return Object.values(safeties);
};
export function createRadioEntitySelector(radioId: string): EntitySelector {
    return (state: RootState): ISchoolElement | undefined => {
        return state.domain.entities.radios[radioId];
    };
}
export const radiosSelector = (state: RootState): Radio[] => {
    const radios = state.domain.entities.radios || {};
    return Object.values(radios);
};

export function createSchoolEntitySelector(schoolId: string): EntitySelector {
    return (state: RootState): IEntity | undefined => {
        return state.domain.entities.schools[schoolId];
    };
}
export const schoolsSelector = (state: RootState): School[] => {
    const schools = state.domain.entities.schools || {};
    return Object.values(schools);
};

export function createDistrictEntitySelector(districtId: string): EntitySelector {
    return (state: RootState): IEntity | undefined => {
        return state.domain.entities.districts[districtId];
    };
}
export const districtsSelector = (state: RootState): SchoolDistrict[] => {
    const districts = state.domain.entities.districts || {};
    return Object.values(districts);
};

/**
 * Returns the region which belongs to the district
 * @param districtId
 * @returns
 */
export const districtRegionSelector = (districtId: string): EntitySelector => {
    const schoolDistrict = createDistrictEntitySelector(districtId) as SchoolDistrict;
    const districtRegion = createRegionEntitySelector(schoolDistrict.region?.id as string);
    return districtRegion;
};

export type TRegionOrNullSelector = (state: RootState) => Region | null;
/**
 * Return the floor selected for a specific build
 * @param buildingId
 * @returns
 */
export function getSelectedFloorByBuilding(buildingId: string): TRegionOrNullSelector {
    return (state: RootState): Region | null => {
        // get all the schools
        return state.app.map[buildingId] as Region | null;
    };
}
export function selectSelectedFloors(state: RootState): Region[] {
    const entries = Object.entries(state.app.map).filter(([key, value]) => key !== 'layersHashmap');
    const tempState: Region[] = entries.length ? [] : EMPTY_LIST;

    if (entries.length) {
        entries.forEach(([key, value]) => tempState.push(value as Region));
    }

    return tempState;
}

export function createTagEntitySelector(schoolId: string): EntitySelector {
    return (state: RootState): ISchoolElement | undefined => {
        return state.domain.entities.tags[schoolId];
    };
}
export const tagsSelector = (state: RootState): Tag[] => {
    const tags = state.domain.entities.tags || {};
    return Object.values(tags).filter(tag => !!tag);
};

export function createUserEntitySelector(schoolId: string): EntitySelector {
    return (state: RootState): ISchoolElement | undefined => {
        return state.domain.entities.users[schoolId];
    };
}
export const usersSelector = (state: RootState): User[] => {
    const users = state.domain.entities.users || {};
    return Object.values(users).filter(user => !!user);
};

export function createRoleEntitySelector(schoolId: string): EntitySelector {
    return (state: RootState): ISchoolElement | undefined => {
        return state.domain.entities.roles[schoolId];
    };
}
export const rolesSelector = (state: RootState): Role[] => {
    const roles = state.domain.entities.roles || {};
    return Object.values(roles).filter(role => !!role);
};
export const isTreeLoadedSelector = (state: RootState): boolean => {
    return state.domain.commonLoaded;
};

export type EntitySelectorMap = {
    [key in EntityType]: EntitySelectorCreator;
};
const TYPED_ENTITY_SELECTOR: EntitySelectorMap = {
    [EntityType.DOOR]: createDoorEntitySelector,
    [EntityType.CAMERA]: createCameraEntitySelector,
    [EntityType.DRONE]: createDroneEntitySelector,
    [EntityType.SPEAKER]: createSpeakerEntitySelector,
    [EntityType.SAFETY]: createSafetyEntitySelector,
    [EntityType.REGION]: createRegionEntitySelector,
    [EntityType.RADIO]: createRadioEntitySelector,
    [EntityType.UTILITY]: createUtilityEntitySelector,
    // ------------------------------------------
    [EntityType.SCHOOL]: createSchoolEntitySelector,
    [EntityType.DISTRICT]: createDistrictEntitySelector,
    [EntityType.TAG]: createTagEntitySelector,
    [EntityType.USER]: createUserEntitySelector,
    [EntityType.ROLE]: createRoleEntitySelector,
    // ------------------------------------------
    [EntityType.UNKNOWN]: () => (): ISchoolElement | undefined => undefined,
    [EntityType.COMPOSITECAM]: createCompositeCameraEntitySelector,
};

export function createEntityByIdSelector(compositeId: string, entityType?: EntityType): EntitySelector {
    const type = entityType || EntityType.UNKNOWN;
    const selector = TYPED_ENTITY_SELECTOR[type];
    return selector(compositeId);
}

export function createEntitySelector(composite?: CompositeNode): EntitySelector {
    return composite
        ? createEntityByIdSelector(composite.id || '', CompositeNode.entityType(composite))
        : () => undefined;
}

/**
 * Return the right hash which contains the details about the layer and the source where the markers will live
 * @param composite
 * @returns
 */
export function createLayerSelector(composite: CompositeNode): (state: RootState) => ILayersHashmap | undefined {
    return (state: RootState): ILayersHashmap | undefined => {
        try {
            if (composite) {
                if (composite.entityType && state.app.map.layersHashmap[composite.entityType]) {
                    // initially, find the layer according the device type.
                    return state.app.map.layersHashmap[composite.entityType] as ILayersHashmap;
                }
                // does it has a type but not a subType like BUILDING for example
                if (composite.type && !composite.subType) {
                    // in the case it's a building.. get the right one according its id from the BUILDING leaf
                    return (state.app.map.layersHashmap[composite.type] ||
                        state.app.map.layersHashmap.DEFAULT) as ILayersHashmap;
                }
                if (composite.type) {
                    // in the case it's a building.. get the right one according its id from the BUILDING leaf
                    return (state.app.map.layersHashmap[composite.type] ||
                        state.app.map.layersHashmap.DEFAULT) as ILayersHashmap;
                }
                // in the case that it has a TYPE and a SUBTTYPE like ZONE>Cameras
                // if (composite.type && composite.subType) {
                //     layer = state.app.map.layersHashmap[composite.type][composite.subType];
                // }
            }
            return state.app.map?.layersHashmap.DEFAULT as ILayersHashmap;
        } catch (error) {
            console.error('Composite', composite, state.app.map.layersHashmap);
            return undefined;
        }
    };
}

export function allLayersSelector(state: RootState): ILayersHashmap | undefined {
    return state.app.map?.layersHashmap;
}

export function selectedZonesSelector(state: RootState): string[] {
    return state.app.zones || [];
}

function findTopMostZone(
    state: RootState,
    composite: CompositeNode,
    selectedZones: string[]
): CompositeNode | undefined {
    let topZone: CompositeNode | undefined;

    composite?.parentIds?.forEach(parentId => {
        if (!topZone) {
            const parent = createRegionEntitySelector(parentId)(state) as CompositeNode;
            if (
                parent?.compositeType === CompositeType.CONTAINER /* && (parent as Region).type === RegionType.ZONE */
            ) {
                const zone = findTopMostZone(state, parent, selectedZones);
                if (zone) {
                    topZone = zone;
                } else if (parent.id && selectedZones.includes(parent.id)) {
                    topZone = parent;
                }
            }
        }
    });

    if (!topZone && composite.compositeType === CompositeType.CONTAINER) {
        const region = composite as Region;
        if (region?.id && selectedZones.includes(region.id)) {
            topZone = composite;
        }
    }

    return topZone;
}

/**
 * Return the right hash which contains the details about the layer and the source where the markers will live
 * @param composite
 * @returns
 */
export function createParentZoneSelector(composite: CompositeNode): (state: RootState) => CompositeNode | undefined {
    return (state: RootState): CompositeNode | undefined => {
        let topZone: CompositeNode | undefined;
        const selectedZones = selectedZonesSelector(state);
        const entity = createEntitySelector(composite)(state) as CompositeNode;
        if (entity) {
            topZone = findTopMostZone(state, entity, selectedZones);
        }

        return topZone;
    };
}

export const oneOfTypes = (node: CompositeNode, entityTypes: EntityType[]): boolean => {
    let rez = false;
    entityTypes.forEach(type => {
        if (CompositeNode.isEntityType(node, type)) {
            rez = true;
        }
    });

    return rez;
};

export function collectEntitiesByType(
    state: RootState,
    root: CompositeNode | undefined,
    entityTypes: EntityType[],
    includeZones?: boolean
): CompositeNode[] {
    const nodes: CompositeNode[] = [];

    (root?.children ? Object.values(root?.children) : []).forEach(child => {
        if (CompositeNode.nodeType(child) === CompositeType.CONTAINER) {
            if ((child as Region).type !== RegionType.ZONE || includeZones) {
                if (oneOfTypes(child, entityTypes)) {
                    const node = createEntitySelector(child)(state) as CompositeNode;
                    if (node) nodes.push(node);
                }

                const subnodes = collectEntitiesByType(state, child, entityTypes);
                subnodes.forEach(subnode => nodes.push(subnode));
            }
        } else if (oneOfTypes(child, entityTypes)) {
            const node = createEntitySelector(child)(state) as CompositeNode;
            if (node) nodes.push(node);
        }
    });

    return nodes;
}

type CompositeSelector = (state: RootState) => CompositeNode | undefined;
export function createCompositeSelector(regionId: string | undefined): CompositeSelector {
    return (state: RootState): CompositeNode | undefined => {
        const regionsRoot = compositeRootSelector(state);
        return regionId ? findComposite(regionsRoot, regionId) : undefined;
    };
}

function addEntityToCollection(entities: ISchoolElement[], entity: ISchoolElement): void {
    if (!entities.find(e => e.id === entity.id)) {
        entities.push(entity);
    }
}

type EntitiesSelector = (state: RootState) => ISchoolElement[];
export function createEntitiesSelector(
    id: string | undefined,
    entityTypes: EntityType[],
    includeZones?: boolean
): EntitiesSelector {
    return (state: RootState): ISchoolElement[] => {
        const entities: ISchoolElement[] = [];
        const composite = createCompositeSelector(id)(state);
        if (composite) {
            const entity = composite.id ? (createEntitySelector(composite)(state) as CompositeNode) : undefined;
            if (entity && oneOfTypes(entity, entityTypes)) {
                addEntityToCollection(entities, entity);
            }

            const nodes = collectEntitiesByType(state, composite, entityTypes, includeZones);
            nodes.forEach(node => addEntityToCollection(entities, node));
        }

        return entities.length ? entities : EMPTY_LIST;
    };
}

type SchoolRegionsSelector = (state: RootState) => Region[];
export function createSchoolRegionsSelector(schoolId: string, includeZones?: boolean): SchoolRegionsSelector {
    return (state: RootState): Region[] => {
        const school = createSchoolEntitySelector(schoolId)(state) as School;
        const entities = createEntitiesSelector(school?.region?.id, [EntityType.REGION], includeZones)(state);
        return entities.map(entity => entity as Region);
    };
}

export function createRegionsSelector(regionId: string | undefined): SchoolRegionsSelector {
    return (state: RootState): Region[] => {
        const entities = createEntitiesSelector(regionId, [EntityType.REGION])(state);
        return entities.map(entity => entity as Region);
    };
}

export function createParentRegionsSelector(regionId: string | undefined): SchoolRegionsSelector {
    return (state: RootState): Region[] => {
        const composite = createCompositeSelector(regionId)(state);
        if (composite && composite.parentIds) {
            const parents = composite.parentIds.map(id => createCompositeSelector(id)(state));
            return parents.map(entity => (entity as unknown) as Region);
        }

        return EMPTY_LIST;
    };
}

/**
 * Returns all root regions
 * @returns Region[]
 */
export function createSchoolRegionsTreeSelector(schoolId?: string): RegionSelector {
    return (state: RootState): Region => {
        // get all the schools
        const { schools } = state.domain.entities;
        const school = schoolId ? schools[schoolId] : null;
        return state.domain.entities.regions[school?.region?.id || ''];
    };
}

type SchoolDoorSelector = (state: RootState) => Door[];
export function createSchoolDoorsSelector(schoolId: string): SchoolDoorSelector {
    return (state: RootState): Door[] => {
        const school = createSchoolEntitySelector(schoolId)(state) as School;
        const entities = createEntitiesSelector(school?.region?.id, [EntityType.DOOR])(state);
        return entities.map(entity => entity as Door);
    };
}

type SchoolCameraSelector = (state: RootState) => Camera[];
export function createSchoolCamerasSelector(schoolId: string): SchoolCameraSelector {
    return (state: RootState): Camera[] => {
        const school = createSchoolEntitySelector(schoolId)(state) as School;
        const entities = createEntitiesSelector(school?.region?.id, [EntityType.CAMERA])(state);
        return entities.map(entity => entity as Camera);
    };
}

type SchoolDroneSelector = (state: RootState) => Drone[];
export function createSchoolDronesSelector(schoolId: string): SchoolDroneSelector {
    return (state: RootState): Drone[] => {
        const school = createSchoolEntitySelector(schoolId)(state) as School;
        const entities = createEntitiesSelector(school?.region?.id, [EntityType.DRONE])(state);
        return entities.map(entity => entity as Drone);
    };
}

type SchoolSpeakerSelector = (state: RootState) => Speaker[];
export function createSchoolSpeakersSelector(schoolId: string): SchoolSpeakerSelector {
    return (state: RootState): Speaker[] => {
        const school = createSchoolEntitySelector(schoolId)(state) as School;
        const entities = createEntitiesSelector(school?.region?.id, [EntityType.SPEAKER])(state);
        return entities.map(entity => entity as Speaker);
    };
}

export type SchoolElementSelector = (state: RootState) => ISchoolElement[];
export function createSchoolElementsSelector(schoolId: string): SchoolElementSelector {
    return (state: RootState): ISchoolElement[] => {
        const school = createSchoolEntitySelector(schoolId)(state) as School;
        return createEntitiesSelector(school?.region?.id, [
            EntityType.DOOR,
            EntityType.CAMERA,
            EntityType.SPEAKER,
            EntityType.UTILITY,
            EntityType.SAFETY,
            EntityType.DRONE,
            EntityType.RADIO,
        ])(state);
    };
}

export type SchoolZoneSelector = (state: RootState) => Zone[];
export function createSchoolZonesSelector(schoolRegionId: string): SchoolZoneSelector {
    return (state: RootState): Zone[] => {
        const schoolRegion = createCompositeSelector(schoolRegionId)(state);
        if (schoolRegion?.children) {
            return Object.values(schoolRegion?.children)
                .filter(
                    child =>
                        child.compositeType === CompositeType.CONTAINER && (child as Region).type === RegionType.ZONE
                )
                .map(child => child as Zone)
                .map((child: Zone) => {
                    child.color = child.props?.find(prop => prop.key === 'color')?.value || DEFAULT_ZONE_COLOR;
                    return child;
                });
        }

        return [];
    };
}

export const allRegionsSelector = createSelector(compositeRootSelector, (root: CompositeNode) => {
    const traverseTree = (node: CompositeNode, regions: CompositeNode[]): void => {
        if (CompositeNode.nodeType(node) === CompositeType.CONTAINER) {
            regions.push(node);

            if (node.children) {
                Object.values(node.children).forEach(subnode => traverseTree(subnode, regions));
            }
        }
    };

    const regions: CompositeNode[] = [];
    traverseTree(root, regions);

    return regions.reduce<CompositeNodesMap>(
        (cMap, current) => ({
            ...cMap,
            [current.id as string]: current,
        }),
        {}
    );
});

type BuildingFloorInfo = { floor: Region; selected: boolean };
export type BuildingFloorsSelector = (state: RootState) => BuildingFloorInfo[];
export function createBuildingFloorSelector(building: CompositeNode): BuildingFloorsSelector {
    return (state: RootState): BuildingFloorInfo[] => {
        let floors: BuildingFloorInfo[] = [];
        if (building?.children) {
            const selectedFloor = getSelectedFloorByBuilding(building.id || '')(state) as Region;

            floors = Object.values(building.children)
                .filter(c => c.type === RegionType.FLOOR)
                .map(c => {
                    return { floor: c as Region, selected: c.id === selectedFloor?.id };
                });
        }

        return floors;
    };
}
