import { authRoles } from 'app/auth';
import MigrationTool from './MigrationTool';

const MigrationToolConfig = {
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
            path: '/ui/admin/migrationTool',
            component: MigrationTool,
        },
    ],
};

export default MigrationToolConfig;
