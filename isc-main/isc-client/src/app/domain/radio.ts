import { DeviceState } from 'app/store/appState';
import { DEACTIVATED, EntityType } from 'app/utils/domain.constants';
import GeoPoint from './geo.location';
import ISchoolElement from './school.element';

export default class Radio implements ISchoolElement {
    entityType: string = EntityType.RADIO;

    id?: string;

    externalId?: string = '';

    name?: string;

    description?: string;

    parentIds?: string[];

    status?: string = DEACTIVATED;

    type?: string = 'GPS';

    geoLocation?: GeoPoint;

    state?: DeviceState;

    updated?: Date;

    constructor(rec?: Radio) {
        this.id = rec?.id;
        this.externalId = rec?.externalId;
        this.name = rec?.name;
        this.description = rec?.description;
        this.type = rec?.type;
        this.status = rec?.status;
        this.geoLocation = rec?.geoLocation;
        this.state = rec?.state;
        this.parentIds = rec?.parentIds || [];
        this.updated = rec?.updated;
    }
}
