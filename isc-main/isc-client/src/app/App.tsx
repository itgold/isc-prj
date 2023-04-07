import React from 'react';
import { Provider } from 'react-redux';
import { Router } from 'react-router-dom';
import history from '@history';
import { GlobalHistory } from '@history/GlobalHistory';

import { MuiPickersUtilsProvider } from '@material-ui/pickers';
import { createGenerateClassName, jssPreset, StylesProvider } from '@material-ui/core/styles';
import { create, Plugin } from 'jss';
import jssExtend from 'jss-plugin-extend';
import rtl from 'jss-rtl';

import MomentUtils from '@date-io/moment';
import FuseAuthorization from '@fuse/core/FuseAuthorization';
import FuseLayout from '@fuse/core/FuseLayout';
import FuseTheme from '@fuse/core/FuseTheme';
import { WebSocketContext } from '@common/components/web.socket.context';
import { version } from '../../package.json';
import AppContext from './AppContext';
import { Auth } from './auth';
import routes from './fuse-configs/routesConfig';
import store from './store';
import { AppMessagesBinding } from './store/bindings/app.binding';
import { AppInitializer } from './components/app/AppInitializer';

const jss = create({
    ...jssPreset(),
    plugins: [...(jssPreset().plugins as Plugin[]), jssExtend(), rtl()],
    insertionPoint: document.getElementById('jss-insertion-point') || undefined,
});
const generateClassName = createGenerateClassName();

const App = (): JSX.Element => {
    return (
        <AppContext.Provider
            value={{
                routes,
            }}
        >
            <StylesProvider jss={jss} generateClassName={generateClassName}>
                <Provider store={store}>
                    <MuiPickersUtilsProvider utils={MomentUtils}>
                        <Router history={history}>
                            <Auth>
                                <WebSocketContext>
                                    <AppInitializer>
                                        <GlobalHistory />
                                        <FuseAuthorization>
                                            <FuseTheme>
                                                <FuseLayout />
                                                <AppMessagesBinding />
                                            </FuseTheme>
                                        </FuseAuthorization>
                                    </AppInitializer>
                                </WebSocketContext>
                            </Auth>
                        </Router>
                    </MuiPickersUtilsProvider>
                </Provider>
            </StylesProvider>
        </AppContext.Provider>
    );
};

export default App;

console.log('Version', version);
