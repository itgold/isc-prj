import JWTToken from '@common/auth/jwt.token';
import { DefaultTheme } from '@material-ui/styles';
import SchoolDistrict from 'app/domain/school.district';
import School from 'app/domain/school';
import Door from 'app/domain/door';
import Camera from 'app/domain/camera';
import Drone from 'app/domain/drone';
import { IscTableContext } from 'app/components/table/IscTableContext';
import Region from 'app/domain/region';
import User from 'app/domain/user';
import Role from 'app/domain/role';
import Tag from 'app/domain/tag';
import Radio from 'app/domain/radio';
import Speaker from 'app/domain/speaker';
import { CompositeNode } from 'app/domain/composite';
import { SearchResult } from 'app/domain/search';
import DeviceEvent, { Alert } from 'app/domain/deviceEvent';
import { DeviceActionPayload, DeviceActionResult } from 'app/components/map/deviceActions/IDeviceAction';
import CodeDictionary from 'app/domain/codeDictionary';
import { FloatingWindow } from 'app/components/map/FloatingWindow';
import { ILayersHashmap } from 'app/components/map/SchoolMapComponent';
import { IEntity } from '../domain/entity';
import Utility from '../domain/utility';
import Safety from '../domain/safety';

export interface UserDataState {
    displayName: string;
    photoURL: string;
    email: string;
    shortcuts: string[];
}

export interface UserState {
    role: string[];
    data: UserDataState;
}

export interface AuthState {
    user: UserState;
    login: {
        rest: {
            success: boolean;
            error: any;
        };
        token: JWTToken;
    };
}

export interface NavigationItemState {
    id: string;
    type: string;
    title?: string;
    translate?: string;
    icon?: string;
    auth?: string[];
    children?: NavigationItemState[];
}

export interface FuseTheme {
    main: string;
    navbar: string;
    toolbar: string;
    footer: string;
}

export interface FuseLayout {
    style: string;
    config: {
        mode: string;
        scroll: string;
        navbar: {
            display: boolean;
            folded: boolean;
            position: string; // 'left'
        };
        toolbar: {
            display: boolean;
            style: string; // 'fixed',
            position: string; // 'below'
        };
        footer: {
            display: boolean;
            style: string; // 'fixed',
            position: string; // 'below'
        };
        leftSidePanel: {
            display: boolean;
        };
        rightSidePanel: {
            display: boolean;
        };
    };
}

export interface FuseSettingsState {
    customScrollbars: boolean;
    animations: boolean;
    direction: string; // 'ltr'
    theme: FuseTheme;
    layout: FuseLayout;
}

export interface FuseStateSettings<Theme = DefaultTheme> {
    initial: FuseSettingsState;
    defaults: FuseSettingsState;
    current: FuseSettingsState;
    themes: any;
    mainTheme: Partial<Theme> | ((outerTheme: Theme) => Theme);
    navbarTheme: Partial<Theme> | ((outerTheme: Theme) => Theme);
    toolbarTheme: Partial<Theme> | ((outerTheme: Theme) => Theme);
    footerTheme: Partial<Theme> | ((outerTheme: Theme) => Theme);
    mainThemeDark: Partial<Theme> | ((outerTheme: Theme) => Theme);
    mainThemeLight: Partial<Theme> | ((outerTheme: Theme) => Theme);
}

export interface FuseState<Theme = DefaultTheme> {
    navigation: NavigationItemState[];
    settings: FuseStateSettings<Theme>;
    navbar: {
        foldedOpen: boolean;
        mobileOpen: boolean;
    };
    message: {
        state: null;
        options: {
            anchorOrigin: {
                vertical: string;
                horizontal: string;
            };
            autoHideDuration: number;
            message: string;
            messageComponent?: React.ReactNode;
            variant: string;
        };
    };
    dialog: {
        state: boolean;
        options: any;
    };
    routes: any;
}

export interface Records {
    [key: string]: any[];
}

// ----------------------------------------
// School entity dynamic state
export interface DeviceStateItem {
    type: string;
    value: string;
    updated: Date;
}
export type DeviceState = Array<DeviceStateItem>;

// ----------------------------------------
export interface MapContextStates {
    [key: string]: IscTableContext;
}

export interface DashboardsState {
    mapDashboard: MapContextStates;
    currentSchoolId?: string;
    deviceAction?: {
        action?: DeviceActionPayload;
        result?: DeviceActionResult;
    };
}

export interface EntityMap<T extends IEntity> {
    [key: string]: T;
}

export interface Entities {
    doors: EntityMap<Door>;
    cameras: EntityMap<Camera>;
    speakers: EntityMap<Speaker>;
    safeties: EntityMap<Safety>;
    drones: EntityMap<Drone>;
    regions: EntityMap<Region>;
    radios: EntityMap<Radio>;

    schools: EntityMap<School>;
    districts: EntityMap<SchoolDistrict>;
    tags: EntityMap<Tag>;
    users: EntityMap<User>;
    roles: EntityMap<Role>;
    utilities: EntityMap<Utility>;

    compositecams: EntityMap<Region>;
}

export interface SchoolEvents {
    currentPage: SearchResult<DeviceEvent> | null;
    newEvents: DeviceEvent[];
}
export interface Alerts {
    currentPage: SearchResult<Alert> | null;
    newEvents: DeviceEvent[];
}

export interface Composites {
    commonLoaded: boolean;
    domainLoadError: boolean;
    root: CompositeNode;
    entities: Entities;
    events: SchoolEvents;
    alerts: Alerts;
    deviceCodes: EntityMap<CodeDictionary>;
}

export interface AppStateMap {
    [key: string]: any;
    layersHashmap: ILayersHashmap;
}

export interface AppState {
    map: AppStateMap;
    zones: string[];

    dialog: {
        title: '';
        message: '';
        // callbacks
        onConfirm: null;
        onCancel: null;
        onClose: null;
        showDialog: false;
    };

    floatingWindows: FloatingWindow[];
}

export interface RootState {
    app: AppState;
    auth?: AuthState;
    fuse: FuseState;
    domain: Composites;

    quickPanel?: {
        state: boolean;
        data: any;
    };

    dashboards?: DashboardsState;
}
