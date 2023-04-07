import { authRoles } from 'app/auth';
import Radios from './RadiosPage';

const SchoolsConfig = {
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
            path: '/ui/admin/radios',
            component: Radios,
        },
    ],
};

export default SchoolsConfig;
