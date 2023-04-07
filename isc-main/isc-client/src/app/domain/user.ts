import { DEACTIVATED } from 'app/utils/domain.constants';
import { IEntity } from './entity';
import Role from './role';

export default class User implements IEntity {
    id?: string;

    name = '';

    status: string = DEACTIVATED;

    email = '';

    password = '';

    trialUser = false;

    imageUrl?: string;

    roles?: Role[];

    constructor(rec?: any) {
        this.id = rec?.id;
        this.name = rec?.name;
        this.status = rec?.status;

        this.email = rec?.email;
        this.password = rec?.password;
        this.trialUser = rec?.trialUser;
        this.imageUrl = rec?.imageUrl;
        this.roles = rec?.roles;
    }
}
