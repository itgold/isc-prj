import React, { useEffect, useRef, useState } from 'react';
import _ from '@lodash';

import { IconButton, makeStyles, TableCell, Tooltip } from '@material-ui/core';
import { FaEdit, FaTrashAlt } from 'react-icons/fa';

import IscTable from 'app/components/table/IscTable';
import { ITableHeadColumn, ICellCallback, Order } from 'app/components/table/IscTableHead';
import { Filter, IscTableContext } from 'app/components/table/IscTableContext';
import Zone, { DEFAULT_ZONE_COLOR } from 'app/domain/zone';
import { IEntityField, IUpdateEntityDialog } from 'app/components/crud/types';
import { findParentSchool } from 'app/components/map/utils/MapUtils';
import UpdateEntityDialog from 'app/components/crud/EditEntityDialog';
import queryService from 'app/services/query.service';
import * as SchoolActions from 'app/store/actions/admin/model.queries';
import * as Selectors from 'app/store/selectors';
import Region from 'app/domain/region';
import School from 'app/domain/school';
import { CompositeNode, CompositeNodesMap } from 'app/domain/composite';
import { EMPTY_SEARCH_RESULTS, SearchResult } from 'app/domain/search';
import { createQueryFilter, EMPTY_LIST, RegionType } from 'app/utils/domain.constants';
import { IscDataProvider } from 'app/components/table/IscTableDataProvider';
import { ServerDataProvider } from 'app/components/table/providers/ServerDataProvider';
import { DIALOG_REASON_SUBMIT } from '@common/components/app.dialog.props';
import { useSelector } from 'react-redux';
import { RootState } from 'app/store/appState';
import { IEntity } from '../../../domain/entity';

const useStyles = makeStyles(() => ({
    rootSpecial: {
        width: '100%',
        position: 'relative',
    },
}));

export class ZoneWithExtraData extends Zone {
    school?: string;

    children?: CompositeNodesMap;

    constructor(rec?: ZoneWithExtraData) {
        super(rec);

        this.school = rec?.school;
        this.children = rec?.children;
    }
}

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
        id: 'subType',
        align: 'left',
        width: 100,
        disablePadding: false,
        label: 'Type',
        sort: true,
        filter: 'string',
    },
    {
        id: 'description',
        align: 'left',
        disablePadding: false,
        label: 'Description',
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
            row: ZoneWithExtraData,
            column: ITableHeadColumn,
            index: number,
            cellCallback?: ICellCallback
        ): React.ReactNode => {
            return (
                <TableCell padding="default" key={index}>
                    <Tooltip title="Edit">
                        <IconButton
                            aria-label="edit"
                            size="small"
                            onClick={(event: React.MouseEvent<HTMLButtonElement>) => {
                                event.stopPropagation();
                                if (cellCallback?.onAction) cellCallback?.onAction('edit', row);
                            }}
                        >
                            <FaEdit />
                        </IconButton>
                    </Tooltip>

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

export const entityFields: IEntityField[] = [
    { name: 'name', inputLabel: 'Name' },
    {
        name: 'subType',
        inputLabel: 'Type',
        type: 'enum',
        valueOptions: ['Speakers', 'Cameras'],
        defaultValue: 'Speakers',
    },
    {
        name: 'status',
        inputLabel: 'Status',
        type: 'enum',
        valueOptions: ['ACTIVATED', 'DEACTIVATED'],
        defaultValue: 'ACTIVATED',
    },
    { name: 'description', type: 'text', optional: true, inputLabel: 'Description' },
    {
        name: 'school',
        inputLabel: 'School',
        type: 'asyncEnum',
        optional: true,
        valueOptionsAsync: {
            queryAction: SchoolActions.querySchools,
            dataSelector: Selectors.schoolsSelector,
        },
        defaultValue: (entity: Region): School => {
            return findParentSchool(entity?.parentIds);
        },
    },
    { name: 'color', type: 'color', optional: true, inputLabel: 'Color', defaultValue: '#0d47a1b0' },
];

export interface RegionsMap {
    [key: string]: string[];
}

interface ZonesListProps {
    onSelectElement?: (element: Zone | null) => void;
    onDoubleClick?: (element: Zone | null) => void;
    onUpdateZone?: (data: ZoneWithExtraData) => void;
    cellCallback?: ICellCallback;
    showFilters: boolean;

    textFilter?: string;
    selectedRow?: Zone;
}

interface ZonesListState {
    context: IscTableContext;
    tableData: SearchResult;
    showFilters: boolean;
    textFilter: string;
    updateRequired: boolean;
}

const EMPTY_FILTER = {};

const queryAction = (context: IscTableContext): Promise<SearchResult> => {
    const columnsFilter = createQueryFilter(context.filter);
    columnsFilter.push({ key: 'type', value: RegionType.ZONE });

    const filter = {
        tags: context.tags,
        columns: columnsFilter,
    };
    return queryService.queryZonesWithFilter(
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

function usePrevious(selectedRow: Zone | undefined): Zone | undefined {
    const ref = useRef<Zone>();
    useEffect(() => {
        ref.current = selectedRow;
    });

    return ref.current;
}

/**
 * Returns a table which contains the elements to be dragable into the map
 */
const ZonesList = (props: ZonesListProps): JSX.Element => {
    const dialogRef = useRef<IUpdateEntityDialog>(null);
    const prevDelectedZone = usePrevious(props.selectedRow);
    const regionsMap = useSelector<RootState, CompositeNodesMap>(Selectors.allRegionsSelector);

    const classes = useStyles();
    const [pageState, setPageState] = useState<ZonesListState>({
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
                    data: data.data.map((zone: Zone) => {
                        const children: CompositeNodesMap = {};
                        zone.childIds?.forEach(id => {
                            const child = regionsMap[id];
                            if (child) {
                                children[id] = child;
                            }
                        });

                        (zone as CompositeNode).children = children;
                        zone.color = zone.props?.find(prop => prop.key === 'color')?.value || DEFAULT_ZONE_COLOR;
                        return zone;
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
            props.onDoubleClick(row as Zone);
        } else if (props.onSelectElement) {
            props.onSelectElement(row as Zone);
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

    const handleEditDialogClose = (reason: string, data: ZoneWithExtraData): Promise<boolean> => {
        if (DIALOG_REASON_SUBMIT === reason) {
            return new Promise<boolean>(resolve => {
                try {
                    const oldZone = dataProvider.data.find(zone => zone.id === data.id);
                    // id school got changed -> clear region selection
                    if (oldZone && !_.isEqual(oldZone.parentIds, data.parentIds)) {
                        data.children = {};
                        data.childIds = [];
                    }

                    resolve(true);
                    if (props.onUpdateZone) {
                        props.onUpdateZone(data);
                    }
                } catch (e) {
                    console.log('!!! Error: not implemented !!!');
                }
            });
        }

        return Promise.resolve(true);
    };

    useEffect(() => {
        if (pageState.updateRequired || !_.isEqual(prevDelectedZone, props.selectedRow)) {
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
        prevDelectedZone,
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
            <UpdateEntityDialog
                entityFields={entityFields}
                ref={dialogRef}
                onClose={handleEditDialogClose}
                dialogTitle="Create/Update Zone"
                addButtonLabel="Save"
            />
        </>
    );
};

export default ZonesList;
