/* eslint-disable */
import React from 'react';
import { Box, Button, FormControlLabel, Grid, Link, Paper, TextField, Typography } from '@material-ui/core';
import SyncIcon from '@material-ui/icons/Sync';
import { RestService } from '@common/services/rest.service';
import CSS from 'csstype';

class HelperService extends RestService {
    triggerSimulation(payload: any): Promise<any> {
        return this.client.post('/rest/simulate/random', payload, {
            headers: {
                ...this.defaultHeaders(),
            },
        });
    }
}
const helperService = new HelperService();

export class SimulatorRandom extends React.Component<
    {},
    {
        simulator: {
            requested: string[];
            accepted: string[];
            rejected: string[];
            history: any[];
            isLoading: boolean;
            error: string;
        };
    }
> {
    css = {
        linkBlock: {
            textAlign: 'right' as CSS.Property.TextAlign,
            width: '100%',
        },
        restoreDefaults: {
            float: 'right',
        },
        deviceSimInput: {
            // border: '1px solid red',
            width: '100%',
            margin: '10px 0 10px 0',
        },
        submitButton: {
            width: '100%',
        },
        paperBlock: {
            margin: '10px 0',
        },
        paperErrorBlock: {
            border: '1px solid red',
            margin: '10px 0',
        },
        deviceError: {
            textAlign: 'right' as CSS.Property.TextAlign,
            color: 'red',
            margin: '-3px 0 2px 0',
            'font-size': '0.85em',
        },
        dataTable: {
            width: '100%',
        },
        odd: {
            background: '#f1f1f1',
        },
    };

    private localStoreKey = 'simulator.requested';

    private timer: NodeJS.Timeout | null = null;

    constructor(props: {}) {
        super(props);
        this.state = {
            simulator: {
                isLoading: false,
                error: '',
                requested: [],
                accepted: [],
                rejected: [],
                history: [],
            },
        };
        this.setRequestedDeviceId = this.setRequestedDeviceId.bind(this);
    }

    readRequestedDeviceIds() {
        return this.state.simulator.requested;
    }

    readRejectedDeviceIds() {
        return this.state.simulator.rejected;
    }

    readRequestHistories() {
        return this.state.simulator.history;
    }

    componentDidMount() {
        this.iniDefaults();
        this.tickingOnOff();
    }

    iniDefaults() {
        const deviceIds = ['92944e3c-4612-4cfd-a035-7edf6220a7f2', '5a3f5c5f-e8ce-41c1-9ac3-6b67e0406b99'];

        if (!localStorage.getItem(this.localStoreKey)) {
            localStorage.setItem(this.localStoreKey, JSON.stringify(deviceIds));
            this.saveRequestedDeviceIds(deviceIds);
        } else {
            const deviceList = localStorage.getItem(this.localStoreKey);
            const oldState = deviceList ? JSON.parse(deviceList) : [];
            this.saveRequestedDeviceIds(oldState);
        }
    }

    addAnother(e: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
        e.preventDefault();
        const newState = this.readRequestedDeviceIds();
        newState.push('');
        this.saveRequestedDeviceIds(newState);
    }

    restoreDefaults(e: React.MouseEvent<HTMLButtonElement>) {
        e.preventDefault();
        localStorage.removeItem(this.localStoreKey);
        this.iniDefaults();
        this.setLoadingFlag(false);
    }

    setRequestedDeviceId(deviceId: string, idx: number) {
        const newState = this.readRequestedDeviceIds();
        newState[idx] = deviceId;
        this.saveRequestedDeviceIds(newState);
        localStorage.setItem(this.localStoreKey, JSON.stringify(newState));
    }

    hasError(deviceId: string): boolean {
        const rejected = this.readRejectedDeviceIds();
        const found = rejected.filter(item => item === deviceId);
        return found.length > 0;
    }

    tickingOnOff() {
        if (this.timer) {
            clearInterval(this.timer);
        } else {
            this.timer = setInterval(() => {
                this.refreshHistory();
            }, 500);
        }
    }

    simulateRandomEvents(e: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
        e.preventDefault();
        const newState = this.readRequestedDeviceIds().filter(item => item !== '');
        this.saveRequestedDeviceIds(newState);
        this.setLoadingFlag(true);

        const payload = [];
        const elem = document.getElementById('deviceSimulateRequesterWidget')?.getElementsByTagName('input');
        if (elem) {
            for (let i = 0; i < elem?.length; i++) {
                if (elem.item(i)?.value) {
                    payload.push(elem.item(i)?.value);
                }
            }
        }

        helperService
            .triggerSimulation(payload)
            .then(res => this.handleSuccessCase(res))
            .catch(err => {
                this.handleErrorCase(err);
            })
            .finally(() => this.setLoadingFlag(false));
    }

    handleSuccessCase(serverResponse: any) {
        const newState = this.state.simulator;
        if (serverResponse.hasOwnProperty('accepted') && serverResponse.hasOwnProperty('rejected')) {
            // add accepted to the state
            newState.accepted = serverResponse.accepted;
            newState.rejected = serverResponse.rejected;
            // @ts-ignore
            newState.history.push(serverResponse);
            newState.error = '';
            this.setState({ simulator: newState });
        } else {
            if (serverResponse.hasOwnProperty('errorCode')) {
                this.setErrorFlag(serverResponse.errorCode);
            } else {
                this.setErrorFlag('Something went wrong');
            }
        }
    }

    handleErrorCase(e: any) {
        this.setErrorFlag('Simulator is not running...');
    }

    refreshHistory() {
        const requestHistories = this.readRequestHistories();
        const newState = this.state.simulator;
        const filtered = requestHistories.filter(item => this.displayETA(item.eta) > 0);
        newState.history = filtered;
        this.setState({ simulator: newState });
    }

    setLoadingFlag(flag: boolean) {
        const newState = this.state.simulator;
        newState.isLoading = flag;
        this.setState({ simulator: newState });
    }
    saveRequestedDeviceIds(deviceIds: string[]) {
        const newState = this.state.simulator;
        newState.requested = deviceIds;
        this.setState({ simulator: newState });
    }
    setErrorFlag(flag: string) {
        const newState = this.state.simulator;
        newState.error = flag;
        this.setState({ simulator: newState });
    }

    displayETA(eta: number): number {
        let rtn = 0;
        const diff = eta - new Date().getTime();
        if (diff > 0) {
            const seconds = diff / 1000;
            rtn = Number.parseInt(Math.floor(seconds).toString(), 10);
        }
        return rtn;
    }

    displayDate(millis: number): string {
        const date = new Date(millis);
        const rtn = date.toLocaleString();
        const out = rtn.split(', ');
        return out[0];
    }

    displayTime(millis: number): string {
        const date = new Date(millis);
        const rtn = date.toLocaleString();
        const out = rtn.split(', ');
        return out[1];
    }

    renderErrorBlock() {
        const rtn: JSX.Element[] = [];
        if (!this.state.simulator.error) {
            return rtn;
        }

        rtn.push(
            <Grid item xs={3}>
                <Paper style={this.css.paperErrorBlock}>
                    <Box component="div" p={3}>
                        <b>Error</b>
                        <p>{this.state.simulator.error}</p>
                        <div style={this.css.deviceSimInput}>
                            <Button
                                color="primary"
                                variant="outlined"
                                size="small"
                                disabled={this.state.simulator.isLoading}
                                onClick={e => {
                                    this.simulateRandomEvents(e);
                                }}
                            >
                                Try Again
                            </Button>
                        </div>
                    </Box>
                </Paper>
            </Grid>
        );
        return rtn;
    }

    renderHistory() {
        const rtn: JSX.Element[] = [];
        const requestHistories = this.readRequestHistories();
        requestHistories.sort((a: any, b: any) => {
            return a.timestamp > b.timestamp ? -1 : 1;
        });

        requestHistories.forEach((deviceHistory: any, idx: number) => {
            rtn.push(
                <Grid key={idx} item xs={3}>
                    <Paper style={this.css.paperBlock}>
                        <Box component="div" p={3}>
                            <Typography className="h1">Request History</Typography>
                            <table style={this.css.dataTable}>
                                <tbody>
                                    <tr style={this.css.odd}>
                                        <td>Accepted</td>
                                        <td>{deviceHistory.accepted.length}</td>
                                    </tr>
                                    <tr>
                                        <td>Rejected</td>
                                        <td>{deviceHistory.rejected.length}</td>
                                    </tr>
                                    <tr style={this.css.odd}>
                                        <td>Repeat</td>
                                        <td>{deviceHistory.repeat}</td>
                                    </tr>
                                    <tr>
                                        <td>Sleep</td>
                                        <td>{deviceHistory.sleepMs}</td>
                                    </tr>
                                    <tr style={this.css.odd}>
                                        <td>TTL</td>
                                        <td>{this.displayETA(deviceHistory.eta)}</td>
                                    </tr>
                                    <tr>
                                        <td>Timestamp</td>
                                        <td>
                                            {this.displayDate(deviceHistory.timestamp)}
                                            <br />
                                            {this.displayTime(deviceHistory.timestamp)}
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </Box>
                    </Paper>
                </Grid>
            );
        });
        return rtn;
    }

    renderDeviceForm() {
        const rtn: JSX.Element[] = [];
        const deviceList = this.readRequestedDeviceIds();
        deviceList.forEach((deviceId: string, i: number) => {
            rtn.push(
                <TextField
                    style={this.css.deviceSimInput}
                    key={'deviceSimulateRequesterKey_' + i}
                    name={'deviceSim_' + i}
                    label={'Device ' + (i + 1)}
                    variant="outlined"
                    size="small"
                    value={deviceId}
                    disabled={this.state.simulator.isLoading}
                    error={this.hasError(deviceId)}
                    onChange={e => this.setRequestedDeviceId(e.target.value, i)}
                />
            );
            if (this.hasError(deviceId)) {
                rtn.push(<div style={this.css.deviceError}>Device Not Found</div>);
            }
        });
        rtn.push(
            <Button
                key="deviceSimulateRequesterKey_another"
                color="primary"
                variant="text"
                size="small"
                disabled={this.state.simulator.isLoading}
                onClick={e => {
                    this.addAnother(e);
                }}
            >
                + Add Another
            </Button>
        );
        return rtn;
    }

    render() {
        return (
            <Grid container spacing={2}>
                <Grid item xs={3}>
                    <div id="deviceSimulateRequesterWidget">
                        <div style={this.css.linkBlock}>
                            <Button
                                color="primary"
                                variant="text"
                                size="small"
                                disabled={this.state.simulator.isLoading}
                                onClick={e => {
                                    this.restoreDefaults(e);
                                }}
                            >
                                Restore Defaults
                            </Button>
                        </div>
                        <div>{this.renderDeviceForm()}</div>
                        <Button
                            style={this.css.submitButton}
                            startIcon={<SyncIcon />}
                            color="primary"
                            variant="contained"
                            size="small"
                            disabled={this.state.simulator.isLoading}
                            onClick={e => {
                                this.simulateRandomEvents(e);
                            }}
                        >
                            Simulate Random Events
                        </Button>
                    </div>
                </Grid>
                <Grid item xs={9}>
                    <Grid container spacing={1}>
                        {this.renderErrorBlock()}
                        {this.renderHistory()}
                    </Grid>
                </Grid>
            </Grid>
        );
    }
}

export default SimulatorRandom;
