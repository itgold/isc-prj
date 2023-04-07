import { ThemeProvider } from '@material-ui/core/styles';
import { DefaultTheme } from '@material-ui/styles';
import React from 'react';
import { useSelector } from 'react-redux';
import { RootState } from 'app/store/appState';

interface IscPageSimpleHeaderProps {
    header?: React.ReactNode;
    classes: any;
}

function IscPageSimpleHeader(props: IscPageSimpleHeaderProps) {
    const mainThemeDark = useSelector<RootState>(({ fuse }) => fuse.settings.mainThemeDark) as
        | Partial<DefaultTheme>
        | ((outerTheme: DefaultTheme) => DefaultTheme);

    return (
        <div className={props.classes.header}>
            {props.header && <ThemeProvider theme={mainThemeDark}>{props.header}</ThemeProvider>}
        </div>
    );
}

export default IscPageSimpleHeader;
