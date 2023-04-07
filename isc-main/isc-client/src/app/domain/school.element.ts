import { DeviceState } from 'app/store/appState';
import { EntityType } from 'app/utils/domain.constants';
import { IEntity } from './entity';
import GeoPoint from './geo.location';
import GeoPolygon from './geo.polygon';

export default interface ISchoolElement extends IEntity {
    entityType?: EntityType | string;

    name?: string;

    parentIds?: string[];

    geoLocation?: GeoPoint | null;

    geoBoundaries?: GeoPolygon | null;

    state?: DeviceState;

    updated?: Date;
}
