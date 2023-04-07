import { authRoles } from 'app/auth';
import AlertTriggerPage from './AlertTriggersPage';

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
            path: '/ui/admin/alertTriggers',
            component: AlertTriggerPage,
        },
    ],
};

export default SchoolsConfig;
