import { FunctionComponent } from 'react';
import { useScreenWidth } from '../../hooks/screen-size';

type ScreenWidthChildren = (screenWidth: number) => any;

interface IScreenWidthProps {
    children: ScreenWidthChildren;
}

/*
    Example:

    import React from 'react';
    import { ScreenWidth } from './ScreenWidth';

    export class HooksRenderProps extends React.Component {
        render() {
            return (
            <ScreenWidth>
                {(width) => <p style={{ fontSize: '48px' }}>width: {width}</p>}
            </ScreenWidth>
            );
        }
    }
*/
export const ScreenWidth: FunctionComponent<IScreenWidthProps> = ({ children }) => {
    const screenWidth: number = useScreenWidth();

    return children(screenWidth);
};
