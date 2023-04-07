/* eslint-disable class-methods-use-this */
import { BaseInMemoryDataProvider } from 'app/components/table/providers/BaseInMemoryDataProvider';
import { ITableHeadColumn } from 'app/components/table/IscTableHead';
import { IscTableContext } from 'app/components/table/IscTableContext';
import { OnContextChanged } from 'app/components/table/IscTableDataProvider';
import ISchoolElement from 'app/domain/school.element';

export class SchoolDataProvider extends BaseInMemoryDataProvider<ITableHeadColumn> {
    constructor(
        entities: ISchoolElement[],
        headers: ITableHeadColumn[],
        initialContext: IscTableContext,
        contextChangeListener: OnContextChanged
    ) {
        super(headers, initialContext, contextChangeListener);

        super.updateData(entities);
    }

    protected filterByTags(data: any[], tags: string[]): any[] {
        if (!tags.length) {
            return data;
        }

        return data.filter((row: any) => {
            const rowTags = row.tags as any[];
            if (rowTags && rowTags.length) {
                const tagNames: string[] = rowTags.map(tag => tag.name || tag);
                return tags.reduce((acc: boolean, tag: string) => {
                    return acc || tagNames.indexOf(tag) >= 0;
                }, false);
            }

            return false;
        });
    }

    protected extractRawValue(row: any, column: ITableHeadColumn): any {
        const columnName = column.alias || column.id;
        let rawValue = row[columnName];
        if (columnName === 'name' && row.id) {
            rawValue += ` ${row.id}`;
        }

        return rawValue;
    }
}
