import { Filter } from 'app/components/table/IscTableContext';
import ISchoolElement from 'app/domain/school.element';
import { ColumnFilter } from 'app/domain/search';
import MomentUtils from '@date-io/moment';
import Region from 'app/domain/region';

const momentUtils: MomentUtils = new MomentUtils();

export const NONE_ENTITY = '00000000-0000-0000-0000-000000000000';

export const ACTIVATED = 'ACTIVATED';
export const DEACTIVATED = 'DEACTIVATED';

export enum EntityType {
    REGION = 'REGION',
    DOOR = 'DOOR',
    CAMERA = 'CAMERA',
    SPEAKER = 'SPEAKER',
    SAFETY = 'SAFETY',
    DRONE = 'DRONE',
    RADIO = 'RADIO',

    SCHOOL = 'SCHOOL',
    DISTRICT = 'DISTRICT',
    TAG = 'TAG',
    USER = 'USER',
    ROLE = 'ROLE',
    UTILITY = 'UTILITY',

    UNKNOWN = 'UNKNOWN',
    COMPOSITECAM = 'compositeCam',
}

export type EntityCollectionType =
    | 'doors'
    | 'cameras'
    | 'speakers'
    | 'drones'
    | 'radios'
    | 'safeties'
    | 'regions'
    | 'schools'
    | 'districts'
    | 'tags'
    | 'users'
    | 'utilities'
    | 'roles'
    | 'compositecams';
export function convertToStateType(entityType: EntityType | string): EntityCollectionType {
    const type: EntityType = typeof entityType === 'string' ? (<any>EntityType)[entityType.toUpperCase()] : entityType;

    const entityCode = type.toString().toLowerCase();
    let rtn: string;

    // workaround for plurals
    if (entityCode.endsWith('y')) {
        const tmp = entityCode.split('y');
        rtn = `${tmp[0]}ies`;
    } else {
        rtn = `${type.toString().toLowerCase()}s`;
    }
    return rtn as EntityCollectionType;
}

export enum RegionType {
    BUILDING = 'BUILDING',
    FLOOR = 'FLOOR',
    ROOM = 'ROOM',
    WALL = 'WALL',
    STAIRS = 'STAIRS',
    ELEVATOR = 'ELEVATOR',
    UNKNOWN = 'UNKNOWN',
    ROOT = 'ROOT',
    SCHOOL = 'SCHOOL',
    SCHOOL_DISTRICT = 'SCHOOL_DISTRICT',
    ZONE = 'ZONE',
    POINT_REGION = 'POINT_REGION',
}
export function allRegionTypes(): RegionType[] {
    return [
        RegionType.BUILDING,
        RegionType.FLOOR,
        RegionType.ROOM,
        RegionType.WALL,
        RegionType.STAIRS,
        RegionType.ELEVATOR,
        RegionType.UNKNOWN,
        RegionType.SCHOOL,
        RegionType.SCHOOL_DISTRICT,
        RegionType.ROOT,
        RegionType.POINT_REGION,
    ];
}
export function allAssignableRegionTypes(): RegionType[] {
    return allRegionTypes().filter(
        type => type !== RegionType.SCHOOL && type !== RegionType.SCHOOL_DISTRICT && type !== RegionType.ROOT
    );
}

export const EMPTY_LIST: any[] = [];

export function resolveId(data: ISchoolElement): string {
    let { id } = data;
    if (NONE_ENTITY === id) {
        id = '';
    }

    return id || '';
}

export const ALLOWED_PARENT_TYPES = [
    RegionType.SCHOOL_DISTRICT,
    RegionType.SCHOOL,
    RegionType.BUILDING,
    RegionType.FLOOR,
    RegionType.ROOM,
    RegionType.STAIRS,
    RegionType.ELEVATOR,
];

export function hasGeoData(entity?: ISchoolElement): boolean {
    if (entity) {
        const isRegion = !entity.entityType || entity.entityType === EntityType.REGION;
        return !!(
            ((!isRegion || (entity as Region).type === RegionType.POINT_REGION) && entity.geoLocation) ||
            entity.geoBoundaries
        );
    }

    return false;
}

const DATE_LENGTH = 'YYYY-MM-DD'.length;
const DEFAULT_TIME = 'T00:00:00';

export function createQueryFilter(filter: Filter): ColumnFilter[] {
    const { moment } = momentUtils;
    const timezone = moment(new Date()).format('Z'); // '-0700';

    return filter
        ? Object.keys(filter).map(key => {
              if (key === 'created' || key === 'updated') {
                  const values = filter[key].indexOf(' ') > 0 ? filter[key].split(' ') : [filter[key]];
                  const valuesWithTimezone: string[] = [];

                  values.forEach(value => {
                      valuesWithTimezone.push(value.length === DATE_LENGTH ? value + DEFAULT_TIME + timezone : value);
                  });

                  return { key, value: valuesWithTimezone.join(' ') };
              }

              return { key, value: filter[key] };
          })
        : [];
}

export interface StringMap {
    [key: string]: string;
}

export interface ObjectMap<T = any> {
    [key: string]: T;
}

export const SHORT_SCHOOL_NAME: StringMap = {
    'Unified School District Region': 'USD',
    'High School Region': 'HS',
    'Mann Elementary School Region': 'MES',
    'Vista Elementary School Region': 'VES',
    'Rodeo Elementary School Region': 'RES',
    'Elementary School Region': 'ES',
};
