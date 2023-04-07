import { connect } from 'react-redux';
import AppMessages from 'app/components/app/AppMessages';
import * as appActions from '../actions/app';

const mapDispatchToProps = dispatch => {
    return {
        showDialog: props => {
            dispatch(
                appActions.showDialog(
                    props.title,
                    props.message,
                    props.onConfirm,
                    props.onCancel,
                    props.onClose,
                    props.showDialog
                )
            );
        },
        hideDialog: value => dispatch(appActions.hideDialog(value)),
    };
};
const mapStateToProps = state => {
    return {
        dialog: state.app.dialog,
    };
};

export const AppMessagesBinding = connect(mapStateToProps, mapDispatchToProps)(AppMessages);
