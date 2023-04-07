import { authRoles } from 'app/auth';
import Users from './Users';

const UsersConfig = {
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
            path: '/ui/admin/users',
            component: Users,
        },
    ],
};

export default UsersConfig;
