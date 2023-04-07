import React from 'react';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import Tooltip from '@material-ui/core/Tooltip';
import TableSortLabel from '@material-ui/core/TableSortLabel';
import IscTableHeadFilter from './IscTableHeadFilter';
import { Filter } from './IscTableContext';

export type FilterType = 'string' | 'number' | 'date' | 'boolean';

export interface FilterConfig {
    filterType: FilterType;
    format?: string;
    filter?: (value: any, filterValue: string, row?: any) => boolean;
}

export interface ICellCallback {
    onAction?: (action: string, row: any) => void;
    context: { [key: string]: any };
}

/**
 * Column properties
 */
export interface ITableHeadColumn {
    /**
     * Unique ID of the column. Used to extract data from the data row when rendering a cell for this column.
     * If there is no property with the same name in row object you need to provide "formatter" property.
     */
    id: string;

    /**
     * Alternative property name to use as a row's cell value for the filtering.
     */
    alias?: string;

    /**
     * Cell content align
     */
    align?: 'inherit' | 'left' | 'center' | 'right' | 'justify';

    /**
     * Whether to disable default padding for the cell. Useful together with 'formatter'
     */
    disablePadding?: boolean;

    /**
     * Label to show in the header
     */
    label: string;

    /**
     * Whether to allow sorting on the column
     */
    sort?: boolean;

    /**
     * Column filter if enabled
     */
    filter?: FilterType | FilterConfig;

    /**
     * Column width
     */
    width?: number;

    /**
     * Will tell how this column should be rendered in the table
     */
    formatter?: (row: any, column: ITableHeadColumn, index: number, cellCallback?: ICellCallback) => React.ReactNode;

    type?: 'dateTimeRange' | 'string';
}

export type Order = 'asc' | 'desc';

/**
 * Definitions regarding which column should be sorted in for what way
 */
export interface IColumnOrder {
    /**
     * defines which directions should the table order by
     */
    direction?: Order;
    /**
     * what column (by id) is going to be sorted
     */
    id: string | null;
}

/**
 * Table Head props
 */
interface ITableHeadProps {
    /**
     * Columns to be set into the head of the table
     */
    columns: Array<ITableHeadColumn>;
    /**
     * Callback triggered when the sort is requested
     */
    onSort: (columnId: string) => void;
    /**
     * Defines which column should be ordering the table
     */
    columnOrder?: IColumnOrder;

    /**
     * Flag to show or not filters
     */
    showFilters?: boolean;

    /**
     * Callback triggered when filter changed
     */
    onFilter?: (filter: Filter) => void;

    filter?: Filter;
}

/**
 * Table head which could be inject into the IscTable
 */
const IscTableHead = (props: ITableHeadProps) => {
    const createSortHandler = (property: string) => () => {
        props.onSort(property);
    };

    return (
        <TableHead>
            <TableRow>
                {props.columns.map(column => {
                    return (
                        <TableCell
                            key={column.id}
                            align={column.align}
                            padding={column.disablePadding ? 'none' : 'default'}
                            sortDirection={props.columnOrder?.id === column.id ? props.columnOrder?.direction : false}
                            width={column.width}
                        >
                            {column.sort ? (
                                <Tooltip
                                    title="Sort"
                                    placement={column.align === 'right' ? 'bottom-end' : 'bottom-start'}
                                    enterDelay={300}
                                >
                                    <TableSortLabel
                                        active={props.columnOrder?.id === column.id}
                                        direction={props.columnOrder?.direction}
                                        onClick={createSortHandler(column.id)}
                                    >
                                        {column.label}
                                    </TableSortLabel>
                                </Tooltip>
                            ) : (
                                column.label
                            )}
                        </TableCell>
                    );
                })}
            </TableRow>
            <IscTableHeadFilter
                filter={props.filter}
                onFilter={props.onFilter}
                columns={props.columns}
                visible={props.showFilters}
            />
        </TableHead>
    );
};

export default IscTableHead;
