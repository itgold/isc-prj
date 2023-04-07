import React, { useRef, useState } from 'react';
import { useDispatch } from 'react-redux';
import { deepOrange } from '@material-ui/core/colors';
import { Grid, IconButton, makeStyles, Switch, TableCell, Tooltip, withStyles } from '@material-ui/core';
import { FaEdit, FaTrashAlt } from 'react-icons/fa';
import MomentUtils from '@date-io/moment';

import * as SchoolActions from 'app/store/actions/admin/model.queries';
import * as Actions from 'app/store/actions/admin';
import EntityCrudPage, { IEntityCrudPage } from 'app/components/crud/EntityCrudPage';
import { ICellCallback, IColumnOrder, ITableHeadColumn } from 'app/components/table/IscTableHead';
import { IEntityField } from 'app/components/crud/types';
import queryService from 'app/services/query.service';
import IscPageSimple from 'app/components/layout/IscPageSimple';
import { SearchResult } from 'app/domain/search';
import { IscTableContext } from 'app/components/table/IscTableContext';
import { createQueryFilter } from 'app/utils/domain.constants';

const momentUtils: MomentUtils = new MomentUtils();

const columns: Array<ITableHeadColumn> = [
    {
        id: 'name',
        align: 'left',
        width: 260,
        disablePadding: false,
        label: 'Name',
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
        width: 120,
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
        width: 120,
    },

    {
        id: 'address',
        align: 'left',
        disablePadding: false,
        label: 'Address',
        formatter: (row: any, column: ITableHeadColumn, index: number) => (
            <TableCell padding="default" key={index}>
                {row.address}, {row.city}, {row.state} {row.zipCode}, {row.country}
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
    { name: 'name' },
    { name: 'status', type: 'enum', valueOptions: ['ACTIVATED', 'DEACTIVATED'], defaultValue: 'ACTIVATED' },
    { name: 'contactEmail' },
    { name: 'address' },
    { name: 'city' },
    { name: 'state' },
    { name: 'zipCode' },
    { name: 'country' },
];

const useStyles = makeStyles(() => ({
    searchBar: {
        width: '400px',
        marginRight: 20,
    },
    toolbarText: {
        color: 'white',
    },
}));

const ShowAllSwitch = withStyles({
    switchBase: {
        color: deepOrange[300],
        '&$checked': {
            color: deepOrange[500],
        },
        '&$checked + $track': {
            backgroundColor: deepOrange[500],
        },
    },
    checked: {},
    track: {},
})(Switch);

export default function EnhancedTable(): JSX.Element {
    const defaultSort: IColumnOrder = {
        id: 'name',
        direction: 'asc',
    };
    const entityCrudPage = useRef<IEntityCrudPage>(null);
    const [showAll, setShowAll] = useState<boolean>(false);
    const toggleShowAll = (event: React.ChangeEvent<HTMLInputElement>): void => {
        setShowAll(event.target.checked);
        entityCrudPage?.current?.refresh();
    };

    const dataSelector = (data: SearchResult): SearchResult => {
        return data;
    };
    const queryAction = (context: IscTableContext): Promise<SearchResult> => {
        const columnsFilter = createQueryFilter(context.filter);
        if (!showAll) columnsFilter.push({ key: 'status', value: 'ACTIVATED' });
        const filter = {
            tags: context.tags,
            columns: columnsFilter,
        };
        return queryService.queryDistrictsWithFilter(
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
        return queryService.createSchoolDistrict(data).then((payload: any) => {
            dispatch(SchoolActions.addSchoolDistrict(payload));
            dispatch(Actions.loadRegionTree(true));
        });
    };
    const updateAction = (data: any): Promise<any> => {
        return queryService.updateSchoolDistrict(data).then((payload: any) => {
            dispatch(SchoolActions.updateSchoolDistrictSuccess(payload));
            dispatch(Actions.loadRegionTree(true));
        });
    };
    const deleteAction = (data: any): Promise<any> => {
        return queryService.deleteSchoolDistrict(data).then((payload: any) => {
            dispatch(SchoolActions.deleteSchoolDistrictSuccess(payload));
            dispatch(Actions.loadRegionTree(true));
            return payload;
        });
    };

    const classes = useStyles();
    return (
        <IscPageSimple>
            <EntityCrudPage
                ref={entityCrudPage}
                columns={columns}
                entityFields={entityFields}
                dataSelector={dataSelector}
                queryAction={queryAction}
                createAction={createAction}
                updateAction={updateAction}
                deleteAction={deleteAction}
                sortOrder={defaultSort}
                header={
                    <Grid component="label" container alignItems="center" spacing={1}>
                        <Grid item className={classes.toolbarText}>
                            Active Only
                        </Grid>
                        <Grid item>
                            <ShowAllSwitch checked={showAll} onChange={toggleShowAll} name="showAll" />
                        </Grid>
                        <Grid item className={classes.toolbarText}>
                            Show All
                        </Grid>
                    </Grid>
                }
            />
        </IscPageSimple>
    );
}
