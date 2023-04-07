/* eslint-disable max-classes-per-file */
/* eslint-disable react/prefer-stateless-function */
import React from 'react';
import { ClassNameMap } from '@material-ui/styles';
import { createStyles, withStyles, StyleRules } from '@material-ui/core/styles';

import 'ol/ol.css';
import '@common/components/map/controls/ol-ext.min.css';
import '@common/components/map/controls/map.controls.css';

import FullSizeLayout from '@common/components/layout/fullsize.layout';
import { ViewStyle, SplitPanel, SplitView } from '@common/components/layout/splitpanel';
import { TabPanel, TabPanelView } from '@common/components/layout/tabpanel';
import { DEFAULT_TAB_HEIGHT } from '@common/components/layout/tabpanel/TabPanelView';
import { PanelLocation } from 'app/components/map/SchoolMapSplitPanel';
import { Button } from '@material-ui/core';

const styles = (): StyleRules =>
    createStyles({
        panel: {
            backgroundColor: 'green',
        },
    });

interface TestPageState {
    flag: boolean;
}

interface TestPageProps {
    classes: Partial<ClassNameMap<string>>;
}

function LeftPanel({ theFlag, onToggle }: { theFlag: boolean; onToggle: () => void }): JSX.Element {
    console.log(`RENDER LEFT: ${theFlag}`);
    return (
        <div>
            <div>LEFT PANEL, The flag: {`${theFlag}`}</div>
            <Button variant="contained" color="secondary" onClick={() => onToggle()}>
                Test
            </Button>
        </div>
    );
}

function TestPanel({ label, theFlag }: { label: string; theFlag: boolean }): JSX.Element {
    console.log(`RENDER LABEL ${label}: ${theFlag}`);
    return (
        <div>
            <div>
                {label}, The flag: {`${theFlag}`}
            </div>
        </div>
    );
}

class TestPage extends React.Component<TestPageProps, TestPageState> {
    constructor(props: TestPageProps) {
        super(props);

        this.state = {
            flag: false,
        };
    }

    handleResizeEnd(name: string, sizes: number[]): void {
        console.log('Size changed', name, sizes, this.state.flag);
    }

    handleToggleGrid(name: string, minimized: boolean): void {
        console.log('Toggle changed', name, minimized, this.state.flag);
    }

    toggleFlag(): void {
        this.setState(state => {
            return { ...state, flag: !state.flag };
        });
    }

    render(): React.ReactNode {
        const { classes } = this.props;
        const defaultSize = '260px';

        console.log(`!!! RENDER ${this.state.flag}`);
        return (
            <FullSizeLayout>
                {/*
                <SplitView
                    id="TOP"
                    resizerStyle={classes.resizer}
                    split={ViewStyle.vertical}
                    onResizeEnd={(sizes: number[]) => this.handleResizeEnd(PanelLocation.left, sizes)}
                >
                    <SplitPanel
                        id="LEFT"
                        minSize={`${DEFAULT_TAB_HEIGHT}px`}
                        initialSize={`${defaultSize}`}
                        className={classes.panel}
                    >
                        <LeftPanel theFlag={this.state.flag} onToggle={() => this.toggleFlag()} />
                    </SplitPanel>

                    <SplitPanel id="MAP">
                        <TestPanel label="MAP PANEL" theFlag={this.state.flag} />
                    </SplitPanel>
                </SplitView>
                */}

                <SplitView
                    id="TOP"
                    resizerStyle={classes.resizer}
                    split={ViewStyle.vertical}
                    onResizeEnd={(sizes: number[]) => this.handleResizeEnd(PanelLocation.left, sizes)}
                >
                    <SplitPanel
                        id="LEFT"
                        minSize={`${DEFAULT_TAB_HEIGHT}px`}
                        initialSize={`${defaultSize}`}
                        className={classes.panel}
                    >
                        <TabPanelView
                            align={ViewStyle.vertical}
                            onToggle={minimized => this.handleToggleGrid(PanelLocation.left, minimized)}
                        >
                            <TabPanel disabled name="Devices">
                                <LeftPanel theFlag={this.state.flag} onToggle={() => this.toggleFlag()} />
                            </TabPanel>
                        </TabPanelView>
                    </SplitPanel>

                    <SplitPanel id="TOP MIDDLE">
                        <SplitView
                            id="TOP MIDDLE"
                            split={ViewStyle.horisontal}
                            onResizeEnd={(sizes: number[]) => this.handleResizeEnd(PanelLocation.bottom, sizes)}
                        >
                            <SplitPanel id="CENTRAL">
                                <SplitView
                                    id="CENTRAL"
                                    split={ViewStyle.vertical}
                                    onResizeEnd={(sizes: number[]) => this.handleResizeEnd(PanelLocation.bottom, sizes)}
                                >
                                    <SplitPanel id="MAP">
                                        <TestPanel label="MAP PANEL" theFlag={this.state.flag} />
                                    </SplitPanel>

                                    <SplitPanel
                                        id="RIGHT"
                                        minSize={`${DEFAULT_TAB_HEIGHT}px`}
                                        initialSize={`${defaultSize}`}
                                        className={classes.panel}
                                    >
                                        <TabPanelView
                                            horizontalAlign="right"
                                            align={ViewStyle.vertical}
                                            onToggle={minimized =>
                                                this.handleToggleGrid(PanelLocation.right, minimized)
                                            }
                                        >
                                            <TabPanel name="Panel #1">
                                                <TestPanel label="RIGHT PANEL" theFlag={this.state.flag} />
                                            </TabPanel>
                                        </TabPanelView>
                                    </SplitPanel>
                                </SplitView>
                            </SplitPanel>

                            <SplitPanel id="BOTTOM" minSize={`${DEFAULT_TAB_HEIGHT}px`} initialSize={`${defaultSize}`}>
                                <TabPanelView
                                    align={ViewStyle.horisontal}
                                    selectedIndex={0}
                                    onToggle={minimized => this.handleToggleGrid(PanelLocation.bottom, minimized)}
                                >
                                    <TabPanel name="Devices">
                                        <TestPanel label="BOTTOM PANEL" theFlag={this.state.flag} />
                                    </TabPanel>
                                </TabPanelView>
                            </SplitPanel>
                        </SplitView>
                    </SplitPanel>
                </SplitView>
            </FullSizeLayout>
        );
    }
}

export default withStyles(styles)(TestPage);
