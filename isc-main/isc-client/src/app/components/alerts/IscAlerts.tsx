import React, { useCallback, useEffect, useState } from 'react';
import { Badge, Box, IconButton, Popover } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { FiAlertCircle } from 'react-icons/fi';
import * as Selectors from 'app/store/selectors';
import { Alerts, RootState } from 'app/store/appState';
import { useDispatch, useSelector } from 'react-redux';
import School from 'app/domain/school';
import FullSizeLayout from '@common/components/layout/fullsize.layout';
import clsx from 'clsx';
import userPreferencesService, { UserContexts } from 'app/services/userPreference.service';
import _ from 'lodash';
import * as Actions from 'app/store/actions';
import AlertsComponent, { COLUMNS } from './AlertsComponent';
import { createEmptyContext, IscTableContext, IscTableDataAdaptor } from '../table/IscTableContext';
import { OnContextChanged } from '../table/IscTableDataProvider';
import { ServerDataProvider } from '../table/providers/ServerDataProvider';

const useStyles = makeStyles(theme => ({
    alertsIcon: {},
    alertsBtn: {
        color: theme.palette.error.dark,
    },
    alertsGrid: {
        minHeight: 500,
        height: '50%',
    },
    root: {
        display: 'flex',
        flexDirection: 'column',
        padding: 0,
    },
}));

class AlertsServerDataProvider extends ServerDataProvider {}

function createProvider(
    alerts: Alerts,
    dataContext: IscTableContext,
    contextChangeListener: OnContextChanged
): AlertsServerDataProvider {
    const currentPage = alerts.currentPage
        ? { ...alerts.currentPage }
        : {
              data: [],
              numberOfItems: 0,
              numberOfPages: 0,
          };

    return new AlertsServerDataProvider(currentPage, COLUMNS, dataContext, contextChangeListener);
}

const IscAlerts = () => {
    const classes = useStyles();
    const [userMenu, setUserMenu] = useState<HTMLButtonElement | null>(null);
    const [dataContext, setDataContext] = useState<IscTableContext>(createEmptyContext());
    const currentSchoolId = useSelector<RootState, string | undefined>(Selectors.currentSchoolSelector);
    const schools = useSelector<RootState, School[]>(Selectors.schoolsSelector);
    const currentSchool = schools.find(s => s.id === currentSchoolId);
    const dispatch = useDispatch();
    const alerts = useSelector<RootState, Alerts>(Selectors.alertsSelector);
    const [showFilters, setShowFilters] = useState<boolean>(false);

    const userPreferences = userPreferencesService.loadPreferences(UserContexts.alerts);
    const { showDistrictAlerts } = userPreferences;
    const [showAll, setShowAll] = useState<boolean>(showDistrictAlerts === undefined || showDistrictAlerts === 'true');

    const userMenuClick = (event: React.MouseEvent<HTMLButtonElement>): void => {
        setUserMenu(event.currentTarget);
    };

    const userMenuClose = () => {
        setUserMenu(null);
    };

    const search = useCallback(
        (context: IscTableContext, school?: School): void => {
            const schoolId = school?.id || null;
            const columnFilter = schoolId ? [{ key: 'schoolId', value: schoolId }] : [];

            if (context.filter) {
                Object.keys(context.filter).forEach(filter => {
                    const filterValue = context.filter[filter];
                    if (filterValue?.length) {
                        if (filter === 'eventTime') {
                            // TODO: parse and provide in format with client timezone
                            columnFilter.push({ key: filter, value: filterValue });
                        } else {
                            columnFilter.push({ key: filter, value: filterValue });
                        }
                    }
                });
            }

            dispatch(
                Actions.queryAlerts({
                    filter: { columns: columnFilter },
                    pagination: {
                        page: context.pagination?.currentPage || 0,
                        size: context.pagination?.rowsPerPage || 25,
                    },
                    sort: [],
                })
            );
        },
        [dispatch]
    );

    const dataProvider: AlertsServerDataProvider = createProvider(
        alerts,
        dataContext,
        (newContext: IscTableContext) => {
            if (!_.isEqual(dataContext, newContext)) {
                setDataContext(newContext);
                search(newContext, showAll ? undefined : currentSchool);
            }
        }
    );

    const onRefreshGrid = useCallback(
        (reset: boolean): void => {
            let newContext = dataContext;
            if (reset) {
                newContext = createEmptyContext();
                setDataContext(newContext);
                setShowFilters(false);
            }

            search(newContext, showAll ? undefined : currentSchool);
        },
        [currentSchool, dataContext, search, showAll]
    );

    const onToggleShowAll = (show: boolean): void => {
        setShowAll(show);
        search(dataContext, show ? undefined : currentSchool);
        userPreferencesService.updatePreference(UserContexts.alerts, {
            showDistrictAlerts: `${show}`,
        });
    };

    useEffect(() => {
        onRefreshGrid(true);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [currentSchool]);

    useEffect(() => {
        if (alerts.newEvents?.length) {
            onRefreshGrid(true);
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [alerts.newEvents]);

    const closeAlerts = () => {
        setUserMenu(null);
    };

    return (
        <IscTableDataAdaptor context={dataContext} dataProvider={dataProvider}>
            <IconButton
                color="primary"
                classes={{ root: classes.alertsBtn }}
                aria-owns="widget-menu"
                aria-haspopup="true"
                onClick={userMenuClick}
            >
                <Badge
                    className={classes.alertsIcon}
                    color="secondary"
                    badgeContent={dataProvider.totalRecords}
                    max={999}
                >
                    <FiAlertCircle />
                </Badge>
            </IconButton>

            <Popover
                open={Boolean(userMenu)}
                anchorEl={userMenu}
                onClose={userMenuClose}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'center',
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'center',
                }}
                PaperProps={{
                    style: { width: '60%', marginTop: 4 },
                }}
            >
                <Box className={clsx(classes.alertsGrid, classes.root)}>
                    <FullSizeLayout>
                        <AlertsComponent
                            showAll={showAll}
                            showFilters={showFilters}
                            onToggleShowAll={onToggleShowAll}
                            onClose={closeAlerts}
                        />
                    </FullSizeLayout>
                </Box>
            </Popover>
        </IscTableDataAdaptor>
    );
};
export default IscAlerts;
