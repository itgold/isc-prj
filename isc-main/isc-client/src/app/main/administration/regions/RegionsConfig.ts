import { authRoles } from 'app/auth';
import Regions from './RegionsPage';

const RegionsConfig = {
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
            path: '/ui/admin/regions',
            component: Regions,
        },
    ],
};

export default RegionsConfig;
