import React, { useEffect, useState } from 'react';
import _ from '@lodash';
import { makeStyles } from '@material-ui/core';

import IscTable from 'app/components/table/IscTable';
import { ITableHeadColumn, ICellCallback, Order } from 'app/components/table/IscTableHead';
import { IscTableContext } from 'app/components/table/IscTableContext';
import ISchoolElement from 'app/domain/school.element';
import Region from 'app/domain/region';
import regionHierarchyService from 'app/services/region.hierarchy.service';
import { EntityDataProvider } from 'app/components/crud/EntityDataProvider';
import { CompositeNode } from 'app/domain/composite';
import { EntityType } from 'app/utils/domain.constants';
import Zone, { ZoneType } from 'app/domain/zone';

const useStyles = makeStyles(() => ({
    rootSpecial: {
        width: '100%',
        position: 'relative',
    },
}));

export const columns: Array<ITableHeadColumn> = [
    {
        id: 'name',
        align: 'left',
        disablePadding: false,
        label: 'Name',
        sort: true,
        filter: 'string',
        width: 160,
    },
    {
        id: 'description',
        align: 'left',
        disablePadding: false,
        label: 'Description',
        sort: true,
        filter: 'string',
    },
];

interface EntitiesListProps {
    region?: Region | null;
    zone?: Zone | null;
    onSelectElement?: (element: ISchoolElement | null) => void;
    onDoubleClick?: (element: ISchoolElement | null) => void;
    cellCallback?: ICellCallback;
}

interface EntitiesListState {
    context: IscTableContext;
    regionId: string | null;
    data: ISchoolElement[];
}

/**
 * Returns a table which contains the elements to be dragable into the map
 */
const EntitiesList = (props: EntitiesListProps): JSX.Element => {
    const classes = useStyles();

    const [context, setContext] = useState<EntitiesListState>({
        context: {
            pagination: {
                showPagination: true,
                rowsPerPage: 25,
                currentPage: 0,
            },
            sortOrder: {
                direction: 'asc',
                id: null,
            },
            filter: {},
            tags: [],
        },
        regionId: null,
        data: [],
    });
    const dataProvider = new EntityDataProvider(context.data, columns, context.context, newContext => {
        if (!_.isEqual(context.context, newContext)) {
            setContext({
                ...context,
                context: newContext,
            });
        }
    });

    const onRowClick = (row: ISchoolElement | null, dblClick?: boolean): void => {
        if (dblClick && props.onDoubleClick) {
            props.onDoubleClick(row);
        } else if (props.onSelectElement) {
            props.onSelectElement(row);
        }
    };

    const filteredData = (): ISchoolElement[] => {
        return dataProvider.data;
    };

    const onChangePage = (page: number): void => {
        dataProvider.context = {
            ...context.context,
            pagination: {
                ...context.context.pagination,
                currentPage: page,
            },
        };
    };

    const onChangeRowsPerPage = (rowsPerPage: number): void => {
        dataProvider.context = {
            ...context.context,
            pagination: {
                ...context.context.pagination,
                rowsPerPage,
                currentPage: 0,
            },
        };
    };

    const onSort = (columnId: string, sortOrder: Order): void => {
        dataProvider.context = {
            ...context.context,
            sortOrder: {
                direction: sortOrder,
                id: columnId,
            },
        };
    };

    const regionId = props.region?.id || null;
    useEffect(() => {
        if (regionId !== context.regionId) {
            if (regionId) {
                let entityType = EntityType.SPEAKER;
                if (props.zone?.subType === ZoneType.CAMERAS) {
                    entityType = EntityType.CAMERA;
                }

                regionHierarchyService.getComposite(regionId).then((composite: CompositeNode) => {
                    setContext({
                        ...context,
                        regionId,
                        data: CompositeNode.leafs(composite, [entityType]),
                    });
                });
            } else {
                setContext({
                    ...context,
                    regionId,
                    data: [],
                });
            }
        } // eslint-disable-next-line
    }, [regionId, context]);

    return (
        <IscTable
            className={classes.rootSpecial}
            columns={columns}
            onRowClick={onRowClick}
            onRowDblClick={(row: ISchoolElement | null) => onRowClick(row, true)}
            showPagination
            cellCallback={props.cellCallback}
            totalRecords={dataProvider.totalRecords}
            data={filteredData()}
            pagination={context.context.pagination}
            onChangePage={onChangePage}
            onChangeRowsPerPage={onChangeRowsPerPage}
            onSort={onSort}
            sortOrder={context.context.sortOrder}
        />
    );
};

export default EntitiesList;
