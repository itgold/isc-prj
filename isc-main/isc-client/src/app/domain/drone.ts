import { DeviceState } from 'app/store/appState';
import { DEACTIVATED, EntityType } from 'app/utils/domain.constants';
import GeoPoint from './geo.location';
import ISchoolElement from './school.element';

export default class Drone implements ISchoolElement {
    entityType: string = EntityType.DRONE;

    id?: string;

    externalId?: string = '';

    name?: string;

    description?: string;

    parentIds?: string[];

    status?: string = DEACTIVATED;

    type?: string = 'TETHERED';

    geoLocation?: GeoPoint;

    state?: DeviceState;

    updated?: Date;

    constructor(rec?: Drone) {
        this.id = rec?.id;
        this.externalId = rec?.externalId;
        this.name = rec?.name;
        this.description = rec?.description;
        this.type = rec?.type;
        this.status = rec?.status;
        this.geoLocation = rec?.geoLocation;
        this.parentIds = rec?.parentIds || [];
        this.state = rec?.state;
        this.updated = rec?.updated;
    }
}
