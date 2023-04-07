import { authRoles } from 'app/auth';
import { logout } from 'app/store/actions/auth';
import store from 'app/store';

const LogoutConfig = {
    auth: authRoles.AnyAuthenticated,
    routes: [
        {
            path: '/logout',
            component: () => {
                store.dispatch(logout());
                return 'Logging out..';
            },
        },
    ],
};

export default LogoutConfig;
