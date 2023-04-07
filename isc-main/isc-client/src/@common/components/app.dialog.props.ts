export const DIALOG_REASON_BACKDROP = 'backdropClick';
export const DIALOG_REASON_ESCAPE = 'escapeKeyDown';
export const DIALOG_REASON_CANCEL = 'cancelBtn';
export const DIALOG_REASON_SUBMIT = 'submitBtn';
export type OnCloseReason = 'backdropClick' | 'escapeKeyDown' | 'cancelBtn' | 'submitBtn';

export interface AppDialogProps<T> {
    onClose?: (reason: OnCloseReason, data: T) => void;
    open: boolean;
}
