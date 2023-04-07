import { CompositeNode } from 'app/domain/composite';
import * as Actions from 'app/store/actions';
import DeviceAction from './DeviceAction';

export default class ToggleZone extends DeviceAction {
    name = 'toggleZone';

    label = 'Toggle Zone';

    execute(device: CompositeNode): void {
        if (device?.id) {
            this.dispatch(Actions.toggleZone(device.id));
        } else {
            console.log(`Action ${this.label} is skipped! Invalid device.`, device);
        }
    }
}
