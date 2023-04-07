/* eslint-disable class-methods-use-this */
import gql from 'graphql-tag';

import graphQlService from '@common/services/graphql.service';
import { cloneDeep } from '@common/utils/dom.utils';
import { EntityType } from 'app/utils/domain.constants';
import Radio from 'app/domain/radio';
import { PageRequest, QueryFilter, SearchResult, SortOrder } from 'app/domain/search';
import { CompositeNode } from 'app/domain/composite';
import { entityWithId, extractError } from './query.service';

export interface RadioCrudResponse {
    newRadio?: CompositeNode;
    updateRadio?: CompositeNode;
    deleteRadio?: CompositeNode;
}

export const fragments = {
    radioFragment: gql`
        fragment radioFragment on Radio {
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
};

class RadioDeviceService {
    queryRadiosWithFilter(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query Radios($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                queryRadios(filter: $filter, page: $page, sort: $sort) {
                    data {
                        ...radioFragment
                    }
                    numberOfItems
                    numberOfPages
                }
            }
            ${fragments.radioFragment}
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
                update.queryRadios?.data?.forEach((radio: Radio) => {
                    radio.entityType = EntityType.RADIO;
                });
                return update.queryRadios;
            });
    }

    queryRadios(sortColumn: string | null = 'name', sortOrder = 'asc'): Promise<RadioCrudResponse> {
        const payload = gql`
            query Radios($page: PageRequest, $sort: [SortOrder]) {
                radios(page: $page, sort: $sort) {
                    ...radioFragment
                }
            }
            ${fragments.radioFragment}
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
                update.radios.forEach((radio: Radio) => {
                    radio.entityType = EntityType.RADIO;
                });
                return update;
            });
    }

    createRadio(radio: any): Promise<RadioCrudResponse> {
        const entity = cloneDeep(radio, ['__typename', 'tags', 'school', 'regions', 'entityType']);
        const payload = gql`
            mutation NewRadio($radio: RadioInput!, $tags: [TagInput]) {
                newRadio(radio: $radio, tags: $tags) {
                    ...radioFragment
                }
            }
            ${fragments.radioFragment}
        `;

        entity.parentIds = radio.regions || [];
        const tags = radio.tags
            ? radio.tags.map((tag: any) => {
                  return entityWithId(tag);
              })
            : null;

        return graphQlService
            .mutate({
                variables: {
                    radio: entity,
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
                update.newRadio.entityType = EntityType.RADIO;
                return update;
            });
    }

    updateRadio(radio: any): Promise<RadioCrudResponse> {
        const entity = cloneDeep(radio, [
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
            mutation UpdateRadio($radio: RadioInput!, $tags: [TagInput]) {
                updateRadio(radio: $radio, tags: $tags) {
                    ...radioFragment
                }
            }
            ${fragments.radioFragment}
        `;

        entity.parentIds = radio.regions;
        const tags = radio.tags
            ? radio.tags.map((tag: any) => {
                  return entityWithId(tag);
              })
            : null;

        return graphQlService
            .mutate({
                variables: {
                    radio: entity,
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
                update.updateRadio.entityType = EntityType.RADIO;
                return update;
            });
    }

    deleteRadio(radioId: string): Promise<RadioCrudResponse> {
        const payload = gql`
            mutation DeleteRadio($radioId: String) {
                deleteRadio(radioId: $radioId) {
                    status
                    id
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    radioId,
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

const queryService = new RadioDeviceService();
export default queryService;
