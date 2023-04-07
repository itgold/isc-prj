import React from 'react';
import {
    debounce,
    TableRow,
    TableCell,
    Input,
    makeStyles,
    createStyles,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    InputAdornment,
    IconButton,
    Checkbox,
} from '@material-ui/core';
import _ from '@lodash';
import { DateTimePicker } from '@material-ui/pickers';
import { DateType } from '@date-io/type';
import { dateTimeRangeToString, endDateTimeFromString, startDateTimeFromString } from 'app/utils/filter.utils';
import MomentUtils from '@date-io/moment';
import { FiDelete } from 'react-icons/fi';
import { ITableHeadColumn, FilterConfig } from './IscTableHead';
import { Filter } from './IscTableContext';

const momentUtils: MomentUtils = new MomentUtils();

const useStyles = makeStyles(() =>
    createStyles({
        root: {
            width: '100%',
            minWidth: '300px',
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
        lable: {
            fontWeight: 'bold',
        },
    })
);

interface DateTimeRangePickerDialogProps {
    onClose?: () => void;
    onApply?: (startDate: Date | null, endDate: Date | null) => void;
    startDate: Date | null;
    endDate: Date | null;
}

const DateTimeRangePickerDialog = (props: DateTimeRangePickerDialogProps): JSX.Element => {
    const [value1, setValue1] = React.useState<Date | null>(props.startDate || new Date());
    const [value2, setValue2] = React.useState<Date | null>(props.endDate || new Date());

    const onClose = (): void => {
        if (props.onClose) {
            props.onClose();
        }
    };
    const onApply = (): void => {
        if (props.onApply) {
            props.onApply(value1, value2);
        }
    };

    const handleChange1 = (newValue: DateType | null): void => {
        setValue1(newValue?.toDate() || null);
    };
    const handleChange2 = (newValue: DateType | null): void => {
        setValue2(newValue?.toDate() || null);
    };

    // https://material-ui-pickers.dev/demo/datetime-picker
    const classes = useStyles();
    return (
        <Dialog
            open
            onClose={onClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
            className={classes.dialog}
        >
            <DialogTitle className={classes.dialogTitle} id="alert-dialog-title">
                Date/Time Range
            </DialogTitle>
            <DialogContent className={classes.root}>
                <DateTimePicker label="Start" value={value1} onChange={handleChange1} format="YYYY-MM-DD LT" />
                &nbsp; &nbsp;
                <DateTimePicker label="End" value={value2} onChange={handleChange2} format="YYYY-MM-DD LT" />
            </DialogContent>
            <DialogActions>
                <Button color="primary" onClick={onApply}>
                    Apply
                </Button>
                <Button color="primary" onClick={onClose}>
                    Cancel
                </Button>
            </DialogActions>
        </Dialog>
    );
};

interface IscTableHeadFilterProps {
    // classes: Partial<ClassNameMap<string>>;
    columns: Array<ITableHeadColumn>;
    visible?: boolean;

    filter?: Filter;
    onFilter?: (filter: Filter) => void;
}

interface IscTableHeadFilterState {
    propsFilter: Filter;
    currentFilter: Filter;
    showFilterDialog: boolean;
}

class IscTableHeadFilter extends React.Component<IscTableHeadFilterProps, IscTableHeadFilterState> {
    onFilter: (filter: Filter) => void;

    // This is workaround for the React gdsfp which is broken by design.
    // you do not really know IF nextProps are CHANGED or NOT. It will be called anyway after state changed.
    static getDerivedStateFromProps(
        nextProps: IscTableHeadFilterProps,
        prevState: IscTableHeadFilterState
    ): IscTableHeadFilterState | null {
        if (!_.isEqual(prevState.propsFilter, nextProps.filter)) {
            return {
                ...prevState,
                propsFilter: { ...nextProps.filter },
                currentFilter: { ...nextProps.filter },
            };
        }

        return null; // no state change
    }

    constructor(props: IscTableHeadFilterProps) {
        super(props);

        const propsFilter = props.filter || {};
        this.state = {
            propsFilter: { ...propsFilter },
            currentFilter: { ...propsFilter },
            showFilterDialog: false,
        };

        this.onFilter = debounce((filter: Filter) => {
            if (this.props.onFilter) {
                this.props.onFilter(filter);
            }
        }, 500);
    }

    onChange(columnId: string, filterValue: string | null): void {
        this.setState(
            state => {
                let { currentFilter } = state;
                if (!currentFilter) currentFilter = {};

                const newState = { ...state, currentFilter: { ...currentFilter } };

                const value = filterValue;
                if (value?.length) newState.currentFilter[columnId] = value;
                else delete newState.currentFilter[columnId];

                return newState;
            },
            () => {
                this.onFilter(this.state.currentFilter);
            }
        );
    }

    onCloseFilterDialog = (): void => {
        this.setState(prevState => ({
            ...prevState,
            showFilterDialog: false,
        }));
    };

    onApplyFilter = (startDate: Date | null, endDate: Date | null, column: ITableHeadColumn): void => {
        this.onCloseFilterDialog();

        const filterValue = dateTimeRangeToString(startDate, endDate);
        this.onChange(column.id, filterValue);
    };

    dateFilterStart = (column: ITableHeadColumn): Date | null => {
        return startDateTimeFromString(this.state.currentFilter[column.id]);
    };

    dateFilterEnd = (column: ITableHeadColumn): Date | null => {
        return endDateTimeFromString(this.state.currentFilter[column.id]);
    };

    userFriendlyDateRange = (column: ITableHeadColumn): string => {
        let value = this.state?.currentFilter?.[column.id];
        if (value) {
            const startDate = startDateTimeFromString(value);
            const endDate = endDateTimeFromString(value);
            value = `${momentUtils.format(momentUtils.date(startDate), 'YYYY-MM-DD LT')} .. ${momentUtils.format(
                momentUtils.date(endDate),
                'YYYY-MM-DD LT'
            )}`;
        }

        return value || '';
    };

    clearFilter = (e: React.MouseEvent, column: ITableHeadColumn): void => {
        e.stopPropagation();
        this.onChange(column.id, null);
    };

    render(): JSX.Element {
        if (this.props.visible) {
            return (
                <TableRow>
                    {this.props.columns.map(column => {
                        const filterType = column.filter
                            ? (column.filter as any).filterType
                                ? (column.filter as FilterConfig).filterType
                                : column.filter
                            : undefined;
                        return (
                            <TableCell padding="default" key={column.id} align={column.align} width={column.width}>
                                {column.type === 'dateTimeRange' && (
                                    <>
                                        <Input
                                            style={{ display: 'none' }}
                                            fullWidth
                                            readOnly={!column.filter}
                                            disabled={!column.filter}
                                            onChange={(
                                                event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>
                                            ) => this.onChange(column.id, event.target.value)}
                                            inputProps={{ 'aria-label': 'description' }}
                                            value={this.state?.currentFilter?.[column.id] || ''}
                                            onClick={() =>
                                                this.setState(prevState => ({
                                                    ...prevState,
                                                    showFilterDialog: true,
                                                }))
                                            }
                                        />

                                        <Input
                                            fullWidth
                                            readOnly
                                            disabled={!column.filter}
                                            onChange={(
                                                event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>
                                            ) => this.onChange(column.id, event.target.value)}
                                            inputProps={{ 'aria-label': 'description' }}
                                            value={this.userFriendlyDateRange(column)}
                                            onClick={() =>
                                                this.setState(prevState => ({
                                                    ...prevState,
                                                    showFilterDialog: true,
                                                }))
                                            }
                                            style={{ fontSize: '0.7em' }}
                                            title={this.userFriendlyDateRange(column)}
                                            endAdornment={
                                                <InputAdornment position="end">
                                                    <IconButton
                                                        size="small"
                                                        style={{ marginRight: '-8px' }}
                                                        onClick={event => this.clearFilter(event, column)}
                                                    >
                                                        <FiDelete />
                                                    </IconButton>
                                                </InputAdornment>
                                            }
                                        />

                                        {this.state.showFilterDialog && (
                                            <DateTimeRangePickerDialog
                                                startDate={this.dateFilterStart(column)}
                                                endDate={this.dateFilterEnd(column)}
                                                onClose={this.onCloseFilterDialog}
                                                onApply={(startDate, endDate) =>
                                                    this.onApplyFilter(startDate, endDate, column)
                                                }
                                            />
                                        )}
                                    </>
                                )}
                                {((!column.type && !filterType) ||
                                    column.type === 'string' ||
                                    filterType === 'string') && (
                                    <Input
                                        fullWidth
                                        readOnly={!column.filter}
                                        disabled={!column.filter}
                                        onChange={(event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) =>
                                            this.onChange(column.id, event.target.value)
                                        }
                                        inputProps={{ 'aria-label': 'description' }}
                                        value={this.state?.currentFilter?.[column.id] || ''}
                                    />
                                )}
                                {!column.type && filterType && filterType === 'boolean' && (
                                    <Checkbox
                                        readOnly={!column.filter}
                                        disabled={!column.filter}
                                        style={{
                                            transform: 'scale(1)',
                                            padding: 2,
                                        }}
                                        size="small"
                                        value={this.state?.currentFilter?.[column.id] || ''}
                                        onChange={(event: React.ChangeEvent<HTMLInputElement>, checked: boolean) =>
                                            this.onChange(column.id, `${checked}`)
                                        }
                                    />
                                )}
                            </TableCell>
                        );
                    })}
                </TableRow>
            );
        }

        return <></>;
    }
}

export default IscTableHeadFilter;
