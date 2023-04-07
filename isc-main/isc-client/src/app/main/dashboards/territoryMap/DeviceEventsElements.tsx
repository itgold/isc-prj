import React from 'react';
import IscTable from 'app/components/table/IscTable';
import { ITableHeadColumn, ICellCallback, Order } from 'app/components/table/IscTableHead';
import { makeStyles } from '@material-ui/core';
import { Filter, useIscTableContext, useIscTableDataProvider } from 'app/components/table/IscTableContext';
import ISchoolElement from 'app/domain/school.element';
import clsx from 'clsx';
import DeviceEvent from 'app/domain/deviceEvent';
import { IEntity } from 'app/domain/entity';

const useStyles = makeStyles(() => ({
    rootSpecial: {
        position: 'absolute',
        left: 0,
        right: 0,
        top: 0,
        bottom: 0,
    },
}));

interface DeviceEventsElementsProps {
    showFilters?: boolean;
    onSelectElement?: (element: DeviceEvent | null) => void;
    onDoubleClick?: (element: DeviceEvent | null) => void;
    cellCallback?: ICellCallback;

    columns: Array<ITableHeadColumn>;
    className?: string;
}

/**
 * Returns a table which contains the elements to be dragable into the map
 */
const DeviceEventsElements = (props: DeviceEventsElementsProps): JSX.Element => {
    const classes = useStyles();
    const dataProvider = useIscTableDataProvider();
    const context = useIscTableContext();

    const onRowClick = (row: IEntity | null, dblClick?: boolean): void => {
        if (dblClick && props.onDoubleClick) {
            props.onDoubleClick(row as DeviceEvent);
        } else if (props.onSelectElement) {
            props.onSelectElement(row as DeviceEvent);
        }
    };

    const filteredData = (): DeviceEvent[] => {
        return dataProvider.data || [];
    };

    const onChangeFilter = (filter: Filter): void => {
        dataProvider.context = {
            ...context,
            filter,
            pagination: {
                ...context.pagination,
                currentPage: 0,
            },
        };
    };

    const onChangePage = (page: number): void => {
        dataProvider.context = {
            ...context,
            pagination: {
                ...context.pagination,
                currentPage: page,
            },
        };
    };

    const onChangeRowsPerPage = (rowsPerPage: number): void => {
        dataProvider.context = {
            ...context,
            pagination: {
                ...context.pagination,
                rowsPerPage,
                currentPage: 0,
            },
        };
    };

    const onSort = (columnId: string, sortOrder: Order): void => {
        dataProvider.context = {
            ...context,
            sortOrder: {
                direction: sortOrder,
                id: columnId,
            },
        };
    };

    return (
        <IscTable
            className={clsx(classes.rootSpecial, props.className)}
            columns={props.columns}
            onRowClick={onRowClick}
            onRowDblClick={(row: ISchoolElement | null) => onRowClick(row, true)}
            showPagination
            showFilters={props.showFilters}
            cellCallback={props.cellCallback}
            totalRecords={dataProvider.totalRecords}
            data={filteredData()}
            filter={context.filter}
            onChangeFilter={onChangeFilter}
            pagination={context.pagination}
            onChangePage={onChangePage}
            onChangeRowsPerPage={onChangeRowsPerPage}
            onSort={onSort}
            sortOrder={context.sortOrder}
        />
    );
};

export default DeviceEventsElements;
