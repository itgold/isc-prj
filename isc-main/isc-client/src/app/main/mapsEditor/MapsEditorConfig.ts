import React from 'react';
import { authRoles } from 'app/auth';

const MapsEditor = React.lazy(() => import('./MapsEditor'));
const MapsEditorConfig = {
    settings: {
        layout: {
            config: {
                footer: {
                    display: false,
                },
            },
        },
    },
    auth: authRoles.SystemAdministrator,
    routes: [
        {
            path: '/ui/admin/schoolsMapEditor/:mapId',
            component: MapsEditor,
        },
        {
            path: '/ui/admin/schoolsMapEditor',
            component: MapsEditor,
        },
    ],
};

export default MapsEditorConfig;
