import { authRoles } from 'app/auth';
import TerritoryMap from './TerritoryMap';

const TerritoryMapConfig = {
    settings: {
        layout: {
            config: {
                footer: {
                    display: false,
                },
            },
        },
    },
    auth: authRoles.Dashboards,
    routes: [
        {
            path: '/ui/dashboards/map',
            component: TerritoryMap,
        },
    ],
};

export default TerritoryMapConfig;
