import React from 'react';
import { makeStyles, createStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import { ViewStyle } from './SplitView';

const useStyles = makeStyles(() =>
    createStyles({
        wrappertyle: {
            background: '#000',
            opacity: 0.2,
            zIndex: 1000,
            boxSizing: 'border-box',
            backgroundClip: 'padding-box',

            '&:hover': {
                transition: 'all 2s ease',
            },
        },
        horizontalWrapper: {
            height: '11px',
            margin: '-5px 0',
            borderTop: '5px solid rgba(255, 255, 255, 0)',
            borderBottom: '5px solid rgba(255, 255, 255, 0)',
            cursor: 'row-resize',
            width: '100%',

            '&:hover': {
                borderTop: '5px solid rgba(0, 0, 0, 0.5)',
                borderBottom: '5px solid rgba(0, 0, 0, 0.5)',
            },

            '&.disabled': {
                cursor: 'not-allowed',

                '&:hover': {
                    borderColor: 'transparent',
                },
            },
        },
        verticalWrapper: {
            width: '11px',
            margin: '0 -5px',
            borderLeft: '5px solid rgba(255, 255, 255, 0)',
            borderRight: '5px solid rgba(255, 255, 255, 0)',
            cursor: 'col-resize',

            '&:hover': {
                borderLeft: '5px solid rgba(0, 0, 0, 0.5)',
                borderRight: '5px solid rgba(0, 0, 0, 0.5)',
            },
            '&.disabled': {
                cursor: 'not-allowed',

                '&:hover': {
                    borderColor: 'transparent',
                },
            },
        },
    })
);

interface WrapperProps {
    children?: React.ReactNode;
    className?: string;

    'data-attribute': ViewStyle;
    'data-type': string;

    onClick: (event: React.MouseEvent<HTMLDivElement>) => void;
    onDoubleClick: (event: React.MouseEvent<HTMLDivElement>) => void;
    onMouseDown: (event: React.MouseEvent<HTMLDivElement>) => void;
    onTouchEnd: (event: React.TouchEvent<HTMLDivElement>) => void;
    onTouchStart: (event: React.TouchEvent<HTMLDivElement>) => void;
}

interface ResizerProps {
    children?: React.ReactNode;
    className?: string;
    index?: number;
    split?: ViewStyle;

    onClick?: (event: React.MouseEvent<Element>, index: number) => void;
    onDoubleClick?: (event: React.MouseEvent<Element>, index: number) => void;
    onMouseDown?: (event: React.MouseEvent<Element>, index: number) => void;
    onTouchEnd?: (event: React.TouchEvent<Element>, index: number) => void;
    onTouchStart?: (event: React.TouchEvent<Element>, index: number) => void;
}

function Wrapper({ className = '', children, ...rest }: WrapperProps): JSX.Element {
    const classes = useStyles();

    return (
        <div className={clsx(classes.wrappertyle, className)} {...rest}>
            {children}
        </div>
    );
}

const HorizontalWrapper = ({ ...props }: WrapperProps): JSX.Element => {
    const classes = useStyles();
    return Wrapper({ className: classes.horizontalWrapper, ...props });
};
const VerticalWrapper = ({ ...props }: WrapperProps): JSX.Element => {
    const classes = useStyles();
    return Wrapper({ className: classes.verticalWrapper, ...props });
};

export default function Resizer(props: ResizerProps): JSX.Element {
    const {
        index = 0,
        split = ViewStyle.vertical,
        onClick = () => {},
        onDoubleClick = () => {},
        onMouseDown = () => {},
        onTouchEnd = () => {},
        onTouchStart = () => {},
    } = props;

    const childProps = {
        'data-attribute': split,
        'data-type': 'Resizer',
        onMouseDown: (event: React.MouseEvent<HTMLDivElement>) => onMouseDown(event, index),
        onTouchStart: (event: React.TouchEvent<HTMLDivElement>) => {
            event.preventDefault();
            onTouchStart(event, index);
        },
        onTouchEnd: (event: React.TouchEvent<HTMLDivElement>) => {
            event.preventDefault();
            onTouchEnd(event, index);
        },
        onClick: (event: React.MouseEvent<HTMLDivElement>) => {
            if (onClick) {
                event.preventDefault();
                onClick(event, index);
            }
        },
        onDoubleClick: (event: React.MouseEvent<HTMLDivElement>) => {
            if (onDoubleClick) {
                event.preventDefault();
                onDoubleClick(event, index);
            }
        },
    };

    return split === 'vertical' ? <VerticalWrapper {...childProps} /> : <HorizontalWrapper {...childProps} />;
}
