import React, { useRef, RefObject, useState, useEffect } from 'react';
import clsx from 'clsx';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import { TableRow, TableCell, TableContainer, makeStyles, fade } from '@material-ui/core';
import TablePagination from '@material-ui/core/TablePagination';

import { StringMap } from 'app/utils/domain.constants';
import { AppTheme } from '@common/components/theme';
import _ from 'lodash';
import IscTableHead, { ICellCallback, IColumnOrder, ITableHeadColumn, Order } from './IscTableHead';
import { Pagination } from './IscTableDataProvider';
import { Filter } from './IscTableContext';
import { IEntity } from '../../domain/entity';

/**
 * TODO: Move this to a global scheme
 */
const useStyles = makeStyles((theme: AppTheme) => ({
    tableContainer: {},
    paginationContainer: {
        position: 'absolute',
        bottom: 0,
        left: 0,
        right: 0,
        minHeight: 44,
    },

    tableWithPagination: {
        marginBottom: 48,
    },

    tableRow: {
        '&.MuiTableRow-root:hover': {
            backgroundColor: fade(theme.palette.secondary.dark, 0.25),
        },
        '&.Mui-selected': {
            backgroundColor: theme.palette.primary.main,
            '& > .MuiTableCell-root': {
                color: theme.palette.primary.contrastText,
                '& > .MuiIconButton-sizeSmall': {
                    '& > .MuiIconButton-label': {
                        color: theme.palette.primary.contrastText,
                    },
                },
            },
        },
        '&.Mui-selected:hover': {
            backgroundColor: theme.palette.primary.main,
            '& > .MuiTableCell-root': {
                color: theme.palette.secondary.light,
            },
        },
    },
}));

export interface ITable {
    /**
     * Array of columns which defines the table head
     */
    columns: Array<ITableHeadColumn>;

    /**
     * Should show the Pagination?
     */
    showPagination?: boolean | false;

    /**
     * Trigger the row click passing the current row as reference
     */
    onRowClick?: (row: IEntity | null) => void;

    /**
     * Trigger the row click passing the current row as reference
     */
    onRowDblClick?: (row: IEntity | null) => void;

    /**
     * Callback fired when the page is changed.
     */
    onChangePage?: (page: number) => void;

    /**
     * Callback fired when the number of rows per page is changed.
     */
    onChangeRowsPerPage?: (rowsPerPage: number) => void;

    /**
     * Callback fired when the table sorting got changed.
     */
    onSort?: (columnId: string, sortOrder: Order) => void;

    onChangeFilter?: (filter: Filter) => void;

    /**
     * Flag enable or disable showing table filters
     */
    showFilters?: boolean;

    className?: string;

    cellCallback?: ICellCallback;

    data: IEntity[];
    totalRecords: number;
    pagination: Pagination;
    sortOrder?: IColumnOrder;
    filter?: Filter;

    /**
     * Defines which row was selected by the parent
     */
    selectedRow?: IEntity | null;
}

/**
 * Table used and shared through the project
 */
const IscTable = (props: ITable): JSX.Element => {
    // const [selectedRow, setSelectedRow] = useState(props.selectedRow || null);
    const [selection, setSelection] = useState({
        prevSelected: props.selectedRow,
        rowSelected: props.selectedRow,
    });
    const elRef = useRef<HTMLDivElement>() as RefObject<HTMLDivElement>;

    const { sortOrder, onSort } = props;
    const { pagination, onChangePage, onChangeRowsPerPage } = props;
    const { onChangeFilter } = props;
    const { data, totalRecords } = props;

    const safeCallback = (callback: () => void): void => {
        if (elRef.current) {
            callback();
        }
    };

    const handleRequestSort = (columnId: string): void => {
        const direction = sortOrder?.id === columnId && sortOrder?.direction === 'asc' ? 'desc' : 'asc';
        if (onSort) {
            safeCallback(() => onSort(columnId, direction));
        }
    };

    const handleChangePage = (event: unknown, newPage: number): void => {
        if (onChangePage) {
            safeCallback(() => onChangePage(newPage));
        }
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>): void => {
        const rowsPerPage = parseInt(event.target.value, 10);
        if (onChangeRowsPerPage) {
            safeCallback(() => onChangeRowsPerPage(rowsPerPage));
        }
    };

    const handleFilterChange = (newFilter: Filter): void => {
        if (onChangeFilter) {
            safeCallback(() => onChangeFilter(newFilter));
        }
    };

    const handleRowSelectionChange = (row: IEntity, isDoubleClick?: boolean): boolean => {
        setSelection({
            ...selection,
            rowSelected: row,
        });

        const { onRowClick, onRowDblClick } = props;
        const handler = isDoubleClick ? onRowDblClick || onRowClick : onRowClick;
        if (handler) {
            safeCallback(() => handler(row));
        }

        return false;
    };

    const classes = useStyles();

    const isSelected = (selected: IEntity | undefined | null, row: IEntity): boolean => {
        return !!row?.id && selected?.id === row.id;
    };

    useEffect(() => {
        if (props.selectedRow?.id !== selection.prevSelected?.id) {
            setSelection({
                prevSelected: props.selectedRow,
                rowSelected: props.selectedRow,
            });
        }
    }, [props.selectedRow, selection.prevSelected]);

    return (
        <div ref={elRef} className={clsx('w-full flex flex-col', props.className)}>
            <TableContainer
                className={clsx(classes.tableContainer, props.showPagination && classes.tableWithPagination)}
            >
                <Table stickyHeader size="small">
                    <IscTableHead
                        columnOrder={sortOrder}
                        onSort={handleRequestSort}
                        columns={props.columns}
                        showFilters={props.showFilters}
                        filter={props.filter}
                        onFilter={handleFilterChange}
                    />
                    <TableBody>
                        {/* for each data */}
                        {data.map((row, index) => (
                            // creates a row
                            <TableRow
                                key={index}
                                className={classes.tableRow}
                                onDoubleClick={() =>
                                    // triggers the callback passing the current row as reference
                                    handleRowSelectionChange(row, true)
                                }
                                onClick={() =>
                                    // triggers the callback passing the current row as reference
                                    handleRowSelectionChange(row)
                                }
                                selected={isSelected(selection.rowSelected, row)}
                            >
                                {/* and fill each columns from the table based on the data[colum] */}
                                {props.columns.map(
                                    (column, cIndex) =>
                                        (column.formatter &&
                                            column.formatter(row, column, cIndex, props.cellCallback)) || (
                                            <TableCell key={cIndex} component="td" scope="row">
                                                {column.id ? (row as StringMap)[column.id] : ''}
                                            </TableCell>
                                        )
                                )}
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            {props.showPagination && (
                <TablePagination
                    className={`overflow-hidden ${classes.paginationContainer}`}
                    component="div"
                    count={totalRecords}
                    rowsPerPage={pagination.rowsPerPage}
                    page={totalRecords > 0 ? pagination.currentPage : 0}
                    backIconButtonProps={{
                        'aria-label': 'Previous Page',
                    }}
                    nextIconButtonProps={{
                        'aria-label': 'Next Page',
                    }}
                    onChangePage={handleChangePage}
                    onChangeRowsPerPage={handleChangeRowsPerPage}
                />
            )}
        </div>
    );
};

export default React.memo(IscTable, (prevProps: ITable, nextProps: ITable) => {
    const dataChanged = !_.isEqual(prevProps.data, nextProps.data);
    const paginationChanged = !_.isEqual(prevProps.pagination, nextProps.pagination);
    const showFiltersChanged = !_.isEqual(prevProps.showFilters, nextProps.showFilters);
    const selectedRowChanged = !_.isEqual(prevProps.selectedRow, nextProps.selectedRow);
    const sortOrderChanged = !_.isEqual(prevProps.sortOrder, nextProps.sortOrder);
    const filterChanged = !_.isEqual(prevProps.filter, nextProps.filter);

    return !(
        dataChanged ||
        paginationChanged ||
        showFiltersChanged ||
        selectedRowChanged ||
        sortOrderChanged ||
        filterChanged
    );
});
