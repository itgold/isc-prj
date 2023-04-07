import React, { createRef } from 'react';
import clsx from 'clsx';
import { Theme, createStyles, withStyles, StyleRules } from '@material-ui/core/styles';
import { grey } from '@material-ui/core/colors';
import { Dialog, DialogTitle, List, ListItem, ListItemText } from '@material-ui/core';
import { ClassNameMap } from '@material-ui/styles';
import SearchBar from '../../core/search.bar';
import { IGeoData } from './providers/search';
import SearchServiceHolder from './search.provider';

const styles = (theme: Theme): StyleRules =>
    createStyles({
        search: {
            padding: 10,
            width: 500,
            backgroundColor: grey[200],
        },
        searchHeader: {
            backgroundColor: theme.palette.primary.dark,
            color: theme.palette.primary.contrastText,
        },
        searchResults: {
            marginTop: '2px',
            maxHeight: '200px',
            overflow: 'auto',
        },
        searchResultsHidden: {
            visibility: 'hidden',
        },
        searchResultItem: {
            '&:hover': {
                backgroundColor: theme.palette.primary.main,
                color: theme.palette.primary.contrastText,
            },
        },
    });

export interface ISearchDialogProps {
    classes: Partial<ClassNameMap<string>>;
    open: boolean;
    selectedValue?: string;
    onClose: (value: IGeoData | null) => void;
}

interface ISearchDialogState {
    query: string;
    options: IGeoData[];
}

class GeoSearchDialog extends React.Component<ISearchDialogProps, ISearchDialogState> {
    searchService = new SearchServiceHolder();

    elementRef = createRef<typeof Dialog>();

    constructor(props: ISearchDialogProps) {
        super(props);

        this.state = {
            query: props.selectedValue || '',
            options: [],
        };
    }

    handleListItemClick(event: React.MouseEvent<HTMLDivElement, MouseEvent>, index: number): void {
        const item = this.state.options[index];
        this.props.onClose(item);
    }

    handleClose(): void {
        this.props.onClose(null);
    }

    cancelSearch(): void {
        this.setState(prevState => ({
            ...prevState,
            options: [],
        }));
    }

    search(query: string, doNotWait = false): void {
        this.setState(
            prevState => ({
                ...prevState,
                query,
            }),
            () => {
                this.updateSearch(query, doNotWait);
            }
        );
    }

    updateSearch(query: string, doNotWait = false): void {
        // Important: this function called async, so we must check if the component
        // is alive before updating state

        const self = this;
        this.searchService.query(
            query,
            (): void => {
                // On start
                // console.log('Search started ...');
            },
            (results: IGeoData[]): void => {
                // on complete
                // console.log('Search completed.', results);
                if (self.elementRef.current) {
                    self.setState(prevState => ({
                        ...prevState,
                        options: results,
                    }));
                }
            },
            doNotWait
        );
    }

    render(): JSX.Element {
        const { classes } = this.props;
        return (
            <Dialog
                ref={this.elementRef}
                onClose={() => this.handleClose()}
                aria-labelledby="geo-search-dialog-title"
                open={this.props.open}
            >
                <DialogTitle className={classes.searchHeader} id="geo-search-dialog-title">
                    Search location
                </DialogTitle>
                <div className={classes.search}>
                    <SearchBar
                        value={this.state.query}
                        onChange={newValue => this.search(newValue)}
                        onRequestSearch={() => this.search(this.state.query, true)}
                        onCancelSearch={() => this.cancelSearch()}
                    />
                    <List
                        dense
                        className={clsx(classes.searchResults, {
                            [classes.searchResultsHidden as string]: !this.state.options.length,
                        })}
                    >
                        {this.state.options.map((option, index) => (
                            <ListItem
                                button
                                key={option.name + index}
                                onClick={event => this.handleListItemClick(event, 0)}
                                className={classes.searchResultItem}
                            >
                                <ListItemText primary={option.name} />
                            </ListItem>
                        ))}
                    </List>
                </div>
            </Dialog>
        );
    }
}

export default withStyles(styles)(GeoSearchDialog);
