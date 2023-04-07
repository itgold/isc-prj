import { authRoles } from 'app/auth';
import Speakers from './SpeakersPage';

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
            path: '/ui/admin/speakers',
            component: Speakers,
        },
    ],
};

export default SchoolsConfig;
