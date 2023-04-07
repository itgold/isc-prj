import { authRoles } from 'app/auth';
import Cameras from './CamerasPage';

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
            path: '/ui/admin/cameras',
            component: Cameras,
        },
    ],
};

export default SchoolsConfig;
