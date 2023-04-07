import { authRoles } from 'app/auth';
import ExternalUsers from './ExternalUsers';

const ExternalUsersConfig = {
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
            path: '/ui/admin/externalUsers',
            component: ExternalUsers,
        },
    ],
};

export default ExternalUsersConfig;
