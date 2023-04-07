import { authRoles } from 'app/auth';
import Schools from './Schools';

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
            path: '/ui/admin/schoolsList',
            component: Schools,
        },
    ],
};

export default SchoolsConfig;
