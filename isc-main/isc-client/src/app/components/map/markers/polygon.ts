import GeometryType from 'ol/geom/GeometryType';
import Details from '@material-ui/icons/Details';
import { EntityType } from 'app/utils/domain.constants';
import { BaseTool } from './baseTool';

/**
 * Specifies the polygon element
 */
class PolygonTool extends BaseTool {
    icon = Details;

    value = GeometryType.POLYGON;

    entityType = EntityType.UNKNOWN;
}

const polygon = new PolygonTool();
export default polygon;
