/* eslint-disable */
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import * as Actions from 'app/store/actions/admin';
import { RootState } from 'app/store/appState';
import { probateSecurity } from 'app/utils/auth.utils';
import FuseSplashScreen from '@fuse/core/FuseSplashScreen';
import FuseServerError from '../../../@fuse/core/FuseServerError';

interface AppInitializerProps {
    children: React.ReactNode;
}

/**
 * Common place to load global cashable collections
 */
export function AppInitializer(props: AppInitializerProps): JSX.Element {
    const isAuthenticated = useSelector<RootState, boolean>(({ auth }) => !!auth?.login?.token?.access_token);
    const loaded = useSelector<RootState, boolean>(({ domain }) => domain.commonLoaded);
    const compositeLoadError = useSelector<RootState, boolean>((state) => state.domain.domainLoadError);

    const dispatch = useDispatch();

    useEffect(() => {
        if (isAuthenticated && !loaded && !compositeLoadError) {
            probateSecurity().then(() => {
                // the application is authenticated
                // Do one time initializations here

                // 1. Load some common disctionaries
                dispatch(Actions.queryDeviceStateCodes());
                // 2. Load School Districts
                dispatch(Actions.querySchoolDistricts());
                // 3. Load Schools
                dispatch(Actions.querySchools());
                // 4. Load Regions tree
                dispatch(Actions.loadRegionTree());
            });
        }
    });

    if (compositeLoadError){
        return <FuseServerError />
    }

    return isAuthenticated && !loaded ? <FuseSplashScreen /> : <>{props.children}</>;
}
