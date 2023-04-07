import React from 'react';
import CSS from 'csstype';

export interface WindowTheme {
    title?: React.CSSProperties;
    frame?: React.CSSProperties;
    transition?: string;
}

export const defaultTheme: WindowTheme = {
    title: {
        userSelect: 'none',
        WebkitUserSelect: 'none',
        msUserSelect: 'none',
        MozUserSelect: 'none',
        overflow: 'hidden',
        width: '100%',
        height: 25,
    },
    frame: {
        position: 'absolute',
        margin: 0,
        padding: 0,
        overflow: 'hidden',
    },
    transition: 'all 0.25s ease-in-out',
};

interface ButtonProps {
    style: React.CSSProperties;
    hoverStyle: React.CSSProperties;
    downStyle: React.CSSProperties;
    cursor?: CSS.Property.Cursor | undefined;
}
interface ButtonState {
    hover: boolean;
    down: boolean;
}

export class Button extends React.Component<ButtonProps, ButtonState> {
    constructor(props: ButtonProps) {
        super(props);

        this.state = {
            hover: false,
            down: false,
        };
    }

    render(): JSX.Element {
        const { style, hoverStyle, downStyle, children, cursor, ...other } = this.props;

        const dragging = /resize$/.test(cursor as string);

        const buttonStyle: React.CSSProperties | undefined = {
            ...style,
            ...(this.state.hover && !dragging ? hoverStyle : {}),
            ...(this.state.down && !dragging ? downStyle : {}),
            cursor,
        };

        return (
            <button
                type="button"
                onMouseEnter={() => this.setState({ hover: true })}
                onMouseLeave={() => this.setState({ hover: false, down: false })}
                onMouseDown={() => this.setState({ down: true })}
                onMouseUp={() => this.setState({ down: false })}
                style={buttonStyle}
                {...other}
            >
                {children}
            </button>
        );
    }
}

interface TitleBarProps {
    children: React.ReactNode | React.ReactNode[];
    className?: string;
    style?: React.CSSProperties;

    buttons?: React.HTMLAttributes<HTMLDivElement>;
    button1?: ButtonProps;
    button1Children?: React.ReactNode | React.ReactNode[];
    button2?: ButtonProps;
    button2Children?: React.ReactNode | React.ReactNode[];
    button3?: ButtonProps;
    button3Children?: React.ReactNode | React.ReactNode[];
    dnrState?: React.CSSProperties;
}

export const TitleBar = ({
    children,
    buttons,
    button1,
    button2,
    button3,
    button1Children,
    button2Children,
    button3Children,
    dnrState,
}: TitleBarProps): JSX.Element => (
    <div>
        <div {...buttons}>
            {button1 && (
                <Button {...button1} cursor={dnrState?.cursor}>
                    {button1Children}
                </Button>
            )}
            {button2 && (
                <Button {...button2} cursor={dnrState?.cursor}>
                    {button2Children}
                </Button>
            )}
            {button3 && (
                <Button {...button3} cursor={dnrState?.cursor}>
                    {button3Children}
                </Button>
            )}
        </div>
        {children}
    </div>
);

export interface IscThemeProps {
    title: React.ReactNode | React.ReactNode[];
    onClose: () => void;
    onMinimize: () => void;
    onMaximize: () => void;

    /* eslint-disable react/no-unused-prop-types */
    titleBarColor?: string;
}

export interface IscWindowTheme {
    theme: WindowTheme;
    titleBar: JSX.Element;
}

export const OSXTheme = ({ title, onClose, onMinimize, onMaximize }: IscThemeProps): IscWindowTheme => {
    const titleHeight = 25;
    const buttonRadius = 6;
    const fontSize = 14;
    const fontFamily = 'Helvetica, sans-serif';

    const style = {
        height: titleHeight,
    };

    const buttonStyle = {
        padding: 0,
        margin: 0,
        marginRight: '4px',
        width: buttonRadius * 2,
        height: buttonRadius * 2,
        borderRadius: buttonRadius,
        content: '',
        border: '1px solid rgba(0, 0, 0, 0.2)',
        outline: 'none',
    };
    const buttons = {
        style: {
            height: titleHeight,
            position: 'absolute' as CSS.Property.Position,
            float: 'left' as CSS.Property.Float,
            margin: '0 8px',
            display: 'flex',
            alignItems: 'center',
        },
    };

    const closeButton = {
        style: {
            ...buttonStyle,
            backgroundColor: 'rgb(255, 97, 89)',
        },
        hoverStyle: {
            backgroundColor: 'rgb(230, 72, 64)',
        },
        downStyle: {
            backgroundColor: 'rgb(204, 46, 38)',
        },
        onClick: onClose,
    };
    const minimizeButton = {
        style: {
            ...buttonStyle,
            backgroundColor: 'rgb(255, 191, 47)',
        },
        hoverStyle: {
            backgroundColor: 'rgb(230, 166, 22)',
        },
        downStyle: {
            backgroundColor: 'rgb(204, 140, 0)',
        },
        onClick: onMinimize,
    };
    const maximizeButton = {
        style: {
            ...buttonStyle,
            backgroundColor: 'rgb(37, 204, 62)',
        },
        hoverStyle: {
            backgroundColor: 'rgb(12, 179, 37)',
        },
        downStyle: {
            backgroundColor: 'rgb(0, 153, 11)',
        },
        onClick: onMaximize,
    };

    return {
        theme: {
            title: {
                ...defaultTheme.title,
                fontFamily,
                borderTopLeftRadius: '5px',
                borderTopRightRadius: '5px',
                background: 'linear-gradient(0deg, #d8d8d8, #ececec)',
                color: 'rgba(0, 0, 0, 0.7)',
                fontSize,
                height: titleHeight,
            } as React.CSSProperties,
            frame: {
                ...defaultTheme.frame,
                borderRadius: '5px',
            } as React.CSSProperties,
            transition: 'all 0.25s ease-in-out',
        },
        titleBar: (
            <TitleBar
                style={style}
                buttons={buttons}
                button1={closeButton}
                button2={minimizeButton}
                button3={maximizeButton}
            >
                <div
                    style={{
                        width: '100%',
                        height: '100%',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                    }}
                >
                    {title}
                </div>
            </TitleBar>
        ),
    };
};

export const WindowsTheme = ({
    title,
    onClose,
    onMinimize,
    onMaximize,
    titleBarColor = '#0095ff',
}: IscThemeProps): IscWindowTheme => {
    const titleHeight = 25;
    const fontSize = 14;
    const fontFamily = 'Helvetica, sans-serif';

    const style = {
        height: titleHeight,
    };

    const buttonStyle = {
        padding: 0,
        margin: 0,
        width: 25,
        height: 25,
        outline: 'none',
        border: 'none',
        textAlign: 'center' as CSS.Property.TextAlign,
    };

    const buttons = {
        style: {
            height: titleHeight,
            position: 'absolute' as CSS.Property.Position,
            right: 0,
            margin: 0,
            display: 'flex',
            alignItems: 'center' as CSS.Property.TextAlign,
            verticalAlign: 'baseline',
        },
    };

    const closeButton = {
        style: {
            ...buttonStyle,
            fontSize: '20px',
            fontWeight: 500,
            lineHeight: '36px',
            backgroundColor: titleBarColor,
        },
        hoverStyle: {
            backgroundColor: '#ec6060',
        },
        downStyle: {
            backgroundColor: '#bc4040',
        },
        onClick: onClose,
    };

    const minimizeButton = {
        style: {
            ...buttonStyle,
            lineHeight: '22px',
            backgroundColor: titleBarColor,
        },
        hoverStyle: {
            backgroundColor: 'rgba(0, 0, 0, 0.1)',
        },
        downStyle: {
            backgroundColor: 'rgba(0, 0, 0, 0.2)',
        },
        onClick: onMinimize,
    };

    const maximizeButton = {
        style: {
            ...buttonStyle,
            lineHeight: '12px',
            backgroundColor: titleBarColor,
        },
        hoverStyle: {
            backgroundColor: 'rgba(0, 0, 0, 0.1)',
        },
        downStyle: {
            backgroundColor: 'rgba(0, 0, 0, 0.2)',
        },
        onClick: onMaximize,
    };
    return {
        theme: {
            title: {
                ...defaultTheme.title,
                fontFamily,
                background: titleBarColor,
                color: 'rgba(0, 0, 0, 0.7)',
                fontSize,
                height: titleHeight,
            } as React.CSSProperties,
            frame: {
                ...defaultTheme.frame,
            } as React.CSSProperties,
            transition: 'all 0.25s ease-in-out',
        },
        titleBar: (
            <TitleBar
                style={style}
                buttons={buttons}
                button1={minimizeButton}
                button2={maximizeButton}
                button3={closeButton}
                button1Children="‒"
                button2Children="□"
                button3Children="˟"
            >
                <div
                    style={{
                        width: '100%',
                        height: '100%',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                    }}
                >
                    {title}
                </div>
            </TitleBar>
        ),
    };
};
