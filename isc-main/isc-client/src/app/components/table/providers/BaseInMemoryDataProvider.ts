/* eslint-disable class-methods-use-this */
import MomentUtils from '@date-io/moment';
import { ITableHeadColumn, Order, FilterConfig, FilterType } from '../IscTableHead';
import { BaseDataProvider } from '../IscTableDataProvider';
import { Filter } from '../IscTableContext';

const momentUtils: MomentUtils = new MomentUtils();

function descendingComparator<T>(a: T, b: T, orderBy: keyof T): number {
    let aVal = a[orderBy];
    if (aVal && (aVal as any).toLowerCase) aVal = (aVal as any).toLowerCase();

    let bVal = b[orderBy];
    if (bVal && (bVal as any).toLowerCase) bVal = (bVal as any).toLowerCase();

    if (bVal < aVal) {
        return -1;
    }
    if (bVal > aVal) {
        return 1;
    }
    return 0;
}

/**
 * Base implementation class for in-memory IscDataProvider
 */
export abstract class BaseInMemoryDataProvider<H extends ITableHeadColumn> extends BaseDataProvider<H> {
    private _snapshot: any[] = [];

    get totalRecords(): number {
        return this._snapshot.length;
    }

    get data(): any[] {
        return this.pagination
            ? this._snapshot.slice(
                  this.pagination.currentPage * this.pagination.rowsPerPage,
                  this.pagination.currentPage * this.pagination.rowsPerPage + this.pagination.rowsPerPage
              )
            : this._snapshot;
    }

    get pureData(): any[] {
        return this._snapshot;
    }

    protected refreshData(): void {
        const order: Order = this.sortOrder && this.sortOrder.direction ? this.sortOrder.direction : 'asc';
        const orderBy = this.sortOrder && this.sortOrder.id ? this.sortOrder.id : null;

        let data = super.rawData();
        if (this.filter) {
            data = this.filterData(data, this.filter);
        }

        if (this.tags) {
            data = this.filterByTags(data, this.tags);
        }

        this._snapshot = orderBy ? this.stableSort(data, this.getComparator(order, orderBy)) : data;

        super.refreshData();
    }

    protected filterData(data: any[], filter: Filter): any[] {
        let filteredData = data;

        Object.keys(filter).forEach(columnId => {
            const column = this.headers.find(header => header.id === columnId);
            const filterValue = filter[columnId]?.toLowerCase();
            if (column && filterValue && filteredData.length) {
                const typedFilter = this.typedFilterValue(column, filterValue);
                const filterConfig = this.resolveFilterConfig(column);

                filteredData = filteredData.filter(row => {
                    const cellValue = this.extractCellValue(row, column);
                    if (cellValue) {
                        if (filterConfig.filter) {
                            return filterConfig.filter(cellValue, filterValue, row);
                        }
                        return this.matchCellValue(column, cellValue, typedFilter);
                    }

                    return false;
                });
            }
        });

        return filteredData;
    }

    protected extractRawValue(row: any, column: H): any {
        return row[column.alias || column.id];
    }

    protected extractCellValue(row: any, column: H): any {
        // simplest case first when the column id represent property in dataset
        let rawValue = this.extractRawValue(row, column);
        if (!rawValue) {
            if (column.formatter) {
                const reactNode: React.ReactNode = column.formatter(row, column, 1, undefined);
                const type = typeof reactNode;
                if (!reactNode) {
                    rawValue = '';
                } else if (type === 'string' || type === 'number') {
                    rawValue = reactNode;
                } else if ((reactNode as any).props) {
                    const { children, entity } = (reactNode as any).props;
                    rawValue = children?.toString() || '';
                } else {
                    rawValue = reactNode.toString();
                }
            } else {
                rawValue = '';
            }
        }

        return rawValue;
    }

    private resolveFilterConfig(column: H): FilterConfig {
        let filterConfig: FilterConfig = {
            filterType: 'string',
        };

        if (column.filter) {
            const type = typeof column.filter;
            const isConfig = type === 'object' && 'filterType' in (column.filter as any);

            filterConfig = isConfig
                ? (column.filter as FilterConfig)
                : {
                      filterType: column.filter as FilterType,
                  };
        }

        return filterConfig;
    }

    protected typedFilterValue(column: H, filterValue: string): any {
        const filterConfig = this.resolveFilterConfig(column);

        let filter: any = `${filterValue}`;
        switch (filterConfig.filterType) {
            case 'number':
                filter = parseInt(filterValue, 10);
                break;
            case 'boolean':
                filter = filterValue === 'true';
                break;
            case 'date':
                if (!column.formatter && filterConfig.format) {
                    filter = momentUtils.parse(filterValue, 'YYYY-MM-DD').toDate();
                }
                break;
            default:
                filter = `${filterValue}`;
        }

        return filter;
    }

    protected matchCellValue(column: H, value: any, filter: any): boolean {
        if (!filter) {
            return false;
        }

        const type = typeof value;
        switch (type) {
            case 'boolean':
            case 'number':
                return filter === value;
            case 'object':
                return `${value}`.toLowerCase().indexOf(filter) >= 0;

            default:
                return value.toLowerCase().indexOf(filter) >= 0;
        }
    }

    protected getComparator<Key extends keyof any>(
        order: Order,
        orderBy: Key
    ): (a: { [key in Key]: number | string | Date }, b: { [key in Key]: number | string | Date }) => number {
        return order === 'desc'
            ? (a, b) => descendingComparator(a, b, orderBy)
            : (a, b) => -descendingComparator(a, b, orderBy);
    }

    protected stableSort<T>(array: T[], comparator: (a: T, b: T) => number) {
        const stabilizedThis = array.map((el, index) => [el, index] as [T, number]);
        stabilizedThis.sort((a, b) => {
            const order = comparator(a[0], b[0]);
            if (order !== 0) return order;
            return a[1] - b[1];
        });
        return stabilizedThis.map(el => el[0]);
    }

    protected filterByTags(data: any[], tags: string[]): any[] {
        console.log('WARNING: Filtering by tags not implemented!', tags);
        return data;
    }
}
