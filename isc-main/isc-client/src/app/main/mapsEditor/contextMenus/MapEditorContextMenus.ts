import { EntityType } from 'app/utils/domain.constants';
import { IContextMenuView } from './ContextMenu';
import ContextMenuCamera from './ContextMenuCamera';
import ContextMenuDoor from './ContextMenuDoor';
import ContextMenuDrone from './ContextMenuDrone';
import ContextMenuRegion from './ContextMenuRegion';
import ContextMenuSpeaker from './ContextMenuSpeaker';
import ContextMenuUtility from './ContextMenuUtility';
import ContextMenuSafety from './ContextMenuSafety';
import ContextMenuRadio from './ContextMenuRadio';

/**
 * Combines the context menu with an mark/entity type used in the MapEditor
 */
const mapEditorContextMenus: Array<IContextMenuView> = [
    {
        entityType: EntityType.DOOR,
        contextMenu: ContextMenuDoor,
    },
    {
        entityType: EntityType.DRONE,
        contextMenu: ContextMenuDrone,
    },
    {
        entityType: EntityType.REGION,
        contextMenu: ContextMenuRegion,
    },
    {
        entityType: EntityType.SPEAKER,
        contextMenu: ContextMenuSpeaker,
    },
    {
        entityType: EntityType.CAMERA,
        contextMenu: ContextMenuCamera,
    },
    {
        entityType: EntityType.UTILITY,
        contextMenu: ContextMenuUtility,
    },
    {
        entityType: EntityType.SAFETY,
        contextMenu: ContextMenuSafety,
    },
    {
        entityType: EntityType.RADIO,
        contextMenu: ContextMenuRadio,
    },
];
export default mapEditorContextMenus;
