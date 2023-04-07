import { DEACTIVATED } from 'app/utils/domain.constants';
import { IEntity } from './entity';

export default class ExternalUser implements IEntity {
    id?: string;

    status: string = DEACTIVATED;

    title = '';

    firstName = '';

    lastName = '';

    phoneNumber = '';

    externalId = '';

    schoolSite = '';

    officialJobTitle = '';

    idFullName = '';

    idNumber = '';

    officeClass = '';

    updated?: Date;

    source = '';

    constructor(rec?: any) {
        this.id = rec?.id;
        this.status = rec?.status;

        this.title = rec?.title;
        this.firstName = rec?.firstName;
        this.lastName = rec?.lastName;
        this.phoneNumber = rec?.phoneNumber;
        this.externalId = rec?.externalId;
        this.schoolSite = rec?.schoolSite;
        this.officialJobTitle = rec?.officialJobTitle;
        this.idFullName = rec?.idFullName;
        this.idNumber = rec?.idNumber;
        this.officeClass = rec?.officeClass;
        this.updated = rec?.updated;
        this.source = rec?.source;
    }
}
