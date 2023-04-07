import { EntityType } from 'app/utils/domain.constants';
import { GiArrowCursor } from 'react-icons/gi';
import { BaseTool } from './baseTool';

/**
 * Specifies the cursor element which disable the latest
 */
class CursorTool extends BaseTool {
    icon = GiArrowCursor;

    entityType = EntityType.UNKNOWN;
}

const cursor = new CursorTool();
export default cursor;
