/* eslint-disable class-methods-use-this */
import gql from 'graphql-tag';
import { RestService } from '@common/services/rest.service';
import {
    Alert,
    AlertEvent,
    AlertStatus,
    AlertUpdateRequest,
    ApplicationEvent,
    EventPayload,
    resolveAlertType,
    TYPE_ALERT,
} from 'app/domain/deviceEvent';
import { PageRequest, QueryFilter, SearchResult, SortOrder } from 'app/domain/search';
import graphQlService from '@common/services/graphql.service';
import { cloneDeep } from '@common/utils/dom.utils';
import { CrudResponse, extractError } from './query.service';

export const fragments = {
    alertTriggerFragment: gql`
        fragment alertTriggerFragment on AlertTrigger {
            id
            name
            processorType
            active
            updated

            matchers {
                type
                body
                updated
            }
        }
    `,
};

interface UpdateAlertResponse {
    id: string;
    status: string;
}

class EventsService extends RestService {
    relatedEvents(correlationId: string): Promise<ApplicationEvent<EventPayload>[]> {
        return new Promise((resolve, reject) => {
            this.client
                .get(`/rest/events/relatedEvents/${correlationId}`, {
                    headers: {
                        ...this.defaultHeaders(),
                    },
                })
                .then(rez => resolve((rez as unknown) as ApplicationEvent<EventPayload>[]))
                .catch(rez => reject(rez));
        });
    }

    queryAlertTriggersWithFilter(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query AlertTriggers($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                queryAlertTriggers(filter: $filter, page: $page, sort: $sort) {
                    data {
                        ...alertTriggerFragment
                    }
                    numberOfItems
                    numberOfPages
                }
            }
            ${fragments.alertTriggerFragment}
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
                return update.queryAlertTriggers;
            });
    }

    alertTriggerProcessors(): Promise<string[]> {
        return new Promise((resolve, reject) => {
            this.client
                .get('/rest/events/alertTriggerProcessors', {
                    headers: {
                        ...this.defaultHeaders(),
                    },
                })
                .then(rez => resolve((rez as unknown) as string[]))
                .catch(rez => reject(rez));
        });
    }

    createAlertTrigger(alertTrigger: any): Promise<CrudResponse> {
        const entity = cloneDeep(alertTrigger, ['__typename', 'updated']);

        const payload = gql`
            mutation NewAlertTrigger($alertTrigger: AlertTriggerInput!) {
                newAlertTrigger(alertTrigger: $alertTrigger) {
                    ...alertTriggerFragment
                }
            }
            ${fragments.alertTriggerFragment}
        `;

        entity.matchers = alertTrigger.matchers;
        return graphQlService
            .mutate({
                variables: {
                    alertTrigger: entity,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    updateAlertTrigger(alertTrigger: any): Promise<CrudResponse> {
        const entity = cloneDeep(alertTrigger, ['__typename', 'updated']);
        const payload = gql`
            mutation UpdateAlertTrigger($alertTrigger: AlertTriggerInput!) {
                updateAlertTrigger(alertTrigger: $alertTrigger) {
                    ...alertTriggerFragment
                }
            }
            ${fragments.alertTriggerFragment}
        `;

        entity.matchers = alertTrigger.matchers;
        return graphQlService
            .mutate({
                variables: {
                    alertTrigger: entity,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    deleteAlertTrigger(alertTriggerId: string): Promise<CrudResponse> {
        const payload = gql`
            mutation DeleteAlertTrigger($alertTriggerId: String) {
                deleteAlertTrigger(alertTriggerId: $alertTriggerId) {
                    status
                    id
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    alertTriggerId,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data;
            });
    }

    queryEvents(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query Events($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                events(filter: $filter, page: $page, sort: $sort) {
                    data {
                        deviceId
                        eventId
                        correlationId
                        referenceId
                        eventTime
                        receivedTime
                        type
                        payload {
                            type
                            deviceId
                            code
                            description
                            notes
                            user
                        }
                        schoolId
                        districtId
                    }
                    numberOfItems
                    numberOfPages
                }
            }
        `;

        return graphQlService
            .query({
                variables: {
                    filter: filter || {},
                    page: page || {
                        page: 0,
                        size: 10,
                    },
                    sort: sort || {
                        property: 'eventTime',
                        direction: 'ASC',
                    },
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                return update.events;
            })
            .then(events => {
                events.data.forEach((event: AlertEvent) => {
                    const alertType = resolveAlertType(event);
                    if (alertType) {
                        event.deviceType = alertType;
                    }
                });

                return events;
            });
    }

    queryAlerts(filter: QueryFilter, page: PageRequest, sort: SortOrder[]): Promise<SearchResult> {
        const payload = gql`
            query Alerts($filter: QueryFilter, $page: PageRequest, $sort: [SortOrder]) {
                queryAlerts(filter: $filter, page: $page, sort: $sort) {
                    data {
                        id
                        triggerId
                        deviceId
                        deviceType
                        severity
                        count
                        status
                        eventId
                        schoolId
                        districtId
                        code
                        description
                        updated
                    }
                    numberOfItems
                    numberOfPages
                }
            }
        `;

        return graphQlService
            .query({
                variables: {
                    filter: filter || {},
                    page: page || {
                        page: 0,
                        size: 10,
                    },
                    sort: sort || {
                        property: 'updated',
                        direction: 'ASC',
                    },
                },
                query: payload,
            })
            .then(response => response.data)
            .then(update => {
                return update.queryAlerts;
            });
    }

    updateAlert(updateRequest: AlertUpdateRequest): Promise<UpdateAlertResponse> {
        const payload = gql`
            mutation UpdateAlert($action: AlertUpdateRequest) {
                updateAlert(action: $action) {
                    status
                    id
                }
            }
        `;

        return graphQlService
            .mutate({
                variables: {
                    action: updateRequest,
                },
                mutation: payload,
            })
            .then(response => {
                return response.errors?.length
                    ? Promise.reject(new Error(extractError(response.errors)))
                    : response.data.updateAlert;
            });
    }
}

const eventsService = new EventsService();
export default eventsService;
