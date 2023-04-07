import { DEACTIVATED } from 'app/utils/domain.constants';
import SchoolDistrict from './school.district';
import GeoPoint from './geo.location';
import { IEntity } from './entity';
import Region from './region';

export default class School implements IEntity {
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

    district?: SchoolDistrict;

    geoLocation?: GeoPoint;

    geoZoom?: number;

    geoRotation?: number;

    region?: Region;

    constructor(rec?: School) {
        this.id = rec?.id;
        this.name = rec?.name;
        this.description = rec?.description;
        this.contactEmail = rec?.contactEmail;
        this.address = rec?.address;
        this.city = rec?.city;
        this.state = rec?.state;
        this.zipCode = rec?.zipCode;
        this.country = rec?.country;
        this.status = rec?.status;
        this.district = rec?.district;

        this.geoLocation = rec?.geoLocation;
        this.geoZoom = rec?.geoZoom;
        this.geoRotation = rec?.geoRotation;
        this.region = rec?.region;
    }
}
