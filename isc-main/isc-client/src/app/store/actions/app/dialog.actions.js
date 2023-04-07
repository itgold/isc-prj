import { createActions } from 'redux-actions';

export const { showDialog, hideDialog } = createActions({
    SHOW_DIALOG: (title, message, onConfirm, onCancel, onClose) => ({
        title,
        message,
        onConfirm,
        onCancel,
        onClose,
        showDialog: true,
    }),
    HIDE_DIALOG: value => value,
});
