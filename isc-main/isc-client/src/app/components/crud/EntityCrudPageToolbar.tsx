import React from 'react';
import { Button, fade, makeStyles, Theme, ThemeProvider, Tooltip } from '@material-ui/core';
import SearchBar from '@common/components/core/search.bar';
import AddIcon from '@material-ui/icons/Add';
import { useSelector } from 'react-redux';
import { RootState } from 'app/store/appState';
import { createStyles, DefaultTheme } from '@material-ui/styles';
import ToggleButton from '@material-ui/lab/ToggleButton';
import { FaFilter } from 'react-icons/fa';
import chroma from 'chroma-js';

interface EntityCrudPageToolbarProps {
    textFilter?: string;
    showFilters?: boolean;

    onSearch?: (text: string) => void;
    onAdd?: () => void;
    onShowFilters?: (show: boolean) => void;
    header?: React.ReactNode;
}

const useStyles = makeStyles((theme: Theme) => {
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

        searchBar: {
            width: '300px',
            marginRight: 20,
        },
    });
});

export default function EntityCrudPageToolbar(props: EntityCrudPageToolbarProps): JSX.Element {
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
            <div className="flex items-center">
                <Tooltip title="Show Filters" aria-label="show-filters">
                    <ToggleButton
                        size="small"
                        value="check"
                        selected={props.showFilters}
                        onChange={handleShowFilter}
                        classes={{
                            root: classes.root,
                            sizeSmall: classes.small,
                            selected: classes.selected,
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
                        value={props.textFilter}
                        onChange={updateSearch}
                        variant="small"
                        placeholder="Search by name ..."
                        cancelOnEscape
                        disabled={props.showFilters}
                    />
                </ThemeProvider>
                {props.header}
            </div>

            {props.onAdd && (
                <Button variant="contained" color="secondary" startIcon={<AddIcon />} onClick={handleAdd}>
                    Add
                </Button>
            )}
        </div>
    );
}
