import React, { useState } from 'react';
import MomentUtils from '@date-io/moment';
import {
    Button,
    createStyles,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    makeStyles,
    TableCell,
    Tooltip,
} from '@material-ui/core';
import { FaInfoCircle } from 'react-icons/fa';

import EntityCrudPage from 'app/components/crud/EntityCrudPage';
import { ICellCallback, IColumnOrder, ITableHeadColumn } from 'app/components/table/IscTableHead';
import queryService from 'app/services/query.service';
import IscPageSimple from 'app/components/layout/IscPageSimple';
import { SearchResult } from 'app/domain/search';
import { IscTableContext } from 'app/components/table/IscTableContext';
import ExternalUser from 'app/domain/externalUser';

const momentUtils: MomentUtils = new MomentUtils();

const columns: Array<ITableHeadColumn> = [
    {
        id: 'title',
        align: 'left',
        disablePadding: false,
        label: 'Title',
        width: 100,
        sort: true,
        filter: 'string',
    },
    {
        id: 'lastName',
        align: 'left',
        disablePadding: false,
        label: 'Last Name',
        sort: true,
        filter: 'string',
    },
    {
        id: 'firstName',
        align: 'left',
        disablePadding: false,
        label: 'First Name',
        sort: true,
        filter: 'string',
    },
    {
        id: 'status',
        align: 'left',
        disablePadding: false,
        label: 'Status',
        width: 120,
        sort: true,
        filter: 'string',
    },
    {
        id: 'phoneNumber',
        align: 'left',
        width: 150,
        disablePadding: false,
        label: 'Phone',
        sort: true,
        filter: 'string',
    },
    {
        id: 'updated',
        align: 'center',
        disablePadding: false,
        label: 'Updated',
        sort: true,
        filter: {
            filterType: 'date',
            format: 'YYYY-MM-DD',
        },
        formatter: (row: ExternalUser, column: ITableHeadColumn, index: number) => (
            <TableCell padding="default" key={index}>
                {momentUtils.format(momentUtils.date(row.updated), 'YYYY-MM-DD')}
            </TableCell>
        ),
        width: 120,
    },
    {
        id: 'actions',
        align: 'left',
        disablePadding: false,
        label: 'Actions',
        width: 100,
        formatter: (row: ExternalUser, column: ITableHeadColumn, index: number, cellCallback?: ICellCallback) => {
            return (
                <TableCell padding="default" key={index}>
                    <Tooltip title="Details">
                        <IconButton
                            aria-label="details"
                            size="small"
                            onClick={() =>
                                cellCallback?.onAction ? cellCallback?.onAction('showDetails', row) : undefined
                            }
                        >
                            <FaInfoCircle />
                        </IconButton>
                    </Tooltip>
                    {/* 
                    <Tooltip title="Refresh">
                        <IconButton
                            aria-label="refresh"
                            size="small"
                            onClick={() =>
                                cellCallback?.onAction ? cellCallback?.onAction('refresh', row) : undefined
                            }
                        >
                            <FaSyncAlt />
                        </IconButton>
                    </Tooltip>
                    */}
                </TableCell>
            );
        },
    },
];

const useStyles = makeStyles(() =>
    createStyles({
        root: {
            width: '100%',
            minWidth: '400px',
            marginTop: '0px',
            paddingTop: '0px',
            overflowY: 'visible',
            backgroundColor: 'transparent',
        },
        dialog: {
            '& .MuiDialog-paper': {
                overflowY: 'visible',
            },
        },
        dialogTitle: {
            marginBottom: '0px',
            paddingBottom: '0px',
        },
        lable: {
            fontWeight: 'bold',
        },
    })
);

interface ExternalUserDetailsDialogProps {
    user: ExternalUser;
    onClose?: () => void;
}

const ExternalUserDetailsDialog = (props: ExternalUserDetailsDialogProps): JSX.Element => {
    const onClose = (): void => {
        if (props.onClose) {
            props.onClose();
        }
    };

    const classes = useStyles();
    return (
        <Dialog
            fullWidth
            open
            onClose={onClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
            className={classes.dialog}
        >
            <DialogTitle className={classes.dialogTitle} id="alert-dialog-title">
                External User Details
            </DialogTitle>
            <DialogContent className={classes.root}>
                <div style={{ minHeight: '120px', paddingTop: 10 }} className="w-full">
                    <table style={{ width: '100%' }}>
                        <colgroup>
                            <col style={{ width: '20%' }} />
                            <col style={{ width: '30%' }} />
                            <col style={{ width: '20%' }} />
                            <col style={{ width: '30%' }} />
                        </colgroup>
                        <tbody>
                            <tr>
                                <td className={classes.lable}>Title</td>
                                <td>{props.user.title}</td>
                                <td className={classes.lable}>School Site</td>
                                <td>{props.user.schoolSite}</td>
                            </tr>
                            <tr>
                                <td className={classes.lable}>First Name</td>
                                <td>{props.user.firstName}</td>
                                <td className={classes.lable}>Official Job Title</td>
                                <td>{props.user.officialJobTitle}</td>
                            </tr>
                            <tr>
                                <td className={classes.lable}>Last Name</td>
                                <td>{props.user.lastName}</td>
                                <td className={classes.lable}>ID Full Name</td>
                                <td>{props.user.idFullName}</td>
                            </tr>
                            <tr>
                                <td className={classes.lable}>Phone</td>
                                <td>{props.user.phoneNumber}</td>
                                <td className={classes.lable}>ID Number</td>
                                <td>{props.user.idNumber}</td>
                            </tr>
                            <tr>
                                <td className={classes.lable}>Status</td>
                                <td>{props.user.status}</td>
                                <td className={classes.lable}>OFFICE/CLASS</td>
                                <td>{props.user.officeClass}</td>
                            </tr>
                            <tr>
                                <td className={classes.lable}>External Id</td>
                                <td>{props.user.externalId}</td>
                                <td className={classes.lable} />
                                <td />
                            </tr>
                            <tr>
                                <td className={classes.lable}>Source</td>
                                <td>{props.user.source}</td>
                                <td className={classes.lable} />
                                <td />
                            </tr>
                        </tbody>
                    </table>
                </div>
            </DialogContent>
            <DialogActions>
                <Button color="primary" onClick={onClose}>
                    Close
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default function ExternalUsers(): JSX.Element {
    const [showDialog, setShowDialog] = useState<ExternalUser | null>(null);

    const defaultSort: IColumnOrder = {
        id: 'lastName',
        direction: 'asc',
    };
    const dataSelector = (data: SearchResult): SearchResult => {
        return data;
    };
    const queryAction = (context: IscTableContext): Promise<SearchResult> => {
        const filter = {
            tags: context.tags,
            columns: context.filter
                ? Object.keys(context.filter).map(key => {
                      return { key, value: context.filter[key] };
                  })
                : [],
        };
        return queryService.queryExternalUsersWithFilter(
            filter,
            {
                page: context.pagination?.currentPage || 0,
                size: context.pagination?.rowsPerPage || 25,
            },
            [
                {
                    property: context.sortOrder.id || 'lastName',
                    direction: (context.sortOrder.direction as string)?.toUpperCase() || 'ASC',
                },
            ]
        );
    };

    const onAction = (action: string, row: ExternalUser): void => {
        if (action === 'showDetails') {
            setShowDialog(row as ExternalUser);
        }
    };

    const onCloseDeleteDialog = (): void => {
        setShowDialog(null);
    };

    return (
        <IscPageSimple>
            <EntityCrudPage
                columns={columns}
                entityFields={[]}
                dataSelector={dataSelector}
                queryAction={queryAction}
                sortOrder={defaultSort}
                onAction={onAction}
            />
            {showDialog && <ExternalUserDetailsDialog onClose={onCloseDeleteDialog} user={showDialog} />}
        </IscPageSimple>
    );
}
