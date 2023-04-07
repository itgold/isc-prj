import { authRoles } from 'app/auth';
import Tags from './Tags';

const TagsConfig = {
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
            path: '/ui/admin/tags',
            component: Tags,
        },
    ],
};

export default TagsConfig;
