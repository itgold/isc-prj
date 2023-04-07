import React, { forwardRef, useImperativeHandle } from 'react';
import { useDispatch } from 'react-redux';
import { Button, DialogActions, DialogContent, DialogTitle, makeStyles } from '@material-ui/core';

import { openDialog, closeDialog } from 'app/store/actions/fuse/dialog.actions';

export interface IErrorDialog {
    openDialog(title: string, errorDetails: any): void;
}

const useStyles = makeStyles(() => ({
    errorMessages: {
        color: 'red',
    },
}));

const ErrorDialog = forwardRef((props, ref) => {
    const dispatch = useDispatch();
    const classes = useStyles();

    const generateErrorDetails = (errorDetails: any) => {
        if (errorDetails?.graphQLErrors?.length) {
            return (
                <ul>
                    {errorDetails.graphQLErrors.map((error: any, index: number) => (
                        <li key={index}>{error.extensions?.errorMessage || error.message}</li>
                    ))}
                </ul>
            );
        }

        console.log('Server error', errorDetails);
        return <></>;
    };

    // The component instance will be extended with whatever you return from the
    // callback passed as the second argument
    useImperativeHandle(ref, () => ({
        openDialog(title: string, errorDetails: any) {
            dispatch(
                openDialog({
                    children: (
                        <>
                            <DialogTitle id="alert-dialog-title">{title}</DialogTitle>
                            <DialogContent className={classes.errorMessages}>
                                {generateErrorDetails(errorDetails)}
                            </DialogContent>
                            <DialogActions>
                                <Button onClick={() => dispatch(closeDialog())} color="primary">
                                    Close
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

export default ErrorDialog;
