import { authRoles } from 'app/auth';
import Zones from './ZonesPage';

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
            path: '/ui/admin/zones',
            component: Zones,
        },
    ],
};

export default SchoolsConfig;
