import React, { useEffect, useState } from 'react';
import FuseScrollbars from '@fuse/core/FuseScrollbars';
import AppBar from '@material-ui/core/AppBar';
import { makeStyles } from '@material-ui/core/styles';
import Logo from 'app/fuse-layouts/shared-components/Logo';
import Navigation from 'app/fuse-layouts/shared-components/Navigation';
import clsx from 'clsx';
import { RestService } from '@common/services/rest.service';

class HelperService extends RestService {
    queryEnv(): Promise<any> {
        return this.client.get('/rest/env', {
            headers: {
                ...this.defaultHeaders(),
            },
        });
    }
}
const helperService = new HelperService();

const useStyles = makeStyles(theme => ({
    content: {
        overflowX: 'hidden',
        overflowY: 'auto',
        '-webkit-overflow-scrolling': 'touch',
        background:
            'linear-gradient(rgba(0, 0, 0, 0) 30%, rgba(0, 0, 0, 0) 30%), linear-gradient(rgba(0, 0, 0, 0.25) 0, rgba(0, 0, 0, 0) 40%)',
        backgroundRepeat: 'no-repeat',
        backgroundSize: '100% 40px, 100% 10px',
        backgroundAttachment: 'local, scroll',
    },
    appBar: {
        background: `linear-gradient(to right, ${theme.palette.primary.dark} 0%, ${theme.palette.primary.main} 100%)`,
    },

    local: {
        background: 'linear-gradient(to right, #E53935 0%, #F44336 100%)',
        color: 'black',
    },
    dev: { background: 'linear-gradient(to right, #FBC02D 0%, #FDD835 100%)', color: 'black' },
    prod: {},
}));

interface NavbarLayout1Pros {
    className?: string;
}

function NavbarLayout1(props: NavbarLayout1Pros): JSX.Element {
    const classes = useStyles();
    const [env, setEnv] = useState<'local' | 'dev' | 'prod' | null>(null);

    useEffect(() => {
        let detauched = false;
        if (!env) {
            helperService.queryEnv().then(rez => {
                if (!detauched) {
                    setEnv(rez);
                }
            });
        }

        return () => {
            detauched = true;
        };
    });

    return (
        <div className={clsx('flex flex-col overflow-hidden h-full', props.className)}>
            <AppBar
                color="primary"
                position="static"
                elevation={0}
                className={clsx(
                    'flex flex-row items-center flex-shrink h-64 min-h-64 px-4',
                    classes.appBar,
                    env && classes[env]
                )}
            >
                <div className="flex flex-1">
                    <Logo />
                </div>
            </AppBar>

            <FuseScrollbars className={clsx(classes.content)} option={{ suppressScrollX: true }}>
                <Navigation layout="vertical" />
            </FuseScrollbars>
        </div>
    );
}

export default React.memo(NavbarLayout1);
