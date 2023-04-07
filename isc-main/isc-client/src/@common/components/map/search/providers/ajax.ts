export interface IJsonCallParams {
    url: string;
    data: any;
    jsonp?: boolean;
    callbackName?: string;
}

function jsonp(url: string, callback: (data: any) => void, key?: string) {
    // https://github.com/Fresheyeball/micro-jsonp/blob/master/src/jsonp.js
    const { head } = document;
    const script = document.createElement('script');
    // generate minimally unique name for callback function
    const callbackName = `f${Math.round(Math.random() * Date.now())}`;

    // set request url
    script.setAttribute(
        'src',
        /*  add callback parameter to the url
              where key is the parameter key supplied
              and callbackName is the parameter value */
        `${url + (url.includes('?') ? '&' : '?') + key}=${callbackName}`
    );

    /*  place jsonp callback on window,
        the script sent by the server should call this
        function as it was passed as a url parameter */
    const windowObj = window as any;
    windowObj[callbackName] = (data: any) => {
        windowObj[callbackName] = undefined;

        // clean up script tag created for request
        setTimeout(() => head.removeChild(script), 0);

        // hand data back to the user
        callback(data);
    };

    // actually make the request
    head.appendChild(script);
}

function toQueryString(obj: any): string {
    return Object.keys(obj)
        .reduce((a: string[], k: string) => {
            const prop = obj[k];
            a.push(
                typeof prop === 'object' ? toQueryString(prop) : `${encodeURIComponent(k)}=${encodeURIComponent(prop)}`
            );
            return a;
        }, [])
        .join('&');
}

function encodeUrlXhr(url: string, data: any) {
    if (data && typeof data === 'object') {
        url += (/\?/.test(url) ? '&' : '?') + toQueryString(data);
    }
    return url;
}

export function json(obj: IJsonCallParams) {
    return new Promise((resolve, reject) => {
        const url = encodeUrlXhr(obj.url, obj.data);
        const config = {
            method: 'GET',
            mode: 'cors',
            credentials: 'same-origin',
        } as RequestInit;

        if (obj.jsonp) {
            jsonp(url, resolve, obj.callbackName);
        } else {
            fetch(url, config)
                .then(r => r.json())
                .then(resolve)
                .catch(reject);
        }
    });
}
