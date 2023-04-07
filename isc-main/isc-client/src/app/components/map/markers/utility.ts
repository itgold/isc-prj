/* eslint-disable class-methods-use-this */
import GeometryType from 'ol/geom/GeometryType';
import NearMe from '@material-ui/icons/NearMe';
import { Style } from 'ol/style';
import { EntityType } from 'app/utils/domain.constants';
import ISchoolElement from 'app/domain/school.element';
import { Feature } from 'ol';
import Geometry from 'ol/geom/Geometry';
import Point from 'ol/geom/Point';
import { fromLonLat } from 'ol/proj';
import { BaseTool } from './baseTool';
import Utility from '../../../domain/utility';
import { createIconStyle } from '../utils/MapUtils';

const defaultStyle = createIconStyle('utility-general.png', 0.2, 1);

/**
 * Specifies the speaker element
 */
class UtilityTool extends BaseTool {
    icon = NearMe;

    value = GeometryType.POINT;

    entityType = EntityType.UTILITY;

    style = defaultStyle;

    getLabel(/* model: ISchoolElement */): string {
        return 'Utility';
    }

    fromModel(model: ISchoolElement): Feature<Geometry> | undefined {
        let feature;
        const { geoLocation } = model;

        if (geoLocation) {
            feature = new Feature<Geometry>();
            feature.setGeometry(new Point(fromLonLat([geoLocation.x, geoLocation.y])));
            feature.setStyle(this.getEntityTypeIcon(model));
            feature.setProperties({
                entityType: EntityType.UTILITY,
                data: model,
            });
        }

        return feature;
    }

    getLayerName(): string {
        return 'UTILITY';
    }

    getEntityTypeIcon(model: ISchoolElement): Style {
        const entity = new Utility(model);

        let imageIconPath;
        switch (entity.type) {
            case 'VENTILATION_SHUTOFF':
                imageIconPath = 'utility-ventilation-shutoff.png';
                break;
            case 'ELECTRICAL_SHUTOFF':
                imageIconPath = 'utility-electrical-shutoff.png';
                break;
            case 'GAS_SHUTOFF':
                imageIconPath = 'utility-gas-shutoff.png';
                break;
            case 'WATER_SHUTOFF':
                imageIconPath = 'utility-water-shutoff.png';
                break;
            case 'FIRE_ALARM_PANEL':
                imageIconPath = 'utility-fire-alarm-panel.png';
                break;
            case 'STAND_PIPE':
                imageIconPath = 'utility-stand-pipe.png';
                break;
            case 'STAND_PIPE_DRY':
                imageIconPath = 'utility-stand-pipe-dry.png';
                break;
            default:
                imageIconPath = 'utility-general.png';
        }

        return createIconStyle(imageIconPath, 0.15, 1);
    }
}

const utility = new UtilityTool();
export default utility;
