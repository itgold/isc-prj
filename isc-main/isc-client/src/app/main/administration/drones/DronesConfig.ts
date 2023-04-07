import { authRoles } from 'app/auth';
import Drones from './DronesPage';

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
            path: '/ui/admin/drones',
            component: Drones,
        },
    ],
};

export default SchoolsConfig;
