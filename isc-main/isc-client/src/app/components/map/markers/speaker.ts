/* eslint-disable class-methods-use-this */
import GeometryType from 'ol/geom/GeometryType';
import NearMe from '@material-ui/icons/NearMe';
import { EntityType } from 'app/utils/domain.constants';
import ISchoolElement from 'app/domain/school.element';
import { Feature } from 'ol';
import Geometry from 'ol/geom/Geometry';
import Point from 'ol/geom/Point';
import { fromLonLat } from 'ol/proj';
import { RGBColor } from '@common/components/colors';
import { BaseTool } from './baseTool';
import { createIconStyle } from '../utils/MapUtils';

const SpeakerColors = {
    Good: {
        r: 25,
        g: 93,
        b: 25,
    },
    Bad: {
        r: 255,
        g: 0,
        b: 0,
    },
};

/*
const defaultStyle = [
    getWfsStyle(16, 16, `${publicPath}/assets/images/mapElements/speaker.svg`, [
        SpeakerColors.Good.r,
        SpeakerColors.Good.g,
        SpeakerColors.Good.b,
        0.7,
    ]),
];
*/
const defaultStyle = [createIconStyle('speaker.png', 0.15, 1)];

/**
 * Specifies the speaker element
 */
class SpeakerTool extends BaseTool {
    icon = NearMe;

    value = GeometryType.POINT;

    entityType = EntityType.SPEAKER;

    style = defaultStyle;

    getLabel(/* model: ISchoolElement */): string {
        return 'Speaker';
    }

    fromModel(model: ISchoolElement): Feature<Geometry> | undefined {
        let feature;
        const { geoLocation } = model;

        if (geoLocation) {
            feature = new Feature<Geometry>();
            feature.setGeometry(new Point(fromLonLat([geoLocation.x, geoLocation.y])));
            feature.setStyle(this.style);
            feature.setProperties({
                entityType: EntityType.SPEAKER,
                data: model,
            });
        }

        return feature;
    }

    getLayerName(): string {
        return 'SPEAKER';
    }

    protected calculateAnimationColor(model: ISchoolElement): RGBColor | null {
        return SpeakerColors.Good;
    }
}

const speaker = new SpeakerTool();
export default speaker;
