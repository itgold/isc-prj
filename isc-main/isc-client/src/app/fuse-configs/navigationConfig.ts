import { authRoles } from 'app/auth';

export interface NavItemSettings {
    // false/true determines whether the user should be redirected or not when clicking on the label,
    redirect?: boolean;
    // A different name to be labeled
    label?: string;
    // A different address to be redirect when the user clicks
    href?: string;
}

export interface UrlSettings {
    [key: string]: NavItemSettings | undefined;
}

export interface NavigationConfigItem {
    id: string;
    title: string;
    translate?: string;
    type: string;
    icon: string;
    url?: string;
    auth: string[];
    urlSettings?: UrlSettings;
    children?: NavigationConfigItem[];
}

const navigationConfig = [
    {
        id: 'dashboards',
        title: 'Dashboards',
        translate: 'DASHBOARDS',
        type: 'group',
        icon: 'apps',
        auth: authRoles.AnyAuthenticated,
        children: [
            {
                id: 'dashboard-map',
                title: 'Map',
                // translate: 'TERRITORY_MAP',
                type: 'item',
                icon: 'map',
                url: '/ui/dashboards/map',
                auth: authRoles.Dashboards,
            },
            {
                id: 'dashboard-social',
                title: 'Social Networks',
                // translate: 'SOCIAL_DASHBOARD',
                type: 'item',
                icon: 'face',
                url: '/ui/dashboards/social',
                auth: authRoles.Dashboards,
            },
        ],
    },
    {
        id: 'admins',
        title: 'Administration',
        type: 'group',
        icon: 'admin_panel_settings',
        auth: authRoles.SystemAdministrator,
        children: [
            {
                id: 'districts',
                title: 'Districts',
                type: 'item',
                icon: 'account_balance',
                url: '/ui/admin/districts',
                auth: authRoles.SystemAdministrator,
                /**
                 * We map the urlSettings for just in the case that we need to decline some default setting.
                 * We can change the `label`, `redirect` or href for each step of the URL. To define the settings for a specific
                 * step, we need to add the step name as property from urlSettings.
                 * urlSettings: {
                 *     dashboards: {
                 *          redirect: false/true determines whether the user should be redirected or not when clicking on the label,
                 *          label: 'A different name to be labeled',
                 *          href: A different address to be redirect when the user clicks
                 *     }
                 * }
                 */
                urlSettings: {
                    admin: {
                        // enable/disable redirection when the user clicks in the step of the Breadcrumb
                        redirect: false,
                    },
                },
            },
            {
                id: 'schools',
                title: 'Schools',
                type: 'item',
                icon: 'school',
                url: '/ui/admin/schoolsList',
                auth: authRoles.SystemAdministrator,
                urlSettings: {
                    admin: {
                        redirect: false,
                    },
                },
            },
            {
                id: 'regions',
                title: 'Regions',
                // translate: 'Regions',
                icon: 'edit_location',
                auth: authRoles.SystemAdministrator,
                type: 'item',
                url: '/ui/admin/regions',
                disabled: false,
            },
            {
                id: 'zones',
                title: 'Zones',
                // translate: 'SPEACKER_ZONES',
                icon: 'speaker_group',
                auth: authRoles.SystemAdministrator,
                type: 'item',
                url: '/ui/admin/zones',
                disabled: false,
            },
            {
                id: 'doors',
                title: 'Doors',
                // translate: 'DOORS',
                icon: 'lock_open',
                auth: authRoles.SystemAdministrator,
                type: 'item',
                url: '/ui/admin/doors',
                disabled: false,
            },
            {
                id: 'cameras',
                title: 'Cameras',
                // translate: 'CAMERAS',
                icon: 'videocam',
                auth: authRoles.SystemAdministrator,
                type: 'item',
                url: '/ui/admin/cameras',
                disabled: false,
            },
            {
                id: 'speakers',
                title: 'Speakers',
                // translate: 'SPEAKERS',
                icon: 'speaker',
                auth: authRoles.SystemAdministrator,
                type: 'item',
                url: '/ui/admin/speakers',
                disabled: false,
            },
            {
                id: 'drones',
                title: 'Drones',
                // translate: 'DRONES',
                icon: 'toys',
                auth: authRoles.SystemAdministrator,
                type: 'item',
                url: '/ui/admin/drones',
                disabled: false,
            },
            {
                id: 'utilities',
                title: 'Utilities',
                // translate: 'USERS',
                icon: 'dehaze',
                auth: authRoles.SystemAdministrator,
                type: 'item',
                url: '/ui/admin/utilities',
            },
            {
                id: 'safeties',
                title: 'Safeties',
                // translate: 'USERS',
                icon: 'healing',
                auth: authRoles.SystemAdministrator,
                type: 'item',
                url: '/ui/admin/safeties',
            },
            {
                id: 'radios',
                title: 'Radios',
                // translate: 'RADIOS',
                icon: 'keyboard_voice',
                auth: authRoles.SystemAdministrator,
                type: 'item',
                url: '/ui/admin/radios',
                disabled: false,
            },
            {
                id: 'tags',
                title: 'Tags',
                // translate: 'TAGS',
                icon: 'label',
                auth: authRoles.SystemAdministrator,
                type: 'item',
                url: '/ui/admin/tags',
                disabled: false,
            },
            {
                id: 'users',
                title: 'Users',
                // translate: 'USERS',
                icon: 'perm_identity',
                auth: authRoles.SystemAdministrator,
                type: 'item',
                url: '/ui/admin/users',
            },
            {
                id: 'externalUsers',
                title: 'External Users',
                // translate: 'USERS',
                icon: 'perm_identity',
                auth: authRoles.SystemAdministrator,
                type: 'item',
                url: '/ui/admin/externalUsers',
            },
        ],
    },
    {
        type: 'divider',
        id: 'divider-1',
    },
    /* {
        id: 'caseManagement',
        title: 'Case Management',
        // translate: 'CASE_MANAGEMENT',
        type: 'item',
        icon: 'apps',
        url: '/ui/admin/caseManagement',
        auth: authRoles.SystemAdministrator,
        disabled: true,
    }, */
    {
        id: 'migrationTool',
        title: 'Import Tool',
        // translate: 'CASE_MANAGEMENT',
        type: 'item',
        icon: 'build',
        url: '/ui/admin/migrationTool',
        auth: authRoles.SystemAdministrator,
    },
    {
        id: 'system',
        title: 'System',
        // translate: 'System',
        type: 'item',
        icon: 'settings',
        url: '/ui/admin/system',
        auth: authRoles.SystemAdministrator,
    },
    {
        id: 'alertTriggers',
        title: 'Alerts',
        // translate: 'System',
        type: 'item',
        icon: 'error_outline',
        url: '/ui/admin/alertTriggers',
        auth: authRoles.SystemAdministrator,
    },
];

export default navigationConfig;
