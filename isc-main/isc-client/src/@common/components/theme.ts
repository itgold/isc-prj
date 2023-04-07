import { Theme } from '@material-ui/core';

export interface AppTheme extends Theme {
    customShadows?: {
        widget: string;
        widgetDark: string;
        widgetWide: string;
    };
    appBar: {
        height: string;
    };
}
