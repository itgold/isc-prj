import React, { useRef, useEffect, useState } from 'react';
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
import { createStyles, DefaultTheme } from '@material-ui/styles';
import { SplitPanel, SplitView, ViewStyle } from '@common/components/layout/splitpanel';
import ErrorDialog, { IErrorDialog } from 'app/components/app/ServerErrorDialog';
import { ToggleButton } from '@material-ui/lab';
import chroma from 'chroma-js';
import eventsService from 'app/services/events.service';
import ConfirmationDialog, { IConfirmationDialog } from 'app/components/app/ConfirmationDialog';
import AlertTrigger, { AlertTriggerMatcher, AlertTriggerMatcherType } from 'app/domain/alertTrigger';
import TriggersList from './TriggersList';
import AlertTriggerDetails from './TriggerDetails';
import { IEntity } from '../../../domain/entity';

const MINIMIZED_GRID_HEIGHT = 460;

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

interface AlertTriggersToolbarProps {
    textFilter?: string;

    onSearch?: (text: string) => void;
    onAdd?: () => void;

    showFilters?: boolean;
    onShowFilters?: (show: boolean) => void;
}

function AlertTriggersToolbar(props: AlertTriggersToolbarProps): JSX.Element {
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
                New Alert Trigger
            </Button>
        </div>
    );
}

interface SpeakerZonesSate {
    leftPanel: number;
    currentTrigger?: AlertTrigger;
    textFilter?: string;
    showFilters: boolean;
}

function processors(alertTriggerProcessors: string[], trigger?: AlertTrigger): string[] {
    const list: string[] = [...alertTriggerProcessors];
    if (trigger?.processorType && list.indexOf(trigger?.processorType) < 0) {
        list.push(trigger.processorType);
    }

    return list;
}

export default function AlertTriggersPage(): JSX.Element {
    const classes = useStyles();
    const errorRef = useRef<IErrorDialog>(null);
    const confRef = useRef<IConfirmationDialog>(null);
    const [alertTriggerProcessors, setAlertTriggerProcessors] = useState<string[]>([]);

    const [pageState, setPageState] = useState<SpeakerZonesSate>({
        leftPanel: MINIMIZED_GRID_HEIGHT,
        showFilters: false,
    });

    const handleAdd = (): void => {
        setPageState({
            ...pageState,
            currentTrigger: {
                active: true,
                matchers: [
                    {
                        type: AlertTriggerMatcherType.CUSTOM,
                        body: `
                        {
                            "and": {
                                "items": [
                                    {
                                        "property": "dateTime.date",
                                        "operator": ">",
                                        "value": "2021-05-01"
                                    },
                                    {
                                        "property": "dateTime.date",
                                        "operator": "<",
                                        "value": "2025-05-30"
                                    }
                                ]
                            }
                        }
                        `,
                    } as AlertTriggerMatcher,
                ],
            } as AlertTrigger,
        });
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
            leftPanel: sizes[1],
        });
    };

    const onSelectZone = (row: AlertTrigger | null): void => {
        if (row?.id /* && pageState.currentZone?.id !== row.id */) {
            setPageState({
                ...pageState,
                currentTrigger: row,
            });
        }
    };

    const cellCallback = {
        onAction: (action: string, row: IEntity): void => {
            if (action === 'delete') {
                setPageState({
                    ...pageState,
                    currentTrigger: row as AlertTrigger,
                });

                confRef.current?.openDialog('Please confirm', row, eventsService.deleteAlertTrigger, errorRef, () => {
                    setPageState({
                        ...pageState,
                        currentTrigger: undefined,
                    });
                });
            } else {
                console.log('Table row action not implemented!', action, row);
            }
        },
        context: {},
    };

    const onUpdated = (entity: AlertTrigger): void => {
        setPageState({
            ...pageState,
            currentTrigger: entity,
        });
    };

    useEffect(() => {
        let isActive = true;
        if (!alertTriggerProcessors.length) {
            eventsService.alertTriggerProcessors().then(rez => {
                if (isActive) setAlertTriggerProcessors(rez);
            });
        }

        return () => {
            isActive = false;
        };
    });

    const gridPanelSizeLeft = Math.max(pageState.leftPanel, MINIMIZED_GRID_HEIGHT);

    return (
        <IscPageSimple>
            <HOCWrapper
                componentRef={FusePageCarded}
                classes={{
                    content: 'flex',
                    header: 'min-h-52 h-52 sm:h-52 sm:min-h-52 pl-12 pr-12',
                }}
                header={
                    <AlertTriggersToolbar
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
                        <SplitPanel minSize={`${MINIMIZED_GRID_HEIGHT}px`} initialSize={`${gridPanelSizeLeft}px`}>
                            <TriggersList
                                textFilter={pageState.textFilter}
                                showFilters={pageState.showFilters}
                                onSelectElement={row => onSelectZone(row)}
                                onDoubleClick={row => onSelectZone(row)}
                                cellCallback={cellCallback}
                                selectedRow={pageState.currentTrigger}
                            />
                        </SplitPanel>
                        <SplitPanel>
                            <AlertTriggerDetails
                                processors={processors(alertTriggerProcessors, pageState.currentTrigger)}
                                trigger={pageState.currentTrigger}
                                onUpdated={onUpdated}
                            />
                        </SplitPanel>
                    </SplitView>
                }
            />
            <ErrorDialog ref={errorRef} />
            <ConfirmationDialog ref={confRef} />
        </IscPageSimple>
    );
}
