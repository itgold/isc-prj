import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import _ from '@lodash';

import { Checkbox, makeStyles } from '@material-ui/core';

import IscTable from 'app/components/table/IscTable';
import { ITableHeadColumn, ICellCallback, Order } from 'app/components/table/IscTableHead';
import { Filter, IscTableContext } from 'app/components/table/IscTableContext';
import ISchoolElement from 'app/domain/school.element';
import Region from 'app/domain/region';
import EntityNameCell from 'app/components/EntityNameCell';
import { EntityDataProvider } from 'app/components/crud/EntityDataProvider';
import { RootState } from 'app/store/appState';
import * as Actions from 'app/store/actions/admin';
import * as Selectors from 'app/store/selectors';
import { EntityType, EMPTY_LIST } from 'app/utils/domain.constants';
import Zone from 'app/domain/zone';
import { filterRegion, findParentSchool } from 'app/components/map/utils/MapUtils';
import School from 'app/domain/school';
import { IEntity } from '../../../domain/entity';

const useStyles = makeStyles(() => ({
    rootSpecial: {
        width: '100%',
        position: 'absolute',
        left: '0px',
        right: '0px',
        top: '0px',
        bottom: '0px',
    },

    tickSize: {
        transform: 'scale(1.5)',
    },
}));

interface RegionExt extends Region {
    checked?: boolean;
}

function safeBoolean(boolValue: string | null): boolean {
    if (`${boolValue}` === 'true') {
        return true;
    }

    return false;
}

export const columns: Array<ITableHeadColumn> = [
    {
        id: 'selection',
        align: 'left',
        disablePadding: false,
        label: '',
        width: 60,
        filter: {
            filterType: 'boolean',
            filter: (name: string, filterValue: string | null, row: RegionExt): boolean => {
                const value = safeBoolean(filterValue);
                return (value && (row.checked || false)) || !value;
            },
        },
        formatter: (
            row: RegionExt,
            column: ITableHeadColumn,
            index: number,
            cellCallback?: ICellCallback
        ): React.ReactNode => {
            return (
                <td className="MuiTableCell-root MuiTableCell-sizeSmall" key={index}>
                    <Checkbox
                        key={index}
                        size="small"
                        style={{
                            transform: 'scale(1)',
                            padding: 2,
                        }}
                        checked={row.checked}
                        onChange={(event: React.ChangeEvent<HTMLInputElement>, checked: boolean) =>
                            cellCallback?.onAction
                                ? checked
                                    ? cellCallback?.onAction('selectRegion', row)
                                    : cellCallback?.onAction('deselectRegion', row)
                                : undefined
                        }
                        inputProps={{ 'aria-label': 'primary checkbox' }}
                    />
                </td>
            );
        },
    },
    {
        id: 'name',
        align: 'left',
        disablePadding: false,
        label: 'Name',
        sort: true,
        filter: {
            filterType: 'string',
            filter: (name: string, filterValue: string, row: ISchoolElement): boolean => {
                return (
                    `${name}`.toLowerCase().indexOf(filterValue) >= 0 ||
                    filterRegion('', filterValue, { parentIds: [row.id || ''] })
                );
            },
        },
        formatter: (row: ISchoolElement, column: ITableHeadColumn, index: number) => (
            <EntityNameCell entity={row} key={index} />
        ),
    },
];

interface RegionsListProps {
    zone?: Zone;
    topRegion: string;
    regions?: Region[];
    selectedRegion?: Region;
    onSelectElement?: (element: Region | null) => void;
    onDoubleClick?: (element: Region | null) => void;
    cellCallback?: ICellCallback;
}

interface RegionsListState {
    context: IscTableContext;
    loaded: boolean;
}

/**
 * Returns a table which contains the elements to be dragable into the map
 */
const RegionsList = (props: RegionsListProps): JSX.Element => {
    const classes = useStyles();
    const dispatch = useDispatch();
    const [zone, setZone] = useState<Zone | undefined>(props.zone);

    const allRegions = useSelector<RootState, ISchoolElement[]>(state => {
        const zoneId = zone?.id;
        const school = zoneId ? (findParentSchool([zoneId]) as School) : null;
        if (school) {
            const schoolRegion = Selectors.createSchoolRegionsTreeSelector(school.id)(state);
            return Selectors.createEntitiesSelector(schoolRegion?.id, [EntityType.REGION], true)(state);
        }

        return EMPTY_LIST;
    }).filter(region => region.id !== props.topRegion && region.id !== zone?.id);

    const regions: Region[] = [];
    allRegions.forEach(region => {
        if (!regions.find(r => r.id === region.id)) {
            regions.push(region as Region);
        }
    });

    const selectedRegions = regions.map(region => {
        return { ...region, checked: !!(props.regions && props.regions.find(r => r.id === region.id)) };
    });
    const [context, setContext] = useState<RegionsListState>({
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
        loaded: false,
    });
    const dataProvider = new EntityDataProvider(selectedRegions, columns, context.context, newContext => {
        if (!_.isEqual(context.context, newContext)) {
            setContext({
                ...context,
                context: newContext,
            });
        }
    });

    const onRowClick = (row: IEntity | null, dblClick?: boolean): void => {
        if (dblClick && props.onDoubleClick) {
            props.onDoubleClick(row as Region);
        } else if (props.onSelectElement) {
            props.onSelectElement(row as Region);
        }
    };

    const filteredData = (): ISchoolElement[] => {
        return dataProvider.data;
    };

    const onChangeFilter = (filter: Filter): void => {
        dataProvider.context = {
            ...context.context,
            filter,
            pagination: {
                ...context.context.pagination,
                currentPage: 0,
            },
        };
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

    useEffect(() => {
        if (!context.loaded) {
            dispatch(Actions.queryRegions());
            setContext({
                ...context,
                loaded: true,
            });
        }
        if (zone?.id !== props.zone?.id) {
            setZone(props.zone);
            dataProvider.context = {
                ...context.context,
                pagination: {
                    ...context.context.pagination,
                    currentPage: 0,
                },
            };
        } // eslint-disable-next-line
    }, [context, dispatch, props.zone]);

    return (
        <IscTable
            className={classes.rootSpecial}
            columns={columns}
            onRowClick={onRowClick}
            onRowDblClick={(row: IEntity | null) => onRowClick(row, true)}
            showPagination
            cellCallback={props.cellCallback}
            totalRecords={dataProvider.totalRecords}
            data={filteredData()}
            filter={context.context.filter}
            onChangeFilter={onChangeFilter}
            pagination={context.context.pagination}
            onChangePage={onChangePage}
            onChangeRowsPerPage={onChangeRowsPerPage}
            onSort={onSort}
            sortOrder={context.context.sortOrder}
            showFilters
            selectedRow={props.selectedRegion}
        />
    );
};

export default RegionsList;
