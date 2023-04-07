import React, { useEffect, useRef } from 'react';
import { makeStyles, Paper, Typography } from '@material-ui/core';
import _ from '@lodash';
import FusePageCarded from '@fuse/core/FusePageCarded';
import HOCWrapper from '@fuse/utils/HOCWrapper';

import { DIALOG_REASON_SUBMIT } from '@common/components/app.dialog.props';
import { EMPTY_SEARCH_RESULTS, SearchResult } from 'app/domain/search';
import { IEntity } from 'app/domain/entity';
import ISchoolElement from 'app/domain/school.element';
import Region from 'app/domain/region';
import { EntityMap, RootState } from 'app/store/appState';
import { CompositeNode, CompositeNodesMap } from 'app/domain/composite';
import { useSelector } from 'react-redux';
import * as Selectors from 'app/store/selectors';
import School from 'app/domain/school';
import { Filter, IscTableContext } from '../table/IscTableContext';
import { IscDataProvider } from '../table/IscTableDataProvider';
import { IColumnOrder, ITableHeadColumn, Order } from '../table/IscTableHead';
import UpdateEntityDialog from './EditEntityDialog';
import EntityCrudPageToolbar from './EntityCrudPageToolbar';
import { EMPTY_LIST, NONE_ENTITY } from '../../utils/domain.constants';
import { IEntityField, IUpdateEntityDialog } from './types';
import ErrorDialog, { IErrorDialog } from '../app/ServerErrorDialog';
import { ServerDataProvider } from '../table/providers/ServerDataProvider';
import IscTable from '../table/IscTable';
import IscTagList, { SelectValue } from '../IscTagList';
import ConfirmationDialog, { IConfirmationDialog } from '../app/ConfirmationDialog';
import { findParentSchool } from '../map/utils/MapUtils';

const useStyles = makeStyles(() => ({
    root: {
        width: '100%',
    },

    tableBody: {
        width: '100%',
    },

    text: {
        color: 'white',
        fontWeight: 'bold',
    },

    tagList: {
        minWidth: 300,
        zIndex: 1000,
    },
}));

function createFilter(column?: string, filterValue?: string): Filter {
    const filter: Filter = {};
    if (column && filterValue) {
        filter[column] = filterValue;
    }

    return filter;
}

export const createContext = (
    sortOrder?: IColumnOrder,
    filterColumn?: string,
    textFilter?: string
): IscTableContext => {
    return {
        pagination: {
            showPagination: true,
            rowsPerPage: 25,
            currentPage: 0,
        },
        sortOrder: sortOrder || {
            direction: 'asc',
            id: null,
        },
        filter: createFilter(filterColumn, textFilter),
        tags: [],
    };
};

export type CrudActionFunction = (data: any) => Promise<any>;

interface EntityCrudPageProps {
    queryAction: (context: IscTableContext) => Promise<any>;
    dataSelector: (data: any) => SearchResult;

    createAction?: CrudActionFunction;
    updateAction?: CrudActionFunction;
    deleteAction?: CrudActionFunction;

    sortOrder?: IColumnOrder;
    header?: React.ReactNode;
    columns?: Array<ITableHeadColumn>;
    entityFields: IEntityField[];
    onAction?: (action: string, row: any) => void;
    /**
     * Determines whether the grid and all its controls should be displayed. By default, it should always be
     * display. In case to be false, the Dialog form will pop up instead
     */
    showGridControls?: boolean;
    /**
     * Allow to set a different title name for the dialog
     */
    dialogTitle?: string;
    /**
     * Set a different label for the action button
     */
    addButtonLabel?: string;
    /**
     * Set a different label for the cancel button
     */
    cancelButtonLabel?: string;
    /**
     * Define whether the dialog should be closed in the action button or not
     */
    keepDialogOpened?: boolean;
    /**
     * Define whether the action button should be disabled or not (by default it depends on the validation)
     */
    disableActionButton?: boolean;
    /**
     * Show/Hide progress bar
     */
    showProgressBar?: boolean;

    showTagsSelector?: boolean;

    populateDeviceProps?: boolean;
}

export interface ISchoolElementWithRegions extends ISchoolElement {
    regions?: Region[];
}

export function fixEntityParentRegions(entities: IEntity[], regions: EntityMap<CompositeNode>): IEntity[] {
    const mapRegions = (schoolElement: ISchoolElementWithRegions): ISchoolElementWithRegions => {
        return {
            ...schoolElement,
            regions:
                schoolElement?.parentIds
                    ?.map((parent: string) => regions[parent] as Region)
                    .filter(region => !!region) || [],
        };
    };

    return entities.map(mapRegions);
}

export interface IEntityCrudPage {
    refresh: () => void;
}

const EntityCrudPage = React.forwardRef<IEntityCrudPage, EntityCrudPageProps>(
    (props: EntityCrudPageProps, ref: React.ForwardedRef<IEntityCrudPage>): JSX.Element => {
        const classes = useStyles();
        const { queryAction, columns, showGridControls, showTagsSelector } = props;
        const dialogRef = useRef<IUpdateEntityDialog>(null);
        const errorRef = useRef<IErrorDialog>(null);
        const confRef = useRef<IConfirmationDialog>(null);
        const regions = useSelector<RootState, CompositeNodesMap>(Selectors.allRegionsSelector);
        const currentSchoolId = useSelector<RootState, string | undefined>(Selectors.currentSchoolSelector);
        const schools = useSelector<RootState, School[]>(Selectors.schoolsSelector);
        const defaultSchoolRegionId = schools.find(s => s.id === currentSchoolId)?.region?.id;

        const [pageState, setPageState] = React.useState({
            tableData: {
                data: EMPTY_LIST,
                numberOfItems: 0,
                numberOfPages: 0,
            },
            showFilters: false,
            textFilter: '',
            updateRequired: true,
            context: createContext(props.sortOrder, props.entityFields[0]?.name, ''),
        });

        React.useImperativeHandle(ref, () => ({
            refresh() {
                setPageState({
                    ...pageState,
                    updateRequired: true,
                });
            },
        }));

        const reloadData = (): void => {
            const queryContext: IscTableContext = JSON.parse(JSON.stringify(pageState.context));
            if (!pageState.showFilters && pageState.textFilter && props.sortOrder?.id) {
                queryContext.filter[props.sortOrder.id] = pageState.textFilter;
            }

            queryAction(queryContext)
                .then(data => {
                    if (dialogRef.current) {
                        const tableData = props.dataSelector(data);
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
                    }
                })
                .catch(error => {
                    errorRef.current?.openDialog('Unable to Fetch data', error);
                });
        };

        const handleClose = (reason: string, data: IEntity): Promise<boolean> => {
            if (DIALOG_REASON_SUBMIT === reason) {
                return new Promise<boolean>(resolve => {
                    const actionName = data.id ? 'Update' : 'Create';
                    const action = data.id ? props.updateAction : props.createAction;

                    if (props.populateDeviceProps) {
                        const selectedSchoolId = (data as any).school;
                        const schoolRegion =
                            schools.find(s => s.id === selectedSchoolId)?.region?.id || defaultSchoolRegionId;
                        if (((data as any).regions?.length || 0) < 1 && schoolRegion) {
                            (data as any).regions = [schoolRegion];
                        }
                    }

                    (action as (data: unknown) => Promise<unknown>)(data)
                        .then(() => {
                            setPageState({
                                ...pageState,
                                updateRequired: true,
                            });
                            resolve(true);
                        })
                        .catch((/* error: Error */) => {
                            resolve(false);
                            const errorMsg = `Unable to ${actionName} entity`;
                            // if (error?.message) {
                            //     errorMsg += `: ${error.message}`;
                            // }

                            const entity = { ...data } as any;
                            if (entity.regions) {
                                entity.parentIds = entity.regions;
                                delete entity.regions;
                                entity.regions = [];
                                entity.parentIds.forEach((element: string) => {
                                    const parent = regions[element];
                                    if (parent) {
                                        entity.regions.push(parent);
                                    }
                                });
                            }
                            dialogRef.current?.openDialog(entity, errorMsg);
                        });
                });
            }

            return Promise.resolve(true);
        };

        const cellCallback = {
            onAction: (action: string, row: IEntity) => {
                if (action === 'edit' && dialogRef?.current) {
                    dialogRef.current.openDialog({ ...row });
                } else if (action === 'delete') {
                    if (props.deleteAction) {
                        confRef.current?.openDialog('Please confirm', row, props.deleteAction, errorRef, () => {
                            setPageState({
                                ...pageState,
                                updateRequired: true,
                            });
                        });
                    }
                } else if (props.onAction) {
                    console.log('this should never be called');
                    props.onAction(action, row);
                } else {
                    console.log('Table row action not implemented!', action, row);
                }
            },
            context: {},
        };

        const handleShowFilters = (show: boolean): void => {
            if (pageState.showFilters !== show) {
                const newState = show
                    ? {
                          ...pageState,
                          showFilters: show,
                      }
                    : {
                          ...pageState,
                          showFilters: show,
                          updateRequired: true,
                          context: {
                              ...pageState.context,
                              filter: {},
                          },
                      };

                setPageState(newState);
            }
        };

        const onChangeFilter = (filter: Filter): void => {
            setPageState({
                ...pageState,
                updateRequired: true,
                context: {
                    ...pageState.context,
                    filter,
                    pagination: {
                        ...pageState.context.pagination,
                        currentPage: 0,
                    },
                },
            });
        };

        const onChangePage = (page: number): void => {
            setPageState({
                ...pageState,
                updateRequired: true,
                context: {
                    ...pageState.context,
                    pagination: {
                        ...pageState.context.pagination,
                        currentPage: page,
                    },
                },
            });
        };

        const onChangeRowsPerPage = (rowsPerPage: number): void => {
            setPageState({
                ...pageState,
                updateRequired: true,
                context: {
                    ...pageState.context,
                    pagination: {
                        ...pageState.context.pagination,
                        rowsPerPage,
                        currentPage: 0,
                    },
                },
            });
        };

        const onSort = (columnId: string, sortOrder: Order): void => {
            setPageState({
                ...pageState,
                updateRequired: true,
                context: {
                    ...pageState.context,
                    sortOrder: {
                        direction: sortOrder,
                        id: columnId,
                    },
                },
            });
        };

        const handleAdd = (): void => {
            if (dialogRef?.current) {
                dialogRef.current.openDialog();
            }
        };

        const handleSearch = (text: string): void => {
            setPageState({
                ...pageState,
                updateRequired: true,
                textFilter: text,
            });
        };

        const handleTagsChange = (value: SelectValue[]): void => {
            const tagsList = value || [];
            const mappedTagList = tagsList.map(selectedValue => selectedValue.value);

            setPageState({
                ...pageState,
                updateRequired: true,
                context: {
                    ...pageState.context,
                    tags: mappedTagList,
                    pagination: {
                        ...pageState.context.pagination,
                        currentPage: 0,
                    },
                },
            });
        };

        useEffect(() => {
            if (pageState.updateRequired) {
                reloadData();
            }

            if (showGridControls === false) handleAdd();
        });

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

        return (
            <HOCWrapper
                componentRef={FusePageCarded}
                classes={{
                    content: 'flex',
                    header: 'min-h-52 h-52 sm:h-52 sm:min-h-52 pl-12 pr-12',
                }}
                header={
                    showGridControls !== false && (
                        <EntityCrudPageToolbar
                            showFilters={pageState.showFilters}
                            onShowFilters={handleShowFilters}
                            onAdd={props.createAction ? handleAdd : undefined}
                            onSearch={handleSearch}
                            textFilter={pageState.textFilter}
                            header={
                                <>
                                    {showTagsSelector && (
                                        <div className="flex flex-1 items-center justify-left px-12">
                                            <Typography className={classes.text}>Tags: &nbsp; </Typography>
                                            <IscTagList
                                                className={classes.tagList}
                                                variant="dark"
                                                onChange={handleTagsChange}
                                                selectedTags={pageState.context.tags}
                                            />
                                        </div>
                                    )}
                                    {props.header}
                                </>
                            }
                        />
                    )
                }
                content={
                    <>
                        {showGridControls !== false && (
                            <Paper className={classes.root}>
                                <IscTable
                                    className={classes.tableBody}
                                    columns={columns as Array<ITableHeadColumn>}
                                    showPagination
                                    showFilters={pageState.showFilters}
                                    cellCallback={cellCallback}
                                    totalRecords={dataProvider.totalRecords}
                                    data={dataProvider.data}
                                    pagination={pageState.context.pagination}
                                    filter={pageState.context.filter}
                                    onChangeFilter={onChangeFilter}
                                    onChangePage={onChangePage}
                                    onChangeRowsPerPage={onChangeRowsPerPage}
                                    onSort={onSort}
                                    sortOrder={pageState.context.sortOrder}
                                />
                            </Paper>
                        )}
                        <UpdateEntityDialog
                            entityFields={props.entityFields}
                            ref={dialogRef}
                            onClose={handleClose}
                            dialogTitle={props.dialogTitle}
                            addButtonLabel={props.addButtonLabel}
                            keepDialogOpened={props.keepDialogOpened}
                            disableActionButton={props.disableActionButton}
                            cancelButtonLabel={props.cancelButtonLabel}
                            showProgressBar={props.showProgressBar}
                        />
                        <ErrorDialog ref={errorRef} />
                        <ConfirmationDialog ref={confRef} />
                    </>
                }
            />
        );
    }
);

export default EntityCrudPage;
