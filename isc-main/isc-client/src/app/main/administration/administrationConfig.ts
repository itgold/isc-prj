import UsersConfig from './users/UsersConfig';
import ExternalUsersConfig from './externalUsers/ExternalUsersConfig';
import SchoolsConfig from './schools/SchoolsConfig';
import DoorsConfig from './doors/DoorsConfig';
import CamerasConfig from './cameras/CamerasConfig';
import SpeakersConfig from './speakers/SpeakersConfig';
import ZonesConfig from './zones/ZonesConfig';
import DronesConfig from './drones/DronesConfig';
import DistrictsConfig from './districts/DistrictsConfig';
import TagsConfig from './tags/TagsConfig';
import RegionsConfig from './regions/RegionsConfig';
import MigrationToolConfig from './migrationTool/MigrationToolConfig';
import SystemSettingsConfig from './system/SystemSettingsConfig';
import UtilitiesConfig from './utilities/UtilitiesConfig';
import SafetiesConfig from './safeties/SafetiesConfig';
import RadiosConfig from './radios/RadiosConfig';
import AlertTriggersConfig from './alertTriggers/AlertTriggersPageConfig';

const administrationConfigs = [
    UsersConfig,
    ExternalUsersConfig,
    SchoolsConfig,
    DoorsConfig,
    CamerasConfig,
    SpeakersConfig,
    DronesConfig,
    SafetiesConfig,
    DistrictsConfig,
    TagsConfig,
    RegionsConfig,
    MigrationToolConfig,
    ZonesConfig,
    SystemSettingsConfig,
    UtilitiesConfig,
    RadiosConfig,
    AlertTriggersConfig,
];

export default administrationConfigs;
