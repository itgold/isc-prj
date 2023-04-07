import { IEntity } from 'app/domain/entity';

export default class CodeDictionary implements IEntity {
    code?: number;

    shortName?: string = '';

    description?: string = '';

    id?: string; // ignore

    constructor(rec?: CodeDictionary) {
        this.id = rec?.shortName;
        this.code = rec?.code;
        this.shortName = rec?.shortName;
        this.description = rec?.description;
    }
}
