import React from 'react';
import clsx from 'clsx';
import { Theme, fade } from '@material-ui/core/styles';
import { makeStyles, createStyles } from '@material-ui/styles';
import { FaFilter } from 'react-icons/fa';
import ToggleButton from '@material-ui/lab/ToggleButton';
import chroma from 'chroma-js';
import { Badge, IconButton, Tooltip } from '@material-ui/core';
import { useIscTableContext, useIscTableDataProvider } from 'app/components/table/IscTableContext';
import { MdCached } from 'react-icons/md';

const useToolbarStyles = makeStyles((theme: Theme) => {
    const baseColor = chroma(theme.palette.secondary.main);
    const baseLightColor = chroma(theme.palette.secondary.light);

    return createStyles({
        root: {
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

const useStyles = makeStyles(() =>
    createStyles({
        root: {},

        badgeStyle: {
            marginBottom: -8,
        },
    })
);

interface DeviceEventsControlsProps {
    showFilters?: boolean;

    onToggleFilters?: (showFilters: boolean) => void;

    toolBarItems?: React.ReactNode;

    newEvents: number;

    onRefreshGrid: (reset: boolean) => void;
}

export default function DeviceEventsControls(props: DeviceEventsControlsProps): JSX.Element {
    const tableContext = useIscTableContext();
    const tableDataAdapter = useIscTableDataProvider();

    const handleShowFilter = (): void => {
        const show = !props.showFilters;
        if (props.onToggleFilters) {
            props.onToggleFilters(show);
        }

        if (!show) {
            tableDataAdapter.context = {
                ...tableContext,
                pagination: {
                    ...tableContext.pagination,
                    currentPage: 0,
                },
                filter: {},
            };
        }
    };

    const onResetGrid = (): void => {
        if (props.onRefreshGrid) {
            props.onRefreshGrid(true);
        }
    };
    const onRefreshGrid = (): void => {
        if (props.onRefreshGrid) {
            props.onRefreshGrid(false);
        }
    };

    const toolbarClasses = useToolbarStyles();
    const classes = useStyles();

    return (
        <div className={clsx('flex flex-1 w-full items-center justify-between', classes.root)}>
            <div className="flex items-center">
                <Tooltip title="Show Filters" aria-label="show-filters">
                    <ToggleButton
                        size="small"
                        value="check"
                        selected={props.showFilters}
                        onChange={handleShowFilter}
                        classes={{
                            root: toolbarClasses.root,
                            sizeSmall: toolbarClasses.small,
                            selected: toolbarClasses.selected,
                        }}
                    >
                        <FaFilter />
                    </ToggleButton>
                </Tooltip>
            </div>

            {props.toolBarItems}

            {props.newEvents > 0 && (
                <div className="flex items-right">
                    <Tooltip title="Refresh">
                        <IconButton className={classes.badgeStyle} onClick={onRefreshGrid}>
                            <Badge badgeContent={props.newEvents} color="secondary">
                                <MdCached color="action" />
                            </Badge>
                        </IconButton>
                    </Tooltip>
                    <Tooltip title="Reset">
                        <IconButton className={classes.badgeStyle} onClick={onResetGrid}>
                            <Badge badgeContent="RESET" color="secondary">
                                <MdCached color="action" />
                            </Badge>
                        </IconButton>
                    </Tooltip>
                </div>
            )}
        </div>
    );
}
