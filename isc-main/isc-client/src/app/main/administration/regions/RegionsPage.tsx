import React, { useState } from 'react';
import MomentUtils from '@date-io/moment';
import * as Actions from 'app/store/actions/admin';
import * as SchoolActions from 'app/store/actions/admin/model.queries';
import EntityCrudPage, { fixEntityParentRegions } from 'app/components/crud/EntityCrudPage';
import { ICellCallback, IColumnOrder, ITableHeadColumn } from 'app/components/table/IscTableHead';
import { EntityMap, RootState } from 'app/store/appState';
import * as Selectors from 'app/store/selectors';
import {
    Button,
    createStyles,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    makeStyles,
    TableCell,
    Tooltip,
} from '@material-ui/core';
import { FaEdit, FaTrashAlt } from 'react-icons/fa';
import { useDispatch, useSelector } from 'react-redux';
import queryService, { CrudResponse } from 'app/services/query.service';
import {
    allAssignableRegionTypes,
    createQueryFilter,
    EntityType,
    NONE_ENTITY,
    RegionType,
} from 'app/utils/domain.constants';
import EntityNameCell from 'app/components/EntityNameCell';
import Region from 'app/domain/region';
import School from 'app/domain/school';
import { SearchResult } from 'app/domain/search';
import { IscTableContext } from 'app/components/table/IscTableContext';
import { CompositeNode, CompositeType } from 'app/domain/composite';
import { findParentSchool, findRegionsSchool } from 'app/components/map/utils/MapUtils';
import SelectFormsy from '@fuse/core/formsy/SelectFormsy';
import { TreeItemData } from '@common/components/tree.dropdown';
import Formsy from 'formsy-react';
import ISchoolElement from '../../../domain/school.element';
import { IEntityField } from '../../../components/crud/types';

const momentUtils: MomentUtils = new MomentUtils();

interface RegionMap {
    [key: string]: string[];
}
const regionTypesMap: RegionMap = {
    SCHOOL: ['SCHOOL_DISTRICT'],
    BUILDING: ['SCHOOL_DISTRICT', 'SCHOOL'],
    ELEVATOR: ['FLOOR'],
    FLOOR: ['BUILDING'],
    ROOM: ['FLOOR'],
    STAIRS: ['BUILDING', 'FLOOR'],
    WALL: ['FLOOR', 'ROOM'],
    UNKNOWN: ['SCHOOL_DISTRICT', 'SCHOOL', 'BUILDING', 'FLOOR', 'ROOM'],
    ZONE: ['SCHOOL'],
    POINT_REGION: ['SCHOOL', 'BUILDING', 'FLOOR', 'ROOM', 'ELEVATOR', 'STAIRS', 'WALL'],
};
export const allowedParentRegions = (regionType: string): string[] => {
    return regionTypesMap[regionType] || [];
};

export const regionsSelector = (state: RootState): ISchoolElement[] => {
    const regions = Selectors.createEntitiesSelector(Selectors.compositeRootSelector(state).id, [EntityType.REGION])(
        state
    );
    return regions.filter(region => region.id !== NONE_ENTITY);
};

export const isEntityDeletable = (entity: ISchoolElement): boolean => {
    const restricted = [RegionType.SCHOOL, RegionType.SCHOOL_DISTRICT, RegionType.ROOT];
    const region = entity as Region;
    return !restricted.includes(region.type as RegionType);
};

export const convertTree = (
    regionType: string,
    children: TreeItemData[],
    allowedRegions: (regionType: string) => string[],
    entity?: Region
): TreeItemData[] => {
    const node: TreeItemData[] = [];

    // for each children of array
    children
        .filter(
            (childNode: TreeItemData) => childNode.compositeType && childNode.compositeType === CompositeType.CONTAINER
        )
        .filter((childNode: TreeItemData) => (childNode as Region).type !== RegionType.ZONE)
        .forEach((childNode: TreeItemData) => {
            const region: TreeItemData = {
                ...childNode,
                disabled:
                    allowedRegions(regionType).indexOf(childNode.type || 'UNKNOWN') < 0 || childNode.id === entity?.id,
            };

            if (region.children) {
                region.children = convertTree(regionType, Object.values(region.children), allowedRegions, entity);
            }
            node.push(region);
        });
    return node;
};

const columns: Array<ITableHeadColumn> = [
    {
        id: 'name',
        align: 'left',
        disablePadding: false,
        label: 'Name',
        sort: true,
        filter: {
            filterType: 'string',
            filter: (value: ISchoolElement, filterValue: string) => `${value}`.indexOf(filterValue) >= 0,
        },
    },
    {
        id: 'type',
        align: 'left',
        disablePadding: false,
        label: 'Type',
        sort: true,
        filter: 'string',
        width: 120,
    },
    {
        id: 'updated',
        align: 'center',
        disablePadding: false,
        label: 'Updated',
        width: 120,
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
        id: 'regions',
        align: 'center',
        disablePadding: false,
        label: 'Parent Region',
        filter: 'string',
        formatter: (row: ISchoolElement, column: ITableHeadColumn, index: number) => (
            <EntityNameCell entity={row} skipEntityName key={index} />
        ),
    },
    {
        id: 'actions',
        align: 'right',
        disablePadding: false,
        label: 'Actions',
        width: 100,
        formatter: (row: ISchoolElement, column: ITableHeadColumn, index: number, cellCallback?: ICellCallback) => {
            return (
                <TableCell padding="default" key={index} align="right">
                    {isEntityDeletable(row) && (
                        <Tooltip title="Edit">
                            <IconButton
                                aria-label="edit"
                                size="small"
                                onClick={() =>
                                    cellCallback?.onAction ? cellCallback?.onAction('edit', row) : undefined
                                }
                            >
                                <FaEdit />
                            </IconButton>
                        </Tooltip>
                    )}
                    {isEntityDeletable(row) && (
                        <Tooltip title="Delete">
                            <IconButton
                                aria-label="delete"
                                size="small"
                                onClick={() =>
                                    cellCallback?.onAction ? cellCallback?.onAction('delete', row) : undefined
                                }
                            >
                                <FaTrashAlt />
                            </IconButton>
                        </Tooltip>
                    )}
                </TableCell>
            );
        },
    },
];

const regionDataSelector = (
    state: RootState,
    params?: School,
    entity?: Region,
    allowedRegions: (regionType: string) => string[] = allowedParentRegions
): TreeItemData[] => {
    const schoolRegion = Selectors.createSchoolRegionsTreeSelector(params?.id)(state) as CompositeNode;
    // convert the tree in a structure which we can iterate over it while creating the options for the dropdown tree view
    const regionType = entity?.type || 'UNKNOWN';
    const topTreeNodes = schoolRegion
        ? convertTree(
              regionType,
              [
                  {
                      ...(schoolRegion as TreeItemData),
                      disabled: allowedRegions(regionType).indexOf(schoolRegion.type || 'UNKNOWN') < 0,
                  },
              ],
              allowedRegions,
              entity
          )
        : [];
    return topTreeNodes;
};

const entityFields: IEntityField[] = [
    { name: 'name', inputLabel: 'Name' },
    {
        alias: 'hidden_type',
        name: 'type',
        inputLabel: 'Type',
        type: 'label',
        hidden: (entity: ISchoolElement): boolean => {
            return isEntityDeletable(entity);
        },
    },
    {
        name: 'type',
        inputLabel: 'Type',
        type: 'enum',
        valueOptions: allAssignableRegionTypes(),
        defaultValue: 'UNKNOWN',
        hidden: (entity: ISchoolElement): boolean => {
            return !isEntityDeletable(entity);
        },
    },
    {
        name: 'subType',
        inputLabel: 'SubType',
        type: 'enum',
        valueOptions: [EntityType.CAMERA],
        defaultValue: EntityType.CAMERA,
        hidden: (entity: ISchoolElement): boolean => {
            return (entity as Region).type !== RegionType.POINT_REGION;
        },
    },
    {
        name: 'floorNumber',
        inputLabel: 'Floor #',
        type: 'string',
        defaultValue: (entity: Region) => {
            return entity?.props?.find(prop => prop.key === 'floorNumber')?.value || '0';
        },
        optional: true,
        hidden: (entity: ISchoolElement): boolean => {
            return (entity as Region).type !== RegionType.FLOOR;
        },
    },
    {
        name: 'floorHeight',
        inputLabel: 'Height',
        type: 'string',
        defaultValue: (entity: Region) => {
            return entity?.props?.find(prop => prop.key === 'floorHeight')?.value || '13';
        },
        optional: true,
        hidden: (entity: ISchoolElement): boolean => {
            return (entity as Region).type !== RegionType.FLOOR;
        },
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
            dataSelector: regionDataSelector,
        },
        defaultValue: [],
    },
];

interface DeleteDialogProps {
    regionToDelete?: Region;
    onCancel?: () => void;
    onConfirm?: (moveChildrenTo: string | null) => void;
    onClose?: (event: unknown, reason: 'backdropClick' | 'escapeKeyDown') => void;
}

const useStyles = makeStyles(() =>
    createStyles({
        root: {
            width: '100%',
            minWidth: '400px',
            marginTop: '0px',
            paddingTop: '0px',
            overflowY: 'visible',
            backgroundColor: 'transparent',
        },
        dialog: {
            '& .MuiDialog-paper': {
                overflowY: 'visible',
            },
        },
        dialogTitle: {
            marginBottom: '0px',
            paddingBottom: '0px',
        },
    })
);

const DeleteDialog = (props: DeleteDialogProps): JSX.Element => {
    const allowedRegions = (): string[] => {
        return props.regionToDelete?.type ? [props.regionToDelete.type] : [];
    };
    const treeData = useSelector<RootState, TreeItemData[]>((rootState: RootState) => {
        const regionsSchool = props.regionToDelete?.parentIds
            ? findRegionsSchool(props.regionToDelete?.parentIds)
            : undefined;
        return regionDataSelector(rootState, regionsSchool, props.regionToDelete, allowedRegions);
    });
    const [data, setData] = useState({
        selected: [] as { value?: unknown }[],
        data: treeData as TreeItemData[],
    });

    const onCancel = (): void => {
        if (props.onCancel) {
            props.onCancel();
        }
    };

    const onConfirm = (): void => {
        if (props.onConfirm) {
            const value: string | null = data.selected.length ? (data.selected[0] as string) : null;
            props.onConfirm(value);
        }
    };
    const onClose = (event: unknown, reason: 'backdropClick' | 'escapeKeyDown'): void => {
        if (props.onClose) {
            props.onClose(event, reason);
        }
    };

    const onChangeTree = (
        event: React.ChangeEvent<{
            name?: string | undefined;
            value: unknown;
        }>
    ): void => {
        const selectedId =
            event.target.value && Array.isArray(event.target.value) && event.target.value.length
                ? event.target.value[0]
                : null;
        setData({
            selected: selectedId ? [selectedId] : [],
            data: treeData as TreeItemData[],
        });
    };

    const classes = useStyles();
    return (
        <Dialog
            fullWidth
            open
            onClose={onClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
            className={classes.dialog}
        >
            <DialogTitle className={classes.dialogTitle} id="alert-dialog-title">
                Delete Region
            </DialogTitle>
            <DialogContent className={classes.root}>
                <div style={{ minHeight: '120px' }} className="w-full">
                    <Formsy className="flex flex-col justify-center w-full">
                        <span>
                            The Region you are about to delete has children assigned. Please specify target region you
                            want to move all children to:
                        </span>
                        <SelectFormsy
                            showCheckboxes
                            required
                            name="regions"
                            label="Target Region"
                            tree
                            treeOptions={data.data}
                            treeHandleExpand
                            onChange={onChangeTree}
                        />
                    </Formsy>
                </div>
            </DialogContent>
            <DialogActions>
                <Button color="primary" onClick={onCancel}>
                    Cancel
                </Button>
                <Button color="primary" autoFocus onClick={onConfirm}>
                    Confirm
                </Button>
            </DialogActions>
        </Dialog>
    );
};

interface ComponentState {
    regionToDelete?: Region;
    showDialog: boolean;
    resolve?: (value?: CrudResponse) => void;
    reject?: (reason?: unknown) => void;
}

export default function EnhancedTable(): JSX.Element {
    const defaultSort: IColumnOrder = {
        id: 'name',
        direction: 'asc',
    };

    const [state, setState] = useState<ComponentState>({
        showDialog: false,
    });
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
        return queryService.queryRegionsWithFilter(
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
    const createAction = (data: Region): Promise<void | CrudResponse> => {
        return queryService.createRegion(data).then((payload: void | CrudResponse) => {
            dispatch(Actions.addRegion(payload));
            dispatch(Actions.loadRegionTree(true));
        });
    };
    const updateAction = (data: Region): Promise<void | CrudResponse> => {
        return queryService.updateRegion(data).then((payload: void | CrudResponse) => {
            dispatch(Actions.updateRegionSuccess(payload));
            dispatch(Actions.loadRegionTree(true));
        });
    };
    const deleteAction = (data: string): Promise<void | CrudResponse | undefined> => {
        const regionToDelete = regions[data];
        const children = regionToDelete?.children ? Object.values(regionToDelete?.children) : [];
        if (!children.length) {
            return queryService.deleteRegion(data).then((payload: CrudResponse) => {
                dispatch(Actions.deleteRegionSuccess(payload));
                dispatch(Actions.loadRegionTree(true));
                return payload;
            });
        }

        return new Promise<CrudResponse | undefined>((resolve, reject) => {
            setState({
                regionToDelete: regionToDelete as Region,
                showDialog: true,
                resolve,
                reject,
            });
        });
    };

    const onConfirmDelete = (moveChildrenTo: string | null): void => {
        const regionToDelete = state.regionToDelete?.id;
        if (regionToDelete) {
            const { resolve, reject } = state;

            queryService
                .deleteRegion(regionToDelete, moveChildrenTo)
                .then((payload: CrudResponse) => {
                    dispatch(Actions.deleteRegionSuccess(payload));
                    dispatch(Actions.loadRegionTree(true));

                    if (resolve) {
                        resolve(payload);
                    }

                    return payload;
                })
                .catch(e => {
                    if (reject) {
                        reject(e);
                    }
                });
        }

        setState({
            showDialog: false,
            regionToDelete: undefined,
            resolve: undefined,
            reject: undefined,
        });
    };
    const onCloseDeleteDialog = (): void => {
        if (state.resolve) {
            state.resolve();
        }

        setState({
            showDialog: false,
            regionToDelete: undefined,
            resolve: undefined,
            reject: undefined,
        });
    };

    return (
        <>
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
            {state.showDialog && (
                <DeleteDialog
                    onConfirm={onConfirmDelete}
                    onClose={onCloseDeleteDialog}
                    onCancel={onCloseDeleteDialog}
                    regionToDelete={state.regionToDelete}
                />
            )}
        </>
    );
}
