import AppBar from '@material-ui/core/AppBar';
import Hidden from '@material-ui/core/Hidden';
import { ThemeProvider } from '@material-ui/core/styles';
import Toolbar from '@material-ui/core/Toolbar';
import NavbarMobileToggleButton from 'app/fuse-layouts/shared-components/NavbarMobileToggleButton';
import UserMenu from 'app/fuse-layouts/shared-components/UserMenu';
import React from 'react';
import { useSelector } from 'react-redux';
import IscStatusBar from 'app/components/IscStatusBar';
import IscAlerts from 'app/components/alerts/IscAlerts';

function ToolbarLayout1(props) {
    const config = useSelector(({ fuse }) => fuse.settings.current.layout.config);
    const toolbarTheme = useSelector(({ fuse }) => fuse.settings.toolbarTheme);

    return (
        <ThemeProvider theme={toolbarTheme}>
            <AppBar
                id="fuse-toolbar"
                className="flex relative z-10"
                color="default"
                style={{ backgroundColor: toolbarTheme.palette.background.default }}
            >
                <Toolbar className="p-0">
                    <div className="flex flex-1">
                        <IscStatusBar />
                    </div>

                    <div className="flex">
                        <IscAlerts />
                        <UserMenu />
                    </div>

                    {config.navbar.display && config.navbar.position === 'right' && (
                        <Hidden lgUp>
                            <NavbarMobileToggleButton />
                        </Hidden>
                    )}
                </Toolbar>
            </AppBar>
        </ThemeProvider>
    );
}

export default React.memo(ToolbarLayout1);
