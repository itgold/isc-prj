import React, { forwardRef, LegacyRef, useImperativeHandle, useRef, useState } from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import eventsService from 'app/services/events.service';
import DialogTitle from '@material-ui/core/DialogTitle';
import { Alert, AlertStatus } from 'app/domain/deviceEvent';
import { DIALOG_REASON_SUBMIT, OnCloseReason } from '@common/components/app.dialog.props';
import Formsy from 'formsy-react';
import { FormControl } from '@material-ui/core';
import { TextFieldFormsy } from '@fuse/core/formsy';

export interface IAlertDialogDialog {
    openDialog(alert: Alert): void;
}

interface AlertAckDialogProps {
    onClose?: (canceled: boolean) => void;
}

/**
 * Display a dialog which confirm or cancels an action
 */
const AlertAckDialog = forwardRef(
    (props: AlertAckDialogProps, ref: React.Ref<IAlertDialogDialog> | undefined): JSX.Element => {
        const formRef = useRef<Formsy>(null);
        const [valid, setValid] = useState<boolean>(true);
        const [show, setShow] = useState<Alert | null>(null);

        const closeDialog = (canceled: boolean): void => {
            setShow(null);

            props.onClose?.(canceled);
        };

        const onCancel = () => {
            closeDialog(true);
        };

        const processSave = (notes: string): Promise<boolean> => {
            const alertId = show?.id;
            if (alertId) {
                return new Promise<boolean>(resolve => {
                    eventsService
                        .updateAlert({
                            alertId,
                            notes,
                            alertStatus: AlertStatus.ACKED,
                        })
                        .then(() => resolve(true))
                        .catch(() => resolve(false));
                });
            }

            return Promise.resolve(false);
        };

        const onConfirm = () => {
            const formData = formRef?.current ? formRef.current.getModel() : null;
            processSave(formData.notes).then(() => {
                closeDialog(false);
            });
        };

        const onClose = (reason: OnCloseReason, data: any): Promise<boolean> => {
            if (DIALOG_REASON_SUBMIT === reason) {
                return new Promise<boolean>(resolve => {
                    const formData = formRef?.current ? formRef.current.getModel() : null;
                    processSave(formData.notes).then(() => {
                        resolve(true);
                        closeDialog(false);
                    });
                });
            }

            closeDialog(true);
            return Promise.resolve(true);
        };

        const disableButton = (): void => {
            setValid(false);
        };

        const enableButton = (): void => {
            setValid(true);
        };

        const openDialog = (alert: Alert): void => {
            setShow(alert);
        };

        useImperativeHandle(ref, () => ({
            openDialog,
        }));

        return (
            <Dialog
                fullWidth
                open={!!show}
                onClose={onClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">Acknowledge Alert</DialogTitle>
                <DialogContent>
                    <div className="w-full">
                        <Formsy
                            onValid={enableButton}
                            onInvalid={disableButton}
                            ref={formRef}
                            className="flex flex-col justify-center w-full"
                        >
                            <FormControl margin="dense" fullWidth>
                                <TextFieldFormsy
                                    type="text"
                                    name="notes"
                                    label="Notes:"
                                    variant="outlined"
                                    margin="dense"
                                    multiline
                                    rows={3}
                                />
                            </FormControl>
                        </Formsy>
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button color="primary" onClick={onCancel}>
                        Cancel
                    </Button>
                    <Button color="primary" autoFocus onClick={onConfirm} disabled={!valid}>
                        Confirm
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
);

export default AlertAckDialog;
