import GeoPoint from './geo.location';

export default class GeoPolygon {
    points: GeoPoint[];

    constructor(rec?: any) {
        this.points = rec?.points || [];
    }
}
