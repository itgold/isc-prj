/*
 * Helper functions for array comparison
 */
const containsAll = (arr1: any[], arr2: any[]) => arr2.every(arr2Item => arr1.includes(arr2Item));
const sameMembers = (arr1: any[], arr2: any[]) => containsAll(arr1, arr2) && containsAll(arr2, arr1);

/**
 * Compare if two arrays have the same values
 * @param arr1 Array 1
 * @param arr2 Array 2
 */
export function isArraysEquials(arr1: any[], arr2: any[]): boolean {
    return sameMembers(arr1, arr2);
}

export const EMPTY_FUNCTION = (): void => {};
