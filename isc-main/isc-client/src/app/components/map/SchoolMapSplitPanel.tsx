import React from 'react';
import { SplitPanel, SplitView, ViewStyle } from '@common/components/layout/splitpanel';
import { makeStyles, createStyles } from '@material-ui/core';
import { DEFAULT_TAB_HEIGHT } from '@common/components/layout/tabpanel/TabPanelView';

const useStyles = makeStyles(theme =>
    createStyles({
        resizer: {},

        panel: {
            backgroundColor: theme.palette.background.default,
        },
    })
);

export enum PanelLocation {
    left = 'left',
    right = 'right',
    bottom = 'bottom',
    center = 'center',
}

interface SchoolMapSplitPanelProps {
    mapPanel: React.ReactNode;

    leftPanel?: React.ReactNode;
    leftPanelWidth?: number;
    leftPanelMinimized?: boolean;

    rightPanel?: React.ReactNode;
    rightPanelWidth?: number;
    rightPanelMinimized?: boolean;

    bottomPanel?: React.ReactNode;
    bottomPanelHeight?: number;
    bottomPanelMinimized?: boolean;

    onResize?: (panel: PanelLocation, sizes: number[]) => void;
}

export default function SchoolMapSplitPanel(props: SchoolMapSplitPanelProps): JSX.Element {
    const classes = useStyles();

    const handleResizeEnd = (panel: PanelLocation, sizes: number[]): void => {
        // update sizes only when size changed and it is not minimized
        if (props.onResize) {
            props.onResize(panel, sizes);
        }
    };

    const defaultSize = 260;
    // left
    const leftPanelMinimized = props.leftPanelMinimized !== false;
    const leftPanelWidth = Math.max(props.leftPanelWidth || 0, defaultSize);
    // right
    const rightPanelMinimized = props.rightPanelMinimized !== false;
    const rightPanelWidth = Math.max(props.rightPanelWidth || 0, defaultSize);
    // bottom
    const bottomPanelMinimized = props.bottomPanelMinimized === true;
    const bottomPanelHeight = Math.max(props.bottomPanelHeight || 0, defaultSize);

    return (
        <SplitView
            resizerStyle={classes.resizer}
            split={ViewStyle.vertical}
            onResizeEnd={(sizes: number[]) => handleResizeEnd(PanelLocation.left, sizes)}
        >
            {props.leftPanel && (
                <SplitPanel
                    minSize={`${DEFAULT_TAB_HEIGHT}px`}
                    initialSize={`${defaultSize}`}
                    size={`${leftPanelWidth}px`}
                    className={classes.panel}
                    minimized={leftPanelMinimized}
                >
                    {props.leftPanel}
                </SplitPanel>
            )}

            <SplitPanel>
                <SplitView
                    split={ViewStyle.horisontal}
                    onResizeEnd={(sizes: number[]) => handleResizeEnd(PanelLocation.bottom, sizes)}
                >
                    <SplitPanel>
                        <SplitView
                            split={ViewStyle.vertical}
                            onResizeEnd={(sizes: number[]) => handleResizeEnd(PanelLocation.bottom, sizes)}
                        >
                            <SplitPanel>{props.mapPanel}</SplitPanel>

                            {props.rightPanel && (
                                <SplitPanel
                                    minSize={`${DEFAULT_TAB_HEIGHT}px`}
                                    initialSize={`${defaultSize}`}
                                    size={`${rightPanelWidth}px`}
                                    className={classes.panel}
                                    minimized={rightPanelMinimized}
                                >
                                    {props.rightPanel}
                                </SplitPanel>
                            )}
                        </SplitView>
                    </SplitPanel>

                    {props.bottomPanel && (
                        <SplitPanel
                            minSize={`${DEFAULT_TAB_HEIGHT}px`}
                            initialSize={`${defaultSize}`}
                            size={`${bottomPanelHeight}px`}
                            minimized={bottomPanelMinimized}
                        >
                            {props.bottomPanel}
                        </SplitPanel>
                    )}
                </SplitView>
            </SplitPanel>
        </SplitView>
    );
}
