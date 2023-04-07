import React, { RefObject } from 'react';
import { withRouter, RouteComponentProps } from 'react-router-dom';
import { connect } from 'react-redux';
import { Action, Dispatch } from 'redux';

import { EditLocation } from '@material-ui/icons';
import { ClassNameMap } from '@material-ui/styles';
import { createStyles, withStyles, Theme, StyleRules } from '@material-ui/core/styles';
import DeleteIcon from '@material-ui/icons/Delete';

import 'ol/ol.css';
import '@common/components/map/controls/ol-ext.min.css';
import '@common/components/map/controls/map.controls.css';

import School from 'app/domain/school';
import { MapContextStates, RootState } from 'app/store/appState';
import * as Selectors from 'app/store/selectors';
import * as MessageActions from 'app/store/actions/fuse/message.actions';
import IscPageSimple from 'app/components/layout/IscPageSimple';

import ISchoolElement from 'app/domain/school.element';

import { createSelector } from 'reselect';
import { createEmptyContext } from 'app/components/table/IscTableContext';
import Feature from 'ol/Feature';
import Geometry from 'ol/geom/Geometry';
import { CompositeNode } from 'app/domain/composite';
import { EntityType, RegionType } from 'app/utils/domain.constants';
import { ActionMap } from 'app/components/map/deviceActions/IDeviceAction';
import { CurrentBuildingAdaptor } from 'app/components/map/MapBuildingContext';
import mapEditorContextMenus from './contextMenus/MapEditorContextMenus';
import { IDefaultAction } from './contextMenus/ContextMenu';
import ErrorDialog, { IErrorDialog } from '../../components/app/ServerErrorDialog';
import MapConfirmationDialog, { IMapConfirmationDialog } from '../../components/app/MapConfirmationDialog';
import MapEditorComponent, { IMapEditorComponent } from './MapEditorComponent';

const styles = (theme: Theme): StyleRules =>
    createStyles({
        defaultButtons: {
            '& > *': {
                margin: theme.spacing(1),
            },
        },
        root: {
            display: 'flex',
            minHeight: '100%',
            marginTop: 0,
            marginBottom: 0,
        },
        widgetBody: {
            height: '100%',
            width: '100%',
        },
        formControl: {
            margin: theme.spacing(1),
            minWidth: 120,
        },
        title: {
            flexGrow: 1,
        },
        hide: {
            display: 'none',
        },
        content: {
            marginLeft: 0,
            marginRight: 0,
        },
        mapPageContent: {
            position: 'relative',
            display: 'flex',
            height: '100%',
            flexDirection: 'column',
            flex: 1,
            outline: 'none',
            overflow: 'hidden',
            userSelect: 'text',
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
    [RegionType.SCHOOL]: [],
};

interface DefaultProps extends RouteComponentProps {
    classes: Partial<ClassNameMap<string>>;
}
interface StateProps {
    schools: School[];
    currentSchoolId?: string;
    contextMap: MapContextStates;
}

interface DispatchProps {
    dispatch(payload: Action): void;
}

interface MapEditorState {
    currentSchoolId?: string;
    school?: School;
}

type MapEditorProps = StateProps & DefaultProps & DispatchProps;

class MapEditor extends React.Component<MapEditorProps, MapEditorState> {
    mapRef: RefObject<IMapEditorComponent> = React.createRef();

    defaultContextMenuActions: Array<IDefaultAction>;

    errorRef: RefObject<IErrorDialog> = React.createRef();

    confRef: RefObject<IMapConfirmationDialog> = React.createRef();

    static getDerivedStateFromProps(nextProps: StateProps, prevState: MapEditorState): MapEditorState | null {
        let state = null;

        if (
            (!prevState.school && nextProps.schools && nextProps.schools.length) ||
            prevState.currentSchoolId !== nextProps.currentSchoolId
        ) {
            const selectedSchool = nextProps.currentSchoolId;
            const school = nextProps.schools.find(s => s.id === selectedSchool);

            state = {
                ...prevState,
                currentSchoolId: selectedSchool,
                school,
            };
        }

        return state; // no state change
    }

    constructor(props: MapEditorProps) {
        super(props);

        const schoolId = (props.match.params as any).mapId || props.currentSchoolId;
        const school = props.schools.find(s => s.id === schoolId);
        this.state = {
            currentSchoolId: props.currentSchoolId,
            school,
        };

        // add more actions to the contextmenu
        this.defaultContextMenuActions = [
            {
                label: 'Delete Marker',
                callback: this.deleteMapMarker,
                icon: <DeleteIcon />,
            },
        ];
    }

    deleteMapMarker = (feature: Feature<Geometry> | null): void => {
        if (feature) {
            const row = feature?.getProperties().data;
            try {
                this.confRef.current?.openDialog(
                    'Please confirm',
                    row,
                    () => this.mapRef.current?.updateRegionEntity(row, false, true),
                    this.errorRef
                );
            } catch (e) {
                console.log(e);
            }
        }
    };

    private showMessage = (row: ISchoolElement, isDeleteAction = false): void => {
        let messageText = `${CompositeNode.entityType(row)} saved.`;
        if (isDeleteAction) {
            messageText = `${CompositeNode.entityType(row)} deleted.`;
        }

        this.props.dispatch(
            MessageActions.showMessage({
                message: `${messageText}`, // text or html
                autoHideDuration: 1000, // ms
                anchorOrigin: {
                    vertical: 'bottom', // top bottom
                    horizontal: 'right', // left center right
                },
                variant: 'success', // success error info warning null
            })
        );
    };

    render(): React.ReactNode {
        const { classes } = this.props;
        const currentSchoolContext = this.state.school
            ? this.props.contextMap[this.state.school.id || '']
            : createEmptyContext();
        const districtName = this.state.school?.district?.name;
        const schoolName = this.state.school?.name || '';

        let currentPageIcon = <EditLocation color="secondary" />;
        let currentPageLabel = schoolName;
        if (districtName) {
            currentPageLabel = ' ';
            currentPageIcon = (
                <>
                    <span key={`${districtName}`} color="secondary" style={{ display: 'inline-block' }}>
                        {districtName}
                    </span>
                    <span style={{ display: 'inline-block' }}>&nbsp; / &nbsp;</span>
                    <span key={`bc${schoolName}`} style={{ display: 'inline-block' }}>
                        {schoolName}
                    </span>

                    <EditLocation color="secondary" />
                </>
            );
        }

        return (
            <IscPageSimple
                contentClass={classes.content}
                currentPageLabel={currentPageLabel}
                currentPageIcon={currentPageIcon}
            >
                <div className={classes.mapPageContent}>
                    <CurrentBuildingAdaptor currentBuilding={undefined}>
                        <MapEditorComponent
                            ref={this.mapRef}
                            pageId="editMap"
                            school={this.state.school}
                            context={currentSchoolContext}
                            contextMenuViews={mapEditorContextMenus}
                            defaultContextMenuActions={this.defaultContextMenuActions}
                            onEntityUpdated={this.showMessage}
                            extraActions={ENTITY_ACTIONS}
                        />
                    </CurrentBuildingAdaptor>
                </div>
                <ErrorDialog ref={this.errorRef} />
                <MapConfirmationDialog ref={this.confRef} />
            </IscPageSimple>
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
const mapStateToProps = (state: RootState): StateProps => statePropsSelector(state);

function mapDispatchToProps(dispatch: Dispatch<Action>): DispatchProps {
    return {
        dispatch: action => dispatch(action),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(withStyles(styles)(MapEditor)));
