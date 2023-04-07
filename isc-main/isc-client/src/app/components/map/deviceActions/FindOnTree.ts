import { CompositeNode } from 'app/domain/composite';
import SelectInTreeEvent from 'app/store/events/SelectInTreeEvent';
import EventBus from 'app/utils/simpleEventBus';
import { CgListTree } from 'react-icons/cg';
import { PanelLocation } from '../SchoolMapSplitPanel';
import DeviceAction from './DeviceAction';

export default class FindOnTree extends DeviceAction {
    name = 'findOnTree';

    label = 'Find on Tree';

    icon = CgListTree;

    execute(device: CompositeNode): void {
        if (device?.id) {
            EventBus.post(new SelectInTreeEvent(device.id));
        } else {
            console.log(`Action ${this.label} is skipped! Invalid device.`, device);
        }
    }
}
