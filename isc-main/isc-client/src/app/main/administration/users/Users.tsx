import React from 'react';
import { useDispatch } from 'react-redux';
import MomentUtils from '@date-io/moment';
import { IconButton, TableCell, Tooltip } from '@material-ui/core';
import { FaEdit, FaTrashAlt } from 'react-icons/fa';

import * as Actions from 'app/store/actions/admin/models.core.queries';
import * as SchoolActions from 'app/store/actions/admin/model.queries';
import * as Selectors from 'app/store/selectors';
import EntityCrudPage from 'app/components/crud/EntityCrudPage';
import { ICellCallback, IColumnOrder, ITableHeadColumn } from 'app/components/table/IscTableHead';
import { RootState } from 'app/store/appState';
import { IEntityField } from 'app/components/crud/types';
import { NONE_ENTITY } from 'app/utils/domain.constants';
import queryService from 'app/services/query.service';
import IscPageSimple from 'app/components/layout/IscPageSimple';
import { SearchResult } from 'app/domain/search';
import { IscTableContext } from 'app/components/table/IscTableContext';

const momentUtils: MomentUtils = new MomentUtils();

const columns: Array<ITableHeadColumn> = [
    {
        id: 'lastName',
        align: 'left',
        width: 200,
        disablePadding: false,
        label: 'Last Name',
        sort: true,
        filter: 'string',
    },
    {
        id: 'firstName',
        align: 'left',
        width: 200,
        disablePadding: false,
        label: 'First Name',
        sort: true,
        filter: 'string',
    },
    {
        id: 'status',
        align: 'left',
        disablePadding: false,
        label: 'Status',
        sort: true,
        filter: 'string',
    },
    {
        id: 'email',
        align: 'left',
        disablePadding: false,
        label: 'Email',
        sort: true,
        filter: 'string',
    },
    {
        id: 'updated',
        align: 'center',
        disablePadding: false,
        label: 'Updated',
        sort: true,
        filter: {
            filterType: 'date',
            format: 'YYYY-MM-DD',
        },
        formatter: (row: any, column: ITableHeadColumn, index: number) => (
            <TableCell padding="default" key={index}>
                {momentUtils.format(momentUtils.date(row.updated), 'YYYY-MM-DD')}
            </TableCell>
        ),
    },
    {
        id: 'actions',
        align: 'left',
        disablePadding: false,
        label: 'Actions',
        width: 100,
        formatter: (row: any, column: ITableHeadColumn, index: number, cellCallback?: ICellCallback) => {
            return (
                <TableCell padding="default" key={index}>
                    <Tooltip title="Edit">
                        <IconButton
                            aria-label="edit"
                            size="small"
                            onClick={() => (cellCallback?.onAction ? cellCallback?.onAction('edit', row) : undefined)}
                        >
                            <FaEdit />
                        </IconButton>
                    </Tooltip>

                    <Tooltip title="Delete">
                        <IconButton
                            aria-label="delete"
                            size="small"
                            onClick={() => (cellCallback?.onAction ? cellCallback?.onAction('delete', row) : undefined)}
                        >
                            <FaTrashAlt />
                        </IconButton>
                    </Tooltip>
                </TableCell>
            );
        },
    },
];

const entityFields: IEntityField[] = [
    { name: 'lastName' },
    { name: 'firstName' },
    {
        name: 'status',
        type: 'enum',
        valueOptions: ['ACTIVATED', 'DEACTIVATED', 'REGISTERED'],
        defaultValue: 'ACTIVATED',
    },

    { name: 'email', validations: { isEmail: true }, validationErrors: { isEmail: 'You have to type valid email' } },
    { name: 'password', optional: true },
    { name: 'imageUrl', optional: true },
    {
        name: 'roles',
        type: 'asyncEnum',
        multi: true,

        valueOptionsAsync: {
            queryAction: Actions.queryRoles,
            dataSelector: (state: RootState) => {
                return Selectors.rolesSelector(state);
            },
        },
    },
    {
        name: 'schoolDistrict',
        type: 'asyncEnum',
        optional: true,

        valueOptionsAsync: {
            queryAction: SchoolActions.querySchoolDistricts,
            dataSelector: (state: RootState) => {
                return Selectors.districtsSelector(state);
            },
        },

        defaultValue: { id: NONE_ENTITY, name: 'NONE' },
    },
];

export default function EnhancedTable(): JSX.Element {
    const defaultSort: IColumnOrder = {
        id: 'lastName',
        direction: 'asc',
    };
    const dataSelector = (data: SearchResult): SearchResult => {
        return data;
    };
    const queryAction = (context: IscTableContext): Promise<SearchResult> => {
        const filter = {
            tags: context.tags,
            columns: context.filter
                ? Object.keys(context.filter).map(key => {
                      return { key, value: context.filter[key] };
                  })
                : [],
        };
        return queryService.queryUsersWithFilter(
            filter,
            {
                page: context.pagination?.currentPage || 0,
                size: context.pagination?.rowsPerPage || 25,
            },
            [
                {
                    property: context.sortOrder.id || 'lastName',
                    direction: (context.sortOrder.direction as string)?.toUpperCase() || 'ASC',
                },
            ]
        );
    };

    const dispatch = useDispatch();
    const createAction = (data: any): Promise<any> => {
        return queryService.createUser(data).then((payload: any) => {
            dispatch(Actions.addUser(payload));
        });
    };
    const updateAction = (data: any): Promise<any> => {
        return queryService.updateUser(data).then((payload: any) => {
            dispatch(Actions.updateUserSuccess(payload));
        });
    };
    const deleteAction = (data: any): Promise<any> => {
        return queryService.deleteUser(data).then((payload: any) => {
            dispatch(Actions.deleteUserSuccess(payload));
            return payload;
        });
    };

    return (
        <IscPageSimple>
            <EntityCrudPage
                columns={columns}
                entityFields={entityFields}
                dataSelector={dataSelector}
                queryAction={queryAction}
                createAction={createAction}
                updateAction={updateAction}
                deleteAction={deleteAction}
                sortOrder={defaultSort}
            />
        </IscPageSimple>
    );
}
