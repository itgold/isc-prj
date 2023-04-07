/* eslint-disable class-methods-use-this */
import { BiReset } from 'react-icons/bi';
import { CompositeNode } from 'app/domain/composite';
import DeviceAction from './DeviceAction';
import { MapControl } from './IDeviceAction';

export default class ResetSchool extends DeviceAction {
    name = 'resetSchool';

    label = 'Reset Map';

    icon = BiReset;

    execute(device: CompositeNode, map?: MapControl): void {
        if (device?.id) {
            map?.resetMap();
            setTimeout(() => {
                if (device?.id) {
                    map?.highlight(device);
                }
            }, 10);
        }
    }
}
