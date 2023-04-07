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
import { DeviceState } from 'app/store/appState';
import { CompositeNode } from 'app/domain/composite';
import { AnimationColors, RGBColor } from '@common/components/colors';
import { BaseTool } from './baseTool';
import CameraWindow from '../../CameraWindow';
import { FloatingWindowProps } from '../FloatingWindow';
import { createIconStyle, regionChildren } from '../utils/MapUtils';

// const defaultStyle = [getWfsStyle(14, 14, `${publicPath}/assets/images/mapElements/cameras.svg`, [27, 117, 213, 0.7])];
// const errorStyle = [getWfsStyle(14, 14, `${publicPath}/assets/images/mapElements/cameras.svg`, [245, 76, 76, 0.7])];
const defaultStyle = [createIconStyle('cameras.png', 0.2, 0.7)];
const errorStyle = [createIconStyle('cameras-error.png', 0.2, 1)];

/**
 * Specifies the securityCam element
 */
class CompositeCameraTool extends BaseTool {
    icon = Videocam;

    value = GeometryType.POINT;

    entityType = EntityType.COMPOSITECAM;

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
            feature.setStyle(this.calculateMarkerStyle(model as Region));
            feature.setProperties({
                entityType: EntityType.COMPOSITECAM,
                data: model,
            });
        }

        return feature;
    }

    private calculateMarkerStyle = (model: Region): Style[] => {
        const modelState: DeviceState = (model as any).state || [];
        if (!modelState.length) {
            const cameras = regionChildren(model?.id || '').filter(
                device => CompositeNode.entityType(device) === EntityType.CAMERA
            );
            cameras?.forEach(camera => {
                camera?.state?.forEach(state => modelState.push(state));
            });
        }

        const ConnectionRestored = cameraStateType(CameraStateType.ConnectionRestored);
        const CONNECTION = cameraState(CameraState.CONNECTION);
        if (modelState && modelState?.find(it => it.type === CONNECTION)?.value === ConnectionRestored) {
            return this.style;
        }

        return errorStyle;
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
        return EntityType.CAMERA;
    }

    floatingWindow(): React.ComponentType<FloatingWindowProps> {
        return CameraWindow;
    }

    protected calculateAnimationColor(model: ISchoolElement): RGBColor | null {
        const modelState: DeviceState = (model as any).state || [];
        if (!modelState.length) {
            const cameras = regionChildren(model?.id || '').filter(
                device => CompositeNode.entityType(device) === EntityType.CAMERA
            );
            cameras?.forEach(camera => {
                camera?.state?.forEach(state => modelState.push(state));
            });
        }

        const ConnectionRestored = cameraStateType(CameraStateType.ConnectionRestored);
        const CONNECTION = cameraState(CameraState.CONNECTION);
        if (modelState && modelState?.find(it => it.type === CONNECTION)?.value === ConnectionRestored) {
            return AnimationColors.Green;
        }

        return AnimationColors.Red;
    }
}

const compositeCam = new CompositeCameraTool();
export default compositeCam;
