/* eslint-disable react-hooks/exhaustive-deps */
import React, { useEffect } from 'react';
import { Theme, fade } from '@material-ui/core/styles';
import { makeStyles, createStyles, DefaultTheme } from '@material-ui/styles';
import { FaFilter } from 'react-icons/fa';
import ToggleButton from '@material-ui/lab/ToggleButton';
import IscTagList, { SelectValue } from 'app/components/IscTagList';
import chroma from 'chroma-js';
import { Typography, Tooltip, ThemeProvider } from '@material-ui/core';
import { useIscTableContext, useIscTableDataProvider } from 'app/components/table/IscTableContext';
import SearchBar from '@common/components/core/search.bar';
import { useSelector } from 'react-redux';
import { RootState } from 'app/store/appState';

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

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        tagList: {
            minWidth: 200,
            zIndex: 1000,
        },
        menuButton: {
            marginLeft: theme.spacing(2),
        },
        menuButtonSmall: {
            marginLeft: theme.spacing(2),
            marginRight: theme.spacing(2),
        },
        hide: {
            display: 'none',
        },
        searchBar: {
            width: '300px',
            marginRight: 20,
        },
    })
);

interface DevicesGridControlsProps {
    showFilters?: boolean;

    onToggleFilters?: (showFilters: boolean) => void;
    selectedSchool?: string | undefined;

    toolBarItems?: React.ReactNode;
}

export default function DevicesGridControls(props: DevicesGridControlsProps): JSX.Element {
    const tableContext = useIscTableContext();
    const tableDataAdapter = useIscTableDataProvider();
    const mainTheme = useSelector<RootState, DefaultTheme>(({ fuse }) => fuse.settings.mainTheme);

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

    const toolbarClasses = useToolbarStyles();
    const classes = useStyles();

    let { tags } = tableContext;
    if (props.selectedSchool) {
        const persistedTags = (localStorage.getItem(props.selectedSchool) || '').trim();
        tags = persistedTags.length > 0 ? persistedTags.split(',') : [];
    }

    const handleTagsChange = (value: SelectValue[]): void => {
        const tagsList = value || [];
        const mappedTagList = tagsList.map(selectedValue => selectedValue.value);
        tableDataAdapter.context = {
            ...tableContext,
            tags: mappedTagList,
            pagination: {
                ...tableContext.pagination,
                currentPage: 0,
            },
        };
        if (props.selectedSchool) localStorage.setItem(props.selectedSchool, mappedTagList.join(','));
    };

    const updateSearch = (value: string): void => {
        tableDataAdapter.context = {
            ...tableContext,
            filter: { name: value },
            pagination: {
                ...tableContext.pagination,
                currentPage: 0,
            },
        };
    };

    useEffect(() => {
        if (props.selectedSchool) {
            handleTagsChange(tags.map(tag => ({ value: tag, label: tag })));
        }
    }, [props.selectedSchool]);

    return (
        <div className="flex flex-1 w-full items-center justify-between">
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
            <div className="flex flex-1 items-center justify-left px-12">
                <ThemeProvider theme={mainTheme}>
                    <SearchBar
                        className={classes.searchBar}
                        value={tableDataAdapter.context.filter.name}
                        onChange={updateSearch}
                        variant="small"
                        placeholder="Search by name ..."
                        cancelOnEscape
                        disabled={props.showFilters}
                    />
                </ThemeProvider>
                <Typography className={toolbarClasses.text}>Tags: &nbsp; </Typography>
                <IscTagList
                    className={classes.tagList}
                    variant="dark"
                    onChange={handleTagsChange}
                    selectedTags={tags}
                />
            </div>

            {props.toolBarItems}
        </div>
    );
}
