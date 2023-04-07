import { authRoles } from 'app/auth';
import SystemSettings from './SystemSettings';

const SystemSettingsConfig = {
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
            path: '/ui/admin/system',
            component: SystemSettings,
        },
    ],
};

export default SystemSettingsConfig;
