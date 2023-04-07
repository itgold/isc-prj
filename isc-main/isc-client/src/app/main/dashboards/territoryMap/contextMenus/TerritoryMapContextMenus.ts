import { IContextMenuView } from 'app/main/mapsEditor/contextMenus/ContextMenu';
import { EntityType } from 'app/utils/domain.constants';
import mapEditorContextMenus from 'app/main/mapsEditor/contextMenus/MapEditorContextMenus';
import ContextMenuCamera from './ContextMenuCamera';
import ContextMenuCompositeCamera from './ContextMenuCompositeCamera';

/**
 * Combines the context menu with an mark/entity type used in the TerritoryMap
 */
const contextMenus: Array<IContextMenuView> = [
    ...mapEditorContextMenus.filter(
        menu => menu.entityType !== EntityType.CAMERA && menu.entityType !== EntityType.COMPOSITECAM
    ),
    {
        entityType: EntityType.CAMERA,
        contextMenu: ContextMenuCamera,
        hideTitle: true,
    },
    {
        entityType: EntityType.COMPOSITECAM,
        contextMenu: ContextMenuCompositeCamera,
        hideTitle: true,
    },
];
export default contextMenus;
