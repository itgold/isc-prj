import React, { useEffect, useState } from 'react';
import { useSelector, connect } from 'react-redux';
import { bindActionCreators, Dispatch, Action } from 'redux';
import _ from 'lodash';

import { Map, MapEvent } from 'ol';
import { IMapComponent } from '@common/components/map/map';
import { RootState } from 'app/store/appState';
import * as Actions from 'app/store/actions';
import { Rect } from '@common/components/layout/window/IscWindow';
import { FloatingWindow, FloatingWindowProps } from './FloatingWindow';
import { resolveEntityTool } from './markers/toolsList';

interface SchoolMapFloatWindowsProps {
    map?: IMapComponent | null;
}

interface MapFloatingWindowProps {
    info: FloatingWindow;
    map?: IMapComponent | null;
}

interface DispatchProps {
    closeFloatingWindow(payload?: any): void;
}

type Props = DispatchProps & MapFloatingWindowProps;

interface MapFloatingWindowState {
    updateFlag: number;
}

class MapFloatingWindowImpl extends React.Component<Props, MapFloatingWindowState> {
    component?: React.ComponentType<FloatingWindowProps>;

    updateHintLine: (windowRect: Rect) => void;

    onMapMove: (evt: MapEvent) => void;

    constructor(props: Props) {
        super(props);

        const tool = resolveEntityTool(this.props.info.entityType);
        this.component = tool?.floatingWindow?.();

        // this.updateHintLine = debounce((windowRect: Rect) => this._updateHintLine(windowRect), 100);
        this.updateHintLine = this._updateHintLine;
        this.onMapMove = this._onMapMove.bind(this);

        this.state = {
            updateFlag: new Date().getTime(),
        };
    }

    componentDidMount(): void {
        if (this.component) {
            this.props.map?.hintLine(this.props.info.entityId, 'create', {
                x: this.props.info.initialLeft || 100,
                y: this.props.info.initialTop || 200,
                width: this.props.info.initialWidth || 200,
                height: this.props.info.initialHeight || 140,
            });
        }

        const theMap: Map | undefined = this.props.map?.getMap();
        if (theMap) {
            theMap.on('moveend', this.onMapMove);
        }
    }

    componentWillUnmount(): void {
        const theMap: Map | undefined = this.props.map?.getMap();
        if (theMap) {
            theMap.un('moveend', this.onMapMove);
        }
        this.onClose();
    }

    onClose(): void {
        this.props.closeFloatingWindow(this.props.info);
        this.props.map?.hintLine(this.props.info.entityId, 'delete');
    }

    onMove(windowRect: Rect): void {
        // this.props.map?.hintLine(this.props.info.entityId, 'delete');
        this.updateHintLine(windowRect);
    }

    _updateHintLine(windowRect: Rect): void {
        this.props.map?.hintLine(this.props.info.entityId, 'update', {
            x: windowRect.left,
            y: windowRect.top,
            width: windowRect.width,
            height: windowRect.height,
        });
    }

    _onMapMove(): void {
        this.setState(state => {
            return { ...state, updateFlag: new Date().getTime() };
        });
    }

    render(): JSX.Element | null {
        const EntityWindow = this.component;
        return EntityWindow
            ? EntityWindow && (
                  <EntityWindow
                      entityId={this.props.info.entityId}
                      data={this.props.info.data}
                      initialTop={this.props.info.initialTop || 200}
                      initialLeft={this.props.info.initialLeft || 100}
                      initialWidth={this.props.info.initialWidth || 200}
                      initialHeight={this.props.info.initialHeight || 100}
                      onClose={() => this.onClose()}
                      onMove={windowRect => this.onMove(windowRect)}
                      onResize={windowRect => this.onMove(windowRect)}
                      updateFlag={this.state.updateFlag}
                  />
              )
            : null;
    }
}

function mapDispatchToProps(dispatch: Dispatch<Action>): DispatchProps {
    return bindActionCreators(
        {
            closeFloatingWindow: Actions.closeFloatingWindow,
        },
        dispatch
    );
}

const MapFloatingWindow = connect(null, mapDispatchToProps)(MapFloatingWindowImpl);

export default function SchoolMapFloatWindows(props: SchoolMapFloatWindowsProps): JSX.Element {
    const floatingWindows = useSelector<RootState, FloatingWindow[]>(state => state.app.floatingWindows);
    const [windows, setWindows] = useState<FloatingWindow[]>([]);

    useEffect(() => {
        if (!_.isEqual(floatingWindows, windows)) {
            props.map?.closeContextMenu();
            setWindows(floatingWindows);
        }
    }, [floatingWindows, props.map, windows]);

    return (
        <span>
            {windows.map(wnd => (
                <MapFloatingWindow key={`popup_${wnd.entityId}`} map={props.map} info={wnd} />
            ))}
        </span>
    );
}
