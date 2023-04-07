import { IEntity } from './entity';

export default class Role implements IEntity {
    id?: string;

    name = '';

    constructor(rec?: any) {
        this.id = rec?.id;
        this.name = rec?.name;
    }
}
