import { DEACTIVATED } from 'app/utils/domain.constants';
import GeoPoint from './geo.location';
import GeoPolygon from './geo.polygon';
import ISchoolElement from './school.element';

export interface RegionProp {
    key: string;
    value: string | null;
}

export default class Region implements ISchoolElement {
    id?: string;

    name?: string = '';

    description?: string = '';

    parentIds?: string[];

    status?: string = DEACTIVATED;

    type?: string = 'UNKNOWN';

    subType?: string;

    geoLocation?: GeoPoint;

    geoBoundaries?: GeoPolygon;

    geoZoom?: number;

    geoRotation?: number;

    updated?: Date;

    /* eslint-disable-next-line */
    props?: RegionProp[] = [];

    constructor(rec?: Region) {
        this.id = rec?.id;
        this.name = rec?.name;
        this.description = rec?.description;
        this.type = rec?.type;
        this.status = rec?.status;
        this.subType = rec?.subType;
        this.geoLocation = rec?.geoLocation;
        this.geoBoundaries = rec?.geoBoundaries;
        this.geoZoom = rec?.geoZoom;
        this.geoRotation = rec?.geoRotation;
        this.parentIds = rec?.parentIds || [];
        this.updated = rec?.updated;
        this.props = rec?.props || [];
    }
}
