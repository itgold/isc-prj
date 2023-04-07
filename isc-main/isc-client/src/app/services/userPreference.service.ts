import DeviceTreeTypePreference from './preferenceTypes/DeviceTreeTypePreference';
import LayerTypePreference from './preferenceTypes/LayerTypePreference';

import AlertsContext from './preferenceTypes/AlertsTypePreference';

type UserPreferenceType = DeviceTreeTypePreference & AlertsContext & LayerTypePreference;

/**
 * Define all context names for the user perspective
 */
export enum UserContexts {
    deviceTree = 'deviceTree',
    layers = 'layers',
    alerts = 'alerts',
}

interface IUserPreferenceService {
    /**
     * Preferences hashmap will be the whole context of the user preference. Each application context is seprated by
     * a distinct key, like the example below:
     */
    // preferences = {
    //     map: {
    //         selectedFloor: 1,
    //         searchField: 'DOOR',
    //     },
    //     system: {
    //         syncMinutes: 5,
    //     },
    // };

    /**
     * Inserts or updates a user preference property in the right scope by passing the context that the property belongs to,
     * property key and property value.
     * @param context Business scope
     * @param property
     * @param value
     * @returns Returns the whole context updated
     */
    // updatePreference(context: string, property: string, value: string | unknown): Promise<IUserPreference>;
    updatePreference(context: string, value: UserPreferenceType): UserPreferenceType;
    /**
     * Get the property value from a specific context/property
     * @param context
     * @param property
     */
    // getProperty(context: string, property: string): Promise<unknown>;
    getProperty(context: string, property: string): unknown;
    /**
     * Returns the whole user preferences OR a specific context of the preferences.
     * @param context
     */
    // loadPreferences(context?: string): Promise<IUserPreference | undefined>;
    loadPreferences(context?: string): UserPreferenceType | undefined;
    /**
     * Delete the whole context of the preferences. In the case the user defines, for example, "map", all the properties for
     * that context will be erased.
     * @param context
     * @returns Returns boolean indicating if the context was deleted or not.
     */
    // deleteContext(context: string): Promise<boolean>;
    deleteContext(context: string): boolean;
    /**
     * Delete the whole user preferences.
     * @returns Returns boolean indicating if the preferences were deleted or not.
     */
    // deletePreference(): Promise<boolean>;
    deletePreference(): boolean;
}

class UserPreferenceService implements IUserPreferenceService {
    // eslint-disable-next-line class-methods-use-this
    // private async getDataContext(context: string): Promise<unknown> {
    //     return new Promise(resolve => {
    //         const contextData = localStorage.getItem(context);
    //         resolve(contextData ? JSON.parse(contextData) : {});
    //     });
    // }

    // eslint-disable-next-line class-methods-use-this
    private getDataContext(context: string): UserPreferenceType {
        const contextData = localStorage.getItem(context);
        return contextData ? JSON.parse(contextData) : {};
    }

    // eslint-disable-next-line class-methods-use-this
    // async getProperty(context: string, property: string): Promise<unknown> {
    //     return new Promise((resolve, reject) => {
    //         const contextData = localStorage.getItem(context);
    //         if (contextData) {
    //             const dataObj = JSON.parse(contextData);
    //             if (dataObj[property]) resolve(dataObj[property]);
    //             else reject(new Error(`The property ${property} wasn't found.`));
    //         } else {
    //             reject(new Error(`There's no context: ${context}.`));
    //         }
    //     });
    // }

    // eslint-disable-next-line class-methods-use-this
    getProperty(context: string, property: string): unknown {
        const contextData = localStorage.getItem(context);
        if (contextData) {
            const dataObj = JSON.parse(contextData);
            if (dataObj[property]) return dataObj[property];
            return undefined;
        }
        return undefined;
    }

    // async deleteContext(context: string): Promise<boolean> {
    //     // eslint-disable-next-line no-async-promise-executor
    //     return new Promise(async resolve => {
    //         const dataContext = await Promise.resolve(this.getDataContext(context));
    //         if (dataContext) {
    //             localStorage.removeItem(context);
    //             resolve(true);
    //         } else resolve(false);
    //     });
    // }

    deleteContext(context: string): boolean {
        // eslint-disable-next-line no-async-promise-executor
        const dataContext = this.getDataContext(context);
        if (dataContext) {
            localStorage.removeItem(context);
            return true;
        }
        return false;
    }

    // eslint-disable-next-line class-methods-use-this
    // async deletePreference(): Promise<boolean> {
    //     // TODO: To be implemented in the BE in order to clear all settings
    //     return new Promise(resolve => {
    //         resolve(false);
    //     });
    // }
    // eslint-disable-next-line class-methods-use-this
    deletePreference(): boolean {
        // TODO: To be implemented in the BE in order to clear all settings
        return false;
    }

    // eslint-disable-next-line class-methods-use-this
    // async loadPreferences(context?: string): Promise<IUserPreference | undefined> {
    //     // eslint-disable-next-line no-async-promise-executor
    //     return new Promise(async resolve => {
    //         if (context) {
    //             const dataContext = await Promise.resolve(this.getDataContext(context));
    //             resolve(dataContext as IUserPreference);
    //         }
    //         // TODO: TBD all context names but this should be returned from the endpoint
    //         resolve(undefined);
    //     });
    // }
    loadPreferences(context?: string): UserPreferenceType {
        // eslint-disable-next-line no-async-promise-executor
        if (context) {
            const dataContext = this.getDataContext(context);
            return dataContext as UserPreferenceType;
        }
        // TODO: TBD all context names but this should be returned from the endpoint
        return {} as UserPreferenceType;
    }

    // async updatePreference(context: string, property: string, value: unknown): Promise<IUserPreference> {
    //     // eslint-disable-next-line no-async-promise-executor
    //     return new Promise(async resolve => {
    //         const dataContext = await Promise.resolve(this.getDataContext(context));
    //         (dataContext as IUserPreference)[property] = value;
    //         localStorage.setItem(context, JSON.stringify(dataContext));
    //         resolve(dataContext as IUserPreference);
    //     });
    // }
    updatePreference(context: string, value: UserPreferenceType): UserPreferenceType {
        let dataContext = this.getDataContext(context);
        dataContext = {
            ...dataContext,
            ...value,
        };
        localStorage.setItem(context, JSON.stringify(dataContext));
        return dataContext as UserPreferenceType;
    }
}

const userPreferencesService = new UserPreferenceService();
export default userPreferencesService;
