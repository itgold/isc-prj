/* eslint-disable class-methods-use-this */
import Videocam from '@material-ui/icons/Videocam';
import GeometryType from 'ol/geom/GeometryType';
import { Style } from 'ol/style';
import { EntityType } from 'app/utils/domain.constants';
import ISchoolElement from 'app/domain/school.element';
import { Feature } from 'ol';
import Geometry from 'ol/geom/Geometry';
import Point from 'ol/geom/Point';
import { fromLonLat } from 'ol/proj';
import Camera, { cameraState, CameraState, cameraStateType, CameraStateType } from 'app/domain/camera';
import Region from 'app/domain/region';
import { AnimationColors, RGBColor } from '@common/components/colors';
import { BaseTool } from './baseTool';
import CameraWindow from '../../CameraWindow';
import { FloatingWindowProps } from '../FloatingWindow';
import { createIconStyle } from '../utils/MapUtils';

// const defaultStyle = [getWfsStyle(16, 14, `${publicPath}/assets/images/mapElements/camera.svg`, [27, 117, 213, 0.7])];
// const errorStyle = [getWfsStyle(16, 14, `${publicPath}/assets/images/mapElements/camera.svg`, [245, 76, 76, 0.7])];
const defaultStyle = [createIconStyle('camera.png', 0.2, 1)];
const errorStyle = [createIconStyle('camera-error.png', 0.2, 1)];

/**
 * Specifies the securityCam element
 */
class CameraTool extends BaseTool {
    icon = Videocam;

    value = GeometryType.POINT;

    entityType = EntityType.CAMERA;

    style = defaultStyle;

    getLabel(/* model: ISchoolElement */): string {
        return 'Camera';
    }

    fromModel(model: ISchoolElement): Feature<Geometry> | undefined {
        let feature;
        const { geoLocation } = model;

        if (geoLocation) {
            feature = new Feature<Geometry>();
            feature.setGeometry(new Point(fromLonLat([geoLocation.x, geoLocation.y])));
            feature.setStyle(this.calculateMarkerStyle(model as Camera));
            feature.setProperties({
                entityType: EntityType.CAMERA,
                data: model,
            });
        }

        return feature;
    }

    private calculateMarkerStyle = (model: Camera): Style[] => {
        const modelState = model?.state;
        const ConnectionRestored = cameraStateType(CameraStateType.ConnectionRestored);
        const CONNECTION = cameraState(CameraState.CONNECTION);
        if (modelState && modelState?.find(it => it.type === CONNECTION)?.value === ConnectionRestored) {
            return this.style;
        }

        return errorStyle;
    };

    public calculateRegionMarkerStyle = (model: Region): Style[] => {
        // TODO: find all cameras included into the point group and check all their states.
        return this.calculateMarkerStyle({} as Camera);
    };

    getStateString(model: ISchoolElement): string {
        const modelState = model?.state;
        if (modelState) {
            const status = modelState.find(it => it.type === cameraState(CameraState.CONNECTION));
            if (status?.value) {
                return `${status.value}`;
            }
        }

        return 'UNKNOWN';
    }

    getLayerName(): string {
        return 'CAMERA';
    }

    floatingWindow(): React.ComponentType<FloatingWindowProps> {
        return CameraWindow;
    }

    protected calculateAnimationColor(model: ISchoolElement): RGBColor | null {
        const modelState = model?.state;
        if (modelState) {
            const ConnectionRestored = cameraStateType(CameraStateType.ConnectionRestored);
            const CONNECTION = cameraState(CameraState.CONNECTION);
            if (modelState && modelState?.find(it => it.type === CONNECTION)?.value === ConnectionRestored) {
                return AnimationColors.Green;
            }

            return AnimationColors.Red;
        }

        return AnimationColors.Red;
    }
}

const securityCam = new CameraTool();
export default securityCam;
