import React from 'react';
import FuseAnimate from '@fuse/core/FuseAnimate';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import { makeStyles, darken } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import clsx from 'clsx';
import JWTLoginTab from './JWTLoginTab';

const useStyles = makeStyles(theme => ({
    root: {
        background: `radial-gradient(${darken(theme.palette.primary.dark, 0.5)} 0%, ${theme.palette.primary.dark} 80%)`,
        color: theme.palette.primary.contrastText,
    },
}));

function Login() {
    const classes = useStyles();
    const publicPath = window.PUBLIC_URL;

    return (
        <div className={clsx(classes.root, 'flex flex-col flex-auto flex-shrink-0 items-center justify-center p-32')}>
            <div className="flex flex-col items-center justify-center w-full">
                <FuseAnimate animation="transition.expandIn">
                    <Card className="w-full max-w-384">
                        <CardContent className="flex flex-col items-center justify-center p-32">
                            <img
                                className="w-128 m-32"
                                src={`${publicPath}/assets/images/logos/logo192.png`}
                                alt="logo"
                            />

                            <Typography variant="h6" className="mt-16 mb-32">
                                LOGIN TO YOUR ACCOUNT
                            </Typography>

                            <JWTLoginTab />
                        </CardContent>
                    </Card>
                </FuseAnimate>
            </div>
        </div>
    );
}

export default Login;
