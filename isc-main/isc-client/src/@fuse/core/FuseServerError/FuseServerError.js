import React from 'react';
import { Button } from '@material-ui/core';

function FuseServerError() {
    const publicPath = window.PUBLIC_URL;

    const tryAgain = () => {
        window.location.reload();
    };

    return (
        <>
            <div className="compositeErrorContainer">
                <img className="centered" src={`${publicPath}/assets/images/500ErrorFlag.png`} alt="error" />
            </div>
            <Button color="primary" className="cent" onClick={() => tryAgain()}>
                Try Again
            </Button>
        </>
    );
}

export default React.memo(FuseServerError);
