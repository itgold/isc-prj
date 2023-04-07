import { authRoles } from 'app/auth';
import SocialDashboard from './SocialDashboard';

const SocialDashboardConfig = {
    settings: {
        layout: {
            config: {},
        },
    },
    auth: authRoles.Dashboards,
    routes: [
        {
            path: '/ui/dashboards/social',
            component: SocialDashboard,
        },
    ],
};

export default SocialDashboardConfig;
