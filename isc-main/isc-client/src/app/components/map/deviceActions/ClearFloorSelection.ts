import RemoveLayersIcon from '@material-ui/icons/LayersClear';
import { CompositeNode } from 'app/domain/composite';
import * as AppActions from 'app/store/actions/app';
import DeviceAction from './DeviceAction';

export default class ClearFloorSelection extends DeviceAction {
    name = 'clearFloorSelection';

    label = 'Clear Floor Selection';

    icon = RemoveLayersIcon;

    execute(device: CompositeNode): void {
        if (device?.id) {
            this.dispatch(AppActions.clearFloorSelection(device?.id));
        }
    }
}
