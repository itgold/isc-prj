import FuseScrollbars from '@fuse/core/FuseScrollbars';
import Drawer from '@material-ui/core/Drawer';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import withReducer from 'app/store/withReducer';
import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import * as Actions from './store/actions/index';
import reducer from './store/reducers';

const useStyles = makeStyles(() => ({
    root: {
        width: 280,
    },
}));

function QuickPanel() {
    const dispatch = useDispatch();
    const state = useSelector(({ quickPanel }) => quickPanel?.state);

    const classes = useStyles();

    return (
        <Drawer
            classes={{ paper: classes.root }}
            open={state}
            anchor="right"
            onClose={() => dispatch(Actions.toggleQuickPanel())}
        >
            <FuseScrollbars>
                <Typography>Quick Panel</Typography>
            </FuseScrollbars>
        </Drawer>
    );
}

export default withReducer('quickPanel', reducer)(React.memo(QuickPanel));
