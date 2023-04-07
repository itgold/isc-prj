import { IEntity } from './entity';

export default class Tag implements IEntity {
    id?: string;

    name = '';

    constructor(rec?: any) {
        this.id = rec?.id;
        this.name = rec?.name;
    }
}
