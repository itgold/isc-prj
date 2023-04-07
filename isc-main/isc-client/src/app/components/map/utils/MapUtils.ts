import ISchoolElement from 'app/domain/school.element';
import Feature, { FeatureLike } from 'ol/Feature';
import Geometry from 'ol/geom/Geometry';
import Region from 'app/domain/region';
import GeoPoint from 'app/domain/geo.location';
import GeoPolygon from 'app/domain/geo.polygon';
import { CompositeNode, CompositeNodesMap, CompositeType } from 'app/domain/composite';
import * as Selectors from 'app/store/selectors';
import { DeviceState, EntityMap, RootState } from 'app/store/appState';
import School from 'app/domain/school';
import { NONE_ENTITY, EntityType, RegionType } from 'app/utils/domain.constants';
import VectorSource from 'ol/source/Vector';
import store from 'app/store';
import VectorLayer from 'ol/layer/Vector';
import { Icon, Style } from 'ol/style';
import { TreeItemData } from '@common/components/tree.dropdown';
import { IMapComponent } from '@common/components/map/map';
import _ from 'lodash';
import { publicPath } from 'app/utils/ui.constants';
import { resolveEntityTool } from '../markers/toolsList';
import { ILayerHash, ILayerHashmapChildren, ILayersHashmap } from '../SchoolMapComponent';

/**
 * Generate the Map marker (aka feature) for a single element `ISchoolElement`
 * @param element
 * @returns
 */
export const generateMapDataFromModel = (element: ISchoolElement): Feature<Geometry> | undefined => {
    const elementTool = resolveEntityTool(CompositeNode.entityType(element));

    const feature = elementTool?.fromModel ? elementTool.fromModel(element) : undefined;
    if (feature) {
        feature.setProperties({
            data: element,
            entityType: elementTool?.entityType || EntityType.UNKNOWN,
        });
    }
    return feature;
};

export function generateMapDataFromModels(elements: ISchoolElement[]): Feature<Geometry>[] {
    const mapData: Feature<Geometry>[] = [];

    elements
        .filter(e => CompositeNode.entityType(e))
        .forEach(element => {
            const mapDataFeature = generateMapDataFromModel(element);
            if (mapDataFeature) {
                mapData.push(mapDataFeature);
            }
        });
    return mapData;
}

export function findRegionsSchool(parentIds?: string[]): School | undefined {
    let schoolNode;

    const rootState = store.getState();
    const schools = Selectors.schoolsSelector(rootState);
    const schoolRegions: EntityMap<School> = {};
    schools.forEach(school => {
        const regionId = school?.region?.id;
        if (regionId) {
            schoolRegions[regionId] = school;
        }
    });

    let ids: string[] | undefined = parentIds;
    while (!schoolNode && ids && ids.length) {
        for (let idx = 0; idx < ids.length && !schoolNode; idx += 1) {
            if (schoolRegions[ids[idx]]) {
                schoolNode = schoolRegions[ids[idx]];
            }
        }

        const parent = Selectors.createRegionEntitySelector(ids[0])(rootState) as CompositeNode;
        ids = parent?.parentIds;
    }

    return schoolNode;
}

export const findParentSchool = (parentIds?: string[]): { id: string; name: string } => {
    let regionsSchool = parentIds ? findRegionsSchool(parentIds) : undefined;
    if (!regionsSchool) {
        const rootState = store.getState();
        const currentSchool = Selectors.currentSchoolSelector(rootState);
        Selectors.schoolsSelector(rootState).forEach(school => {
            if (school.id === currentSchool) {
                regionsSchool = school;
            }
        });
    }

    return regionsSchool
        ? { id: regionsSchool.id || NONE_ENTITY, name: regionsSchool.name || 'NONE' }
        : { id: NONE_ENTITY, name: 'NONE' };
};

export function schoolById(schoolId?: string): School | undefined {
    let schoolNode;

    const rootState = store.getState();
    const schools = Selectors.schoolsSelector(rootState);
    return schools.find(s => s.id === schoolId);
}

export const convertRegionsTree = (regionType: string, children: TreeItemData[], skipZones = true): TreeItemData[] => {
    const node: TreeItemData[] = [];

    // for each children of array
    children
        .filter(
            (childNode: TreeItemData) => childNode.compositeType && childNode.compositeType === CompositeType.CONTAINER
        )
        .filter((childNode: TreeItemData) => (skipZones ? (childNode as Region).type !== RegionType.ZONE : true))
        .forEach((childNode: TreeItemData) => {
            const region: TreeItemData = {
                ...childNode,
            };

            if (region.children) {
                region.children = convertRegionsTree(regionType, Object.values(region.children));
            }
            node.push(region);
        });
    return node;
};

function findParentPath(parentId: string, childId: string, rootState: RootState, paths: string[]): string[][] {
    if (!childId || childId === NONE_ENTITY) {
        return [[...paths]];
    }
    if (childId === parentId) {
        return [[...paths, childId]];
    }

    const parentPaths: string[][] = [];
    const child = Selectors.createRegionEntitySelector(childId)(rootState) as CompositeNode;
    if (child && child.parentIds && child.parentIds.length) {
        child.parentIds.forEach(id => {
            const ppp: string[][] = findParentPath(parentId, id, rootState, [...paths, childId]);
            ppp.forEach(pp => parentPaths.push(pp));
        });
    }

    return parentPaths;
}

export function findRegionParentPath(parentIds?: string[]): string[] {
    const paths: string[] = [];

    const school = findRegionsSchool(parentIds);
    const schoolRegionId = school?.region?.id;
    if (schoolRegionId && parentIds && parentIds.length) {
        const rootState = store.getState();
        for (let idx = 0; idx < parentIds.length; idx += 1) {
            const path: string[][] = findParentPath(schoolRegionId, parentIds[idx], rootState, []);
            path.forEach(p => paths.push(p.join('.')));
        }
    }

    return paths;
}

export function pathToRoot(
    parentIds: string[] | undefined,
    parentChain: Region[],
    state: RootState,
    includeSchool?: boolean
): Region[][] {
    const parentPaths: Region[][] = [];
    parentIds?.forEach(parentId => {
        const parent = Selectors.createRegionEntitySelector(parentId)(state) as Region;
        if (
            parent &&
            (includeSchool || (parent.type !== RegionType.SCHOOL && parent.type !== RegionType.SCHOOL_DISTRICT)) &&
            parent.type !== RegionType.ROOT
        ) {
            const chain = [...parentChain, parent];
            const grandParents: Region[][] = pathToRoot(parent.parentIds, chain, state, includeSchool);
            if (grandParents?.length) {
                grandParents.forEach(grandParent => parentPaths.push(grandParent));
            } else {
                parentPaths.push(chain);
            }
        }
    });

    return parentPaths;
}

export function pathsToRoot(parentIds: string[] | undefined, includeSchool?: boolean): Region[][] {
    return pathToRoot(parentIds, [], store.getState() as RootState, includeSchool);
}

const IGNORED_SUB_PATH = ['USD', 'HS'];

/**
 * Filter the region cell by text, if the current region is a children from a selected region or if its name matches with the typed filter value
 * @param value Current cell value
 * @param filterValue current filter applied
 * @param row Current row
 * @returns boolean
 */
export const filterRegion = (entityId: string, filterValue: string, row: ISchoolElement): boolean => {
    // 1. Parse filterValue -> extract parent chain names
    // 2. resolve all parent chains
    // 3. Match parent chains with filter chains

    // this is parent names from bottom child to top parent
    const parents = filterValue
        .split(/[->]/)
        .map(path => path.trim())
        .filter(path => path.length > 0)
        .filter(path => IGNORED_SUB_PATH.indexOf(path.toUpperCase()) < 1)
        .reverse();

    // this parents from bottom to top
    const parentPaths = pathToRoot(row.parentIds || [], [], store.getState() as RootState);

    const matchingParents = [];
    for (let i = 0; i < parentPaths.length; i += 1) {
        let parentChain = parentPaths[i];
        while (parentChain.length >= parents.length) {
            let nameIdx = 0;
            for (let j = 0; j < parentChain.length; j += 1) {
                const name = parentChain[j].name || '';
                if (name.toLowerCase().indexOf(parents[nameIdx]) >= 0) {
                    nameIdx += 1;
                    if (nameIdx === parents.length) {
                        matchingParents.push(parentChain[0]);
                        break;
                    }
                } else if (nameIdx > 0) {
                    break;
                }
            }

            if (!matchingParents.length && parentChain.length > 1) {
                parentChain = parentChain.splice(1);
            } else {
                break;
            }
        }

        if (matchingParents.length) {
            break; // single match is enough
        }
    }

    return !!matchingParents.length;
};

interface GeoData {
    id?: string;
    externalId?: string;
    geoLocation?: GeoPoint | null;
}
interface RegionGeoData extends GeoData {
    geoBoundaries?: GeoPolygon | null;
}

export function extractGeoData(schoolEntity: ISchoolElement): GeoData {
    const geoData: GeoData = (({ id, geoLocation }) => ({ id, geoLocation }))(schoolEntity);
    geoData.externalId = (schoolEntity as { externalId?: string }).externalId;
    return geoData;
}

export function extractRegionGeoData(schoolEntity: ISchoolElement): RegionGeoData {
    return (({ id, geoLocation, geoBoundaries }) => ({
        id,
        geoLocation,
        geoBoundaries,
    }))(schoolEntity);
}

export function purifyEntityData(schoolEntity: ISchoolElement): ISchoolElement {
    const { entityId, compositeType, ...cleanedUpEntity } = schoolEntity as any;
    return cleanedUpEntity;
}

/**
 * Returns all ids from the elements that belongs to a node
 * @param node CompositeNode[]
 * @param allIds string []
 * @returns string containing all ids
 */
export function getAllElementIdsByNode(
    node: CompositeNode[],
    allIds?: string[] | CompositeNode[],
    returnObject = false
): string[] {
    if (!allIds) allIds = [];
    node.forEach((element: CompositeNode) => {
        if (returnObject) (allIds as CompositeNode[]).push(element);
        else (allIds as string[]).push(element.id as string);
        const eChildren = Object.values(element.children as CompositeNodesMap);
        if (eChildren.length) getAllElementIdsByNode(eChildren, allIds);
    });
    return allIds as string[];
}

/**
 * Get the list of features by a list of string
 * @param source VectorSource
 * @param listIds string[]
 * @returns
 */
export function getFeaturesByIds(source: VectorSource<any>, listIds: string[]): Feature<Geometry>[] {
    return source.getFeatures().filter(feature => listIds.indexOf(feature.getProperties().data?.id) >= 0);
}

function containsRegion(regions: Region[], region: Region): boolean {
    if (regions && region) {
        if (regions.find(r => r.id === region.id)) {
            return true;
        }
    }

    return false;
}

/**
 * Find parent floor and building
 *
 * @param startIds List of region ids to start search with
 * @returns
 */
export function findParentFloor(
    entity: CompositeNode,
    selectedFloors: Region[]
): { floor: Region | undefined; building: Region | undefined } {
    let floorNode: Region | undefined;
    let selectedFloorNode: Region | undefined;
    let building: Region | undefined;

    const rootState = store.getState();

    let ids: string[] =
        entity.compositeType === CompositeType.CONTAINER && entity.id ? [entity.id] : entity.parentIds || [];
    while (!selectedFloorNode && ids && ids.length) {
        const newParentIds: string[] = [];
        for (let idx = 0; idx < ids.length && !selectedFloorNode; idx += 1) {
            const parent = Selectors.createRegionEntitySelector(ids[idx])(rootState) as CompositeNode;
            const region = parent as Region;
            const { type } = region;

            if (type && type === RegionType.FLOOR) {
                if (containsRegion(selectedFloors, region)) {
                    selectedFloorNode = region;
                    break;
                } else if (!floorNode) {
                    floorNode = region;
                }
            } else if (type && type === RegionType.BUILDING) {
                building = region;
            } else {
                parent?.parentIds?.forEach(id => {
                    newParentIds.push(id);
                });
            }
        }

        ids = newParentIds;
    }

    floorNode = selectedFloorNode || floorNode;
    if (floorNode) {
        floorNode.parentIds?.forEach(id => {
            const parent = Selectors.createRegionEntitySelector(id)(rootState) as CompositeNode;
            if ((parent as Region).type === RegionType.BUILDING) {
                building = parent as Region;
            }
        });
    }

    return {
        floor: floorNode,
        building,
    };
}

/**
 * Given an array of elements, returns a single list containing all layers name for each type
 * @param data
 * @returns
 */
export const getAllLayersByType = (data: Array<ISchoolElement | Region>): string[] => {
    const listLayersName: string[] = [];
    while (data.length > 0) {
        listLayersName.push((data[0] as ISchoolElement).entityType || ((data[0] as Region).type as string));
        data = data.filter(
            (e: ISchoolElement | Region) =>
                listLayersName.indexOf((e as ISchoolElement).entityType || ((e as Region).type as string)) === -1
        );
    }
    return listLayersName;
};

export const createLayer = (source: VectorSource<any>, index?: number, maxZoomLevel?: number): VectorLayer<any> => {
    const newVector = new VectorLayer<any>({
        source,
        style: (feature: FeatureLike): Style | Style[] => {
            // returns the style according the type
            const currentTool = resolveEntityTool(feature.getProperties()?.entityType);
            return currentTool?.style || [];
        },
    });

    if (index) newVector.setZIndex(index);
    if (maxZoomLevel && maxZoomLevel > 0) newVector.setMinZoom(maxZoomLevel);
    return newVector;
};

export const getNewSource = (): VectorSource<any> =>
    new VectorSource<any>({
        // read data from the geojson
        features: [],
        wrapX: false,
    });

export function findNode(entityType: EntityType, entityId: string): CompositeNode | undefined {
    const rootState = store.getState();
    const nodes = Selectors.createEntitiesSelector(entityId, [entityType])(rootState);
    return nodes?.length ? nodes[0] : undefined;
}

export function friendlyName(node: CompositeNode): string {
    if (node) {
        const prefix = node.compositeType === CompositeType.LEAF ? node.entityType : (node as Region).type;
        return `${prefix} ${node.name}`;
    }

    return '';
}

/**
 * Compares the data by checking the previous and next data further than checking whether the marker is already inserted or not into
 * the map
 * @param map Map
 * @param prev Previous data
 * @param next Next data
 * @returns Boolean
 */
export function compareMarkerData(map: IMapComponent, prev: ISchoolElement, next: ISchoolElement): boolean {
    if (!map) console.log('mapNotFound', prev);
    let shouldReRender = _.isEqual(prev, next) || !map;
    if (shouldReRender && map)
        shouldReRender = !!(map as IMapComponent).features.find(
            (f: Feature<Geometry>) => f.getProperties()?.data?.id === next.id
        );
    return shouldReRender;
}

export function removeFeature(elementId: string | undefined, map: IMapComponent, source: VectorSource<any>): void {
    const existingFeature = map.features.find((f: Feature<Geometry>) => f.getProperties().data?.id === elementId);
    if (existingFeature) {
        try {
            source.removeFeature(existingFeature);
        } catch (e) {
            console.error(e, existingFeature);
        }
    }
}

export function addFeature(schoolElement: ISchoolElement, source: VectorSource<any>): Feature<Geometry> | undefined {
    // Do render specific marker only once
    const existingFeature = source
        .getFeatures()
        .find((f: Feature<Geometry>) => f.getProperties().data?.id === schoolElement.id);
    let feature = existingFeature;
    if (!existingFeature) {
        const currentSchoolFeature = generateMapDataFromModel(schoolElement);
        if (currentSchoolFeature) {
            feature = currentSchoolFeature;
            source.addFeature(currentSchoolFeature as Feature<Geometry>);
        }
    }

    return feature;
}

export function buildingFloors(building: CompositeNode): { floor: Region; selected: boolean }[] {
    const rootState = store.getState();
    return Selectors.createBuildingFloorSelector(building)(rootState);
}

/**
 * Get region children nodes
 *
 * Important! This function must not be used from the components but regular way must be used (selectors or Redux::connect)
 * @param regionId Region id
 * @returns List of child composite nodes
 */
export function regionChildren(regionId: string): CompositeNode[] {
    const rootState = store.getState();
    const region = Selectors.createRegionEntitySelector(regionId)(rootState) as CompositeNode;
    return region?.children ? Object.values(region.children) : [];
}

export function resolveCompositeDeviceState(
    state: RootState,
    compositeDevice?: ISchoolElement
): {
    devices: ISchoolElement[];
    deviceState?: DeviceState;
} {
    const allStates: DeviceState = [];
    const devices: ISchoolElement[] = [];

    let entityType = CompositeNode.entityType(compositeDevice as CompositeNode);
    if (compositeDevice instanceof Region || (compositeDevice as Region).type === RegionType.POINT_REGION) {
        entityType = (compositeDevice as Region).subType as EntityType;

        const compositeCamRegion = compositeDevice as CompositeNode;
        (compositeCamRegion?.children ? Object.values(compositeCamRegion.children) : [])
            .filter(child => CompositeNode.entityType(child) === entityType)
            .map(child => child as ISchoolElement)
            .forEach(device => devices.push(device));
    } else {
        devices.push(compositeDevice as ISchoolElement);
    }

    devices?.forEach(device => {
        const cs = (Selectors.createEntityByIdSelector(device?.id || '', entityType)(state) as any)
            ?.state as DeviceState;
        cs?.forEach(s => allStates.push(s));
    });

    return { deviceState: allStates, devices };
}

export function getLayerHashMapByElementType(
    composite: CompositeNode,
    layersHashmap: ILayersHashmap
): ILayerHash | ILayerHashmapChildren | undefined {
    if (composite && layersHashmap) {
        try {
            if (composite.entityType && layersHashmap[composite.entityType]) {
                // initially, find the layer according the device type.
                return layersHashmap[composite.entityType] || layersHashmap.DEFAULT;
            }
            // does it has a type but not a subType like SCHOOL ELEMENT for example
            if (composite.type && !composite.subType) {
                // in the case it's a SCHOOL ELEMENT.. get the right one according its id from the SCHOOL ELEMENT leaf
                return layersHashmap[composite.type] || layersHashmap.DEFAULT;
            }
        } catch (error) {
            console.error('Layer not found for', error, composite);
        }
    }

    return layersHashmap ? layersHashmap.DEFAULT : undefined;
}

export function createIconStyle(icon: string, scale: number, opacity: number): Style {
    return new Style({
        image: new Icon({
            src: `${publicPath}/assets/images/mapElements/${icon}`,
            scale,
            opacity,
        }),
        zIndex: 2,
    });
}
