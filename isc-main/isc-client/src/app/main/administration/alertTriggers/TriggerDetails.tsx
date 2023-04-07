import React, { useEffect, useRef, useState } from 'react';
import { useDispatch } from 'react-redux';
import _ from '@lodash';
import Formsy from 'formsy-react';
import { CheckboxFormsy, LabelFormsy, SelectFormsy, TextFieldFormsy } from '@fuse/core/formsy';
import {
    Button,
    Card,
    CardActions,
    CardContent,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    FormControl,
    LinearProgress,
    makeStyles,
    MenuItem,
} from '@material-ui/core';
import * as FuseActions from 'app/store/actions/fuse';

import AlertTrigger, { AlertTriggerMatcherType, AlertTriggerMatcher } from 'app/domain/alertTrigger';
import FullSizeLayout from '@common/components/layout/fullsize.layout';
import clsx from 'clsx';
import ErrorDialog, { IErrorDialog } from 'app/components/app/ServerErrorDialog';
import queryService from 'app/services/events.service';

const useStyles = makeStyles(theme => ({
    rootSpecial: {
        width: '100%',
        position: 'absolute',
        left: '0px',
        right: '0px',
        top: '0px',
        bottom: '0px',
    },

    importRoot: {
        backgroundColor: theme.palette.background.default,
        color: theme.palette.primary.contrastText,
    },

    panel: {
        maxWidth: '80%',
        minWidth: 400,
    },

    actions: {
        margin: 'auto',
        display: 'flex',
        outline: '0',
        position: 'relative',
        justifyContent: 'center',
    },
}));

interface RegionsListProps {
    trigger?: AlertTrigger;
    processors: string[];
    onUpdated?: (entiry: AlertTrigger) => void;
}

interface AlertTriggerDetailsState {
    valid: boolean;
    isSubmitting: boolean;
}

const AlertTriggerDetails = (props: RegionsListProps): JSX.Element => {
    const classes = useStyles();
    const dispatch = useDispatch();
    const [trigger, setTrigger] = useState<AlertTrigger | undefined>(props.trigger);
    const [state, setState] = useState<AlertTriggerDetailsState>({
        valid: false,
        isSubmitting: false,
    });

    const errorRef = useRef<IErrorDialog>(null);
    const formRef = useRef<Formsy>(null);

    useEffect(() => {
        if (!_.isEqual(trigger, props.trigger)) {
            setTrigger(props.trigger);
        }
    }, [props.trigger, trigger]);

    const enableButton = (enable: boolean): void => {
        setState({
            ...state,
            valid: enable,
        });
    };

    const doSaveInternal = (): Promise<boolean> => {
        const formData = formRef.current?.getModel() || {};

        let updatedTrigger: AlertTrigger;
        try {
            const matcherBody = JSON.stringify(JSON.parse(formData.body));
            updatedTrigger = {
                ...(trigger || {}),
                name: formData.name || '',
                processorType: formData.processorType || '',
                active: formData.active !== false,
                matchers: [
                    {
                        type: formData.type,
                        body: matcherBody,
                    },
                ],
            };
        } catch (e) {
            const parseError = (e as Error).message;
            return Promise.reject(
                new Error(
                    `There is an error in the matcher rule. Please check the matcher rule code. Error: ${parseError}`
                )
            );
        }

        const newTrigger = !updatedTrigger.id;
        const crudAction = newTrigger ? queryService.createAlertTrigger : queryService.updateAlertTrigger;
        const actionName = newTrigger ? 'Create' : 'Update';
        return new Promise<boolean>((resolve, reject) => {
            crudAction(updatedTrigger)
                .then(response => {
                    const rez = (newTrigger ? response.newAlertTrigger : response.updateAlertTrigger) as AlertTrigger;
                    props.onUpdated?.(rez);
                    resolve(newTrigger);
                })
                .catch((error: unknown) => {
                    errorRef.current?.openDialog(`Unable to ${actionName}`, error);
                    reject(error);
                });
        });
    };

    const displayMessage = (title: string, message: string): void => {
        dispatch(
            FuseActions.openDialog({
                children: (
                    <>
                        <DialogTitle id="alert-dialog-title">{title}</DialogTitle>
                        <DialogContent>
                            <DialogContentText id="alert-dialog-description">{message}</DialogContentText>
                        </DialogContent>
                        <DialogActions>
                            <Button onClick={() => dispatch(FuseActions.closeDialog())} color="primary" autoFocus>
                                OK
                            </Button>
                        </DialogActions>
                    </>
                ),
            })
        );
    };

    const doSave = (): void => {
        setState({
            ...state,
            isSubmitting: true,
        });

        doSaveInternal()
            .then((newTrigger: boolean) => {
                // displayMessage(
                //     'Success',
                //     newTrigger
                //         ? 'New Alert Trigger rule created successfully!'
                //         : 'Alert Trigger rule updated successfully!'
                // );
                console.log(
                    newTrigger
                        ? 'New Alert Trigger rule created successfully!'
                        : 'Alert Trigger rule updated successfully!'
                );
                setState({
                    ...state,
                    isSubmitting: false,
                });
            })
            .catch(error => {
                displayMessage('Something went wrong', error.message);
                setState({
                    ...state,
                    isSubmitting: false,
                });
            });
    };

    const matcher =
        (trigger?.matchers?.length || 0) > 0
            ? trigger?.matchers[0]
            : ({ type: AlertTriggerMatcherType.CUSTOM, body: '' } as AlertTriggerMatcher);
    const bodyJson = matcher?.body ? JSON.stringify(JSON.parse(matcher.body), null, 4) : '{}';

    return trigger ? (
        <>
            <FullSizeLayout>
                <div
                    className={clsx(
                        classes.importRoot,
                        'flex flex-col flex-auto flex-shrink-0 items-center justify-center p-32'
                    )}
                >
                    <div className="flex flex-col items-center justify-center w-full">
                        <Card
                            className={clsx(classes.panel, 'w-full')}
                            variant="outlined"
                            style={{ overflow: 'visible' }}
                        >
                            <CardContent className="flex flex-col items-center justify-center p-32">
                                <Formsy
                                    onValid={() => enableButton(true)}
                                    onInvalid={() => enableButton(false)}
                                    ref={formRef}
                                    className="flex flex-col justify-center w-full"
                                >
                                    <FormControl margin="dense" fullWidth>
                                        <TextFieldFormsy
                                            autoFocus
                                            type="text"
                                            name="name"
                                            label="Alert Trigger name"
                                            value={trigger?.name}
                                            variant="outlined"
                                            margin="dense"
                                            multiline
                                            required
                                            disabled={state.isSubmitting}
                                        />
                                    </FormControl>
                                    <CheckboxFormsy
                                        type="text"
                                        name="active"
                                        label="Is Active?"
                                        value={trigger?.active}
                                        variant="outlined"
                                        margin="dense"
                                        multiline
                                        rows={3}
                                    />
                                    <FormControl margin="dense" fullWidth>
                                        <TextFieldFormsy
                                            type="text"
                                            name="body"
                                            label="Alert Trigger rule body"
                                            value={bodyJson}
                                            variant="outlined"
                                            margin="dense"
                                            multiline
                                            rows={20}
                                            required
                                            // onChange={onGeoJsonChanged}
                                            disabled={state.isSubmitting}
                                        />
                                    </FormControl>
                                    <FormControl margin="dense" fullWidth>
                                        {props.processors && (
                                            <SelectFormsy
                                                name="processorType"
                                                label="Processor"
                                                value={trigger?.processorType || ''}
                                                required
                                                margin="dense"
                                                disabled={state.isSubmitting}
                                            >
                                                {props.processors.map((option, idx) => (
                                                    <MenuItem key={idx} value={option}>
                                                        {option}
                                                    </MenuItem>
                                                ))}
                                            </SelectFormsy>
                                        )}
                                    </FormControl>
                                    {/*
                                    <FormControl margin="dense" fullWidth>
                                        <SelectFormsy
                                            name="type"
                                            label="Type"
                                            value={matcher?.type}
                                            required
                                            margin="dense"
                                            disabled={state.isSubmitting}
                                        >
                                            {['DATE_TIME', 'CUSTOM'].map(option => (
                                                <MenuItem key={option} value={option}>
                                                    {option}
                                                </MenuItem>
                                            ))}
                                        </SelectFormsy>
                                    </FormControl>
                                    */}

                                    <br />
                                    {state.isSubmitting && <LinearProgress className="mt-16" />}
                                </Formsy>
                            </CardContent>
                            <CardActions className={clsx(classes.actions, 'mb-16')}>
                                <Button
                                    onClick={doSave}
                                    disabled={!state.valid || state.isSubmitting}
                                    variant="contained"
                                    color="primary"
                                >
                                    Save
                                </Button>
                            </CardActions>
                        </Card>
                    </div>
                </div>
            </FullSizeLayout>
            <ErrorDialog ref={errorRef} />
        </>
    ) : (
        <></>
    );
};

export default AlertTriggerDetails;
