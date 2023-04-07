export interface RGBColor {
    r: number;
    g: number;
    b: number;
}

/**
 * Few default colors to be used in the Styles
 */
export const AnimationColors = {
    Red: {
        r: 255,
        g: 0,
        b: 0,
    },
    Blue: {
        r: 0,
        g: 0,
        b: 255,
    },
    Green: {
        r: 0,
        g: 255,
        b: 0,
    },
};

export const hexToRGB = (hex: string): string => {
    hex = hex.replace('#', '');
    const bigint = parseInt(hex, 16);
    // eslint-disable-next-line no-bitwise
    const r = (bigint >> 16) & 255;
    // eslint-disable-next-line no-bitwise
    const g = (bigint >> 8) & 255;
    // eslint-disable-next-line no-bitwise
    const b = bigint & 255;

    return `${r},${g},${b}`;
};
