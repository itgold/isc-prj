import React from 'react';
import _ from '@lodash';
import IconButton from '@material-ui/core/IconButton';
import Input from '@material-ui/core/Input';
import Paper from '@material-ui/core/Paper';
import ClearIcon from '@material-ui/icons/Clear';
import SearchIcon from '@material-ui/icons/Search';
import { grey } from '@material-ui/core/colors';
import { createStyles, StyleRules, withStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import { ClassKeyOfStyles, ClassNameMap } from '@material-ui/styles';

const EMPTY_SEARCH = '';

export type OnCancelListener = () => void;
export type OnChangeListener = (value: string) => void;
export type OnRequestSearchListener = (query: string) => void;

const styles = (): StyleRules =>
    createStyles({
        root: {
            height: 48,
            display: 'flex',
            justifyContent: 'space-between',
        },
        rootSmall: {
            height: 38,
        },
        iconButton: {
            opacity: 0.54,
            transform: 'scale(1, 1)',
            transition: 'transform 200ms cubic-bezier(0.4, 0.0, 0.2, 1)',
        },
        iconButtonHidden: {
            transform: 'scale(0, 0)',
            '& > $icon': {
                opacity: 0,
            },
        },
        iconButtonDisabled: {
            opacity: 0.38,
        },
        searchIconButton: {
            marginRight: -48,
        },
        icon: {
            opacity: 0.54,
            transition: 'opacity 200ms cubic-bezier(0.4, 0.0, 0.2, 1)',
        },
        input: {
            width: '100%',
        },
        searchContainer: {
            margin: 'auto 16px',
            width: 'calc(100% - 48px - 32px)', // 48px button + 32px margin
        },
    });

interface ISearchBarProps {
    /** Whether to clear search on escape */
    cancelOnEscape?: boolean;
    /** Override or extend the styles applied to the component. */
    classes: ClassNameMap<ClassKeyOfStyles<string>>;
    /** Custom top-level class */
    className?: string;
    /** Override the close icon. */
    closeIcon?: JSX.Element;
    /** Disables text field. */
    disabled?: boolean;
    /** Sets placeholder text for the embedded text field. */
    placeholder?: string;
    /** Override the search icon. */
    searchIcon?: JSX.Element; // React.ReactElement;
    /** Override the inline-styles of the root element. */
    style?: React.CSSProperties | null;
    /** The value of the text field. */
    value?: string;
    variant?: 'small' | 'medium';

    /** Fired when the search is cancelled. */
    onCancelSearch?: OnCancelListener;
    /** Fired when the text value changes. */
    onChange?: OnChangeListener;
    /** Fired when the search icon is clicked. */
    onRequestSearch?: OnRequestSearchListener;

    onFocus?: React.FocusEventHandler<HTMLInputElement | HTMLTextAreaElement>;
    onBlur?: React.FocusEventHandler<HTMLInputElement | HTMLTextAreaElement>;
    onKeyUp?: React.KeyboardEventHandler<HTMLTextAreaElement | HTMLInputElement>;
}

interface ISearchBarState {
    value: string;
    focus: boolean;
    active: boolean;
}

/**
 * Material design search bar
 * @see [Search patterns](https://material.io/guidelines/patterns/search.html)
 */
class SearchBar extends React.Component<ISearchBarProps, ISearchBarState> {
    // eslint-disable-next-line react/static-property-placement
    static defaultProps = {
        className: '',
        closeIcon: <ClearIcon style={{ color: grey[600] }} />,
        disabled: false,
        placeholder: 'Search',
        searchIcon: <SearchIcon style={{ color: grey[600] }} />,
        style: null,
        value: EMPTY_SEARCH,
    };

    inputRef = React.createRef<HTMLInputElement>();

    constructor(props: ISearchBarProps) {
        super(props);
        this.state = {
            focus: false,
            value: this.props.value || EMPTY_SEARCH,
            active: false,
        };
    }

    static getDerivedStateFromProps(nextProps: ISearchBarProps, prevState: ISearchBarState): ISearchBarState | null {
        if (!_.isEqual(prevState.value, nextProps.value)) {
            return {
                ...prevState,
                value: nextProps.value || EMPTY_SEARCH,
            };
        }

        return null; // no state change
    }

    handleFocus = (e: React.FocusEvent<HTMLInputElement>): void => {
        this.setState({ focus: true });
        if (this.props.onFocus) {
            this.props.onFocus(e);
        }
    };

    handleBlur = (e: React.FocusEvent<HTMLInputElement>): void => {
        this.setState({ focus: false });
        if (this.state.value.trim().length === 0) {
            this.setState({ value: EMPTY_SEARCH });
        }
        if (this.props.onBlur) {
            this.props.onBlur(e);
        }
    };

    handleInput = (e: React.ChangeEvent<HTMLInputElement>): void => {
        this.setState({ value: e.target.value });
        if (this.props.onChange) {
            this.props.onChange(e.target.value);
        }
    };

    handleCancel = (): void => {
        this.setState({ active: false, value: EMPTY_SEARCH });
        if (this.props.onCancelSearch) {
            this.props.onCancelSearch();
        }

        if (this.props.onChange) {
            this.props.onChange('');
        }
    };

    handleKeyUp = (e: React.KeyboardEvent<HTMLInputElement>): void => {
        if (e.charCode === 13 || e.key === 'Enter') {
            this.handleRequestSearch();
        } else if (this.props.cancelOnEscape && (e.charCode === 27 || e.key === 'Escape')) {
            this.handleCancel();
        }
        if (this.props.onKeyUp) {
            this.props.onKeyUp(e);
        }
    };

    handleRequestSearch = (): void => {
        if (this.props.onRequestSearch) {
            this.props.onRequestSearch(this.state.value);
        }
    };

    /**
     * @public
     * Focus the input component.
     */
    focus = (): void => {
        if (this.inputRef.current) this.inputRef.current.focus();
    };

    /**
     * @public
     * Blur the input component.
     */
    blur = (): void => {
        if (this.inputRef.current) this.inputRef.current.blur();
    };

    render(): JSX.Element {
        const { value, focus, active } = this.state;
        const {
            cancelOnEscape,
            className,
            classes,
            closeIcon,
            disabled,
            onCancelSearch,
            onRequestSearch,
            searchIcon,
            style,
            ...inputProps
        } = this.props;

        return (
            <Paper
                className={clsx(classes.root, className, this.props.variant === 'small' && classes.rootSmall)}
                style={style || undefined}
            >
                <input type="hidden" value={`${active}`} />
                <input type="hidden" value={`${focus}`} />
                <div className={classes.searchContainer}>
                    <Input
                        {...inputProps}
                        inputRef={this.inputRef}
                        onBlur={this.handleBlur}
                        value={value}
                        onChange={this.handleInput}
                        onKeyUp={this.handleKeyUp}
                        onFocus={this.handleFocus}
                        fullWidth
                        className={classes.input}
                        disableUnderline
                        disabled={disabled}
                    />
                </div>
                <IconButton
                    onClick={this.handleRequestSearch}
                    classes={{
                        root: clsx(classes.iconButton, classes.searchIconButton, {
                            [classes.iconButtonHidden]: value !== EMPTY_SEARCH,
                        }),
                        disabled: classes.iconButtonDisabled,
                    }}
                    disabled={disabled}
                >
                    {searchIcon &&
                        React.cloneElement(searchIcon, {
                            classes: { root: classes.icon },
                        })}
                </IconButton>
                <IconButton
                    onClick={this.handleCancel}
                    classes={{
                        root: clsx(classes.iconButton, {
                            [classes.iconButtonHidden]: value === EMPTY_SEARCH,
                        }),
                        disabled: classes.iconButtonDisabled,
                    }}
                    disabled={disabled}
                >
                    {closeIcon &&
                        React.cloneElement(closeIcon, {
                            classes: { root: classes.icon },
                        })}
                </IconButton>
            </Paper>
        );
    }
}

export default withStyles(styles)(SearchBar);
