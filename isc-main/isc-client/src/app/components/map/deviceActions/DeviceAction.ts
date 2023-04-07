import { CompositeNode, CompositeNodesMap, CompositeType } from 'app/domain/composite';
import { RegionType } from 'app/utils/domain.constants';
import { Action } from 'redux';
import store from 'app/store';
import * as Selectors from 'app/store/selectors';
import Region from 'app/domain/region';
import IDeviceAction, { MapControl } from './IDeviceAction';

export default class DeviceAction implements IDeviceAction {
    name = '';

    label = '';

    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    // eslint-disable-next-line class-methods-use-this
    execute(device: CompositeNode, map?: MapControl): void {}

    // eslint-disable-next-line class-methods-use-this
    protected dispatch(payload: Action): void {
        store.dispatch(payload);
    }

    protected findFloorAndBuilding(
        device: CompositeNode,
        floorId: string
    ): { building: CompositeNode | undefined; floor: CompositeNode | undefined } | undefined {
        let rez;

        let floor;
        let building;
        const allElements = Selectors.allRegionsSelector(store.getState());

        if (device.id === floorId) {
            floor = device;
        } else if (
            device.compositeType === CompositeType.CONTAINER &&
            (device as Region).type === RegionType.BUILDING
        ) {
            building = device;
        }

        if (!floor) {
            floor = this.findParentElement(device, RegionType.FLOOR, allElements);
        }
        if (!building) {
            building = this.findParentElement(floor || device, RegionType.BUILDING, allElements);
        }

        if (floor || building) {
            rez = { floor, building };
        }

        return rez;
    }

    private findParentElement(
        node: CompositeNode,
        parentType: RegionType,
        elementsCache: CompositeNodesMap
    ): CompositeNode | undefined {
        if (node.parentIds?.length) {
            const parentToCheck = [];
            for (let idx = 0; idx < node.parentIds.length; idx += 1) {
                const parent = elementsCache[node.parentIds[idx]];
                if (parent) {
                    if ((parent as Region).type === parentType) {
                        return parent;
                    }
                    parentToCheck.push(parent);
                }
            }

            for (let idx = 0; idx < parentToCheck.length; idx += 1) {
                const parent = this.findParentElement(parentToCheck[idx], parentType, elementsCache);
                if (parent) {
                    return parent;
                }
            }
        }
        return undefined;
    }
}
