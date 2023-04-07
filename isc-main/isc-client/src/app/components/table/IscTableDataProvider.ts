import { ITableHeadColumn, IColumnOrder } from './IscTableHead';
import { IscTableContext, Filter } from './IscTableContext';

/**
 * Base type defines pagination details
 */
export interface Pagination {
    /**
     * The number of rows per page to be handled by the pagination
     */
    rowsPerPage: number;

    /**
     * Which page should it be set by default?
     */
    currentPage: number;

    /**
     * Should show the Pagination?
     */
    showPagination?: boolean | false;
}

/**
 * Defines IscTable data provider
 */
export interface IscDataProvider {
    context: IscTableContext;
    data: any[];
    totalRecords: number;
    pureData: any[];
}

export type OnContextChanged = (context: IscTableContext) => void;

/**
 * Base implementation class for IscDataProvider
 */
export abstract class BaseDataProvider<H extends ITableHeadColumn> implements IscDataProvider {
    private _pagination: Pagination;

    private _sortOrder: IColumnOrder;

    private _filter: Filter;

    private _headers: H[] = [];

    private _data: any[] = [];

    private _pureData: any[] = [];

    private _tags: string[] = [];

    private contextChangeListener: OnContextChanged;

    constructor(headers: H[], initialContext: IscTableContext, contextChangeListener: OnContextChanged) {
        this._headers = headers;
        this._pagination = initialContext.pagination;
        this._sortOrder = initialContext.sortOrder;
        this._filter = initialContext.filter;
        this._tags = initialContext.tags;
        this.contextChangeListener = contextChangeListener;

        this.refreshData();
    }

    /* Public methods implementing IscDataProvider interface */
    get headers(): H[] {
        return this._headers;
    }

    get context(): IscTableContext {
        return {
            pagination: this._pagination,
            sortOrder: this._sortOrder,
            filter: this._filter,
            tags: this._tags,
        };
    }

    set context(ctx: IscTableContext) {
        this._pagination = ctx.pagination;
        this._sortOrder = ctx.sortOrder;
        this._filter = ctx.filter;
        this._tags = ctx.tags;
        this.refreshData();
    }

    /* Protected methods below are used for inheritance and mean to be use internally only */
    get totalRecords(): number {
        return this._data.length;
    }

    get data(): any[] {
        return this._data;
    }

    get pureData(): any[] {
        return this._pureData;
    }

    protected updateData(data: any[]): void {
        this._data = data;
        this.refreshData();
    }

    protected refreshData(): void {
        this.contextChangeListener(this.context);
    }

    protected get pagination(): Pagination {
        return this._pagination;
    }

    protected get sortOrder(): IColumnOrder {
        return this._sortOrder;
    }

    protected get filter(): Filter {
        return this._filter;
    }

    protected get tags(): string[] {
        return this._tags;
    }

    protected rawData(): any[] {
        return this._data;
    }
}
