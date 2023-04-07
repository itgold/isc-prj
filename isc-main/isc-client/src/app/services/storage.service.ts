/* eslint-disable class-methods-use-this */

export const STORAGE_SELECTED_SCHOOL = 'selectedSchool';

class StorageService {
    updateProperty(key: string, value: string | unknown): void {
        if (value) {
            localStorage.setItem(key, value as string);
        } else {
            localStorage.removeItem(key);
        }
    }

    readIntProperty(key: string, defaultValue = -1): number {
        const value = localStorage.getItem(key);
        return value ? parseInt(value, 10) : defaultValue;
    }

    readBooleanProperty(key: string, defaultValue = false): boolean {
        const value = localStorage.getItem(key);
        return value ? JSON.parse(value) : defaultValue;
    }

    readProperty(key: string, defaultValue: string | undefined = undefined): string | undefined {
        const value = localStorage.getItem(key);
        return value || defaultValue;
    }
}

const storageService = new StorageService();
export default storageService;
