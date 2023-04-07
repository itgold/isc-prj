import { authRoles } from 'app/auth';
import Doors from './DoorsPage';

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
            path: '/ui/admin/doors',
            component: Doors,
        },
    ],
};

export default SchoolsConfig;
