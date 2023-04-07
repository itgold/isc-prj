import React from 'react';

interface IframeProps {
    src: string;
    title: string;
    className?: string;
    frameBorder?: number | string;
}

const Iframe = (props: IframeProps): JSX.Element => {
    return <iframe title={props.title} src={props.src} className={props.className} frameBorder={props.frameBorder} />;
};

export default Iframe;
