import { DeviceState } from 'app/store/appState';
import { oneOfTypes } from 'app/store/selectors';
import { EntityType, RegionType } from 'app/utils/domain.constants';
import GeoPoint from './geo.location';
import GeoPolygon from './geo.polygon';
import Region from './region';
import ISchoolElement from './school.element';
import { ZoneType } from './zone';

export interface CompositeNodesMap {
    [id: string]: CompositeNode;
}

export enum CompositeType {
    CONTAINER = 'CONTAINER',
    LEAF = 'LEAF',
}

export function nodeType(compositeType: CompositeType | string | undefined): CompositeType | undefined {
    return !compositeType
        ? undefined
        : typeof compositeType === 'string'
        ? (compositeType as CompositeType)
        : compositeType;
}

export class CompositeNode implements ISchoolElement {
    id?: string;

    parentIds?: string[];

    type?: string;

    name?: string;

    compositeType?: CompositeType | string;

    status?: 'ACTIVATED' | 'DEACTIVATED';

    children?: CompositeNodesMap;

    entityType?: EntityType | string;

    subType?: ZoneType;

    geoLocation?: GeoPoint | null;

    geoBoundaries?: GeoPolygon | null;

    state?: DeviceState;

    /* Static helpers */
    public static entityType(element: CompositeNode | ISchoolElement): EntityType | undefined {
        let result: EntityType | undefined;
        if (element) {
            const node = element as CompositeNode;
            result =
                (node.entityType as EntityType) ||
                (CompositeNode.nodeType(node) === CompositeType.CONTAINER ? EntityType.REGION : undefined);

            if (EntityType.REGION === result && RegionType.POINT_REGION === (element as Region).type) {
                if ((element as Region).subType === EntityType.CAMERA) {
                    result = EntityType.COMPOSITECAM;
                }
            }
        }

        return result;
    }

    public static isDeviceEntity(entityType?: EntityType): boolean {
        if (entityType) {
            return [
                EntityType.DOOR,
                EntityType.CAMERA,
                EntityType.SPEAKER,
                EntityType.SAFETY,
                EntityType.DRONE,
                EntityType.RADIO,
                EntityType.UTILITY,
                EntityType.COMPOSITECAM,
            ].includes(entityType);
        }

        return false;
    }

    public static schoolElement(node: CompositeNode): ISchoolElement | undefined {
        if (CompositeNode.nodeType(node) === CompositeType.LEAF) {
            return (node as unknown) as ISchoolElement;
        }

        return undefined;
    }

    public static nodeType(node: CompositeNode): CompositeType | undefined {
        return !node.compositeType
            ? undefined
            : typeof node.compositeType === 'string'
            ? (node.compositeType as CompositeType)
            : node.compositeType;
    }

    public static leafs(node: CompositeNode, entityTypes: EntityType[]): ISchoolElement[] {
        const data: ISchoolElement[] = [];

        const children = node.children ? Object.values(node.children) : [];
        children.forEach(child => {
            if (CompositeNode.nodeType(child) === CompositeType.LEAF) {
                const schoolElement = CompositeNode.schoolElement(child);
                if (schoolElement && oneOfTypes(child, entityTypes)) {
                    data.push(schoolElement);
                }
            } else {
                const childDate = CompositeNode.leafs(child, entityTypes);
                childDate.forEach(subChild => data.push(subChild));
            }
        });

        return data;
    }

    private static convertEntityType(entityType: EntityType | undefined | string): EntityType | undefined {
        return entityType as EntityType;
    }

    public static isEntityType(node: CompositeNode, entityType: EntityType | undefined | string): boolean {
        if (node.entityType === entityType) {
            return true;
        }

        const deviceType = node.entityType || EntityType.REGION;
        const type1 = CompositeNode.convertEntityType(deviceType);
        const type2 = CompositeNode.convertEntityType(entityType);
        return type1 === type2;
    }
}
