import React from 'react';
import MomentUtils from '@date-io/moment';
import * as Actions from 'app/store/actions/admin';
import * as CommonActions from 'app/store/actions/admin/models.core.queries';
import * as SchoolActions from 'app/store/actions/admin/model.queries';
import * as Selectors from 'app/store/selectors';
import EntityCrudPage, { fixEntityParentRegions } from 'app/components/crud/EntityCrudPage';
import { ICellCallback, IColumnOrder, ITableHeadColumn } from 'app/components/table/IscTableHead';
import { EntityMap, RootState } from 'app/store/appState';
import { IconButton, TableCell, Tooltip } from '@material-ui/core';
import { FaEdit, FaTrashAlt } from 'react-icons/fa';
import { IEntityField } from 'app/components/crud/types';
import queryService from 'app/services/query.service';
import { createQueryFilter, NONE_ENTITY } from 'app/utils/domain.constants';
import { useDispatch, useSelector } from 'react-redux';
import IscPageSimple from 'app/components/layout/IscPageSimple';
import EntityNameCell from 'app/components/EntityNameCell';
import School from 'app/domain/school';
import { SearchResult } from 'app/domain/search';
import { IscTableContext } from 'app/components/table/IscTableContext';
import Region from 'app/domain/region';
import { convertRegionsTree, findParentSchool } from 'app/components/map/utils/MapUtils';
import { CompositeNode } from 'app/domain/composite';
import { TreeItemData } from '@common/components/tree.dropdown';
import ISchoolElement from '../../../domain/school.element';

const momentUtils: MomentUtils = new MomentUtils();

const columns: Array<ITableHeadColumn> = [
    {
        id: 'name',
        align: 'left',
        width: 180,
        disablePadding: false,
        label: 'Name',
        sort: true,
        filter: 'string',
    },
    {
        id: 'type',
        align: 'left',
        width: 120,
        disablePadding: false,
        label: 'Type',
        sort: true,
        filter: {
            filterType: 'string',
            filter: (value: ISchoolElement, filterValue: string) => `${value}`.indexOf(filterValue) >= 0,
        },
    },
    {
        id: 'status',
        align: 'left',
        width: 120,
        disablePadding: false,
        label: 'Status',
        sort: true,
        filter: 'string',
    },
    {
        id: 'updated',
        align: 'center',
        width: 120,
        disablePadding: false,
        label: 'Updated',
        sort: true,
        filter: {
            filterType: 'date',
            format: 'YYYY-MM-DD',
        },
        formatter: (row: ISchoolElement, column: ITableHeadColumn, index: number) => (
            <td className="MuiTableCell-root MuiTableCell-sizeSmall" key={index}>
                {momentUtils.format(momentUtils.date(row.updated), 'YYYY-MM-DD')}
            </td>
        ),
    },
    {
        id: 'region',
        align: 'center',
        disablePadding: false,
        label: 'Region',
        filter: 'string',
        formatter: (row: ISchoolElement, column: ITableHeadColumn, index: number) => (
            <EntityNameCell entity={row} skipEntityName key={index} />
        ),
    },
    {
        id: 'actions',
        align: 'left',
        disablePadding: false,
        label: 'Actions',
        width: 100,
        formatter: (row: ISchoolElement, column: ITableHeadColumn, index: number, cellCallback?: ICellCallback) => {
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
    { name: 'id', type: 'label', optional: true },
    { name: 'externalId' },
    { name: 'name' },
    { name: 'description', type: 'text', optional: true },
    { name: 'status', type: 'enum', valueOptions: ['ACTIVATED', 'DEACTIVATED'], defaultValue: 'ACTIVATED' },
    {
        name: 'type',
        type: 'enum',
        valueOptions: ['UNKNOWN', 'INDOOR', 'PERIMETER', 'GATE'],
        defaultValue: 'INDOOR',
    },
    {
        name: 'school',
        inputLabel: 'School',
        type: 'asyncEnum',
        optional: true,
        valueOptionsAsync: {
            queryAction: SchoolActions.querySchools,
            dataSelector: Selectors.schoolsSelector,
        },
        defaultValue: (entity: Region) => {
            return findParentSchool(entity?.parentIds);
        },
    },
    {
        name: 'regions',
        type: 'asyncEnum',
        inputLabel: 'Parent Region',
        optional: true,
        dependsOn: 'school',
        multi: true,
        showAsTree: true,
        valueOptionsAsync: {
            queryAction: Actions.queryRegionsDropdown,
            dataSelector: (state: RootState, params?: School, entity?: Region) => {
                const schoolRegion = Selectors.createSchoolRegionsTreeSelector(params?.id)(state) as CompositeNode;
                // convert the tree in a structure which we can iterate over it while creating the options for the dropdown tree view
                const regionType = entity?.type || 'UNKNOWN';
                const topTreeNodes = schoolRegion
                    ? convertRegionsTree(regionType, [
                          {
                              ...(schoolRegion as TreeItemData),
                          },
                      ])
                    : [];

                return topTreeNodes;
            },
        },
        defaultValue: [],
    },
    {
        name: 'tags',
        type: 'asyncEnum',
        multi: true,

        valueOptionsAsync: {
            queryAction: CommonActions.queryTags,
            dataSelector: (state: RootState) => {
                return Selectors.tagsSelector(state).map(tag => tag.name);
            },
        },
    },
];

export default function EnhancedTable(): JSX.Element {
    const defaultSort: IColumnOrder = {
        id: 'name',
        direction: 'asc',
    };

    const regions = useSelector<RootState, EntityMap<CompositeNode>>(Selectors.allRegionsSelector);
    const dataSelector = (data: SearchResult): SearchResult => {
        return {
            ...data,
            data: fixEntityParentRegions(data.data, regions),
        };
    };
    const queryAction = (context: IscTableContext): Promise<SearchResult> => {
        const filter = {
            tags: context.tags,
            columns: createQueryFilter(context.filter),
        };
        return queryService.queryDoorsWithFilter(
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
        console.log(data);
        return queryService.createDoor(data).then((payload: any) => {
            dispatch(Actions.addDoor(payload));
            dispatch(Actions.loadRegionTree(true));
        });
    };
    const updateAction = (data: any): Promise<any> => {
        return queryService.updateDoor(data).then((payload: any) => {
            dispatch(Actions.updateDoorSuccess(payload));
            dispatch(Actions.loadRegionTree(true));
        });
    };
    const deleteAction = (data: any): Promise<any> => {
        return queryService.deleteDoor(data).then((payload: any) => {
            dispatch(Actions.deleteDoorSuccess(payload));
            dispatch(Actions.loadRegionTree(true));
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
                showTagsSelector
                populateDeviceProps
            />
        </IscPageSimple>
    );
}
