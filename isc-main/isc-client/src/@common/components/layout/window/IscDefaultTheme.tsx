import React from 'react';
import CSS from 'csstype';
import { defaultTheme, IscThemeProps, IscWindowTheme, TitleBar } from './IscWindowTheme';

const IscDefaultTheme = ({ title, onClose }: IscThemeProps): IscWindowTheme => {
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
            right: 0,
            margin: 0,
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

    return {
        theme: {
            title: {
                ...defaultTheme.title,
                fontFamily,
                borderTopLeftRadius: '5px',
                borderTopRightRadius: '5px',
                background: 'linear-gradient(0deg, #002171, #0d47a1)',
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
                // button2={minimizeButton}
                // button3={maximizeButton}
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

export default IscDefaultTheme;
