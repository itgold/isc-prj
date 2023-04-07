import { authRoles } from 'app/auth';
import Safeties from './SafetiesPage';

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
            path: '/ui/admin/safeties',
            component: Safeties,
        },
    ],
};

export default SchoolsConfig;
