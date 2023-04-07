import React, { CSSProperties, useContext } from 'react';
import { makeStyles } from '@material-ui/styles';
import clsx from 'clsx';
import {
    convertSizeToCssValue,
    DEFAULT_PANE_MAX_SIZE,
    DEFAULT_PANE_MIN_SIZE,
    DEFAULT_PANE_SIZE,
    getUnit,
    ViewStyle,
} from './SplitView';
import SplitViewContextProvider, { SplitViewContext } from './SplitViewContext';

const useStyles = makeStyles(() => ({
    root: {
        flexGrow: 1,
    },
}));

export interface SplitPanelProps {
    id?: string;
    children: React.ReactNode | React.ReactNode[];
    className?: string;
    initialSize?: string | number;
    size?: string | number;
    minSize?: string;
    maxSize?: string;
    minimized?: boolean;
}

interface SplitPanelWrapperProps extends SplitPanelProps {
    innerRef: (idx: number, el: Element | null) => void;
    index: number;
    resizersSize: number;
    split: ViewStyle;
}

function resolveStyle(
    split: ViewStyle,
    resizersSize: number,
    minSize: string,
    maxSize: string,
    initialSize: string | number,
    size?: string | number
): CSSProperties {
    const value = `${size || initialSize}`;
    const vertical = split === ViewStyle.vertical;
    const styleProp: {
        minSize: 'minWidth' | 'minHeight';
        maxSize: 'maxWidth' | 'maxHeight';
        size: 'width' | 'height';
    } = {
        minSize: vertical ? 'minWidth' : 'minHeight',
        maxSize: vertical ? 'maxWidth' : 'maxHeight',
        size: vertical ? 'width' : 'height',
    };

    const style: CSSProperties = {
        display: 'flex',
        outline: 'none',
        position: 'relative',
    };

    style[styleProp.minSize] = convertSizeToCssValue(minSize, resizersSize);
    style[styleProp.maxSize] = convertSizeToCssValue(maxSize, resizersSize);

    switch (getUnit(value)) {
        case 'ratio':
            style.flex = value;
            break;
        case '%':
        case 'px':
            style.flexGrow = 0;
            style[styleProp.size] = convertSizeToCssValue(value, resizersSize);
            break;
        default:
            style.flexGrow = 0;
            style[styleProp.size] = convertSizeToCssValue(value, resizersSize);
            break;
    }

    return style;
}

export default function SplitPanel(props: SplitPanelProps): JSX.Element | null {
    const propsTrace = JSON.stringify(props);
    throw new Error(
        `<SplitPanel> component is required to be used incide of <SplitView> component only! Params: ${propsTrace}`
    );
}

function SplitPanelImplInternal(props: SplitPanelWrapperProps): JSX.Element {
    const classes = useStyles();
    const { minimized } = useContext(SplitViewContext);

    const { children, className, split, initialSize, size, minSize, maxSize, resizersSize } = props;
    const minimizedSize = props.minimized ? minSize || DEFAULT_PANE_MIN_SIZE : DEFAULT_PANE_MIN_SIZE;
    const prefixedStyle = resolveStyle(
        split || ViewStyle.vertical,
        resizersSize,
        minSize || DEFAULT_PANE_MIN_SIZE,
        maxSize || DEFAULT_PANE_MAX_SIZE,
        initialSize || DEFAULT_PANE_SIZE,
        minimized ? minimizedSize : size
    );

    const setRef = (element: Element | null): void => {
        if (props.innerRef) props.innerRef(props.index || 0, element);
    };

    return (
        <div className={clsx(classes.root, className)} style={prefixedStyle} ref={setRef}>
            {children}
        </div>
    );
}

export function SplitPanelImpl(props: SplitPanelWrapperProps): JSX.Element {
    return (
        <SplitViewContextProvider minimized={props.minimized}>
            <SplitPanelImplInternal {...props} />
        </SplitViewContextProvider>
    );
}
