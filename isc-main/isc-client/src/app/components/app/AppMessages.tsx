import React from 'react';
import AppDialog from './AppDialog';

/**
 * Contains all components related to messages, such as:
 *  Dialogs;
 *  Popups;
 *  Alerts;
 *  Tooltips;
 *  etc.
 * They should appear by action request
 */
const AppMessages = (props: any) => {
    // TODO: Add more components according requests
    return <>{props.dialog.showDialog && <AppDialog {...props.dialog} hideDialog={props.hideDialog} />}</>;
};

export default AppMessages;
