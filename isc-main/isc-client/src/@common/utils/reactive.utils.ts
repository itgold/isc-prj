/* eslint-disable func-names */
export type Procedure = (...args: any[]) => void;

export type Options = {
    isImmediate: boolean;
};

/**
 * Debounce some action
 *
 * @param func the function which we want to debounce
 * @param waitMilliseconds how many miliseconds must pass after most recent function call, for the original function to be called
 * @param options options object supports now one argument.
 *      isImmediate - if set to true then originalFunction will be called immediately, but on subsequent calls of the debounced
 *          function original function won't be called, unless waitMilliseconds passed after last call
 *
 * Example:
 * const debouncedFunction = debounce(originalFunction, waitMilliseconds, options);
 */
export function debounce<F extends Procedure>(
    func: F,
    waitMilliseconds = 50,
    options: Options = {
        isImmediate: false,
    }
): (this: ThisParameterType<F>, ...args: Parameters<F>) => void {
    let timeoutId: ReturnType<typeof setTimeout> | undefined;

    return function callback(this: ThisParameterType<F>, ...args: Parameters<F>) {
        const context = this;

        const doLater = () => {
            timeoutId = undefined;
            if (!options.isImmediate) {
                func.apply(context, args);
            }
        };

        const shouldCallNow = options.isImmediate && timeoutId === undefined;

        if (timeoutId !== undefined) {
            clearTimeout(timeoutId);
        }

        timeoutId = setTimeout(doLater, waitMilliseconds);

        if (shouldCallNow) {
            func.apply(context, args);
        }
    };
}

/**
 * Throttle calls
 *
 * @param func Function to call not more often then timeout
 * @param waitFor Timeout to prevent to do calls to the <code>func</code> too often
 *
 * Usage:
 *
 * const func = (hello: string) => { console.log(new Date().getTime(), '>>>', hello) }
 * const thrFunc = throttle(func, 1000)
 * thrFunc('hello 1')
 * setTimeout(() => thrFunc('hello 2'), 450)
 * setTimeout(() => thrFunc('hello 3'), 950)
 * setTimeout(() => thrFunc('hello 4'), 1700)
 * setTimeout(() => thrFunc('hello 4.1'), 1700)
 * setTimeout(() => thrFunc('hello 4.2'), 1750)
 * setTimeout(() => thrFunc('hello 4.3'), 1995)
 * setTimeout(() => thrFunc('hello 4.4'), 2000)
 * setTimeout(() => thrFunc('hello 4.5'), 2010)
 * setTimeout(() => thrFunc('hello 5'), 2100)
 */
export const throttle = <F extends (...args: any[]) => any>(func: F, waitFor: number) => {
    const now = () => new Date().getTime();
    let startTime: number = now() - waitFor;
    // eslint-disable-next-line no-return-assign
    const resetStartTime = () => (startTime = now());
    let timeout: NodeJS.Timeout;

    return (...args: Parameters<F>): Promise<ReturnType<F>> =>
        new Promise(resolve => {
            const timeLeft = startTime + waitFor - now();
            if (timeout) {
                clearTimeout(timeout);
            }
            if (startTime + waitFor <= now()) {
                resetStartTime();
                resolve(func(...args));
            } else {
                timeout = setTimeout(() => {
                    resetStartTime();
                    resolve(func(...args));
                }, timeLeft);
            }
        });
};
