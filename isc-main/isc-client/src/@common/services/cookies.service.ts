export default {
    // Example: setCookie('name', 'Batman', 30);
    setCookie(name: string, value: string, maxAgeSeconds: number): void {
        const maxAgeSegment = `; max-age=${maxAgeSeconds}`;
        document.cookie = `${encodeURI(name)}=${encodeURI(value)}${maxAgeSegment}`;
    },

    getCookie(name: string, defaultValue = ''): string {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i += 1) {
            const c = cookies[i].split('=');
            if (c[0] === name) {
                return c[1];
            }
        }
        return defaultValue;
    },

    deleteCookie(name: string): void {
        this.setCookie(name, '', -1);
    },
};
