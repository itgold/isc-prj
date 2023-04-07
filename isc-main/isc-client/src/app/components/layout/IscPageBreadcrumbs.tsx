import React from 'react';
import { Breadcrumbs, Typography } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import getHistory from '@history/GlobalHistory';
import { useSelector } from 'react-redux';
import { NavigationItemState, RootState } from 'app/store/appState';
import { NavigationConfigItem } from 'app/fuse-configs/navigationConfig';

const useStyles = makeStyles({
    lastStepTitle: {
        maxHeight: 19,
    },
});

function generateName(routeName: string): string {
    const friendlyName = routeName[0].toUpperCase() + routeName.slice(1);
    return friendlyName.replace(/([A-Z])/g, ' $1').replace(/^./, str => {
        return str.toUpperCase();
    });
}

interface IscPageBreadcrumbsProps {
    currentPageLabel?: string;
    currentPageIcon?: React.ReactNode;
}

function IscPageBreadcrumbs(props: IscPageBreadcrumbsProps): JSX.Element {
    const navigation = useSelector<RootState, NavigationItemState[]>(({ fuse }) => fuse.navigation);

    /**
     * Get all settings regarding the current URL
     */
    const getUrlPathSettings = (): NavigationConfigItem | null => {
        let childrenConfig;
        // look for some children which has the prop URL === current url
        navigation.some(function iter(element) {
            if ((element as NavigationConfigItem).url === getHistory().location.pathname) {
                childrenConfig = element;
                return true;
            }
            // recursive search
            return Array.isArray(element.children) && element.children.some(iter);
        });
        return childrenConfig || null;
    };

    const classes = useStyles();
    // get the current URL and removes the first occurence of / and splits it into each step of the url
    const urlRoutes = getHistory().location.pathname.replace('/', '').split('/');
    const routes = urlRoutes.map((routeName: string, i: number) => {
        // for the first case which the name of the UI is route, we set the app name
        if (routeName.toLowerCase() === 'ui')
            return (
                <Typography key={`bc${i}`} color="secondary" className={classes.lastStepTitle}>
                    720 Security
                </Typography>
            );
        const urlPathSettings = getUrlPathSettings();

        const pageLabel = props.currentPageLabel ? props.currentPageLabel : generateName(routeName);

        // is it the last step in the route?
        return routeName === urlRoutes[urlRoutes.length - 1] ? (
            <Typography key={`bc${i}`} color="textPrimary" className={classes.lastStepTitle}>
                {/* is there a title or different label for it? */}
                {urlPathSettings?.title ||
                    (urlPathSettings?.urlSettings && urlPathSettings?.urlSettings[routeName]?.label) ||
                    // if not, get the route step as title
                    pageLabel}
                {props.currentPageIcon && props.currentPageIcon}
            </Typography>
        ) : (
            <Typography key={`bc${i}`} color="secondary" className={classes.lastStepTitle}>
                {/* is there a different label for it? */}
                {(urlPathSettings && urlPathSettings.urlSettings && urlPathSettings?.urlSettings[routeName]?.label) ||
                    // if not, get the route step as title
                    generateName(routeName)}{' '}
            </Typography>
        );
    });

    return <Breadcrumbs aria-label="breadcrumb">{routes}</Breadcrumbs>;
}

export default React.memo(IscPageBreadcrumbs);
