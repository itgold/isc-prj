/* eslint-disable class-methods-use-this */
import GeometryType from 'ol/geom/GeometryType';
import Man from '@material-ui/icons/DirectionsWalk';
import { EntityType } from 'app/utils/domain.constants';
import ISchoolElement from 'app/domain/school.element';
import { Feature } from 'ol';
import Geometry from 'ol/geom/Geometry';
import Point from 'ol/geom/Point';
import { fromLonLat } from 'ol/proj';
import { BaseTool } from './baseTool';
import { createIconStyle } from '../utils/MapUtils';

export const defaultStyle = createIconStyle('radio.png', 0.2, 1);

/**
 * Specifies the radio element
 */
class RadioTool extends BaseTool {
    icon = Man;

    value = GeometryType.POINT;

    entityType = EntityType.RADIO;

    style = defaultStyle;

    getLabel(/* model: ISchoolElement */): string {
        return 'Radio';
    }

    fromModel(model: ISchoolElement): Feature<Geometry> | undefined {
        let feature: Feature<Geometry> | undefined;
        const { geoLocation } = model;

        if (geoLocation) {
            feature = new Feature<Geometry>();
            feature.setGeometry(new Point(fromLonLat([geoLocation.x, geoLocation.y])));
            feature.setStyle(this.style);
            feature.setProperties({
                entityType: EntityType.RADIO,
                data: model,
            });
        }

        return feature;
    }

    getLayerName(): string {
        return EntityType.RADIO;
    }
}

const radio = new RadioTool();
export default radio;
