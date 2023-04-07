import Region from './region';

export enum ZoneType {
    SPEAKERS = 'Speakers',
    CAMERAS = 'Cameras',
}

export const DEFAULT_ZONE_COLOR = '#0d47a1b0';

export default class Zone extends Region {
    subType?: ZoneType;

    childIds?: string[];

    color?: string;

    constructor(rec?: Zone) {
        super(rec);

        this.childIds = rec?.childIds || [];
        this.type = !rec?.type ? undefined : typeof rec?.type === 'string' ? (rec?.type as ZoneType) : rec?.type;
        this.color = rec?.color;
    }
}
