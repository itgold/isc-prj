/* eslint-disable  @typescript-eslint/no-unused-expressions */
import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

/**
 * Display a dialog which confirm or cancels an action
 */
const AppDialog = (props: any) => {
    const onCancel = () => {
        props.onCancel && props.onCancel();
        props.hideDialog(true);
    };

    const onConfirm = () => {
        props.onConfirm && props.onConfirm();
        props.hideDialog(true);
    };

    return (
        <Dialog
            fullWidth
            open
            onClose={props.onClose && props.onClose()}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogTitle id="alert-dialog-title">{props.title}</DialogTitle>
            <DialogContent>
                <DialogContentText id="alert-dialog-description">{props.message}</DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button color="primary" onClick={onCancel}>
                    Cancel
                </Button>
                <Button color="primary" autoFocus onClick={onConfirm}>
                    Confirm
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default AppDialog;
