import React from 'react';
import { authRoles } from 'app/auth';

const TestPageConfig = {
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
            path: '/ui/test',
            component: React.lazy(() => import('./TestPage')),
        },
    ],
};

export default TestPageConfig;
