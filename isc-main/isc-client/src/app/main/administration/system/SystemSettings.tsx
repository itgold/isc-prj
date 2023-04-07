/* eslint-disable */
import React from 'react';
import IscPageSimple from 'app/components/layout/IscPageSimple';
import { Box, Button, FormControlLabel, Grid, makeStyles, Paper, TextField } from '@material-ui/core';
import FullSizeLayout from '@common/components/layout/fullsize.layout';
import Widget from '@common/components/layout/widget';
import SyncIcon from '@material-ui/icons/Sync';
import adminService from 'app/services/admin.service';
import { MdOutlineSystemUpdateAlt } from 'react-icons/md';
import SimulatorRandom from './SimulatorRandom';

const useStyles = makeStyles(theme => ({
    syncPanel: {
        backgroundColor: theme.palette.background.default,
        color: theme.palette.secondary.contrastText,
        padding: theme.spacing(2),
    },
    label: {
        verticalAlign: 'middle',
    },
}));

export default function SystemSettings(): JSX.Element {
    const classes = useStyles();

    const triggerDoorsSync = (): void => {
        adminService
            .syncDoors()
            .then(() => console.log('Success!'))
            .catch(err => console.log('Error!', err));
    };
    const triggerCamerasSync = (): void => {
        adminService
            .syncCameras()
            .then(() => console.log('Success!'))
            .catch(err => console.log('Error!', err));
    };
    const triggerUsersSync = (): void => {
        adminService
            .syncUsers()
            .then(() => console.log('Success!'))
            .catch(err => console.log('Error!', err));
    };
    const triggerRadiosSync = (): void => {
        adminService
            .syncRadios()
            .then(() => console.log('Success!'))
            .catch(err => console.log('Error!', err));
    };
    const triggerAllSync = (): void => {
        adminService
            .syncAll()
            .then(() => console.log('Success!'))
            .catch(err => console.log('Error!', err));
    };
    const rebuildCompositeTree = (): void => {
        adminService
            .rebuildCompositeTree()
            .then(() => console.log('Success!'))
            .catch(err => console.log('Error!', err));
    };

    const updateDoorsRegions = (): void => {
        adminService
            .updateDoorRegions()
            .then(() => console.log('Success!'))
            .catch(err => console.log('Error!', err));
    };
    const updateSpeakersRegions = (): void => {
        adminService
            .updateSpeakersRegions()
            .then(() => console.log('Success!'))
            .catch(err => console.log('Error!', err));
    };
    const updateCamerasRegions = (): void => {
        adminService
            .updateCamerasRegions()
            .then(() => console.log('Success!'))
            .catch(err => console.log('Error!', err));
    };

    const section = {
        height: '100%',
        paddingTop: 5,
        backgroundColor: '#ededed',
    };

    return (
        <IscPageSimple>
            <FullSizeLayout>
                <Widget title="Synchronization" noBodyPadding={false} bodyId="map" bodyClass={classes.syncPanel}>
                    <FormControlLabel
                        value="syncDoors"
                        control={
                            <Button
                                onClick={triggerDoorsSync}
                                startIcon={<SyncIcon />}
                                color="primary"
                                variant="contained"
                            >
                                Sync doors ...
                            </Button>
                        }
                        label=""
                        labelPlacement="start"
                    />

                    <FormControlLabel
                        value="syncCameras"
                        control={
                            <Button
                                onClick={triggerCamerasSync}
                                startIcon={<SyncIcon />}
                                color="primary"
                                variant="contained"
                            >
                                Sync cameras ...
                            </Button>
                        }
                        label=""
                        labelPlacement="start"
                    />

                    <FormControlLabel
                        value="syncRadios"
                        control={
                            <Button
                                onClick={triggerRadiosSync}
                                startIcon={<SyncIcon />}
                                color="primary"
                                variant="contained"
                            >
                                Sync radios ...
                            </Button>
                        }
                        label=""
                        labelPlacement="start"
                    />

                    <FormControlLabel
                        value="syncUsers"
                        control={
                            <Button
                                onClick={triggerUsersSync}
                                startIcon={<SyncIcon />}
                                color="primary"
                                variant="contained"
                            >
                                Sync users ...
                            </Button>
                        }
                        label=""
                        labelPlacement="start"
                    />

                    <FormControlLabel
                        value="syncAll"
                        control={
                            <Button
                                onClick={triggerAllSync}
                                startIcon={<SyncIcon />}
                                color="primary"
                                variant="contained"
                            >
                                Sync ALL ...
                            </Button>
                        }
                        label=""
                        labelPlacement="start"
                    />
                </Widget>
                <Widget title="Composite Tree" noBodyPadding={false} bodyId="map" bodyClass={classes.syncPanel}>
                    <FormControlLabel
                        value="Rebuild Now"
                        control={
                            <Button
                                onClick={rebuildCompositeTree}
                                startIcon={<SyncIcon />}
                                color="primary"
                                variant="contained"
                            >
                                Rebuild Now ...
                            </Button>
                        }
                        label=""
                        labelPlacement="start"
                    />
                </Widget>
                <Widget title="Parent Regions" noBodyPadding={false} bodyId="map" bodyClass={classes.syncPanel}>
                    <Grid container spacing={2}>
                        <Grid item xs={4}>
                            <Paper style={section} elevation={3}>
                                <FormControlLabel
                                    value="updateDoorsRegions"
                                    control={
                                        <Box component="span" p={2}>
                                            <Button
                                                onClick={updateDoorsRegions}
                                                startIcon={<MdOutlineSystemUpdateAlt />}
                                                color="primary"
                                                variant="contained"
                                                style={{ whiteSpace: 'nowrap' }}
                                            >
                                                Doors ...
                                            </Button>
                                        </Box>
                                    }
                                    label="Will find all doors and re-assign parent region based on the door naming convention"
                                    labelPlacement="start"
                                    style={{ margin: 10 }}
                                />
                            </Paper>
                        </Grid>
                        <Grid item xs={4}>
                            <Paper style={section} elevation={3}>
                                <FormControlLabel
                                    value="updateSpeakersRegions"
                                    control={
                                        <Box component="span" p={2}>
                                            <Button
                                                onClick={updateSpeakersRegions}
                                                startIcon={<MdOutlineSystemUpdateAlt />}
                                                color="primary"
                                                variant="contained"
                                                style={{ whiteSpace: 'nowrap' }}
                                            >
                                                Speakers ...
                                            </Button>
                                        </Box>
                                    }
                                    label="Will find all speakers and re-assign parent region based on the door naming convention"
                                    labelPlacement="start"
                                    style={{ margin: 10 }}
                                />
                            </Paper>
                        </Grid>
                        <Grid item xs={4}>
                            <Paper style={section} elevation={3}>
                                <FormControlLabel
                                    value="updateCamerasRegions"
                                    control={
                                        <Box component="span" p={2}>
                                            <Button
                                                onClick={updateCamerasRegions}
                                                startIcon={<MdOutlineSystemUpdateAlt />}
                                                color="primary"
                                                variant="contained"
                                                style={{ whiteSpace: 'nowrap' }}
                                            >
                                                Cameras ...
                                            </Button>
                                        </Box>
                                    }
                                    label="Will find all cameras, check for naming convention and combine matching cameras into the composite camera regions."
                                    labelPlacement="start"
                                    style={{ margin: 10 }}
                                />
                            </Paper>
                        </Grid>
                    </Grid>
                </Widget>
                <Widget title="Continuous Simulation" noBodyPadding={false} bodyId="map" bodyClass={classes.syncPanel}>
                    <SimulatorRandom/>
                </Widget>
            </FullSizeLayout>
        </IscPageSimple>
    );
}
