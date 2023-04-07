import { DEACTIVATED } from 'app/utils/domain.constants';
import { IEntity } from './entity';
import Region from './region';

export default class SchoolDistrict implements IEntity {
    id?: string;

    name?: string = '';

    description?: string = '';

    contactEmail?: string = '';

    address?: string;

    city?: string;

    state?: string;

    zipCode?: string;

    country?: string;

    status?: string = DEACTIVATED;

    region?: Region;

    constructor(rec?: SchoolDistrict) {
        this.id = rec?.id;
        this.name = rec?.name;
        this.description = rec?.description;
        this.contactEmail = rec?.contactEmail;
        this.address = rec?.address;
        this.state = rec?.state;
        this.zipCode = rec?.zipCode;
        this.country = rec?.country;
        this.status = rec?.status;
        this.region = rec?.region;
    }
}
