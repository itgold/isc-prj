import { authRoles } from 'app/auth';
import Utilities from './Utilities';

const UtilitiesConfig = {
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
            path: '/ui/admin/utilities',
            component: Utilities,
        },
    ],
};

export default UtilitiesConfig;
