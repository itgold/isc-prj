/* eslint-disable class-methods-use-this */
import { CompositeNode } from 'app/domain/composite';
import getHistory from '@history/GlobalHistory';
import { EditLocation } from '@material-ui/icons';
import DeviceAction from './DeviceAction';

export default class EditMap extends DeviceAction {
    name = 'editMap';

    label = 'Edit Map';

    icon = EditLocation;

    execute(device: CompositeNode): void {
        if (device?.id) {
            // getHistory().push(`/ui/admin/schoolsMapEditor/${device?.id}`, {});
            getHistory().push('/ui/admin/schoolsMapEditor/', {});
        }
    }
}
