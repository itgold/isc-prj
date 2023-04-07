import React, { useEffect, useState } from 'react';

export function useScreenWidth(): number {
    const [width, setWidth] = useState(window.innerWidth);

    useEffect(() => {
        const handler = (event: any) => {
            setWidth(event.target.innerWidth);
        };

        window.addEventListener('resize', handler);

        return () => {
            window.removeEventListener('resize', handler);
        };
    }, []);

    return width;
}

/*
Example:

    import React from 'react';
    import { withHooksHOC } from './withHooksHOC';

    interface IHooksHOCProps {
        width: number;
    }

    class HooksHOC extends React.Component<IHooksHOCProps> {
        render() {
            return <p style={{ fontSize: '48px' }}>width: {this.props.width}</p>;
        }
    }

    export default withHooksHOC(HooksHOC);
*/
export const withHooksHOC = (Component: any) => {
    return (props: any) => {
        const screenWidth = useScreenWidth();

        return <Component width={screenWidth} {...props} />;
    };
};
