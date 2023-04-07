import { DEACTIVATED, EntityType } from 'app/utils/domain.constants';
import GeoPoint from './geo.location';
import ISchoolElement from './school.element';

export default class Speaker implements ISchoolElement {
    entityType: string = EntityType.SPEAKER;

    id?: string;

    name?: string = '';

    description?: string = '';

    parentIds?: string[];

    status?: string = DEACTIVATED;

    type?: string = 'UNKNOWN';

    geoLocation?: GeoPoint;

    updated?: Date;

    constructor(rec?: Speaker) {
        this.id = rec?.id;
        this.name = rec?.name;
        this.description = rec?.description;
        this.type = rec?.type;
        this.status = rec?.status;
        this.geoLocation = rec?.geoLocation;
        this.parentIds = rec?.parentIds || [];
        this.updated = rec?.updated;
    }
}
