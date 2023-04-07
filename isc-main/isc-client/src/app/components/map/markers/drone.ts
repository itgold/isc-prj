/* eslint-disable class-methods-use-this */
import GeometryType from 'ol/geom/GeometryType';
import NearMe from '@material-ui/icons/NearMe';
import { EntityType } from 'app/utils/domain.constants';
import ISchoolElement from 'app/domain/school.element';
import { Feature } from 'ol';
import Geometry from 'ol/geom/Geometry';
import Point from 'ol/geom/Point';
import { fromLonLat } from 'ol/proj';
import { BaseTool } from './baseTool';
import { createIconStyle } from '../utils/MapUtils';

// const defaultStyle = [getWfsStyle(18, 18, `${publicPath}/assets/images/mapElements/drone.svg`, [58, 58, 183, 1])];
const defaultStyle = [createIconStyle('drone.png', 0.2, 1)];

/**
 * Specifies the drone element
 */
class DroneTool extends BaseTool {
    icon = NearMe;

    value = GeometryType.POINT;

    entityType = EntityType.DRONE;

    style = defaultStyle;

    getLabel(/* model: ISchoolElement */): string {
        return 'Drone';
    }

    fromModel(model: ISchoolElement): Feature<Geometry> | undefined {
        let feature: Feature<Geometry> | undefined;
        const { geoLocation } = model;

        if (geoLocation) {
            feature = new Feature<Geometry>();
            feature.setGeometry(new Point(fromLonLat([geoLocation.x, geoLocation.y])));
            feature.setStyle(this.style);
            feature.setProperties({
                entityType: EntityType.DRONE,
                data: model,
            });
        }

        return feature;
    }

    getLayerName(): string {
        return EntityType.DRONE;
    }
}

const drone = new DroneTool();
export default drone;
