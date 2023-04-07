// eslint-disable-next-line max-classes-per-file
import React, { Component, createRef, RefObject } from 'react';
import Formsy from 'formsy-react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    withStyles,
    Theme,
    createStyles,
    MenuItem,
    FormControl,
    LinearProgress,
    StyleRules,
    Typography,
} from '@material-ui/core';
import { OnCloseReason, DIALOG_REASON_CANCEL, DIALOG_REASON_SUBMIT } from '@common/components/app.dialog.props';
import { CheckboxFormsy, LabelFormsy, SelectFormsy, TextFieldFormsy } from '@fuse/core/formsy';
import AsyncFetcher, { FetcherContextConsumer } from '@common/components/core/async.fetcher';
import { ClassNameMap } from '@material-ui/styles';
import { IEntity } from 'app/domain/entity';
import ColorPickerFormsy from '@fuse/core/formsy/ColorPickerFormsy';
import {
    EMPTY_MULTISELECT_OPTION,
    EMPTY_SELECT_OPTION,
    IEntityField,
    ISelectOption,
    IUpdateEntityDialog,
} from './types';

const styles = (theme: Theme): StyleRules =>
    createStyles({
        root: {
            width: '100%',
            minWidth: '400px',
            marginTop: '0px',
            paddingTop: '0px',
            // eslint-disable-next-line @typescript-eslint/no-explicit-any
            overflowY: 'visible',
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
        some: {
            marginRight: theme.spacing(2),
        },
        colorInput: {},

        errorText: {
            color: 'red',
            fontWeight: 'bold',
            fontSize: '1.1em',
            display: 'inline-block',
            width: '100%',
            textAlign: 'center',
        },
    });

interface UpdateEntityDialogProps {
    classes: Partial<ClassNameMap<string>>;
    onClose?: (reason: OnCloseReason, data: any) => Promise<boolean>;
    entityFields: IEntityField[];
    dialogTitle?: string;
    addButtonLabel?: string;
    cancelButtonLabel?: string;
    keepDialogOpened?: boolean;
    disableActionButton?: boolean;
    showProgressBar?: boolean;
    hideCancelButton?: boolean;

    /* eslint-disable react/no-unused-prop-types */
    ref?: React.Ref<IUpdateEntityDialog>;
}

export interface SelectedValues {
    [key: string]: any;
}

interface UpdateEntityDialogState {
    opened: boolean;
    valid: boolean;
    entity?: any;
    selectedValues: SelectedValues;
    error?: string;
}

/**
 * Resolve value for text box
 *
 * @param value Curent field value
 * @param defaultValue Default value
 */
function resolveTextValue(value?: any, defaultValue?: any): ISelectOption | undefined | string {
    let selectedValue: any = '';
    if (value) {
        if (typeof value === 'object') {
            selectedValue = value.name || value.id || '';
        } else {
            selectedValue = value;
        }
    } else if (defaultValue) {
        selectedValue = defaultValue;
    }

    return selectedValue;
}

function toOption(value?: any): ISelectOption {
    if (value) {
        if (value.id && value.name) {
            return value;
        }

        return { id: value, name: value };
    }

    return EMPTY_SELECT_OPTION;
}

/**
 * Provide list of possible drop down options
 *
 * @param value Curent field value
 * @param defaultValue Default value
 * @param values List of available options
 */
function availableOptions(value?: any, defaultValue?: any, values?: any[]): ISelectOption[] {
    let options: ISelectOption[] = [];
    if (values?.length) {
        options = values.map(val => toOption(val));
    } else if (value) {
        if (Array.isArray(value)) {
            options = [...value];
        } else if (typeof value === 'object' && value !== null) {
            options.push(value);
        }
    }

    if (!options.length) {
        if (defaultValue) options.push(toOption(defaultValue));
        else options.push(EMPTY_SELECT_OPTION);
    } else if (defaultValue) {
        const hasDefault = options.find(o => o.id === defaultValue || o.id === defaultValue.id);
        if (!hasDefault) {
            options.push(toOption(defaultValue));
        }
    }

    return options;
}

/**
 * Resolve value for dropdown box
 * Note: The select box is very sensitive when you provide the value which is not available in the options list.
 *
 * @param value Curent field value
 * @param defaultValue Default value
 * @param values List of available options
 */
function resolveSelectValue(value?: any, defaultValue?: any, values?: any[]): ISelectOption | undefined | string {
    const options: ISelectOption[] = availableOptions(value, defaultValue, values);
    let selectedValue: ISelectOption = EMPTY_SELECT_OPTION;
    if (value) {
        selectedValue = toOption(value);
    } else if (defaultValue) {
        selectedValue = toOption(defaultValue);
    } else {
        selectedValue = values?.length ? values[0] : EMPTY_SELECT_OPTION;
    }

    const availableOption = options.find((option: ISelectOption) => option.id === selectedValue.id);
    return availableOption || EMPTY_SELECT_OPTION;
}

/**
 * Resolve value for dropdown box
 * Note: The select box is very sensitive when you provide the value which is not available in the options list.
 *
 * @param value Curent field value
 * @param defaultValue Default value
 * @param values List of available options
 */
function resolveMultiSelectValue(value?: any, defaultValue?: any, values?: any[]): ISelectOption[] {
    const options: ISelectOption[] = availableOptions(value, defaultValue, values);
    let selectedValue: ISelectOption[] = EMPTY_MULTISELECT_OPTION;
    if (value) {
        if (Array.isArray(value)) {
            selectedValue = value.map(v => toOption(v));
        } else {
            selectedValue = [toOption(value)];
        }
    } else if (defaultValue) {
        selectedValue = [toOption(defaultValue)];
    } else {
        selectedValue = values?.length ? [values[0]] : EMPTY_MULTISELECT_OPTION;
    }

    const availableOption = options.filter((option: ISelectOption) => {
        const found = selectedValue.find((selectedOption: ISelectOption) => selectedOption.id === option.id);
        return !!found;
    });
    return availableOption || EMPTY_MULTISELECT_OPTION;
}

// This is workaround for ESLint/Prettier issue with different linting and re-formattion on different computers
// eslint-disable-next-line react/prefer-stateless-function
class UpdateEntityDialog
    extends Component<UpdateEntityDialogProps, UpdateEntityDialogState>
    implements IUpdateEntityDialog {
    formRef: RefObject<Formsy>;

    constructor(props: UpdateEntityDialogProps) {
        super(props);

        this.state = {
            opened: false,
            valid: false,
            entity: undefined,
            selectedValues: this.fieldsValues(),
        };

        this.formRef = createRef<Formsy>();
    }

    getCurrentValue(name: string, type?: string, multiValue?: boolean): string | string[] {
        if (
            !type ||
            type === 'string' ||
            type === 'text' ||
            type === 'boolean' ||
            type === 'label' ||
            type === 'color'
        ) {
            const obj = this.state.selectedValues[name];
            if (obj instanceof Object) {
                return obj.name;
            }
            return obj;
        }
        if (type === 'enum' || 'asyncEnum') {
            if (multiValue) {
                return this.state.selectedValues[name]
                    ?.map((option: ISelectOption) => option?.id)
                    .filter((id: string | undefined) => !!id);
            }
            return this.state.selectedValues[name].id;
        }

        return this.state.selectedValues[name];
    }

    disableButton = (): void => {
        this.setState(prevState => ({
            ...prevState,
            valid: false,
        }));
    };

    enableButton = (): void => {
        this.setState(prevState => ({
            ...prevState,
            valid: true,
        }));
    };

    handleClose = (reason: OnCloseReason, keepDialogOpened?: boolean): void => {
        if (this.props.onClose) {
            const formData = this.formRef?.current ? this.formRef.current.getModel() : null;
            const entity = { ...(this.state.entity || {}), ...formData };
            this.props.onClose(reason, entity).then(closeFlag => {
                if (closeFlag) {
                    this.setState(prevState => ({
                        ...prevState,
                        opened: keepDialogOpened || false,
                    }));
                }
            });
        }
    };

    openDialog = (entity?: any, error?: string): void => {
        this.setState(prevState => ({
            ...prevState,
            opened: true,
            entity,
            selectedValues: this.fieldsValues(entity),
            error,
        }));
    };

    resolveDefaultValue = (entityFields: IEntityField, entity?: IEntity): any => {
        return entityFields.defaultValue && typeof entityFields.defaultValue === 'function'
            ? entityFields.defaultValue(entity)
            : entityFields.defaultValue;
    };

    fieldsValues = (entity?: any): SelectedValues => {
        const values: SelectedValues = {};
        this.props.entityFields.forEach(entityField => {
            if (entityField.type === 'enum' || entityField.type === 'asyncEnum') {
                values[entityField.name] = entityField.showAsTree
                    ? entity?.[`${entityField.name}`]
                    : entityField.multi
                    ? resolveMultiSelectValue(
                          entity?.[`${entityField.name}`],
                          this.resolveDefaultValue(entityField, entity),
                          entityField.valueOptions
                      )
                    : resolveSelectValue(
                          entity?.[`${entityField.name}`],
                          this.resolveDefaultValue(entityField, entity),
                          entityField.valueOptions
                      );
            } else {
                values[entityField.name] = resolveTextValue(
                    entity?.[`${entityField.name}`],
                    this.resolveDefaultValue(entityField, entity)
                );
                if (entityField.alias?.length) {
                    values[entityField.alias] = values[entityField.name];
                }
            }
        });

        return values;
    };

    onSelectChange = (
        event: React.ChangeEvent<{ name?: string; value: unknown }>,
        entityField: IEntityField,
        values: any[] | undefined
    ): void => {
        const entity = this.currentEntity();
        const allOptions = availableOptions(
            this.state.entity?.[`${entityField.name}`],
            this.resolveDefaultValue(entityField, entity),
            values
        );
        // find selected option within available options
        let selectedValue: any | any[];
        if (entityField.multi) {
            selectedValue = event.target.value;
            selectedValue = allOptions.filter((option: ISelectOption) => {
                const found = selectedValue.find((selectedOption: any) => selectedOption === option.id);
                return !!found;
            });
        } else {
            selectedValue = allOptions.find(option => option.id === event.target.value);
        }

        const selectedValues = { ...this.state.selectedValues };
        selectedValues[entityField.name] = selectedValue;

        // Check if we have dependent dropdowns and reset them
        this.props.entityFields
            .filter(
                field => field?.dependsOn === entityField.name && (field.type === 'enum' || field.type === 'asyncEnum')
            )
            .forEach(field => {
                selectedValues[field.name] = field.multi
                    ? EMPTY_MULTISELECT_OPTION
                    : this.resolveDefaultValue(field, entity) || EMPTY_SELECT_OPTION;
            });

        this.setState(prevState => ({
            ...prevState,
            selectedValues,
        }));
    };

    /**
     * Persist the selected nodes from the dropdown tree and persist its values in the current state/entityField
     * @param value
     * @param selectedNodes
     * @param entityField
     */
    onSelectTreeChange = (
        event: React.ChangeEvent<{ name?: string; value: unknown }>,
        entityField: IEntityField
    ): void => {
        const selectedNodes = event.target.value
            ? Array.isArray(event.target.value)
                ? (event.target.value as string[])
                : []
            : [];
        const selectedValues = { ...this.state.selectedValues };
        selectedValues[entityField.name] = selectedNodes.map((e: string) => ({
            id: e,
            name: e,
        }));

        this.setState(prevState => {
            if (prevState.entity?.[entityField.name]) {
                prevState.entity[entityField.name] = selectedValues[entityField.name];
            }

            return {
                ...prevState,
                selectedValues,
            };
        });
    };

    currentEntity(): IEntity {
        const formData = this.formRef?.current ? this.formRef.current.getModel() : null;
        const entity = { ...(this.state?.entity || {}), ...(formData || {}) };
        return entity;
    }

    render(): JSX.Element {
        const { classes, entityFields } = this.props;
        return (
            <>
                <Dialog
                    open={this.state.opened}
                    onClose={(reason: OnCloseReason) => this.handleClose(reason)}
                    aria-labelledby="form-dialog-title"
                    className={classes.dialog}
                >
                    <DialogTitle className={classes.dialogTitle} id="form-dialog-title">
                        {this.props.dialogTitle ? this.props.dialogTitle : this.state.entity?.id ? 'Update' : 'Add'}
                    </DialogTitle>
                    <DialogContent className={classes.root}>
                        {this.props.showProgressBar && <LinearProgress />}
                        {this.state.error && (
                            <Typography variant="caption" color="inherit" className={classes.errorText}>
                                {this.state.error}
                            </Typography>
                        )}

                        <div className="w-full">
                            <Formsy
                                onValid={() => this.enableButton()}
                                onInvalid={() => this.disableButton()}
                                ref={this.formRef}
                                className="flex flex-col justify-center w-full"
                            >
                                <FormControl margin="dense" fullWidth>
                                    {entityFields.map(
                                        entityField =>
                                            ((!entityField.type || entityField.type === 'string') &&
                                                (!entityField.hidden || !entityField.hidden(this.currentEntity())) && (
                                                    <TextFieldFormsy
                                                        shrink
                                                        key={entityField.name}
                                                        type="text"
                                                        name={entityField.name}
                                                        label={entityField.inputLabel || entityField.name}
                                                        value={this.getCurrentValue(
                                                            entityField.name,
                                                            entityField.type,
                                                            entityField.multi
                                                        )}
                                                        validations={
                                                            entityField.validations ? entityField.validations : {}
                                                        }
                                                        validationErrors={
                                                            entityField.validationErrors
                                                                ? entityField.validationErrors
                                                                : {}
                                                        }
                                                        InputProps={entityField.inputProps}
                                                        variant="outlined"
                                                        margin="dense"
                                                        required={!entityField.optional}
                                                        onChange={(event: any) => {
                                                            if (entityField.onChange)
                                                                entityField.onChange(event, this.formRef);
                                                        }}
                                                    />
                                                )) ||
                                            (entityField.type === 'label' &&
                                                (!entityField.hidden || !entityField.hidden(this.currentEntity())) && (
                                                    <TextFieldFormsy
                                                        disabled
                                                        shrink
                                                        key={entityField.name}
                                                        type="text"
                                                        name={entityField.name}
                                                        label={entityField.inputLabel || entityField.name}
                                                        value={this.getCurrentValue(
                                                            entityField.name,
                                                            entityField.type,
                                                            entityField.multi
                                                        )}
                                                        validations={
                                                            entityField.validations ? entityField.validations : {}
                                                        }
                                                        validationErrors={
                                                            entityField.validationErrors
                                                                ? entityField.validationErrors
                                                                : {}
                                                        }
                                                        InputProps={entityField.inputProps}
                                                        variant="outlined"
                                                        margin="dense"
                                                        required={!entityField.optional}
                                                        onChange={(event: any) => {
                                                            if (entityField.onChange)
                                                                entityField.onChange(event, this.formRef);
                                                        }}
                                                    />
                                                )) ||
                                            (entityField.type === 'text' &&
                                                (!entityField.hidden || !entityField.hidden(this.currentEntity())) && (
                                                    <TextFieldFormsy
                                                        key={entityField.name}
                                                        type="text"
                                                        name={entityField.name}
                                                        label={entityField.inputLabel || entityField.name}
                                                        value={this.getCurrentValue(
                                                            entityField.name,
                                                            entityField.type,
                                                            entityField.multi
                                                        )}
                                                        InputProps={entityField.inputProps}
                                                        variant="outlined"
                                                        margin="dense"
                                                        multiline
                                                        rows={3}
                                                        required={!entityField.optional}
                                                        onChange={(event: any) => {
                                                            if (entityField.onChange)
                                                                entityField.onChange(event, this.formRef);
                                                        }}
                                                    />
                                                )) ||
                                            (entityField.type === 'boolean' &&
                                                (!entityField.hidden || !entityField.hidden(this.currentEntity())) && (
                                                    <CheckboxFormsy
                                                        key={entityField.name}
                                                        type="text"
                                                        name={entityField.name}
                                                        label={entityField.inputLabel || entityField.name}
                                                        value={this.getCurrentValue(
                                                            entityField.name,
                                                            entityField.type,
                                                            entityField.multi
                                                        )}
                                                        InputProps={entityField.inputProps}
                                                        variant="outlined"
                                                        margin="dense"
                                                        multiline
                                                        rows={3}
                                                        required={!entityField.optional}
                                                        onChange={(event: any) => {
                                                            if (entityField.onChange)
                                                                entityField.onChange(event, this.formRef);
                                                        }}
                                                    />
                                                )) ||
                                            (entityField.type === 'color' &&
                                                (!entityField.hidden || !entityField.hidden(this.currentEntity())) && (
                                                    <ColorPickerFormsy
                                                        key={entityField.name}
                                                        deferred
                                                        label={entityField.inputLabel || entityField.name}
                                                        className={classes.colorInput}
                                                        name={entityField.name}
                                                        defaultValue="#0d47a1b0"
                                                        value={this.getCurrentValue(
                                                            entityField.name,
                                                            entityField.type,
                                                            entityField.multi
                                                        )}
                                                        onChange={(event: any) => {
                                                            if (entityField.onChange)
                                                                entityField.onChange(event, this.formRef);
                                                        }}
                                                    />
                                                )) ||
                                            (entityField.type === 'enum' &&
                                                (!entityField.hidden || !entityField.hidden(this.currentEntity())) && (
                                                    <SelectFormsy
                                                        key={entityField.name}
                                                        name={entityField.name}
                                                        label={entityField.inputLabel || entityField.name}
                                                        value={this.getCurrentValue(
                                                            entityField.name,
                                                            entityField.type,
                                                            entityField.multi
                                                        )}
                                                        required={!entityField.optional}
                                                        margin="dense"
                                                        multiple={entityField.multi}
                                                        onChange={(
                                                            event: React.ChangeEvent<{
                                                                name?: string;
                                                                value: unknown;
                                                            }>
                                                        ) =>
                                                            this.onSelectChange(
                                                                event,
                                                                entityField,
                                                                entityField.valueOptions
                                                            )
                                                        }
                                                    >
                                                        {availableOptions(
                                                            this.state.entity?.[`${entityField.name}`],
                                                            this.resolveDefaultValue(entityField, this.currentEntity()),
                                                            entityField.valueOptions
                                                        ).map(option => (
                                                            <MenuItem key={option.id} value={option.id}>
                                                                {option.name}
                                                            </MenuItem>
                                                        ))}
                                                    </SelectFormsy>
                                                )) ||
                                            (entityField.type === 'asyncEnum' &&
                                                (!entityField.hidden || !entityField.hidden(this.currentEntity())) && (
                                                    <AsyncFetcher
                                                        queryAction={entityField.valueOptionsAsync?.queryAction}
                                                        queryParams={
                                                            entityField.dependsOn
                                                                ? this.state.selectedValues[entityField.dependsOn]
                                                                : undefined
                                                        }
                                                        context={this.currentEntity()}
                                                        dataSelector={entityField.valueOptionsAsync?.dataSelector}
                                                        key={entityField.name}
                                                    >
                                                        <FetcherContextConsumer>
                                                            {(values: any[]) =>
                                                                values && (
                                                                    <SelectFormsy
                                                                        key={entityField.name}
                                                                        name={entityField.name}
                                                                        label={
                                                                            entityField.inputLabel || entityField.name
                                                                        }
                                                                        value={this.getCurrentValue(
                                                                            entityField.name,
                                                                            entityField.type,
                                                                            entityField.multi
                                                                        )}
                                                                        required={!entityField.optional}
                                                                        margin="dense"
                                                                        multiple={entityField.multi}
                                                                        tree={entityField.showAsTree}
                                                                        showCheckboxes={entityField.showAsTree}
                                                                        treeHandleExpand
                                                                        treeOptions={values}
                                                                        onChange={(event: any) =>
                                                                            entityField.showAsTree
                                                                                ? this.onSelectTreeChange(
                                                                                      event,
                                                                                      entityField
                                                                                  )
                                                                                : this.onSelectChange(
                                                                                      event,
                                                                                      entityField,
                                                                                      values
                                                                                  )
                                                                        }
                                                                    >
                                                                        {availableOptions(
                                                                            this.state.entity?.[`${entityField.name}`],
                                                                            this.resolveDefaultValue(
                                                                                entityField,
                                                                                this.currentEntity()
                                                                            ),
                                                                            values
                                                                        ).map((option, index) => (
                                                                            <MenuItem key={index} value={option.id}>
                                                                                {option.name}
                                                                            </MenuItem>
                                                                        ))}
                                                                    </SelectFormsy>
                                                                )
                                                            }
                                                        </FetcherContextConsumer>
                                                    </AsyncFetcher>
                                                )) ||
                                            (entityField.type === 'group' &&
                                                (!entityField.hidden || !entityField.hidden(this.currentEntity())) && (
                                                    <LabelFormsy
                                                        key={entityField.name}
                                                        className="my-16 mb-0"
                                                        name={entityField.name}
                                                        label={entityField.inputLabel || entityField.name}
                                                    />
                                                ))
                                    )}
                                </FormControl>
                            </Formsy>
                        </div>
                    </DialogContent>
                    <DialogActions>
                        {!this.props.hideCancelButton && (
                            <Button onClick={() => this.handleClose(DIALOG_REASON_CANCEL)} color="primary">
                                {this.props.cancelButtonLabel || 'Cancel'}
                            </Button>
                        )}
                        <Button
                            disabled={
                                this.props.disableActionButton !== undefined && this.props.disableActionButton !== null
                                    ? this.props.disableActionButton
                                    : !this.state.valid
                            }
                            onClick={() =>
                                this.handleClose(
                                    DIALOG_REASON_SUBMIT,
                                    this.props.keepDialogOpened !== undefined && this.props.keepDialogOpened !== null
                                        ? this.props.keepDialogOpened
                                        : false
                                )
                            }
                            color="primary"
                        >
                            {this.props.addButtonLabel || (this.state.entity?.id ? 'Update' : 'Add')}
                        </Button>
                    </DialogActions>
                </Dialog>
            </>
        );
    }
}

export default withStyles(styles)(UpdateEntityDialog);
