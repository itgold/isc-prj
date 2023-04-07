/* eslint-disable class-methods-use-this */
import MeetingRoom from '@material-ui/icons/MeetingRoom';
import GeometryType from 'ol/geom/GeometryType';
import { Style, Icon } from 'ol/style';
import { EntityType, hasGeoData } from 'app/utils/domain.constants';
import ISchoolElement from 'app/domain/school.element';
import { Feature } from 'ol';
import Geometry from 'ol/geom/Geometry';
import { fromLonLat } from 'ol/proj';
import Point from 'ol/geom/Point';
import { IMapComponent } from '@common/components/map/map';
import VectorSource from 'ol/source/Vector';
import RenderEvent from 'ol/render/Event';
import { getVectorContext } from 'ol/render';
import { AnimationColors, RGBColor } from '@common/components/colors';
import IconAnchorUnits from 'ol/style/IconAnchorUnits';
import { publicPath } from 'app/utils/ui.constants';
import { authRoles } from 'app/auth';
import FuseUtils from '@fuse/utils';
import Door from '../../../domain/door';
import { BaseTool } from './baseTool';
import IDeviceAction from '../deviceActions/IDeviceAction';
import LockDown from '../deviceActions/LockDown';
import EndEmergency from '../deviceActions/EndEmergency';
import EmergencyOpen from '../deviceActions/EmergencyOpen';
import OpenDoor from '../deviceActions/OpenDoor';
import { createIconStyle } from '../utils/MapUtils';

export enum DoorState {
    COMMUNICATION,
    HARDWARE,
    MODE,
    REJECTION,
    STATUS,
    INTRUSION,
    BATTERY,
    ALERTS,
}
export function doorState(state: DoorState): string {
    return DoorState[state] as string;
}

// const defaultStyle = getWfsStyle(12, 16, `${publicPath}/assets/images/mapElements/door.svg`, [27, 117, 213, 0.7]);
// const errorStyle = getWfsStyle(12, 16, `${publicPath}/assets/images/mapElements/door.svg`, [245, 76, 76, 0.7]);

const defaultStyle = createIconStyle('door.png', 0.2, 1);
const errorStyle = createIconStyle('door-error.png', 0.2, 1);

export const intrusionIconStyle = new Style({
    image: new Icon({
        anchor: [0.5, 46],
        anchorXUnits: IconAnchorUnits.FRACTION,
        anchorYUnits: IconAnchorUnits.PIXELS,
        src: `${publicPath}/assets/images/mapElements/warning-icon.png`,
        // scale: 0.2,
    }),
    zIndex: 2,
});

const calculateMarkerIcon = (model: Door): Style | null => {
    const intrusion = model?.state?.find(it => it.type === doorState(DoorState.INTRUSION));
    if (intrusion) {
        return null; // intrusionIconStyle;
    }

    return null;
};

const calculateAnimationColor = (model: Door): RGBColor | null => {
    const modelState = model?.state;
    if (modelState) {
        const rejection = modelState.find(
            it => it.type === doorState(DoorState.REJECTION) || it.type === doorState(DoorState.INTRUSION)
        );
        if (rejection) {
            return AnimationColors.Red;
        }

        const status = modelState.find(it => it.type === doorState(DoorState.STATUS));
        if (status && status.value === 'OPENED') {
            return AnimationColors.Green;
        }
    }

    return null;
};

/**
 * Specifies the door element
 */
class DoorTool extends BaseTool {
    icon = MeetingRoom;

    value = GeometryType.POINT;

    entityType = EntityType.DOOR;

    style = defaultStyle;

    getLabel(/* model: ISchoolElement */): string {
        return 'Door';
    }

    fromModel(model: ISchoolElement): Feature<Geometry> | undefined {
        let feature;
        const { geoLocation } = model;

        if (geoLocation) {
            const style = this.calculateStyle && this.calculateStyle(model as Door);
            feature = new Feature<Geometry>();
            feature.setGeometry(new Point(fromLonLat([geoLocation.x, geoLocation.y])));
            feature.setStyle(style);
            feature.setProperties({
                entityType: EntityType.DOOR,
                data: model,
                tool: this,
            });
        }

        return feature;
    }

    updateModelState(
        map: IMapComponent,
        source: VectorSource<any>,
        model: ISchoolElement,
        feature?: Feature<Geometry>,
        ignoreState?: boolean
    ): void {
        if (feature && model.id && hasGeoData(model)) {
            if (!ignoreState) {
                // show animation if needed
                const animationColor = calculateAnimationColor(model as Door);
                if (animationColor) {
                    map.animateFeature(model.id, (f?: Feature<Geometry>, event?: RenderEvent) => {
                        const markerIcon = calculateMarkerIcon(model as Door);
                        const intrusionState = model?.state?.find(it => it.type === doorState(DoorState.INTRUSION));
                        if (event && markerIcon && f && intrusionState) {
                            const vectorContext = getVectorContext(event);

                            const flashGeom = feature?.getGeometry()?.clone();
                            markerIcon.getImage().load();
                            vectorContext.setStyle(markerIcon);
                            if (flashGeom) vectorContext.drawGeometry(flashGeom);
                            map.getMap()?.render();
                        }
                    });
                }
            }
        }
    }

    protected calculateAnimationColor(model: ISchoolElement): RGBColor | null {
        return calculateAnimationColor(model as Door) || AnimationColors.Red;
    }

    private calculateStyle(model: ISchoolElement): Style {
        const modelState = model?.state;
        if (modelState) {
            const intrusion = modelState.find(it => it.type === doorState(DoorState.INTRUSION));
            if (intrusion) {
                return errorStyle;
            }

            return this.style;
        }

        return errorStyle;
    }

    getStateString(model: ISchoolElement): string {
        const modelState = model?.state;
        if (modelState) {
            const status = modelState.find(it => it.type === doorState(DoorState.STATUS));
            if (status?.value) {
                return `${status.value}`;
            }
        }

        return 'UNKNOWN';
    }

    getDeviceActions(model: ISchoolElement, userRoles: string[]): IDeviceAction[] {
        const actions = super.getDeviceActions(model, userRoles);

        if (FuseUtils.hasPermission(authRoles.Analyst, userRoles)) {
            actions.push(new LockDown());
            actions.push(new EmergencyOpen());
            actions.push(new EndEmergency());
            actions.push(new OpenDoor());
        }

        return actions;
    }

    getLayerName(): string {
        return 'DOOR';
    }
}

const door = new DoorTool();
export default door;
