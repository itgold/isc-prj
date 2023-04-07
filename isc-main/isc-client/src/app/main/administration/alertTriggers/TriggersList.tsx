import React, { useEffect, useRef, useState } from 'react';
import _ from '@lodash';

import { IconButton, makeStyles, TableCell, Tooltip } from '@material-ui/core';
import { FaTrashAlt } from 'react-icons/fa';

import IscTable from 'app/components/table/IscTable';
import { ITableHeadColumn, ICellCallback, Order } from 'app/components/table/IscTableHead';
import { Filter, IscTableContext } from 'app/components/table/IscTableContext';
import { IUpdateEntityDialog } from 'app/components/crud/types';
import queryService from 'app/services/events.service';
import { EMPTY_SEARCH_RESULTS, SearchResult } from 'app/domain/search';
import { createQueryFilter, EMPTY_LIST, RegionType } from 'app/utils/domain.constants';
import { IscDataProvider } from 'app/components/table/IscTableDataProvider';
import { ServerDataProvider } from 'app/components/table/providers/ServerDataProvider';
import AlertTrigger from 'app/domain/alertTrigger';
import { IEntity } from '../../../domain/entity';

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
        width: 140,
        disablePadding: false,
        label: 'Name',
        sort: true,
        filter: 'string',
    },
    {
        id: 'processorType',
        align: 'left',
        width: 100,
        disablePadding: false,
        label: 'Processor',
        sort: true,
        filter: 'string',
    },
    {
        id: 'actions',
        align: 'left',
        disablePadding: false,
        label: 'Actions',
        width: 110,
        formatter: (
            row: AlertTrigger,
            column: ITableHeadColumn,
            index: number,
            cellCallback?: ICellCallback
        ): React.ReactNode => {
            return (
                <TableCell padding="default" key={index}>
                    <Tooltip title="Delete">
                        <IconButton
                            aria-label="delete"
                            size="small"
                            onClick={(event: React.MouseEvent<HTMLButtonElement>) => {
                                event.stopPropagation();
                                if (cellCallback?.onAction) cellCallback?.onAction('delete', row);
                            }}
                        >
                            <FaTrashAlt />
                        </IconButton>
                    </Tooltip>
                </TableCell>
            );
        },
    },
];

interface TriggersListProps {
    onSelectElement?: (element: AlertTrigger | null) => void;
    onDoubleClick?: (element: AlertTrigger | null) => void;
    cellCallback?: ICellCallback;
    showFilters: boolean;

    textFilter?: string;
    selectedRow?: AlertTrigger;
}

interface TriggersListState {
    context: IscTableContext;
    tableData: SearchResult;
    showFilters: boolean;
    textFilter: string;
    updateRequired: boolean;
}

const EMPTY_FILTER = {};

const queryAction = (context: IscTableContext): Promise<SearchResult> => {
    const columnsFilter = createQueryFilter(context.filter);
    // columnsFilter.push({ key: 'type', value: RegionType.ZONE });

    const filter = {
        tags: context.tags,
        columns: columnsFilter,
    };
    return queryService.queryAlertTriggersWithFilter(
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

function usePrevious(selectedRow: AlertTrigger | undefined): AlertTrigger | undefined {
    const ref = useRef<AlertTrigger>();
    useEffect(() => {
        ref.current = selectedRow;
    });

    return ref.current;
}

/**
 * Returns a table which contains the elements to be dragable into the map
 */
const TriggersList = (props: TriggersListProps): JSX.Element => {
    const dialogRef = useRef<IUpdateEntityDialog>(null);
    const prevSelectedTrigger = usePrevious(props.selectedRow);

    const classes = useStyles();
    const [pageState, setPageState] = useState<TriggersListState>({
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
            filter: EMPTY_FILTER,
            tags: [],
        },
        tableData: {
            data: EMPTY_LIST,
            numberOfItems: 0,
            numberOfPages: 0,
        },
        showFilters: props.showFilters,
        textFilter: props.textFilter || '',
        updateRequired: true,
    });
    const [textFilter, setTextFilter] = useState<string>(props.textFilter || '');
    const [showFilters, setShowFilters] = useState<boolean>(props.showFilters || false);

    const dataProvider: IscDataProvider = new ServerDataProvider(
        pageState.tableData || EMPTY_SEARCH_RESULTS,
        columns as Array<ITableHeadColumn>,
        pageState.context,
        (context: IscTableContext): void => {
            if (!_.isEqual(context, pageState.context)) {
                setPageState({
                    ...pageState,
                    updateRequired: true,
                    context,
                });
            }
        }
    );

    const reloadData = (): void => {
        const queryContext: IscTableContext = JSON.parse(JSON.stringify(pageState.context));
        queryAction(queryContext)
            .then(data => {
                const tableData = {
                    ...data,
                    data: data.data.map((trigger: IEntity) => {
                        // TODO: do some data lifting if needed
                        return trigger;
                    }),
                };

                let requireUpdate = false;
                let updatedContext = pageState.context;
                if (
                    pageState.context.pagination.currentPage > 0 &&
                    pageState.context.pagination.currentPage >= tableData.numberOfPages
                ) {
                    requireUpdate = true;
                    updatedContext = {
                        ...pageState.context,
                        pagination: {
                            ...pageState.context.pagination,
                            currentPage: Math.max(
                                0,
                                Math.min(pageState.context.pagination.currentPage, tableData.numberOfPages - 1)
                            ),
                        },
                    };
                }

                setPageState({
                    ...pageState,
                    tableData,
                    updateRequired: requireUpdate,
                    context: updatedContext,
                });
            })
            .catch(error => {
                console.log('Unable to Fetch data', error);
            });
    };

    const onRowClick = (row: IEntity | null, dblClick?: boolean): void => {
        if (dblClick && props.onDoubleClick) {
            props.onDoubleClick(row as AlertTrigger);
        } else if (props.onSelectElement) {
            props.onSelectElement(row as AlertTrigger);
        }
    };

    const onChangeFilter = (filter: Filter): void => {
        dataProvider.context = {
            ...pageState.context,
            filter,
            pagination: {
                ...pageState.context.pagination,
                currentPage: 0,
            },
        };
    };

    const onChangePage = (page: number): void => {
        dataProvider.context = {
            ...pageState.context,
            pagination: {
                ...pageState.context.pagination,
                currentPage: page,
            },
        };
    };

    const onChangeRowsPerPage = (rowsPerPage: number): void => {
        dataProvider.context = {
            ...pageState.context,
            pagination: {
                ...pageState.context.pagination,
                rowsPerPage,
                currentPage: 0,
            },
        };
    };

    const onSort = (columnId: string, sortOrder: Order): void => {
        dataProvider.context = {
            ...pageState.context,
            sortOrder: {
                direction: sortOrder,
                id: columnId,
            },
        };
    };

    useEffect(() => {
        if (pageState.updateRequired || !_.isEqual(prevSelectedTrigger, props.selectedRow)) {
            reloadData();
        }
        if (props.textFilter !== undefined && props.textFilter !== textFilter) {
            setTextFilter(props.textFilter);
            dataProvider.context = {
                ...pageState.context,
                filter: {
                    name: props.textFilter,
                },
            };
        }
        if (props.showFilters !== showFilters) {
            setShowFilters(props.showFilters);
            if (!props.showFilters) {
                dataProvider.context = {
                    ...pageState.context,
                    filter: {},
                };
            }
        }

        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [
        pageState.updateRequired,
        pageState.context,
        prevSelectedTrigger,
        props.selectedRow,
        props.textFilter,
        props.showFilters,
    ]);

    return (
        <>
            <IscTable
                className={classes.rootSpecial}
                columns={columns}
                onRowClick={onRowClick}
                onRowDblClick={(row: IEntity | null) => onRowClick(row, true)}
                showPagination
                cellCallback={props.cellCallback}
                totalRecords={dataProvider.totalRecords}
                data={dataProvider.data}
                filter={pageState.context.filter}
                onChangeFilter={onChangeFilter}
                pagination={pageState.context.pagination}
                onChangePage={onChangePage}
                onChangeRowsPerPage={onChangeRowsPerPage}
                onSort={onSort}
                sortOrder={pageState.context.sortOrder}
                showFilters={props.showFilters}
                selectedRow={props.selectedRow}
            />
        </>
    );
};

export default TriggersList;
