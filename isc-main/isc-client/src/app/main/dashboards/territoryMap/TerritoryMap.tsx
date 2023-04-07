import React from 'react';
import { createStyles, withStyles, Theme, StyleRules } from '@material-ui/core/styles';
import { withRouter, RouteComponentProps } from 'react-router-dom';
import { connect, useSelector } from 'react-redux';
import { createSelector } from 'reselect';

import 'ol/ol.css';
import '@common/components/map/controls/ol-ext.min.css';
import '@common/components/map/controls/map.controls.css';

import FullSizeLayout from '@common/components/layout/fullsize.layout';
import School from 'app/domain/school';
import { MapContextStates, RootState } from 'app/store/appState';
import storageService, { STORAGE_SELECTED_SCHOOL } from 'app/services/storage.service';
import * as Selectors from 'app/store/selectors';
import { createEmptyContext } from 'app/components/table/IscTableContext';

import { ClassNameMap } from '@material-ui/styles';
import { EntityType, RegionType } from 'app/utils/domain.constants';
import EditMap from 'app/components/map/deviceActions/EditMap';
import { ActionMap } from 'app/components/map/deviceActions/IDeviceAction';
import { CurrentBuildingAdaptor } from 'app/components/map/MapBuildingContext';
import mapContextMenus from './contextMenus/TerritoryMapContextMenus';
import TerritoryMapComponent from './TerritoryMapComponent';

const styles = (theme: Theme): StyleRules =>
    createStyles({
        root: {
            display: 'flex',
            flexDirection: 'column',
            minHeight: '100%',
            padding: 0, // theme.spacing(3),
        },
        widgetBody: {
            height: '100%',
            width: '100%',
        },
        widgetHeader: {
            padding: 0,
            paddingRight: 12,
            backgroundColor: theme.palette.background.default,
        },
        formControl: {
            marginLeft: theme.spacing(1),
            marginRight: theme.spacing(1),
            minWidth: 120,
        },
        formControlLabel: {
            display: 'inline',
            margin: theme.spacing(1),
            fontSize: '16px',
            fontWeight: 900,
            verticalAlign: 'middle',
        },
    });

const ENTITY_ACTIONS: ActionMap = {
    [EntityType.DOOR]: [],
    [EntityType.CAMERA]: [],
    [EntityType.DRONE]: [],
    [EntityType.SPEAKER]: [],
    // ------------------------------------------
    [RegionType.ZONE]: [],
    [RegionType.UNKNOWN]: [],
    [RegionType.BUILDING]: [],
    [RegionType.FLOOR]: [],
    [RegionType.ROOM]: [],
    [RegionType.WALL]: [],
    [RegionType.STAIRS]: [],
    [RegionType.ELEVATOR]: [],
    [RegionType.SCHOOL]: [new EditMap()],
};

interface StateProps extends RouteComponentProps {
    classes: Partial<ClassNameMap<string>>;
}
interface ReduxStateProps {
    schools: School[];
    currentSchoolId?: string;
    contextMap: MapContextStates;
}

type Props = StateProps & ReduxStateProps;
interface MapState {
    school?: School;
}

const resolveSchool = (schoolId: string | undefined, schools: School[]): School | undefined => {
    const currentSchool = schools && schools.length ? schools.find(s => s.id === schoolId) || schools[0] : undefined;
    return currentSchool;
};
const createState = (state: MapState, schoolId: string | undefined, schools: School[]): MapState => {
    const school = resolveSchool(schoolId, schools);
    if (!schoolId && school) {
        storageService.updateProperty(STORAGE_SELECTED_SCHOOL, school.id);
    }

    return {
        ...state,
        school,
    };
};

class TerritoryMap extends React.Component<Props, MapState> {
    static getDerivedStateFromProps(nextProps: Props, prevState: MapState): MapState | null {
        if (
            nextProps.schools &&
            nextProps.schools.length &&
            (!prevState.school || prevState.school.id !== nextProps.currentSchoolId)
        ) {
            return createState(prevState, nextProps.currentSchoolId, nextProps.schools);
        }

        return null; // no state change
    }

    constructor(props: Props) {
        super(props);

        const schoolId = props.currentSchoolId || storageService.readProperty(STORAGE_SELECTED_SCHOOL);
        this.state = createState({}, schoolId, props.schools);
    }

    resolveSchool(schoolId: string): School | undefined {
        return this.props.schools.find(s => s.id === schoolId);
    }

    render(): React.ReactNode {
        const { classes } = this.props;
        const currentSchoolContext = this.state.school
            ? this.props.contextMap[this.state.school.id || '']
            : createEmptyContext();
        return (
            <FullSizeLayout>
                <div className={classes.root}>
                    {this.state.school !== undefined && (
                        <CurrentBuildingAdaptor currentBuilding={undefined}>
                            <TerritoryMapComponent
                                pageId="dashboard"
                                school={this.state.school}
                                context={currentSchoolContext}
                                contextMenuViews={mapContextMenus}
                                extraActions={ENTITY_ACTIONS}
                            />
                        </CurrentBuildingAdaptor>
                    )}
                </div>
            </FullSizeLayout>
        );
    }
}

const statePropsSelector = createSelector(
    Selectors.schoolsSelector,
    Selectors.currentSchoolSelector,
    Selectors.dashboardContextSelector,
    (schools: School[], currentSchoolId, mapDashboard: MapContextStates) => {
        return {
            schools,
            currentSchoolId,
            contextMap: schools.reduce<MapContextStates>(
                (cMap, current) => ({
                    ...cMap,
                    [current.id as string]: mapDashboard[current.id as string] || createEmptyContext(),
                }),
                {}
            ),
        };
    }
);

const mapStateToProps = (state: RootState): ReduxStateProps => ({
    ...statePropsSelector(state),
});

export default connect(mapStateToProps)(withRouter(withStyles(styles)(TerritoryMap)));
