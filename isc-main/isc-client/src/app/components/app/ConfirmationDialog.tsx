import React, { forwardRef, RefObject, useImperativeHandle, useRef } from 'react';
import { useDispatch } from 'react-redux';
import { Button, DialogActions, DialogContent, DialogTitle } from '@material-ui/core';
import { closeDialog, openDialog } from 'app/store/actions/fuse/dialog.actions';
import DialogContentText from '@material-ui/core/DialogContentText';
import { IErrorDialog } from './ServerErrorDialog';

export interface IConfirmationDialog {
    openDialog(title: string, row: any, deleteAction: any, errorRef: RefObject<IErrorDialog>, gridRefresh: any): void;
}

const ConfirmationDialog = forwardRef((props, ref) => {
    const dispatch = useDispatch();

    const deleteEntity = (row: any, deleteAction: any, errorRef: any, gridRefresh: any) => {
        deleteAction(row.id)
            .then(() => {
                dispatch(closeDialog());
                gridRefresh();
            })
            .catch(() => {
                errorRef.current?.openDialog('Unable to Delete');
            });
    };

    useImperativeHandle(ref, () => ({
        openDialog(title: string, row: any, deleteAction: any, errorRef: typeof useRef, gridRefresh: any) {
            dispatch(
                openDialog({
                    children: (
                        <>
                            <DialogTitle id="alert-dialog-title">{title}</DialogTitle>

                            <DialogContent>
                                <DialogContentText id="alert-dialog-description">
                                    Are you sure you want to delete {row.name}? <br />
                                </DialogContentText>
                            </DialogContent>

                            <DialogActions>
                                <Button onClick={() => dispatch(closeDialog())} color="primary">
                                    Cancel
                                </Button>
                                <Button
                                    onClick={() => deleteEntity(row, deleteAction, errorRef, gridRefresh)}
                                    color="primary"
                                >
                                    Yes, Delete
                                </Button>
                            </DialogActions>
                        </>
                    ),
                })
            );
        },
    }));

    return <></>;
});
export default ConfirmationDialog;
