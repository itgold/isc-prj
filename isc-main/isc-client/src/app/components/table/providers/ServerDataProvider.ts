import { SearchResult } from 'app/domain/search';
import { EMPTY_LIST } from 'app/utils/domain.constants';
import { IscTableContext } from '../IscTableContext';
import { BaseDataProvider, OnContextChanged } from '../IscTableDataProvider';
import { ITableHeadColumn } from '../IscTableHead';
import { IEntity } from '../../../domain/entity';

export class ServerDataProvider extends BaseDataProvider<ITableHeadColumn> {
    constructor(
        private serverData: SearchResult,
        headers: ITableHeadColumn[],
        initialContext: IscTableContext,
        contextChangeListener: OnContextChanged
    ) {
        super(headers, initialContext, contextChangeListener);
        super.updateData(serverData?.data || EMPTY_LIST);
    }

    get totalRecords(): number {
        return this.serverData.numberOfItems;
    }

    get data(): IEntity[] {
        return this.serverData.data;
    }

    get pureData(): IEntity[] {
        return this.serverData.data;
    }

    protected refreshData(): void {
        super.refreshData();
    }
}
