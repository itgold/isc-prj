import { authRoles } from 'app/auth';
import Districts from './DistrictsPage';

const DistrictsConfig = {
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
            path: '/ui/admin/districts',
            component: Districts,
        },
    ],
};

export default DistrictsConfig;
