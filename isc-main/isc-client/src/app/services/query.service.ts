/* eslint-disable class-methods-use-this */
import gql from 'graphql-tag';

import graphQlService from '@common/services/graphql.service';
import { cloneDeep } from '@common/utils/dom.utils';
import { EntityType } from 'app/utils/domain.constants';
import SchoolDistrict from 'app/domain/school.district';
import School from 'app/domain/school';
import Door from 'app/domain/door';
import Camera from 'app/domain/camera';
import Speaker from 'app/domain/speaker';
import Drone from 'app/domain/drone';
import { IEntity } from 'app/domain/entity';
import { PageRequest, QueryFilter, SearchResult, SortOrder } from 'app/domain/search';
import { CompositeNode } from 'app/domain/composite';
import Zone from 'app/domain/zone';
import { ActionResult, DeviceActionPayload, DeviceActionResult } from 'app/components/map/deviceActions/IDeviceAction';
import CodeDictionary from 'app/domain/codeDictionary';
import { GraphQLError } from 'graphql';
import AlertTrigger from 'app/domain/alertTrigger';
import ISchoolElement from 'app/domain/school.element';
import Region, { RegionProp } from '../domain/region';
import Utility from '../domain/utility';
import Safety from '../domain/safety';

/**
 * Examples of queries.
 *
 * Example #1: Simple query without parameters:
 * <code>
 * const payload = gql`
 *      {
 *           districts {
 *               id
 *               name
 *           }
 *       }
 * `;
 * return graphQlService.query({ query: payload }).then(response => response.data);
 * </code>
 *
 * Example #2: Query with parameters and fragment:
 * <code>
 * const payload = gql`
 *       query Districts($page: PageRequest, $sort: [SortOrder]) {
 *               districts(page: $page, sort: $sort) {
 *                   id
 *                   name
 *                   ...schoolDistrictAddress
 *               }
 *           }
 *           ${fragments.schoolDistrictAddress}
 * `;
 *
 * return graphQlService
 *     .query({
 *         variables: {
 *             page: { page: 0, size: 10, },
 *             sort: [ { property: 'name', direction: 'ASC', }, ],
 *         },
 *         query: payload,
 *     })
 *     .then(response => response.data);
 * </code>
 *
 * Example #3: Mutation with parameters and fragment:
 * <code>
 * const payload = gql`
 *       mutation NewSchool($districtId: String!, $school: SchoolInput!) {
 *           newSchool(districtId: $districtId, school: $school) {
 *               id
 *           }
 *       }
 *   `;
 *
 *   return graphQlService
 *       .mutate({
 *           variables: {
 *               districtId,
 *               school: SchoolObject,
 *           },
 *           mutation: payload,
 *       })
 *       .then(response => response.data);
 * </code>
 */

export function extractError(errors: readonly GraphQLError[]): string | undefined {
    return errors?.length ? errors[0].extensions?.errorMessage : undefined;
}

export function entityWithId(entity: IEntity | undefined): IEntity {
    return entity?.id ? cloneDeep(entity, ['__typename']) : { id: entity };
}

export interface CrudResponse {
    newDoor?: CompositeNode;
    updateDoor?: CompositeNode;
    deleteDoor?: CompositeNode;

    newUtility?: CompositeNode;
    updateUtility?: CompositeNode;
    deleteUtility?: CompositeNode;

    newCamera?: CompositeNode;
    updateCamera?: CompositeNode;
    deleteCamera?: CompositeNode;

    newDrone?: CompositeNode;
    updateDrone?: CompositeNode;
    deleteDrone?: CompositeNode;

    newSpeaker?: CompositeNode;
    updateSpeaker?: CompositeNode;
    deleteSpeaker?: CompositeNode;

    newSafety?: CompositeNode;
    updateSafety?: CompositeNode;
    deleteSafety?: CompositeNode;

    newRegion?: CompositeNode;
    updateRegion?: CompositeNode;
    deleteRegion?: CompositeNode;

    newZone?: CompositeNode;
    updateZone?: CompositeNode;

    // =======================
    schools?: CompositeNode[];
    newSchool?: CompositeNode;
    updateSchool?: CompositeNode;
    deleteSchool?: CompositeNode;

    districts?: CompositeNode[];
    newDistrict?: CompositeNode;
    updateDistrict?: CompositeNode;
    deleteDistrict?: CompositeNode;

    tags?: CompositeNode[];
    newTag?: CompositeNode;
    updateTag?: CompositeNode;
    deleteTag?: CompositeNode;

    users?: CompositeNode[];
    newUser?: CompositeNode;
    updateUser?: CompositeNode;
    deleteUser?: CompositeNode;

    roles?: CompositeNode[];
    newRole?: CompositeNode;
    updateRole?: CompositeNode;
    deleteRole?: CompositeNode;

    events?: SearchResult;
    newAlertTrigger: AlertTrigger;
    updateAlertTrigger: AlertTrigger;
    deleteAlertTrigger?: CompositeNode;
}

export const fragments = {
    schoolDistrictAddress: gql`
        fragment schoolDistrictAddress on SchoolDistrict {
            address
            city
            state
            zipCode
            country
            updated
            region {
                id
                name
                geoLocation {
                    x
                    y
                }
                geoBoundaries {
                    points {
                        x
                        y
                    }
                }
                geoZoom
                geoRotation
            }
        }
    `,
    schoolFullDetails: gql`
        fragment schoolFullDetails on School {
            id
            name
            contactEmail
            status
            address
            city
            state
            zipCode
            country
            updated

            district {
                id
                name
            }

            region {
                id
                name
            }
        }
    `,
    regionFragment: gql`
        fragment regionFragment on Region {
            id
            name
            type
            subType
            updated
            parentIds

            geoLocation {
                x
                y
            }
            geoBoundaries {
                points {
                    x
                    y
                }
            }
            geoZoom
            geoRotation
            props {
                key
                value
            }
        }
    `,

    zoneFragment: gql`
        fragment zoneFragment on Zone {
            id
            name
            description
            type
            subType
            status
            props {
                key
                value
            }
            updated
            parentIds
            childIds
        }
    `,

    doorFragment: gql`
        fragment doorFragment on Door {
            id
            externalId
            name
            description
            status
            type
            updated
            parentIds

            geoLocation {
                x
                y
            }
            tags {
                id
                name
            }
            state {
                type
                value
                updated
            }
        }
    `,
    utilityFragment: gql`
        fragment utilityFragment on Utility {
            id
            externalId
            name
            description
            status
            type
            updated
            parentIds

            geoLocation {
                x
                y
            }
            tags {
                id
                name
            }
        }
    `,
    speakerFragment: gql`
        fragment speakerFragment on Speaker {
            id
            externalId
            name
            description
            status
            type
            updated
            parentIds

            geoLocation {
                x
                y
            }
            tags {
                id
                name
            }
            state {
                type
                value
                updated
            }
        }
    `,
    safetyFragment: gql`
        fragment safetyFragment on Safety {
            id
            externalId
            name
            description
            status
            type
            updated
            parentIds

            geoLocation {
                x
                y
            }
            tags {
                id
                name
            }
        }
    `,
    cameraFragment: gql`
        fragment cameraFragment on Camera {
            id
            externalId
            status
            name
            updated
            description
            parentIds

            geoLocation {
                x
                y
            }

            tags {
                id
                name
            }

            cameraGroup {
                groupId
                name
                description
            }
            live
            streams {
                streamId
                name
            }

            state {
                type
                value
                updated
            }
        }
    `,

    droneFragment: gql`
        fragment droneFragment on Drone {
            id
            externalId
            status
            type
            name
            updated
            description
            parentIds

            geoLocation {
                x
                y
            }

            tags {
                id
                name
            }

            state {
                type
                value
                updated
            }
        }
    `,

    userFragment: gql`
        fragment userFragment on User {
            id
            status
            firstName
            lastName
            email
            password
            imageUrl
            lastLogin
            activationDate
            updated
            roles {
                id
                name
            }
            schoolDistrict {
                id
                name
            }
        }
    `,
};

class QueryService {
    querySchoolsWithFilter(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query Schools($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                querySchools(filter: $filter, page: $page, sort: $sort) {
                    data {
                        ...schoolFullDetails
                    }
                    numberOfItems
                    numberOfPages
                }
            }
            ${fragments.schoolFullDetails}
        `;

        return graphQlService
            .query({
                variables: {
                    filter,
                    page,
                    sort,
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                return update.querySchools;
            });
    }

    querySchools(sortColumn: string | null = 'name', sortOrder = 'asc'): Promise<School[]> {
        const payload = gql`
            query Schools($page: PageRequest, $sort: [SortOrder]) {
                schools(page: $page, sort: $sort) {
                    ...schoolFullDetails
                }
            }
            ${fragments.schoolFullDetails}
        `;

        return graphQlService
            .query({
                variables: {
                    page: {
                        page: 0,
                        size: 1000,
                    },
                    sort: [
                        {
                            property: sortColumn,
                            direction: sortOrder.toUpperCase(),
                        },
                    ],
                },
                query: payload,
            })
            .then(response => response.data);
    }

    createSchool(school: School): Promise<School> {
        const entity = cloneDeep(school, ['__typename', 'tags', 'district', 'region']);
        const payload = gql`
            mutation NewSchool($school: SchoolInput!) {
                newSchool(school: $school) {
                    ...schoolFullDetails
                }
            }
            ${fragments.schoolFullDetails}
        `;

        entity.region = entityWithId(school.region);
        entity.schoolDistrict = entityWithId(school.district);

        return graphQlService
            .mutate({
                variables: {
                    school: entity,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    updateSchool(school: School): Promise<School> {
        const entity = cloneDeep(school, ['__typename', 'tags', 'district', 'region', 'updated']);
        const payload = gql`
            mutation UpdateSchool($school: SchoolInput!) {
                updateSchool(school: $school) {
                    ...schoolFullDetails
                }
            }
            ${fragments.schoolFullDetails}
        `;

        entity.region = entityWithId(school.region);
        entity.schoolDistrict = entityWithId(school.district);

        return graphQlService
            .mutate({
                variables: {
                    school: entity,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    deleteSchool(schoolId: string): Promise<CrudResponse> {
        const payload = gql`
            mutation DeleteSchool($schoolId: String) {
                deleteSchool(schoolId: $schoolId) {
                    status
                    id
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    schoolId,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    querySchoolDistricts(sortColumn: string | null = 'name', sortOrder = 'asc'): Promise<SchoolDistrict[]> {
        const payload = gql`
            query Districts($page: PageRequest, $sort: [SortOrder]) {
                districts(page: $page, sort: $sort) {
                    id
                    name
                    status
                    contactEmail
                    ...schoolDistrictAddress
                }
            }
            ${fragments.schoolDistrictAddress}
        `;

        return graphQlService
            .query({
                variables: {
                    page: {
                        page: 0,
                        size: 100,
                    },
                    sort: [
                        {
                            property: sortColumn,
                            direction: sortOrder.toUpperCase(),
                        },
                    ],
                },
                query: payload,
            })
            .then(response => response.data);
    }

    queryDistrictsWithFilter(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query Districts($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                queryDistricts(filter: $filter, page: $page, sort: $sort) {
                    data {
                        id
                        name
                        status
                        contactEmail
                        ...schoolDistrictAddress
                    }
                    numberOfItems
                    numberOfPages
                }
            }
            ${fragments.schoolDistrictAddress}
        `;

        return graphQlService
            .query({
                variables: {
                    filter,
                    page,
                    sort,
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                return update.queryDistricts;
            });
    }

    createSchoolDistrict(district: SchoolDistrict): Promise<SchoolDistrict> {
        const entity = cloneDeep(district, ['__typename', 'tags', 'region']);
        const payload = gql`
            mutation NewDistrict($district: SchoolDistrictInput!) {
                newDistrict(district: $district) {
                    id
                    name
                    status
                    contactEmail
                    ...schoolDistrictAddress
                }
            }
            ${fragments.schoolDistrictAddress}
        `;

        entity.region = entityWithId(district.region);

        return graphQlService
            .mutate({
                variables: {
                    district: entity,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    updateSchoolDistrict(district: SchoolDistrict): Promise<SchoolDistrict> {
        const entity = cloneDeep(district, ['__typename', 'tags', 'region', 'updated']);
        const payload = gql`
            mutation UpdateDistrict($district: SchoolDistrictInput!) {
                updateDistrict(district: $district) {
                    id
                    name
                    status
                    contactEmail
                    ...schoolDistrictAddress
                }
            }
            ${fragments.schoolDistrictAddress}
        `;

        entity.region = entityWithId(district.region);

        return graphQlService
            .mutate({
                variables: {
                    district: entity,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    deleteSchoolDistrict(districtId: string): Promise<CrudResponse> {
        const payload = gql`
            mutation DeleteDistrict($districtId: String) {
                deleteDistrict(districtId: $districtId) {
                    status
                    id
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    districtId,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    queryTags(sortColumn: string | null = 'name', sortOrder = 'asc'): Promise<CrudResponse> {
        const payload = gql`
            query Tags($page: PageRequest, $sort: [SortOrder]) {
                tags(page: $page, sort: $sort) {
                    id
                    name
                }
            }
        `;

        return graphQlService
            .query({
                variables: {
                    page: {
                        page: 0,
                        size: 1000,
                    },
                    sort: [
                        {
                            property: sortColumn,
                            direction: sortOrder.toUpperCase(),
                        },
                    ],
                },
                query: payload,
            })
            .then(response => response.data?.tags || []);
        // .then(tags => tags.map((tag: any) => tag.name));
    }

    queryTagsWithFilter(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query Tags($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                queryTags(filter: $filter, page: $page, sort: $sort) {
                    data {
                        id
                        name
                    }
                    numberOfItems
                    numberOfPages
                }
            }
        `;

        return graphQlService
            .query({
                variables: {
                    filter,
                    page,
                    sort,
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                return update.queryTags;
            });
    }

    createTag(tag: any): Promise<CrudResponse> {
        const entity = cloneDeep(tag, ['__typename']);
        const payload = gql`
            mutation NewTag($tag: TagInput!) {
                newTag(tag: $tag) {
                    id
                    name
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    tag: entity,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    updateTag(tag: any): Promise<CrudResponse> {
        const entity = cloneDeep(tag, ['__typename']);
        const payload = gql`
            mutation UpdateTag($tag: TagInput!) {
                updateTag(tag: $tag) {
                    id
                    name
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    tag: entity,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    deleteTag(tagId: string): Promise<CrudResponse> {
        const payload = gql`
            mutation DeleteTag($tagId: String) {
                deleteTag(tagId: $tagId) {
                    status
                    id
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    tagId,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    queryDoors(sortColumn: string | null = 'name', sortOrder = 'asc'): Promise<CrudResponse> {
        const payload = gql`
            query Doors($page: PageRequest, $sort: [SortOrder]) {
                doors(page: $page, sort: $sort) {
                    ...doorFragment
                }
            }
            ${fragments.doorFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    page: {
                        page: 0,
                        size: 1000,
                    },
                    sort: [
                        {
                            property: sortColumn,
                            direction: sortOrder.toUpperCase(),
                        },
                    ],
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                update.doors.forEach((door: Door) => {
                    door.entityType = EntityType.DOOR;
                });
                return update;
            });
    }

    queryDoorsWithFilter(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query Doors($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                queryDoors(filter: $filter, page: $page, sort: $sort) {
                    data {
                        ...doorFragment
                    }
                    numberOfItems
                    numberOfPages
                }
            }
            ${fragments.doorFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    filter,
                    page,
                    sort,
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                update.queryDoors?.data?.forEach((door: Door) => {
                    door.entityType = EntityType.DOOR;
                });
                return update.queryDoors;
            });
    }

    createDoor(door: any): Promise<CrudResponse> {
        const entity = cloneDeep(door, ['__typename', 'tags', 'school', 'regions', 'entityType', 'state']);
        const payload = gql`
            mutation NewDoor($door: DoorInput!, $tags: [TagInput]) {
                newDoor(door: $door, tags: $tags) {
                    ...doorFragment
                }
            }
            ${fragments.doorFragment}
        `;

        entity.parentIds = door.regions || [];
        const tags = door.tags
            ? door.tags.map((tag: any) => {
                  return entityWithId(tag);
              })
            : null;

        return graphQlService
            .mutate({
                variables: {
                    door: entity,
                    tags,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            })
            .then(update => {
                if (update.newDoor) {
                    update.newDoor.entityType = EntityType.DOOR;
                }
                return update;
            });
    }

    updateDoor(door: any): Promise<CrudResponse> {
        const entity = cloneDeep(door, [
            '__typename',
            'tags',
            'school',
            'regions',
            'entityType',
            'updated',
            'state',
            'lastSyncTime',
        ]);
        const payload = gql`
            mutation UpdateDoor($door: DoorInput!, $tags: [TagInput]) {
                updateDoor(door: $door, tags: $tags) {
                    ...doorFragment
                }
            }
            ${fragments.doorFragment}
        `;

        entity.parentIds = door.regions;
        const tags = door.tags
            ? door.tags.map((tag: any) => {
                  return entityWithId(tag);
              })
            : null;

        return graphQlService
            .mutate({
                variables: {
                    door: entity,
                    tags,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            })
            .then(update => {
                update.updateDoor.entityType = EntityType.DOOR;
                return update;
            });
    }

    deleteDoor(doorId: string): Promise<CrudResponse> {
        const payload = gql`
            mutation DeleteDoor($doorId: String) {
                deleteDoor(doorId: $doorId) {
                    status
                    id
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    doorId,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    queryCamerasWithFilter(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query Cameras($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                queryCameras(filter: $filter, page: $page, sort: $sort) {
                    data {
                        ...cameraFragment
                    }
                    numberOfItems
                    numberOfPages
                }
            }
            ${fragments.cameraFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    filter,
                    page,
                    sort,
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                update.queryCameras?.data?.forEach((camera: Door) => {
                    camera.entityType = EntityType.CAMERA;
                });
                return update.queryCameras;
            });
    }

    queryCameras(sortColumn: string | null = 'name', sortOrder = 'asc'): Promise<CrudResponse> {
        const payload = gql`
            query Cameras($page: PageRequest, $sort: [SortOrder]) {
                cameras(page: $page, sort: $sort) {
                    ...cameraFragment
                }
            }
            ${fragments.cameraFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    page: {
                        page: 0,
                        size: 1000,
                    },
                    sort: [
                        {
                            property: sortColumn,
                            direction: sortOrder.toUpperCase(),
                        },
                    ],
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                update.cameras.forEach((camera: Camera) => {
                    camera.entityType = EntityType.CAMERA;
                });
                return update;
            });
    }

    createCamera(camera: any): Promise<CrudResponse> {
        const entity = cloneDeep(camera, [
            '__typename',
            'tags',
            'school',
            'regions',
            'entityType',
            'state',
            'cameraGroup',
            'live',
            'cameraServiceHost',
            'cameraServicePort',
            'cameraServiceSsl',
            'streams',
        ]);
        const payload = gql`
            mutation NewCamera($camera: CameraInput!, $tags: [TagInput]) {
                newCamera(camera: $camera, tags: $tags) {
                    ...cameraFragment
                }
            }
            ${fragments.cameraFragment}
        `;

        entity.parentIds = camera.regions || [];
        const tags = camera.tags
            ? camera.tags.map((tag: any) => {
                  return entityWithId(tag);
              })
            : null;

        return graphQlService
            .mutate({
                variables: {
                    camera: entity,
                    tags,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            })
            .then(update => {
                update.newCamera.entityType = EntityType.CAMERA;
                return update;
            });
    }

    updateCamera(camera: any): Promise<CrudResponse> {
        const entity = cloneDeep(camera, [
            '__typename',
            'tags',
            'school',
            'regions',
            'entityType',
            'updated',
            'state',
            'cameraGroup',
            'live',
            'cameraServiceHost',
            'cameraServicePort',
            'cameraServiceSsl',
            'streams',
            'lastSyncTime',
        ]);
        const payload = gql`
            mutation UpdateCamera($camera: CameraInput!, $tags: [TagInput]) {
                updateCamera(camera: $camera, tags: $tags) {
                    ...cameraFragment
                }
            }
            ${fragments.cameraFragment}
        `;

        entity.parentIds = camera.regions;
        const tags = camera.tags
            ? camera.tags.map((tag: any) => {
                  return entityWithId(tag);
              })
            : null;

        return graphQlService
            .mutate({
                variables: {
                    camera: entity,
                    tags,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            })
            .then(update => {
                update.updateCamera.entityType = EntityType.CAMERA;
                return update;
            });
    }

    deleteCamera(cameraId: string): Promise<CrudResponse> {
        const payload = gql`
            mutation DeleteCamera($cameraId: String) {
                deleteCamera(cameraId: $cameraId) {
                    status
                    id
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    cameraId,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    queryDrones(sortColumn: string | null = 'name', sortOrder = 'asc'): Promise<CrudResponse> {
        const payload = gql`
            query Drones($page: PageRequest, $sort: [SortOrder]) {
                drones(page: $page, sort: $sort) {
                    ...droneFragment
                }
            }
            ${fragments.droneFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    page: {
                        page: 0,
                        size: 1000,
                    },
                    sort: [
                        {
                            property: sortColumn,
                            direction: sortOrder.toUpperCase(),
                        },
                    ],
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                update.drones.forEach((drone: Drone) => {
                    drone.entityType = EntityType.DRONE;
                });
                return update;
            });
    }

    queryDronesWithFilter(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query Drones($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                queryDrones(filter: $filter, page: $page, sort: $sort) {
                    data {
                        ...droneFragment
                    }
                    numberOfItems
                    numberOfPages
                }
            }
            ${fragments.droneFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    filter,
                    page,
                    sort,
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                update.queryDrones?.data?.forEach((drone: Drone) => {
                    drone.entityType = EntityType.DRONE;
                });
                return update.queryDrones;
            });
    }

    createDrone(drone: any): Promise<CrudResponse> {
        const entity = cloneDeep(drone, ['__typename', 'tags', 'school', 'regions', 'entityType', 'state']);
        const payload = gql`
            mutation NewDrone($drone: DroneInput!, $tags: [TagInput]) {
                newDrone(drone: $drone, tags: $tags) {
                    ...droneFragment
                }
            }
            ${fragments.droneFragment}
        `;

        entity.parentIds = drone.regions || [];
        const tags = drone.tags
            ? drone.tags.map((tag: any) => {
                  return entityWithId(tag);
              })
            : null;

        return graphQlService
            .mutate({
                variables: {
                    drone: entity,
                    tags,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            })
            .then(update => {
                update.newDrone.entityType = EntityType.DRONE;
                return update;
            });
    }

    updateDrone(drone: any): Promise<CrudResponse> {
        const entity = cloneDeep(drone, [
            '__typename',
            'tags',
            'school',
            'regions',
            'entityType',
            'updated',
            'state',
            'lastSyncTime',
        ]);
        const payload = gql`
            mutation UpdateDrone($drone: DroneInput!, $tags: [TagInput]) {
                updateDrone(drone: $drone, tags: $tags) {
                    ...droneFragment
                }
            }
            ${fragments.droneFragment}
        `;

        entity.parentIds = drone.regions;
        const tags = drone.tags
            ? drone.tags.map((tag: any) => {
                  return entityWithId(tag);
              })
            : null;

        return graphQlService
            .mutate({
                variables: {
                    drone: entity,
                    tags,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            })
            .then(update => {
                update.updateDrone.entityType = EntityType.DRONE;
                return update;
            });
    }

    deleteDrone(droneId: string): Promise<CrudResponse> {
        const payload = gql`
            mutation DeleteDrone($droneId: String) {
                deleteDrone(droneId: $droneId) {
                    status
                    id
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    droneId,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    querySpeakers(sortColumn: string | null = 'name', sortOrder = 'asc'): Promise<CrudResponse> {
        const payload = gql`
            query Speakers($page: PageRequest, $sort: [SortOrder]) {
                speakers(page: $page, sort: $sort) {
                    ...speakerFragment
                }
            }
            ${fragments.speakerFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    page: {
                        page: 0,
                        size: 1000,
                    },
                    sort: [
                        {
                            property: sortColumn,
                            direction: sortOrder.toUpperCase(),
                        },
                    ],
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                update.speakers.forEach((speaker: Speaker) => {
                    speaker.entityType = EntityType.SPEAKER;
                });
                return update;
            });
    }

    querySpeakersWithFilter(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query Speakers($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                querySpeakers(filter: $filter, page: $page, sort: $sort) {
                    data {
                        ...speakerFragment
                    }
                    numberOfItems
                    numberOfPages
                }
            }
            ${fragments.speakerFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    filter,
                    page,
                    sort,
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                update.querySpeakers?.data?.forEach((speaker: Speaker) => {
                    speaker.entityType = EntityType.SPEAKER;
                });
                return update.querySpeakers;
            });
    }

    createSpeaker(speaker: any): Promise<CrudResponse> {
        const entity = cloneDeep(speaker, ['__typename', 'tags', 'school', 'regions', 'entityType', 'state']);
        const payload = gql`
            mutation NewSpeaker($speaker: SpeakerInput!, $tags: [TagInput]) {
                newSpeaker(speaker: $speaker, tags: $tags) {
                    ...speakerFragment
                }
            }
            ${fragments.speakerFragment}
        `;

        entity.parentIds = speaker.regions || [];
        const tags = speaker.tags
            ? speaker.tags.map((tag: any) => {
                  return entityWithId(tag);
              })
            : null;

        return graphQlService
            .mutate({
                variables: {
                    speaker: entity,
                    tags,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            })
            .then(update => {
                update.newSpeaker.entityType = EntityType.SPEAKER;
                return update;
            });
    }

    updateSpeaker(speaker: any): Promise<CrudResponse> {
        const entity = cloneDeep(speaker, [
            '__typename',
            'tags',
            'school',
            'regions',
            'entityType',
            'updated',
            'state',
            'lastSyncTime',
        ]);
        const payload = gql`
            mutation UpdateSpeaker($speaker: SpeakerInput!, $tags: [TagInput]) {
                updateSpeaker(speaker: $speaker, tags: $tags) {
                    ...speakerFragment
                }
            }
            ${fragments.speakerFragment}
        `;

        entity.parentIds = speaker.regions;
        const tags = speaker.tags
            ? speaker.tags.map((tag: any) => {
                  return entityWithId(tag);
              })
            : null;

        return graphQlService
            .mutate({
                variables: {
                    speaker: entity,
                    tags,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            })
            .then(update => {
                update.updateSpeaker.entityType = EntityType.SPEAKER;
                return update;
            });
    }

    deleteSpeaker(speakerId: string): Promise<CrudResponse> {
        const payload = gql`
            mutation DeleteSpeaker($speakerId: String) {
                deleteSpeaker(speakerId: $speakerId) {
                    status
                    id
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    speakerId,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    queryRegions(sortColumn: string | null = 'name', sortOrder = 'asc'): Promise<Region[]> {
        const payload = gql`
            query Regions($page: PageRequest, $sort: [SortOrder]) {
                regions(page: $page, sort: $sort) {
                    ...regionFragment
                }
            }
            ${fragments.regionFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    page: {
                        page: 0,
                        size: 1000,
                    },
                    sort: [
                        {
                            property: sortColumn,
                            direction: sortOrder.toUpperCase(),
                        },
                    ],
                },
                query: payload,
            })
            .then(response => response.data);
    }

    queryRegionsWithFilter(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query Regions($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                queryRegions(filter: $filter, page: $page, sort: $sort) {
                    data {
                        ...regionFragment
                    }
                    numberOfItems
                    numberOfPages
                }
            }
            ${fragments.regionFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    filter,
                    page,
                    sort,
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                update.queryRegions?.data?.forEach((region: ISchoolElement) => {
                    region.entityType = EntityType.REGION;
                });
                return update.queryRegions;
            });
    }

    queryRegionsDropdown(): Promise<Region[]> {
        return this.queryRegions();
    }

    createRegion(region: any): Promise<CrudResponse> {
        const entity = cloneDeep(region, [
            '__typename',
            'school',
            'regions',
            'entityType',
            'floorNumber',
            'floorHeight',
            'state',
        ]);
        // map the right properties when there's a boundary set
        if (region.geoBoundaries) {
            entity.geoBoundaries = {
                points: region.geoBoundaries.points.map((points: any) => ({ x: points.x, y: points.y })),
            };
        }

        const payload = gql`
            mutation NewRegion($region: RegionInput!) {
                newRegion(region: $region) {
                    ...regionFragment
                }
            }
            ${fragments.regionFragment}
        `;

        entity.parentIds = region.regions || region.parentIds || [];
        entity.props = region.props || [];
        if (region.floorNumber) {
            entity.props.push({ key: 'floorNumber', value: region.floorNumber });
        }
        if (region.floorHeight) {
            entity.props.push({ key: 'floorHeight', value: region.floorHeight });
        }

        return graphQlService
            .mutate({
                variables: {
                    region: entity,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    updateRegion(region: any): Promise<CrudResponse> {
        const entity = cloneDeep(region, [
            '__typename',
            'school',
            'regions',
            'entityType',
            'updated',
            'status',
            'children',
            'compositeType',
            'floorNumber',
            'floorHeight',
            'state',
            'lastSyncTime',
        ]);

        // exclude all unneeded fields
        if (region.geoBoundaries)
            entity.geoBoundaries = {
                points: region.geoBoundaries.points.map((points: any) => ({ x: points.x, y: points.y })),
            };

        entity.parentIds = region.regions || region.parentIds;
        entity.props = region.props;
        if (!entity.props?.length && (region.floorNumber || region.floorHeight)) {
            entity.props = [];
        }
        if (region.floorNumber) {
            entity.props = entity.props.filter((p: RegionProp) => p.key !== 'floorNumber');
            entity.props.push({ key: 'floorNumber', value: region.floorNumber });
        }
        if (region.floorHeight) {
            entity.props = entity.props.filter((p: RegionProp) => p.key !== 'floorHeight');
            entity.props.push({ key: 'floorHeight', value: region.floorHeight });
        }

        const payload = gql`
            mutation UpdateRegion($region: RegionInput!) {
                updateRegion(region: $region) {
                    ...regionFragment
                }
            }
            ${fragments.regionFragment}
        `;

        return graphQlService
            .mutate({
                variables: {
                    region: entity,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    upsertRegionByName(region: any): Promise<CrudResponse> {
        const entity = cloneDeep(region, [
            '__typename',
            'school',
            'regions',
            'entityType',
            'floorNumber',
            'floorHeight',
            'state',
        ]);
        // map the right properties when there's a boundary set
        if (region.geoBoundaries) {
            entity.geoBoundaries = {
                points: region.geoBoundaries.points.map((points: any) => ({ x: points.x, y: points.y })),
            };
        }

        const payload = gql`
            mutation UpdateRegionByName($region: RegionInput!) {
                updateRegionByName(region: $region) {
                    ...regionFragment
                }
            }
            ${fragments.regionFragment}
        `;

        entity.parentIds = region.regions || region.parentIds || [];
        entity.props = region.props;
        if (!entity.props?.length && (region.floorNumber || region.floorHeight)) {
            entity.props = [];
        }
        if (region.floorNumber) {
            entity.props = entity.props.filter((p: RegionProp) => p.key !== 'floorNumber');
            entity.props.push({ key: 'floorNumber', value: region.floorNumber });
        }
        if (region.floorHeight) {
            entity.props = entity.props.filter((p: RegionProp) => p.key !== 'floorHeight');
            entity.props.push({ key: 'floorHeight', value: region.floorHeight });
        }

        return graphQlService
            .mutate({
                variables: {
                    region: entity,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            })
            .then(data => {
                data.newRegion = data.updateRegionByName;
                return data;
            });
    }

    deleteRegion(regionId: string, moveChildrenTo?: string | null): Promise<CrudResponse> {
        const payload = gql`
            mutation DeleteRegion($regionId: String, $moveChildrenTo: String) {
                deleteRegion(regionId: $regionId, moveChildrenTo: $moveChildrenTo) {
                    status
                    id
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    regionId,
                    moveChildrenTo,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    queryUsers(sortColumn: string | null = 'lastName', sortOrder = 'asc'): Promise<CrudResponse> {
        const payload = gql`
            query User($page: PageRequest, $sort: [SortOrder]) {
                users(page: $page, sort: $sort) {
                    ...userFragment
                }
            }
            ${fragments.userFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    page: {
                        page: 0,
                        size: 1000,
                    },
                    sort: [
                        {
                            property: sortColumn,
                            direction: sortOrder.toUpperCase(),
                        },
                    ],
                },
                query: payload,
            })
            .then(response => response.data);
    }

    queryUsersWithFilter(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query Users($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                queryUsers(filter: $filter, page: $page, sort: $sort) {
                    data {
                        ...userFragment
                    }
                    numberOfItems
                    numberOfPages
                }
            }
            ${fragments.userFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    filter,
                    page,
                    sort,
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                return update.queryUsers;
            });
    }

    createUser(user: any): Promise<CrudResponse> {
        const entity = cloneDeep(user, [
            '__typename',
            'tags',
            'lastLogin',
            'activationDate',
            'schoolDistrict',
            'roles',
            'updated',
        ]);
        const payload = gql`
            mutation NewUser($user: UserInput!) {
                newUser(user: $user) {
                    ...userFragment
                }
            }
            ${fragments.userFragment}
        `;

        entity.schoolDistrict = entityWithId(user.schoolDistrict);
        if (user.roles) {
            entity.roles = user.roles.map((role: any) => {
                return entityWithId(role);
            });
        }

        return graphQlService
            .mutate({
                variables: {
                    user: entity,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    updateUser(user: any): Promise<CrudResponse> {
        const entity = cloneDeep(user, [
            '__typename',
            'tags',
            'lastLogin',
            'activationDate',
            'schoolDistrict',
            'roles',
            'updated',
        ]);
        const payload = gql`
            mutation UpdateUser($user: UserInput!) {
                updateUser(user: $user) {
                    ...userFragment
                }
            }
            ${fragments.userFragment}
        `;

        entity.schoolDistrict = entityWithId(user.schoolDistrict);
        if (user.roles) {
            entity.roles = user.roles.map((role: any) => {
                return entityWithId(role);
            });
        }

        return graphQlService
            .mutate({
                variables: {
                    user: entity,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    deleteUser(userId: string): Promise<CrudResponse> {
        const payload = gql`
            mutation DeleteUser($userId: String) {
                deleteUser(userId: $userId) {
                    status
                    id
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    userId,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    queryExternalUsersWithFilter(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query ExternalUsers($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                queryExternalUsers(filter: $filter, page: $page, sort: $sort) {
                    data {
                        id
                        title
                        firstName
                        lastName
                        status
                        phoneNumber
                        externalId
                        schoolSite
                        officialJobTitle
                        idFullName
                        idNumber
                        officeClass
                        updated
                        source
                    }
                    numberOfItems
                    numberOfPages
                }
            }
        `;

        return graphQlService
            .query({
                variables: {
                    filter,
                    page,
                    sort,
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                return update.queryExternalUsers;
            });
    }

    queryRoles(): Promise<CrudResponse> {
        const payload = gql`
            query Role($page: PageRequest, $sort: [SortOrder]) {
                roles(page: $page, sort: $sort) {
                    id
                    name
                }
            }
        `;

        return graphQlService
            .query({
                variables: {
                    page: {
                        page: 0,
                        size: 1000,
                    },
                    sort: [
                        {
                            property: 'name',
                            direction: 'ASC',
                        },
                    ],
                },
                query: payload,
            })
            .then(response => response.data);
    }

    queryZonesWithFilter(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query Zones($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                queryZones(filter: $filter, page: $page, sort: $sort) {
                    data {
                        ...zoneFragment
                    }
                    numberOfItems
                    numberOfPages
                }
            }
            ${fragments.zoneFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    filter,
                    page,
                    sort,
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                return update.queryZones;
            });
    }

    createZone(zone: Zone): Promise<CrudResponse> {
        const entity = cloneDeep(zone, ['__typename', 'school', 'regions', 'entityType', 'state']);
        const payload = gql`
            mutation NewZone($zone: ZoneInput!) {
                newZone(zone: $zone) {
                    ...zoneFragment
                }
            }
            ${fragments.zoneFragment}
        `;

        entity.parentIds = zone.parentIds || [];
        entity.childIds = zone.childIds || [];
        entity.props = zone.props || [];

        return graphQlService
            .mutate({
                variables: {
                    zone: entity,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    updateZone(zone: Zone): Promise<CrudResponse> {
        const entity = cloneDeep(zone, [
            '__typename',
            'school',
            'regions',
            'entityType',
            'updated',
            'children',
            'compositeType',
            'state',
        ]);

        const payload = gql`
            mutation UpdateZone($zone: ZoneInput!) {
                updateZone(zone: $zone) {
                    ...zoneFragment
                }
            }
            ${fragments.zoneFragment}
        `;

        entity.parentIds = zone.parentIds || [];
        entity.childIds = zone.childIds || [];
        entity.props = zone.props || [];

        return graphQlService
            .mutate({
                variables: {
                    zone: entity,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    deviceAction(actionPayload: DeviceActionPayload): Promise<DeviceActionResult> {
        const payload = gql`
            mutation DeviceAction($action: DeviceAction!) {
                deviceAction(action: $action) {
                    status
                    errors {
                        code
                        message
                    }
                }
            }
        `;

        return new Promise<DeviceActionResult>((resolve, reject) => {
            graphQlService
                .mutate({
                    variables: {
                        action: actionPayload,
                    },
                    mutation: payload,
                })
                .then(response => response.data.deviceAction)
                .then(actionResult => {
                    if (actionResult?.status === ActionResult.SUCCESS) {
                        resolve(actionResult);
                    } else {
                        reject(actionResult);
                    }
                })
                .catch(error => {
                    const errorResult = {
                        status: ActionResult.FAILURE,
                        errors: [{ code: 'SERVER_ERROR', message: error.toString() }],
                    };

                    reject(errorResult);
                });
        });
    }

    queryDeviceCodes(): Promise<CodeDictionary[]> {
        const payload = gql`
            query DeviceCodes {
                deviceCodes {
                    name
                    value {
                        code
                        shortName
                        description
                    }
                }
            }
        `;

        return graphQlService
            .query({
                query: payload,
            })
            .then(response => response.data?.deviceCodes || []);
    }

    queryUtilities(sortColumn: string | null = 'name', sortOrder = 'asc'): Promise<CrudResponse> {
        const payload = gql`
            query Utilities($page: PageRequest, $sort: [SortOrder]) {
                utilities(page: $page, sort: $sort) {
                    ...utilityFragment
                }
            }
            ${fragments.utilityFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    page: {
                        page: 0,
                        size: 1000,
                    },
                    sort: [
                        {
                            property: sortColumn,
                            direction: sortOrder.toUpperCase(),
                        },
                    ],
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                update.safeties.forEach((safety: Safety) => {
                    safety.entityType = EntityType.SAFETY;
                });
                return update;
            });
    }

    queryUtilitiesWithFilter(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query Utilities($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                queryUtilities(filter: $filter, page: $page, sort: $sort) {
                    data {
                        ...utilityFragment
                    }
                    numberOfItems
                    numberOfPages
                }
            }
            ${fragments.utilityFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    filter,
                    page,
                    sort,
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                update.queryUtilities?.data?.forEach((utility: Utility) => {
                    utility.entityType = EntityType.UTILITY;
                });
                return update.queryUtilities;
            });
    }

    createUtility(utility: any): Promise<CrudResponse> {
        const entity = cloneDeep(utility, ['__typename', 'tags', 'school', 'regions', 'entityType', 'state']);
        const payload = gql`
            mutation NewUtility($utility: UtilityInput!, $tags: [TagInput]) {
                newUtility(utility: $utility, tags: $tags) {
                    ...utilityFragment
                }
            }
            ${fragments.utilityFragment}
        `;

        entity.parentIds = utility.regions || [];
        const tags = utility.tags
            ? utility.tags.map((tag: any) => {
                  return entityWithId(tag);
              })
            : null;

        return graphQlService
            .mutate({
                variables: {
                    utility: entity,
                    tags,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            })
            .then(update => {
                if (update.newUtility) {
                    update.newUtility.entityType = EntityType.UTILITY;
                }
                return update;
            });
    }

    updateUtility(utility: any): Promise<CrudResponse> {
        const entity = cloneDeep(utility, [
            '__typename',
            'tags',
            'school',
            'regions',
            'entityType',
            'updated',
            'state',
            'lastSyncTime',
        ]);
        const payload = gql`
            mutation UpdateUtility($utility: UtilityInput!, $tags: [TagInput]) {
                updateUtility(utility: $utility, tags: $tags) {
                    ...utilityFragment
                }
            }
            ${fragments.utilityFragment}
        `;

        entity.parentIds = utility.regions;
        const tags = utility.tags
            ? utility.tags.map((tag: any) => {
                  return entityWithId(tag);
              })
            : null;

        return graphQlService
            .mutate({
                variables: {
                    utility: entity,
                    tags,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            })
            .then(update => {
                update.updateUtility.entityType = EntityType.UTILITY;
                return update;
            });
    }

    deleteUtility(utilityId: string): Promise<CrudResponse> {
        const payload = gql`
            mutation DeleteUtility($utilityId: String) {
                deleteUtility(utilityId: $utilityId) {
                    status
                    id
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    utilityId,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    querySafeties(sortColumn: string | null = 'name', sortOrder = 'asc'): Promise<CrudResponse> {
        const payload = gql`
            query Safeties($page: PageRequest, $sort: [SortOrder]) {
                safeties(page: $page, sort: $sort) {
                    ...safetyFragment
                }
            }
            ${fragments.safetyFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    page: {
                        page: 0,
                        size: 1000,
                    },
                    sort: [
                        {
                            property: sortColumn,
                            direction: sortOrder.toUpperCase(),
                        },
                    ],
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                update.safeties.forEach((safety: Safety) => {
                    safety.entityType = EntityType.SAFETY;
                });
                return update;
            });
    }

    querySafetiesWithFilter(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query Safeties($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                querySafeties(filter: $filter, page: $page, sort: $sort) {
                    data {
                        ...safetyFragment
                    }
                    numberOfItems
                    numberOfPages
                }
            }
            ${fragments.safetyFragment}
        `;

        return graphQlService
            .query({
                variables: {
                    filter,
                    page,
                    sort,
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                update.querySafeties?.data?.forEach((safety: Safety) => {
                    safety.entityType = EntityType.SAFETY;
                });
                return update.querySafeties;
            });
    }

    createSafety(safety: any): Promise<CrudResponse> {
        const entity = cloneDeep(safety, ['__typename', 'tags', 'school', 'regions', 'entityType', 'state']);
        const payload = gql`
            mutation NewSafety($safety: SafetyInput!, $tags: [TagInput]) {
                newSafety(safety: $safety, tags: $tags) {
                    ...safetyFragment
                }
            }
            ${fragments.safetyFragment}
        `;

        entity.parentIds = safety.regions || [];
        const tags = safety.tags
            ? safety.tags.map((tag: any) => {
                  return entityWithId(tag);
              })
            : null;

        return graphQlService
            .mutate({
                variables: {
                    safety: entity,
                    tags,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            })
            .then(update => {
                if (update.newSafety) {
                    update.newSafety.entityType = EntityType.SAFETY;
                }
                return update;
            });
    }

    updateSafety(safety: any): Promise<CrudResponse> {
        const entity = cloneDeep(safety, [
            '__typename',
            'tags',
            'school',
            'regions',
            'entityType',
            'updated',
            'state',
            'lastSyncTime',
        ]);
        const payload = gql`
            mutation UpdateSafety($safety: SafetyInput!, $tags: [TagInput]) {
                updateSafety(safety: $safety, tags: $tags) {
                    ...safetyFragment
                }
            }
            ${fragments.safetyFragment}
        `;

        entity.parentIds = safety.regions;
        const tags = safety.tags
            ? safety.tags.map((tag: any) => {
                  return entityWithId(tag);
              })
            : null;

        return graphQlService
            .mutate({
                variables: {
                    safety: entity,
                    tags,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            })
            .then(update => {
                update.updateSafety.entityType = EntityType.SAFETY;
                return update;
            });
    }

    deleteSafety(safetyId: string): Promise<CrudResponse> {
        const payload = gql`
            mutation DeleteSafety($safetyId: String) {
                deleteSafety(safetyId: $safetyId) {
                    status
                    id
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    safetyId,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }
}

const queryService = new QueryService();
export default queryService;
