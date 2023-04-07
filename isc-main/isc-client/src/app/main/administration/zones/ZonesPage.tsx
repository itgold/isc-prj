import React, { useRef, useState } from 'react';
import IscPageSimple from 'app/components/layout/IscPageSimple';
import HOCWrapper from '@fuse/utils/HOCWrapper';
import FusePageCarded from '@fuse/core/FusePageCarded';
import { Button, fade, Grid, makeStyles, Theme, Tooltip } from '@material-ui/core';
import { ThemeProvider } from '@material-ui/core/styles';
import AddIcon from '@material-ui/icons/Add';
import { FaFilter } from 'react-icons/fa';
import { RootState } from 'app/store/appState';
import { useSelector, useDispatch } from 'react-redux';
import SearchBar from '@common/components/core/search.bar';
import { DIALOG_REASON_SUBMIT } from '@common/components/app.dialog.props';
import { createStyles, DefaultTheme } from '@material-ui/styles';
import { SplitPanel, SplitView, ViewStyle } from '@common/components/layout/splitpanel';
import ISchoolElement from 'app/domain/school.element';
import Region from 'app/domain/region';
import Zone, { DEFAULT_ZONE_COLOR } from 'app/domain/zone';
import UpdateEntityDialog from 'app/components/crud/EditEntityDialog';
import { IUpdateEntityDialog } from 'app/components/crud/types';
import ErrorDialog, { IErrorDialog } from 'app/components/app/ServerErrorDialog';
import { ToggleButton } from '@material-ui/lab';
import chroma from 'chroma-js';
import FuseUtils from '@fuse/utils';
import queryService from 'app/services/query.service';
import { RegionType } from 'app/utils/domain.constants';
import { cloneDeep } from '@common/utils/dom.utils';
import store from 'app/store';
import * as Selectors from 'app/store/selectors';
import * as Actions from 'app/store/actions/admin';
import School from 'app/domain/school';
import { CompositeNodesMap } from 'app/domain/composite';
import ConfirmationDialog, { IConfirmationDialog } from 'app/components/app/ConfirmationDialog';
import ZonesList, { entityFields, ZoneWithExtraData } from './ZonesList';
import RegionsList from './RegionsList';
import EntitiesList from './EntitiesList';

const MINIMIZED_GRID_HEIGHT = 390;

const useStyles = makeStyles((theme: Theme) => {
    const baseColor = chroma(theme.palette.secondary.main);
    const baseLightColor = chroma(theme.palette.secondary.light);

    return createStyles({
        searchBar: {
            width: '400px',
            marginRight: 20,
        },

        resizerHorizontal: {},
        resizerVertical: {},

        filterBtn: {
            borderWidth: '1px',
            borderStyle: 'solid',
            color: theme.palette.secondary.contrastText,
            backgroundColor: theme.palette.secondary.main,
            borderColor: fade(theme.palette.secondary.dark, 0.25),

            '&:hover': {
                backgroundColor: theme.palette.secondary.dark,
                borderColor: baseLightColor.alpha(0.5).css(),
            },

            '&$selected': {
                borderWidth: '1px',
                borderStyle: 'solid',
                backgroundColor: theme.palette.secondary.dark,
                borderColor: theme.palette.secondary.light,
                color: theme.palette.text.secondary,

                '&:hover': {
                    backgroundColor: baseColor.alpha(0.8).css(),
                    borderColor: baseLightColor.alpha(0.9).css(),
                },
            },
        },
        selected: {},
        small: {},
        text: {
            color: 'white',
            fontWeight: 'bold',
        },
    });
});

interface SpeakerZonesToolbarProps {
    textFilter?: string;

    onSearch?: (text: string) => void;
    onAdd?: () => void;

    showFilters?: boolean;
    onShowFilters?: (show: boolean) => void;
}

function ZonesToolbar(props: SpeakerZonesToolbarProps): JSX.Element {
    const classes = useStyles();
    const mainTheme = useSelector<RootState, DefaultTheme>(({ fuse }) => fuse.settings.mainTheme);

    const handleAdd = (): void => {
        if (props.onAdd) {
            props.onAdd();
        }
    };

    const updateSearch = (value: string): void => {
        if (props.onSearch) {
            props.onSearch(value);
        }
    };

    const handleShowFilter = (): void => {
        if (props.onShowFilters) {
            props.onShowFilters(!props.showFilters);
        }
    };

    return (
        <div className="flex flex-1 w-full items-center justify-between">
            <Grid component="label" container alignItems="center" spacing={1}>
                <Grid item>
                    <Tooltip title="Show Filters" aria-label="show-filters">
                        <ToggleButton
                            size="small"
                            value="check"
                            selected={props.showFilters}
                            onChange={handleShowFilter}
                            classes={{
                                root: classes.filterBtn,
                                sizeSmall: classes.small,
                                selected: classes.selected,
                            }}
                        >
                            <FaFilter />
                        </ToggleButton>
                    </Tooltip>
                </Grid>
                <Grid item>
                    <ThemeProvider theme={mainTheme}>
                        <SearchBar
                            className={classes.searchBar}
                            value={props.textFilter}
                            onChange={updateSearch}
                            variant="small"
                            placeholder="Search by name ..."
                            cancelOnEscape
                        />
                    </ThemeProvider>
                </Grid>
            </Grid>

            <Button
                className="whitespace-no-wrap normal-case m-2"
                variant="contained"
                color="secondary"
                startIcon={<AddIcon />}
                onClick={handleAdd}
            >
                New Zone
            </Button>
        </div>
    );
}

interface SpeakerZonesSate {
    rightPanel: number;
    bottomPanel: number;
    region?: Region;
    currentZone?: ZoneWithExtraData;
    textFilter?: string;
    showFilters: boolean;
}

export default function SpeakerZones(): JSX.Element {
    const classes = useStyles();
    const dialogRef = useRef<IUpdateEntityDialog>(null);
    const errorRef = useRef<IErrorDialog>(null);
    const confRef = useRef<IConfirmationDialog>(null);
    const dispatch = useDispatch();

    const [pageState, setPageState] = useState<SpeakerZonesSate>({
        rightPanel: MINIMIZED_GRID_HEIGHT,
        bottomPanel: MINIMIZED_GRID_HEIGHT,
        showFilters: false,
    });

    const updateCurrentZone = (zone: ZoneWithExtraData): void => {
        if (zone?.id) {
            setPageState({
                ...pageState,
                currentZone: zone,
            });
        }
    };

    const handleSave = (zone: ZoneWithExtraData, newZone?: boolean): Promise<void> => {
        const crudAction = newZone ? queryService.createZone : queryService.updateZone;
        const newRegion = {
            ...cloneDeep(zone, ['type', 'color', 'updated', 'newZone', 'children']),
            type: RegionType.ZONE,
        } as ZoneWithExtraData;

        newRegion.parentIds = zone.parentIds;
        newRegion.childIds = zone.children
            ? Object.values(zone.children)
                  .map(region => region.id || '')
                  .filter(regionId => regionId.length) || []
            : [];
        newRegion.props = [];
        newRegion.props.push({ key: 'color', value: zone.color || DEFAULT_ZONE_COLOR });

        if (newZone) {
            newRegion.id = undefined;
        }

        const actionName = newZone ? 'Create' : 'Update';
        return new Promise<void>((resolve, reject) => {
            crudAction(newRegion)
                .then(response => {
                    const updatedZone = (newZone ? response.newZone : response.updateZone) as ZoneWithExtraData;
                    updatedZone.children = zone.children;
                    updatedZone.color =
                        updatedZone.props?.find(prop => prop.key === 'color')?.value || DEFAULT_ZONE_COLOR;
                    updateCurrentZone(updatedZone);

                    if (newZone) {
                        dispatch(Actions.loadRegionTree(true));
                    } else {
                        // dispatch(Actions.updateRegion(updatedZone));
                        dispatch(Actions.loadRegionTree(true));
                    }
                    resolve();
                })
                .catch((error: unknown) => {
                    errorRef.current?.openDialog(`Unable to ${actionName}`, error);
                    reject(error);
                });
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
            textFilter: text,
        });
    };

    const handleOnShowFilters = (show: boolean): void => {
        const newState = {
            ...pageState,
            showFilters: show,
        };
        if (!show) {
            newState.textFilter = '';
        }

        setPageState(newState);
    };

    const handleResizeEnd = (sizes: number[]): void => {
        // update sizes only when size changed and it is not minimized
        setPageState({
            ...pageState,
            rightPanel: sizes[1],
        });
    };
    const handleResizeEnd2 = (sizes: number[]): void => {
        // update sizes only when size changed and it is not minimized
        setPageState({
            ...pageState,
            bottomPanel: sizes[1],
        });
    };

    const onSelectZone = (row: Zone | null): void => {
        if (row?.id /* && pageState.currentZone?.id !== row.id */) {
            setPageState({
                ...pageState,
                currentZone: row,
                region: undefined,
            });
        }
    };
    const onSelectRegion = (row: Region | null): void => {
        if (row?.id /* && pageState.region?.id !== row.id */) {
            setPageState({
                ...pageState,
                region: row,
            });
        }
    };

    const cellCallback = {
        onAction: (action: string, row: ISchoolElement): void => {
            if (action === 'edit') {
                dialogRef.current?.openDialog(row);
            } else if (action === 'delete') {
                setPageState({
                    ...pageState,
                    currentZone: row as Zone,
                });

                confRef.current?.openDialog('Please confirm', row, queryService.deleteRegion, errorRef, () => {
                    setPageState({
                        ...pageState,
                        currentZone: undefined,
                    });
                    dispatch(Actions.loadRegionTree(true));
                });
            } else if (action === 'selectRegion') {
                if (pageState.currentZone && row.id) {
                    const children: CompositeNodesMap = { ...(pageState.currentZone.children || {}) };
                    children[row.id] = row;
                    const currentZone = {
                        ...pageState.currentZone,
                        children,
                    };
                    handleSave(currentZone);
                }
            } else if (action === 'deselectRegion') {
                if (pageState.currentZone && row.id) {
                    const children: CompositeNodesMap = {};
                    if (pageState.currentZone.children) {
                        Object.values(pageState.currentZone.children).forEach(child => {
                            if (child.id && child.id !== row.id) {
                                children[child.id] = child;
                            }
                        });
                    }

                    const currentZone = {
                        ...pageState.currentZone,
                        children,
                    };
                    handleSave(currentZone);
                }
            } else {
                console.log('Table row action not implemented!', action, row);
            }
        },
        context: {},
    };

    const resolveParentIds = (schoolId: string | undefined): string[] => {
        const school = (schoolId
            ? Selectors.createSchoolEntitySelector(schoolId)(store.getState())
            : undefined) as School;
        return school?.region?.id ? [school.region.id] : [];
    };

    const handleEditDialogClose = (reason: string, data: ZoneWithExtraData): Promise<boolean> => {
        if (DIALOG_REASON_SUBMIT === reason) {
            if (data.id) {
                const parentIds = resolveParentIds(data.school);
                // if school got changed we need to clean up both parents and children (because they still belong to different school)
                if (parentIds && (!data.parentIds?.length || !data.parentIds.includes(parentIds[0]))) {
                    data.parentIds = parentIds;
                    data.children = {};
                }
                updateCurrentZone(data);
                handleSave(data);
            } else {
                data.id = FuseUtils.generateGUID();
                data.parentIds = resolveParentIds(data.school);
                data.children = {};
                updateCurrentZone(data);
                handleSave(data, true);
            }
        }

        return Promise.resolve(true);
    };

    const gridPanelSizeRigh = Math.max(pageState.rightPanel, MINIMIZED_GRID_HEIGHT);
    const gridPanelSizeBottom = Math.max(pageState.bottomPanel, MINIMIZED_GRID_HEIGHT);

    return (
        <IscPageSimple>
            <HOCWrapper
                componentRef={FusePageCarded}
                classes={{
                    content: 'flex',
                    header: 'min-h-52 h-52 sm:h-52 sm:min-h-52 pl-12 pr-12',
                }}
                header={
                    <ZonesToolbar
                        showFilters={pageState.showFilters}
                        textFilter={pageState.textFilter}
                        onSearch={handleSearch}
                        onAdd={handleAdd}
                        onShowFilters={handleOnShowFilters}
                    />
                }
                content={
                    <SplitView
                        split={ViewStyle.vertical}
                        resizerStyle={classes.resizerVertical}
                        onResizeEnd={(sizes: number[]) => handleResizeEnd(sizes)}
                    >
                        <SplitPanel>
                            <ZonesList
                                textFilter={pageState.textFilter}
                                showFilters={pageState.showFilters}
                                onSelectElement={row => onSelectZone(row)}
                                onDoubleClick={row => onSelectZone(row)}
                                cellCallback={cellCallback}
                                selectedRow={pageState.currentZone}
                                onUpdateZone={zone => handleEditDialogClose(DIALOG_REASON_SUBMIT, zone)}
                            />
                        </SplitPanel>
                        <SplitPanel minSize={`${MINIMIZED_GRID_HEIGHT}px`} initialSize={`${gridPanelSizeRigh}px`}>
                            <SplitView
                                split={ViewStyle.horisontal}
                                resizerStyle={classes.resizerHorizontal}
                                onResizeEnd={(sizes: number[]) => handleResizeEnd2(sizes)}
                            >
                                <SplitPanel>
                                    <RegionsList
                                        zone={pageState.currentZone}
                                        topRegion={
                                            pageState.currentZone?.parentIds ? pageState.currentZone.parentIds[0] : ''
                                        }
                                        regions={
                                            pageState.currentZone?.children
                                                ? (Object.values(pageState.currentZone.children) as Region[])
                                                : []
                                        }
                                        onSelectElement={row => onSelectRegion(row)}
                                        onDoubleClick={row => onSelectRegion(row)}
                                        cellCallback={cellCallback}
                                        selectedRegion={pageState.region}
                                    />
                                </SplitPanel>
                                <SplitPanel
                                    minSize={`${MINIMIZED_GRID_HEIGHT}px`}
                                    initialSize={`${gridPanelSizeBottom}px`}
                                >
                                    <EntitiesList region={pageState.region} zone={pageState.currentZone} />
                                </SplitPanel>
                            </SplitView>
                        </SplitPanel>
                    </SplitView>
                }
            />
            <UpdateEntityDialog
                entityFields={entityFields}
                ref={dialogRef}
                onClose={handleEditDialogClose}
                dialogTitle="Create/Update Zone"
                addButtonLabel="Save"
            />
            <ErrorDialog ref={errorRef} />
            <ConfirmationDialog ref={confRef} />
        </IscPageSimple>
    );
}
