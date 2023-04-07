import GeometryType from 'ol/geom/GeometryType';
import RadioButtonUnchecked from '@material-ui/icons/RadioButtonUnchecked';
import { EntityType } from 'app/utils/domain.constants';
import { BaseTool } from './baseTool';

/**
 * Specifies the circle element
 */
class CircleTool extends BaseTool {
    icon = RadioButtonUnchecked;

    value = GeometryType.CIRCLE;

    entityType = EntityType.UNKNOWN;
}

const circle = new CircleTool();
export default circle;
