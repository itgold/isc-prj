export default class GeoPoint {
    x: number;

    y: number;

    constructor(rec?: any) {
        this.x = rec?.x;
        this.y = rec?.y;
    }
}
