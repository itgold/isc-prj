/* eslint-disable @typescript-eslint/no-explicit-any */
/* eslint-disable no-prototype-builtins */
/* eslint-disable no-restricted-syntax */

import _ from 'lodash';

/**
 * Example of usage:
 * ObjectUtils.diff(obj1, obj2)
 */

export interface ObjectComparison {
    added: any;
    updated: {
        [propName: string]: Change;
    };
    removed: any;
    unchanged: any;
}

export interface Change {
    oldValue: any;
    newValue: any;
}

export class ObjectUtils {
    static diff(o1: any, o2: any, deep = false): ObjectComparison {
        const added: any = {};
        const updated: any = {};
        const removed: any = {};
        const unchanged: any = {};
        for (const prop in o1) {
            if (o1.hasOwnProperty(prop)) {
                const o2PropValue = o2[prop];
                const o1PropValue = o1[prop];
                if (o2.hasOwnProperty(prop)) {
                    // if (o2PropValue === o1PropValue) {
                    if (_.isEqual(o2PropValue, o1PropValue)) {
                        unchanged[prop] = o1PropValue;
                    } else {
                        updated[prop] =
                            deep && this.isObject(o1PropValue) && this.isObject(o2PropValue)
                                ? this.diff(o1PropValue, o2PropValue, deep)
                                : { newValue: o2PropValue };
                    }
                } else {
                    removed[prop] = o1PropValue;
                }
            }
        }
        for (const prop in o2) {
            if (o2.hasOwnProperty(prop)) {
                const o1PropValue = o1[prop];
                const o2PropValue = o2[prop];
                if (o1.hasOwnProperty(prop)) {
                    // if (o1PropValue !== o2PropValue) {
                    if (!_.isEqual(o1PropValue, o2PropValue)) {
                        if (!deep || !this.isObject(o1PropValue)) {
                            updated[prop].oldValue = o1PropValue;
                            console.log('!!! Changed: ', o1PropValue, o2PropValue);
                        }
                    }
                } else {
                    added[prop] = o2PropValue;
                }
            }
        }
        return { added, updated, removed, unchanged };
    }

    /**
     * @return if obj is an Object, including an Array.
     */
    static isObject(obj: any): boolean {
        return obj !== null && typeof obj === 'object';
    }
}
