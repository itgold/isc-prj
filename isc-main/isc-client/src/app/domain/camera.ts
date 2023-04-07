import { DeviceState } from 'app/store/appState';
import { DEACTIVATED, EntityType } from 'app/utils/domain.constants';
import GeoPoint from './geo.location';
import ISchoolElement from './school.element';

export enum CameraStateType {
    LiveFeedStopped = 'LiveFeedStopped',
    LiveFeedStarted = 'LiveFeedStarted',
    FeedNoMotion = 'FeedNoMotion',
    FeedWithMotion = 'FeedWithMotion',
    FeedNotRecorded = 'FeedNotRecorded',
    FeedRecorded = 'FeedRecorded',
    NoEvents = 'NoEvents',
    SomeEvents = 'SomeEvents',
    ConnectionRestored = 'ConnectionRestored',
    ConnectionLost = 'ConnectionLost',
    DBAccessRestored = 'DBAccessRestored',
    DBAccessLost = 'DBAccessLost',
    DiskSpaceOk = 'DiskSpaceOk',
    DiskSpaceOut = 'DiskSpaceOut',
    FeedRestored = 'FeedRestored',
    FeedLost = 'FeedLost',
}
export function cameraStateType(state: CameraStateType): string {
    return CameraStateType[state] as string;
}

export enum CameraState {
    CONNECTION,
    LIVE,
    MOTION,
    RECORDING,
    HARDWARE,
    ALERTS,
}
export function cameraState(state: CameraState): string {
    return CameraState[state] as string;
}

export interface CameraStream {
    streamId: string;
    name?: string;
}

export default class Camera implements ISchoolElement {
    entityType: string = EntityType.CAMERA;

    id?: string;

    externalId? = '';

    parentIds?: string[];

    status?: string = DEACTIVATED;

    type?: string = 'VIDEO';

    geoLocation?: GeoPoint;

    streams?: CameraStream[];

    name?: string;

    description?: string;

    state?: DeviceState;

    updated?: Date;

    constructor(rec?: Camera) {
        this.id = rec?.id;
        this.externalId = rec?.externalId;
        this.type = rec?.type;
        this.name = rec?.name;
        this.description = rec?.description;
        this.status = rec?.status;
        this.geoLocation = rec?.geoLocation;
        this.streams = rec?.streams;
        this.parentIds = rec?.parentIds || [];
        this.state = rec?.state;
        this.updated = rec?.updated;
    }
}
