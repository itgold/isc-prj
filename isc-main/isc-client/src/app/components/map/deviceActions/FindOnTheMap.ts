/* eslint-disable class-methods-use-this */
import { FaEye } from 'react-icons/fa';
import { CompositeNode } from 'app/domain/composite';
import DeviceAction from './DeviceAction';
import { MapControl } from './IDeviceAction';

export default class FindOnTheMap extends DeviceAction {
    name = 'highlight';

    label = 'Find On Map';

    icon = FaEye;

    execute(device: CompositeNode, map?: MapControl): void {
        if (device?.id) {
            map?.highlight(device);
        }
    }
}
