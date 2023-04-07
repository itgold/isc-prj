import GeometryType from 'ol/geom/GeometryType';
import FiberManualRecord from '@material-ui/icons/FiberManualRecord';
import { EntityType } from 'app/utils/domain.constants';
import { BaseTool } from './baseTool';

/**
 * Specifies the point element
 */
class PointTool extends BaseTool {
    icon = FiberManualRecord;

    value = GeometryType.POINT;

    entityType = EntityType.UNKNOWN;
}

const point = new PointTool();
export default point;
