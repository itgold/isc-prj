import { ITableHeadColumn } from 'app/components/table/IscTableHead';
import { BaseInMemoryDataProvider } from 'app/components/table/providers/BaseInMemoryDataProvider';
import { IscTableContext } from 'app/components/table/IscTableContext';
import { OnContextChanged } from 'app/components/table/IscTableDataProvider';

const randomString = (length: number): string => {
    return Math.random().toString(20).substr(2, length);
};

const randomNumber = (min: number, max: number): number => {
    return Math.random() * (max - min) + min;
};

const randomDateRange = (start: Date, end: Date): Date => {
    return new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
};

const randomDate = (): Date => {
    return randomDateRange(new Date(2012, 0, 1), new Date());
};

const deviceTypes = ['Door', 'Camera', 'Drone', 'Gate'];
const randomDeviceType = (): string => {
    const idx = Math.floor(randomNumber(0, deviceTypes.length));
    return deviceTypes[idx];
};

const deviceStatus = ['Online', 'Offline', 'Initializing', 'Unknown'];
const randomDeviceStatus = (): string => {
    const idx = Math.floor(randomNumber(0, deviceStatus.length));
    return deviceStatus[idx];
};

export class DummyDataProvider extends BaseInMemoryDataProvider<ITableHeadColumn> {
    constructor(
        initialContext: IscTableContext,
        contextChangeListener: OnContextChanged,
        columns: ITableHeadColumn[],
        private numberOfRows: number
    ) {
        super(columns, initialContext, contextChangeListener);

        columns.forEach(header => this.headers.push(header));
        this.generateData();
    }

    private generateData(): void {
        const data: any[] = [];
        for (let i = 0; i < this.numberOfRows; i += 1) {
            data.push(this.generateRow(i));
        }

        super.updateData(data);
    }

    private generateRow(index: number): any {
        const data: any = {};
        this.headers.forEach((header: ITableHeadColumn) => {
            let propertyName = header.id;
            let value: any;
            switch (header.id) {
                case 'type':
                    value = randomDeviceType();
                    break;
                case 'status':
                    value = randomDeviceStatus();
                    break;
                case 'updated':
                    value = randomDate();
                    propertyName = 'someDate';
                    break;
                default:
                    value = randomString(randomNumber(3, 20));
            }

            data[propertyName] = value;
        });

        data.id = index;
        return data;
    }
}
