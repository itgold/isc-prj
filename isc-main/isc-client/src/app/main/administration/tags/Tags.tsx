import React from 'react';
import { useDispatch } from 'react-redux';
import { IconButton, TableCell, Tooltip } from '@material-ui/core';
import { FaEdit, FaTrashAlt } from 'react-icons/fa';

import * as Actions from 'app/store/actions/admin/models.core.queries';
import EntityCrudPage from 'app/components/crud/EntityCrudPage';
import { ICellCallback, IColumnOrder, ITableHeadColumn } from 'app/components/table/IscTableHead';
import { IEntityField } from 'app/components/crud/types';
import queryService from 'app/services/query.service';
import IscPageSimple from 'app/components/layout/IscPageSimple';
import { SearchResult } from 'app/domain/search';
import { IscTableContext } from 'app/components/table/IscTableContext';

const columns: Array<ITableHeadColumn> = [
    {
        id: 'name',
        align: 'left',
        disablePadding: false,
        label: 'Tag',
        sort: true,
        filter: 'string',
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

const entityFields: IEntityField[] = [{ name: 'name' }];

export default function EnhancedTable(): JSX.Element {
    const defaultSort: IColumnOrder = {
        id: 'name',
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
        return queryService.queryTagsWithFilter(
            filter,
            {
                page: context.pagination?.currentPage || 0,
                size: context.pagination?.rowsPerPage || 25,
            },
            [
                {
                    property: context.sortOrder.id || 'name',
                    direction: (context.sortOrder.direction as string)?.toUpperCase() || 'ASC',
                },
            ]
        );
    };

    const dispatch = useDispatch();
    const createAction = (data: any): Promise<any> => {
        return queryService.createTag(data).then((payload: any) => {
            dispatch(Actions.addTag(payload));
        });
    };
    const updateAction = (data: any): Promise<any> => {
        return queryService.updateTag(data).then((payload: any) => {
            dispatch(Actions.updateTagSuccess(payload));
        });
    };
    const deleteAction = (data: any): Promise<any> => {
        return queryService.deleteTag(data).then((payload: any) => {
            dispatch(Actions.deleteTagSuccess(payload));
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
