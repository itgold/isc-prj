import React from 'react';
import { Action, Dispatch, bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import MomentUtils from '@date-io/moment';
import { createStyles, StyleRules, Theme, withStyles } from '@material-ui/core/styles';

import * as Actions from 'app/store/actions/admin';
import * as Selectors from 'app/store/selectors';

import { DeviceState, RootState } from 'app/store/appState';

import SchoolMapBase, {
    DefaultProps,
    SchoolMapActionProps,
    ReduxStoreProps,
    SchoolMapComponentProps,
    SchoolMapState,
} from 'app/components/map/SchoolMapBase';
import { createSelector } from 'reselect';
import Region from 'app/domain/region';
import { PanelLocation } from 'app/components/map/SchoolMapSplitPanel';
import { TabPanel, TabPanelView } from '@common/components/layout/tabpanel';
import { ViewStyle } from '@common/components/layout/splitpanel';
import RegionsGridComponent from 'app/components/map/regionGrid/RegionsGridComponent';
import { fade, IconButton, makeStyles, TableCell, Tooltip } from '@material-ui/core';
import ISchoolElement from 'app/domain/school.element';
import { ICellCallback, ITableHeadColumn } from 'app/components/table/IscTableHead';
import { FaEye } from 'react-icons/fa';
import { BsQuestion } from 'react-icons/bs';
import { hasGeoData } from 'app/utils/domain.constants';
import EntityNameCell from 'app/components/EntityNameCell';
import { CompositeNode } from 'app/domain/composite';
import { resolveEntityTool } from 'app/components/map/markers/toolsList';
import { filterRegion } from 'app/components/map/utils/MapUtils';
import { CgListTree } from 'react-icons/cg';
import DeviceEventsComponent from './DeviceEventsComponent';
import DevicesGridComponent from '../../../components/map/deviceGrid/DevicesGridComponent';

const momentUtils: MomentUtils = new MomentUtils();

const styles = (theme: Theme): StyleRules =>
    createStyles({
        defaultButtons: {
            '& > *': {
                margin: theme.spacing(1),
            },
        },
        root: {},

        missingGeoIcon: {
            color: fade(theme.palette.text.secondary, 0.25),
        },
    });
const useStyles = makeStyles((theme: Theme) => {
    return styles(theme);
});

interface EntityStateProps {
    entity: ISchoolElement;
}

function EntityState(props: EntityStateProps): JSX.Element {
    const entityType = CompositeNode.entityType(props.entity)?.toLowerCase();
    const elementTool = resolveEntityTool(entityType);
    const deviceState = elementTool?.getStateString ? elementTool.getStateString(props.entity) : '';

    return <td className="MuiTableCell-root MuiTableCell-sizeSmall">{deviceState}</td>;
}

function MissingGeoDataIcon(): JSX.Element {
    const classes = useStyles();
    const ClickAction = (): void => {
        console.log('Click: selected floor');
    };

    return (
        <Tooltip title="Missing on Map" aria-label="show-filters">
            <IconButton className={classes.missingGeoIcon} color="inherit" size="small" onClick={ClickAction}>
                <BsQuestion />
            </IconButton>
        </Tooltip>
    );
}

const DEVICES_GRID: Array<ITableHeadColumn> = [
    {
        id: 'name',
        align: 'left',
        disablePadding: false,
        label: 'Name',
        sort: true,
        filter: 'string',
    },
    {
        id: 'entityType',
        align: 'left',
        width: 160,
        disablePadding: false,
        label: 'Type',
        sort: true,
        filter: {
            filterType: 'string',
            filter: (value: ISchoolElement, filterValue: string): boolean => {
                return `${value}`.toLowerCase().indexOf(filterValue) >= 0;
            },
        },
    },
    {
        id: 'state',
        align: 'left',
        disablePadding: false,
        label: 'Status',
        sort: true,
        filter: {
            filterType: 'string',
            filter: (value: DeviceState, filterValue: string, row: ISchoolElement): boolean => {
                const entityType = CompositeNode.entityType(row)?.toLowerCase();
                const elementTool = resolveEntityTool(entityType);
                const deviceState = elementTool?.getStateString ? elementTool.getStateString(row) : '';
                return `${deviceState}`.toLowerCase().indexOf(filterValue) >= 0;
            },
        },
        formatter: (row: ISchoolElement, column: ITableHeadColumn, index: number) => (
            <EntityState key={index} entity={row} />
        ),
    },
    {
        id: 'updated',
        align: 'center',
        disablePadding: false,
        width: 120,
        label: 'Updated',
        sort: true,
        filter: {
            filterType: 'date',
            format: 'YYYY-MM-DD',
        },
        formatter: (row: ISchoolElement, column: ITableHeadColumn, index: number): React.ReactNode => (
            <td className="MuiTableCell-root MuiTableCell-sizeSmall" key={index}>
                {momentUtils.format(momentUtils.date(row.updated), 'YYYY-MM-DD')}
            </td>
        ),
    },
    {
        id: 'region',
        alias: 'id',
        align: 'center',
        disablePadding: false,
        label: 'Region',
        filter: {
            filterType: 'string',
            filter: filterRegion,
        },
        formatter: (row: ISchoolElement, column: ITableHeadColumn, index: number): React.ReactNode => (
            <EntityNameCell entity={row} skipSchoolName skipEntityName key={index} />
        ),
    },
    {
        id: 'actions',
        align: 'left',
        disablePadding: false,
        label: 'Actions',
        width: 100,
        formatter: (
            row: ISchoolElement,
            column: ITableHeadColumn,
            index: number,
            cellCallback?: ICellCallback
        ): React.ReactNode => {
            return (
                <TableCell padding="default" key={index}>
                    <Tooltip title="Find on Tree" aria-label="show-filters">
                        <IconButton
                            size="small"
                            style={{ fontSize: '1.4rem' }}
                            onClick={(event: React.MouseEvent) => {
                                event.stopPropagation();
                                if (cellCallback?.onAction) cellCallback?.onAction('findInTree', row);
                                return false;
                            }}
                        >
                            <CgListTree />
                        </IconButton>
                    </Tooltip>
                    {hasGeoData(row) ? (
                        <Tooltip title="Find On Map">
                            <IconButton
                                aria-label="highlight"
                                size="small"
                                onClick={(event: React.MouseEvent) => {
                                    event.stopPropagation();
                                    if (cellCallback?.onAction) cellCallback?.onAction('highlight', row);
                                    return false;
                                }}
                            >
                                <FaEye />
                            </IconButton>
                        </Tooltip>
                    ) : (
                        <MissingGeoDataIcon />
                    )}
                </TableCell>
            );
        },
    },
];

const REGIONS_GRID: Array<ITableHeadColumn> = [
    {
        id: 'name',
        align: 'left',
        disablePadding: false,
        label: 'Name',
        sort: true,
        filter: 'string',
    },
    {
        id: 'type',
        align: 'left',
        width: 160,
        disablePadding: false,
        label: 'Type',
        sort: true,
        filter: {
            filterType: 'string',
            filter: (value: ISchoolElement, filterValue: string): boolean =>
                `${value}`.toLowerCase().indexOf(filterValue) >= 0,
        },
    },
    {
        id: 'updated',
        align: 'center',
        disablePadding: false,
        width: 120,
        label: 'Updated',
        sort: true,
        filter: {
            filterType: 'date',
            format: 'YYYY-MM-DD',
        },
        formatter: (row: ISchoolElement, column: ITableHeadColumn, index: number): React.ReactNode => (
            <td className="MuiTableCell-root MuiTableCell-sizeSmall" key={index}>
                {momentUtils.format(momentUtils.date(row.updated), 'YYYY-MM-DD')}
            </td>
        ),
    },
    {
        id: 'region',
        alias: 'id',
        align: 'center',
        disablePadding: false,
        label: 'Parent',
        filter: {
            filterType: 'string',
            filter: filterRegion,
        },
        formatter: (row: ISchoolElement, column: ITableHeadColumn, index: number): React.ReactNode => (
            <EntityNameCell entity={row} skipSchoolName skipEntityName key={index} />
        ),
    },
    {
        id: 'actions',
        align: 'left',
        disablePadding: false,
        label: 'Actions',
        width: 100,
        formatter: (
            row: ISchoolElement,
            column: ITableHeadColumn,
            index: number,
            cellCallback?: ICellCallback
        ): React.ReactNode => {
            return (
                <TableCell padding="default" key={index}>
                    <Tooltip title="Find on Tree" aria-label="show-filters">
                        <IconButton
                            size="small"
                            style={{ fontSize: '1.4rem' }}
                            onClick={(event: React.MouseEvent) => {
                                event.stopPropagation();
                                if (cellCallback?.onAction) cellCallback?.onAction('findInTree', row);
                                return false;
                            }}
                        >
                            <CgListTree />
                        </IconButton>
                    </Tooltip>

                    {hasGeoData(row) ? (
                        <Tooltip title="Find On Map">
                            <IconButton
                                aria-label="highlight"
                                size="small"
                                onClick={(event: React.MouseEvent) => {
                                    event.stopPropagation();
                                    if (cellCallback?.onAction) cellCallback?.onAction('highlight', row);
                                    return false;
                                }}
                            >
                                <FaEye />
                            </IconButton>
                        </Tooltip>
                    ) : (
                        <MissingGeoDataIcon />
                    )}
                </TableCell>
            );
        },
    },
];

type SchoolMapProps = SchoolMapComponentProps & SchoolMapActionProps & ReduxStoreProps & DefaultProps;

class TerritoryMapComponent extends SchoolMapBase<SchoolMapProps, SchoolMapState> {
    createBottomPanel(/* treeProvider: ICompositeTreeDataProvider */): React.ReactNode {
        return (
            <TabPanelView
                align={ViewStyle.horisontal}
                selectedIndex={this.state.bottomPanelIndex}
                onToggle={minimized => this.handleToggleGrid(PanelLocation.bottom, minimized)}
                onSelect={activaIndex => this.handleActiveTabChange(PanelLocation.bottom, activaIndex)}
            >
                <TabPanel name="Events">
                    <DeviceEventsComponent school={this.props.school} mapControl={this.mapControl} />
                </TabPanel>
                <TabPanel name="Devices">
                    <DevicesGridComponent
                        school={this.props.school}
                        mapControl={this.mapControl}
                        columns={DEVICES_GRID}
                        cellCallback={this.cellCallback}
                    />
                </TabPanel>
                <TabPanel name="Regions">
                    <RegionsGridComponent
                        school={this.props.school}
                        mapControl={this.mapControl}
                        columns={REGIONS_GRID}
                        cellCallback={this.cellCallback}
                    />
                </TabPanel>
            </TabPanelView>
        );
    }
}

type StatePropsSelectorCreator = (state: RootState) => ReduxStoreProps;
function statePropsSelectorCreator(schoolId: string): StatePropsSelectorCreator {
    return createSelector(
        Selectors.createSchoolRegionsTreeSelector(schoolId),
        Selectors.selectSelectedFloors,
        (schoolRegion: Region, selectedFloors: Region[]) => {
            return {
                schoolRegion,
                selectedFloors,
            };
        }
    );
}

const mapStateToProps = (state: RootState, ownProps: SchoolMapComponentProps): ReduxStoreProps => ({
    ...statePropsSelectorCreator(ownProps.school?.id || '')(state),
    layersHashmap: state.app.map.layersHashmap,
});

function mapDispatchToProps(dispatch: Dispatch<Action>): SchoolMapActionProps {
    return {
        ...bindActionCreators(
            {
                querySchoolElements: Actions.querySchoolElements,
            },
            dispatch
        ),
        dispatch: action => dispatch(action),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles)(TerritoryMapComponent));
