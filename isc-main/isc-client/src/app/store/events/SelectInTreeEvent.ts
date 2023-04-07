import { UIBusEvent } from 'app/utils/simpleEventBus';

export default class SelectInTreeEvent implements UIBusEvent {
    _nodeId?: string;

    constructor(nodeId?: string) {
        this._nodeId = nodeId;
    }

    // eslint-disable-next-line class-methods-use-this
    name(): string {
        return 'selectInTree';
    }

    get nodeId(): string | undefined {
        return this._nodeId;
    }
}
