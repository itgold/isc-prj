/* eslint-disable @typescript-eslint/no-explicit-any */

export function extend(...args: any[]): any {
    // Variables
    const extended: any = {};
    let deep = false;
    let i = 0;
    const { length } = args;

    // Check if a deep merge
    const firstArgument = args[0];
    if (Object.prototype.toString.call(firstArgument) === '[object Boolean]') {
        deep = firstArgument;
        i += 1;
    }

    // Merge the object into the extended object
    const merge = (obj: any): any => {
        Object.keys(obj).forEach(prop => {
            // If deep merge and property is an object, merge properties
            if (deep && Object.prototype.toString.call(obj[prop]) === '[object Object]') {
                extended[prop] = extend(true, extended[prop], obj[prop]);
            } else {
                extended[prop] = obj[prop];
            }
        });

        // for (const prop in obj) {
        //     if (Object.prototype.hasOwnProperty.call(obj, prop)) {
        //         // If deep merge and property is an object, merge properties
        //         if (deep && Object.prototype.toString.call(obj[prop]) === '[object Object]') {
        //             extended[prop] = extend(true, extended[prop], obj[prop]);
        //         } else {
        //             extended[prop] = obj[prop];
        //         }
        //     }
        // }
    };

    // Loop through each object and conduct a merge
    for (; i < length; i += 1) {
        merge(args[i]);
    }

    return extended;
}

export const cloneDeep = (obj: any, blackList: string[]): any => {
    const keys = Object.keys(obj);
    const filteredKeys = keys.filter(key => !blackList.includes(key));

    return filteredKeys.reduce((result: any, key: string) => {
        const value = obj[key];
        result[key] = typeof value === 'object' && value !== null ? cloneDeep(value, blackList) : value;
        return result;
    }, {});
};

// Copies a string to the clipboard. Must be called from within an
// event handler such as click. May return false if it failed, but
// this is not always possible. Browser support for Chrome 43+,
// Firefox 42+, Safari 10+, Edge and Internet Explorer 10+.
// Internet Explorer: The clipboard feature may be disabled by
// an administrator. By default a prompt is shown the first
// time the clipboard is used (per session).
export function copyToClipboard(textToCopy: string): Promise<void> {
    // navigator clipboard api needs a secure context (https)
    if (navigator.clipboard && window.isSecureContext) {
        // navigator clipboard api method'
        return navigator.clipboard.writeText(textToCopy);
    }
    // text area method
    const textArea = document.createElement('textarea');
    textArea.value = textToCopy;
    // make the textarea out of viewport
    textArea.style.position = 'fixed';
    textArea.style.left = '-999999px';
    textArea.style.top = '-999999px';
    document.body.appendChild(textArea);
    textArea.focus();
    textArea.select();
    return new Promise((res, rej) => {
        // here the magic happens
        if (document.execCommand('copy')) {
            res();
        } else {
            rej();
        }
        textArea.remove();
    });
}
