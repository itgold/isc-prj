import React from 'react';
import { Redirect } from 'react-router-dom';
import FuseUtils from '@fuse/utils';
import pagesConfigs from 'app/main/commonPages/pagesConfigs';
import authPagesConfigs from 'app/auth/pages/authPagesConfig';
import dashboardsConfig from 'app/main/dashboards/dashboardsConfig';
import mapsEditorConfig from 'app/main/mapsEditor/MapsEditorConfig';
import testPageConfig from 'app/main/testPage/TestPageConfig';
import administrationConfigs from 'app/main/administration/administrationConfig';
import { authRoles } from 'app/auth';

const routeConfigs = [
    ...pagesConfigs,
    ...dashboardsConfig,
    ...administrationConfigs,
    mapsEditorConfig,
    testPageConfig,
    ...authPagesConfigs,
];

const routes = [
    ...FuseUtils.generateRoutesFromConfigs(routeConfigs, authRoles.AnyAuthenticated),
    {
        path: '/',
        exact: true,
        component: () => <Redirect to="/ui/dashboards/map" />,
    },
    {
        path: '/index.html',
        exact: true,
        component: () => <Redirect to="/ui/dashboards/map" />,
    },
    {
        component: () => <Redirect to="/ui/error-404" />,
    },
];

export default routes;
