import LayersIcon from '@material-ui/icons/Layers';
import { CompositeNode, CompositeNodesMap } from 'app/domain/composite';
import * as AppActions from 'app/store/actions/app';
import DeviceAction from './DeviceAction';

export default class SelectFloor extends DeviceAction {
    name = 'selectFloor';

    label = 'Select Floor';

    icon = LayersIcon;

    execute(device: CompositeNode): void {
        if (device?.id) {
            const buildingWithFloor = this.findFloorAndBuilding(device, device.id);
            if (buildingWithFloor?.building?.id && buildingWithFloor?.floor?.id) {
                const floorPayload: CompositeNodesMap = {};
                floorPayload[buildingWithFloor.building.id] = buildingWithFloor.floor as CompositeNode;

                this.dispatch(AppActions.setFloorSelection(floorPayload));
            }
        }
    }
}
