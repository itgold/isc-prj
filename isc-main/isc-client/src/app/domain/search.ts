import { IEntity } from './entity';

export interface ColumnFilter {
    key: string;
    value: string;
}

export interface QueryFilter {
    tags: string[];
    columns: ColumnFilter[];
}

export const EMPTY_SEARCH_RESULTS = { data: [], numberOfItems: 0, numberOfPages: 0 };

export interface SearchResult<T extends IEntity = IEntity> {
    data: T[];
    numberOfItems: number;
    numberOfPages: number;
}

export interface PageRequest {
    page: number;
    size: number;
}

export interface SortOrder {
    property: string;
    direction: string;
}
