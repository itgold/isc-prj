import React, { useEffect, useRef, useState } from 'react';
import { IEntity } from 'app/domain/entity';
import { ICellCallback, ITableHeadColumn } from 'app/components/table/IscTableHead';
import {
    createStyles,
    fade,
    Grid,
    IconButton,
    makeStyles,
    Switch,
    TableCell,
    Theme,
    Tooltip,
    withStyles,
} from '@material-ui/core';
import DeviceEvent, { Alert } from 'app/domain/deviceEvent';
import MomentUtils from '@date-io/moment';
import { Alerts, RootState } from 'app/store/appState';
import * as Selectors from 'app/store/selectors';
import { useSelector } from 'react-redux';
import { EntityType, hasGeoData } from 'app/utils/domain.constants';
import { findNode, schoolById } from 'app/components/map/utils/MapUtils';
import { CompositeNode } from 'app/domain/composite';
import clsx from 'clsx';
import HOCWrapper from '@fuse/utils/HOCWrapper';
import FusePageCarded from '@fuse/core/FusePageCarded';
import { deepOrange } from '@material-ui/core/colors';
import { RiChatDeleteLine } from 'react-icons/ri';
import { CgListTree } from 'react-icons/cg';
import { BsCheckCircle } from 'react-icons/bs';
import AlertsElements from './AlertsElements';
import FindOnTree from '../map/deviceActions/FindOnTree';
import AlertAckDialog, { IAlertDialogDialog } from './AlertAckDialog';
import AlertIgnoreDialog from './AlertIgnoreDialog';

const momentUtils: MomentUtils = new MomentUtils();

interface AlertsComponentProps {
    showAll?: boolean;
    showFilters?: boolean;
    onToggleShowAll?: (showAll: boolean) => void;
    onClose?: () => void;
}

const useStyles = makeStyles((theme: Theme) => {
    return createStyles({
        missingGeoIcon: {
            color: fade(theme.palette.text.secondary, 0.25),
        },
        toolbarText: {
            color: 'white',
        },
        alertGrid: {
            fontSize: '0.9em',

            '& .MuiTableCell-sizeSmall': {
                fontSize: '1em',
                paddingTop: 0,
                paddingBottom: 0,
            },
        },
    });
});

interface EventActionProps {
    event: Alert;
    cellCallback?: ICellCallback;
}

function EventAction(props: EventActionProps): JSX.Element {
    const { deviceId, deviceType } = props.event;

    const device = useSelector<RootState, IEntity | undefined>(
        Selectors.createEntitySelector({ id: deviceId, entityType: deviceType } as CompositeNode)
    );

    return (
        <TableCell className="MuiTableCell-sizeSmall" padding="default">
            <Tooltip title="Acknowledge">
                <IconButton
                    aria-label="acknowledge"
                    size="small"
                    onClick={(event: React.MouseEvent) => {
                        event.stopPropagation();
                        if (props.cellCallback?.onAction) {
                            props.cellCallback?.onAction('acknowledge', props.event);
                        }
                        return false;
                    }}
                >
                    <BsCheckCircle />
                </IconButton>
            </Tooltip>
            <Tooltip title="Ignore">
                <IconButton
                    aria-label="ignore"
                    size="small"
                    onClick={(event: React.MouseEvent) => {
                        event.stopPropagation();
                        if (props.cellCallback?.onAction) {
                            props.cellCallback?.onAction('ignore', props.event);
                        }
                        return false;
                    }}
                >
                    <RiChatDeleteLine />
                </IconButton>
            </Tooltip>

            {device && hasGeoData(device) && (
                <Tooltip title="Find on Tree" aria-label="show-filters">
                    <IconButton
                        size="small"
                        style={{ fontSize: '1.4rem' }}
                        onClick={(event: React.MouseEvent) => {
                            event.stopPropagation();
                            if (props.cellCallback?.onAction) props.cellCallback?.onAction('findInTree', props.event);
                            return false;
                        }}
                    >
                        <CgListTree />
                    </IconButton>
                </Tooltip>
            )}
        </TableCell>
    );
}

function shortSchoolName(schoolId: string): string {
    let schoolName = schoolId;
    const school = schoolById(schoolId);
    if (school) {
        schoolName = school.name || schoolId;
        schoolName = schoolName
            .split(' ')
            .map(namePart => namePart.charAt(0).toUpperCase())
            .join('');
    }

    return schoolName === 'ALL' ? '' : schoolName;
}

export const COLUMNS: Array<ITableHeadColumn> = [
    {
        id: 'eventTime',
        align: 'center',
        disablePadding: false,
        width: 160,
        label: 'Time',
        type: 'dateTimeRange',
        filter: {
            filterType: 'date',
            format: 'YYYY-MM-DD LT',
        },
        formatter: (row: Alert, column: ITableHeadColumn, index: number): React.ReactNode => (
            <td className={clsx('MuiTableCell-root MuiTableCell-sizeSmall')} key={index}>
                {momentUtils.format(momentUtils.date(row.updated), 'YYYY-MM-DD LT')}
            </td>
        ),
    },
    {
        id: 'schoolId',
        align: 'left',
        disablePadding: false,
        label: 'School',
        filter: 'string',
        width: 80,
        formatter: (row: Alert, column: ITableHeadColumn, index: number): React.ReactNode => (
            <TableCell className="MuiTableCell-sizeSmall" padding="default" key={index}>
                {shortSchoolName(row.schoolId)}
            </TableCell>
        ),
    },
    {
        id: 'count',
        align: 'left',
        disablePadding: false,
        label: '#',
        filter: 'string',
        width: 70,
    },
    {
        id: 'code',
        align: 'left',
        disablePadding: false,
        label: 'Code',
        filter: 'string',
        width: 150,
        formatter: (row: Alert, column: ITableHeadColumn, index: number): React.ReactNode => (
            <TableCell
                className="MuiTableCell-sizeSmall"
                padding="default"
                key={index}
                style={{ wordBreak: 'break-word' }}
            >
                {row.code}
            </TableCell>
        ),
    },
    {
        id: 'deviceType',
        align: 'left',
        width: 100,
        disablePadding: false,
        label: 'Type',
        filter: {
            filterType: 'string',
            filter: (value: IEntity, filterValue: string): boolean => `${value}`.indexOf(filterValue) >= 0,
        },
        formatter: (row: Alert, column: ITableHeadColumn, index: number): React.ReactNode => (
            <TableCell className="MuiTableCell-sizeSmall" padding="default" key={index}>
                {row.deviceType}
            </TableCell>
        ),
    },
    {
        id: 'description',
        align: 'left',
        disablePadding: false,
        label: 'Description',
        filter: 'string',
        formatter: (row: Alert, column: ITableHeadColumn, index: number): React.ReactNode => (
            <TableCell
                className="MuiTableCell-sizeSmall"
                padding="default"
                key={index}
                style={{ wordBreak: 'break-word' }}
            >
                {row.description}
            </TableCell>
        ),
    },
    {
        id: 'actions',
        align: 'left',
        disablePadding: false,
        label: 'Actions',
        width: 110,
        formatter: (
            row: Alert,
            column: ITableHeadColumn,
            index: number,
            cellCallback?: ICellCallback
        ): React.ReactNode => {
            return <EventAction key={index} event={row} cellCallback={cellCallback} />;
        },
    },
];

const ShowAllSwitch = withStyles({
    switchBase: {
        color: deepOrange[300],
        '&$checked': {
            color: deepOrange[500],
        },
        '&$checked + $track': {
            backgroundColor: deepOrange[500],
        },
    },
    checked: {},
    track: {},
})(Switch);

export default function AlertsComponent(props: AlertsComponentProps): JSX.Element {
    const ackRef = useRef<IAlertDialogDialog>(null);
    const supressRef = useRef<IAlertDialogDialog>(null);

    const toggleShowAll = (event: React.ChangeEvent<HTMLInputElement>): void => {
        props.onToggleShowAll?.(event.target.checked);
    };

    const onSelectElement = (event: DeviceEvent | null): void => {
        console.log('Alert selected', event);
    };

    const cellCallback = {
        onAction: (action: string, row: Alert) => {
            if (action === 'findInTree') {
                if (row?.deviceId) {
                    const entityType = row?.deviceType as EntityType;
                    const node = findNode(entityType, row?.deviceId);
                    if (node) {
                        new FindOnTree().execute(node as CompositeNode);
                    }
                }
                props.onClose?.();
            } else if (action === 'acknowledge') {
                ackRef.current?.openDialog(row);
            } else if (action === 'ignore') {
                supressRef.current?.openDialog(row);
            } else {
                console.log('Table row action not implemented!', action, row);
            }
        },
        context: {},
    };

    const closeConfirmationDialog = (canceled: boolean): void => {
        if (!canceled) {
            props.onClose?.();
        }
    };

    const classes = useStyles();
    return (
        <HOCWrapper
            componentRef={FusePageCarded}
            classes={{
                content: 'flex',
                header: 'pl-12 pr-12',
            }}
            header={
                <Grid container justify="flex-end">
                    <div>
                        <Grid component="label" container alignItems="center" spacing={1}>
                            <Grid item className={classes.toolbarText}>
                                Current School
                            </Grid>
                            <Grid item>
                                <ShowAllSwitch checked={props.showAll} onChange={toggleShowAll} name="showAll" />
                            </Grid>
                            <Grid item className={classes.toolbarText}>
                                District
                            </Grid>
                        </Grid>
                    </div>
                </Grid>
            }
            content={
                <>
                    <AlertsElements
                        columns={COLUMNS}
                        onSelectElement={(row: DeviceEvent | null) => onSelectElement(row)}
                        cellCallback={cellCallback}
                        showFilters={props.showFilters}
                        className={classes.alertGrid}
                    />
                    <AlertIgnoreDialog ref={supressRef} onClose={closeConfirmationDialog} />
                    <AlertAckDialog ref={ackRef} onClose={closeConfirmationDialog} />
                </>
            }
        />
    );
}
