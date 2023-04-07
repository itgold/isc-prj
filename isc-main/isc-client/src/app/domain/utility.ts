import { DEACTIVATED, EntityType } from 'app/utils/domain.constants';
import { IEntity } from './entity';
import GeoPoint from './geo.location';

export default class Utility implements IEntity {
    id?: string;

    entityType: string = EntityType.UTILITY;

    name?: string = '';

    description?: string = '';

    parentIds?: string[];

    externalId?: string;

    status?: string = DEACTIVATED;

    type?: string = 'UNKNOWN';

    geoLocation?: GeoPoint;

    updated?: Date;

    constructor(rec?: any) {
        this.id = rec?.id;
        this.status = rec?.status;
        this.description = rec?.description;
        this.type = rec?.type;
        this.status = rec?.status;
        this.geoLocation = rec?.geoLocation;
        this.parentIds = rec?.parentIds || [];
        this.externalId = rec?.externalId;
        this.updated = rec?.updated;
    }
}
