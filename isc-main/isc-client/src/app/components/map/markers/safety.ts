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
import Safety from '../../../domain/safety';
import { createIconStyle } from '../utils/MapUtils';

const defaultStyle = createIconStyle('safety-general.png', 0.2, 1);

/**
 * Specifies the safety element
 */
class SafetyTool extends BaseTool {
    icon = NearMe;

    value = GeometryType.POINT;

    entityType = EntityType.SAFETY;

    style = defaultStyle;

    getLabel(/* model: ISchoolElement */): string {
        return 'Safety';
    }

    fromModel(model: ISchoolElement): Feature<Geometry> | undefined {
        let feature;
        const { geoLocation } = model;

        if (geoLocation) {
            feature = new Feature<Geometry>();
            feature.setGeometry(new Point(fromLonLat([geoLocation.x, geoLocation.y])));
            feature.setStyle(this.getEntityTypeIcon(model));
            feature.setProperties({
                entityType: EntityType.SAFETY,
                data: model,
            });
        }

        return feature;
    }

    getLayerName(): string {
        return 'SAFETY';
    }

    getEntityTypeIcon(model: ISchoolElement): Style {
        const entity = new Safety(model);

        let imageIconPath;
        switch (entity.type) {
            case 'FIRE_ALARM':
                imageIconPath = 'safety-fire-alarm.png';
                break;
            case 'DEFIBRILLATOR':
                imageIconPath = 'safety-defibrillator.png';
                break;
            case 'FIRST_AID_KIT':
                imageIconPath = 'safety-first-aid-kit.png';
                break;
            case 'FIRE_EXTINGUISHER':
                imageIconPath = 'safety-fire-extinguisher.png';
                break;
            case 'FIRE_HOSE':
                imageIconPath = 'safety-fire-hose.png';
                break;
            case 'FIRE_HOSE_CABINET':
                imageIconPath = 'safety-fire-hose-cabinet.png';
                break;
            case 'PULL_STATION':
                imageIconPath = 'safety-pull-station.png';
                break;
            case 'SMOKE_DETECTOR':
                imageIconPath = 'safety-smoke-detector.png';
                break;
            default:
                imageIconPath = 'safety-general.png';
        }

        return createIconStyle(imageIconPath, 0.15, 1);
    }
}

const safety = new SafetyTool();
export default safety;
