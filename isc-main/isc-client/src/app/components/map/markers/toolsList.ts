import door from './door';
import ITool from './ITool';
import region from './region';
import securityCam from './securityCam';
import drone from './drone';
import radio from './radio';
import point from './point';
import line from './line';
import polygon from './polygon';
import circle from './circle';
import speaker from './speaker';
import utility from './utility';
import safety from './safety';
import compositeCam from './compositeCam';

/**
 * Contains the list of tools to be used to mark the map editor (still very crude idea)
 */
const toolsList: Array<ITool> = [
    door,
    securityCam,
    speaker,
    safety,
    drone,
    radio,
    region,
    point,
    line,
    polygon,
    circle,
    utility,
    compositeCam,
];
/**
 * Return the right tool according the entity type
 * @param entityType The string name which represents the tool and its entity
 * @returns
 */
export function resolveEntityTool(entityType: string | undefined): ITool | undefined {
    return toolsList.find(tool => tool.entityType.toLowerCase() === entityType?.toLowerCase()) as ITool;
}

export default toolsList;
