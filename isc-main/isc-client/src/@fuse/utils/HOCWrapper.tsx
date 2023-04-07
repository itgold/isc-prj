import React from 'react';

/**
 * Wrapper to consume Fuse Javascript components which has properties not assignable to TypeScript types
 * @param props
 */
const HOCWrapper = (props: any) => {
    const CompRef = props.componentRef;
    const compProps: any = { ...props };
    return <CompRef {...compProps} />;
};

export default HOCWrapper;
