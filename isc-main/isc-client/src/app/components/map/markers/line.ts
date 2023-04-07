import GeometryType from 'ol/geom/GeometryType';
import ShowChart from '@material-ui/icons/ShowChart';
import { EntityType } from 'app/utils/domain.constants';
import { BaseTool } from './baseTool';

/**
 * Specifies the line element
 */
class LineTool extends BaseTool {
    icon = ShowChart;

    value = GeometryType.LINE_STRING;

    entityType = EntityType.UNKNOWN;
}

const line = new LineTool();
export default line;
