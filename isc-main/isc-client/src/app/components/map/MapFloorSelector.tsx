import React from 'react';
import { connect } from 'react-redux';
import OlBar from 'ol-ext/control/Bar';
import OlToggle from 'ol-ext/control/Toggle';
import { IMapComponent } from '@common/components/map/map';
import Region from 'app/domain/region';
import { CompositeNode } from 'app/domain/composite';
import { RootState } from 'app/store/appState';
import { Feature } from 'ol';
import { Stroke, Style } from 'ol/style';
import Geometry from 'ol/geom/Geometry';
import { buildingFloors } from './utils/MapUtils';

interface MapFloorSelectorProps {
    building?: Region;
    map?: IMapComponent | null;
}
interface MapFloorSelectorStateProps {
    floors: { floor: Region; selected: boolean }[];
}

type Props = MapFloorSelectorProps & MapFloorSelectorStateProps;

interface MapFloorSelectorState {
    building?: Region;
}

class MapFloorSelectorImpl extends React.Component<Props, MapFloorSelectorState> {
    mainBar?: OlBar;

    originalStyle?: Style;

    static getDerivedStateFromProps(nextProps: Props, prevState: MapFloorSelectorState): MapFloorSelectorState | null {
        if (prevState.building?.id !== nextProps.building?.id) {
            return {
                ...prevState,
                building: nextProps.building,
            };
        }

        return null;
    }

    constructor(props: Props) {
        super(props);

        this.state = {
            building: undefined,
        };
    }

    componentDidMount(): void {
        this.showBuildingFloorsSelector();
    }

    componentDidUpdate(): void {
        this.showBuildingFloorsSelector();
    }

    componentWillUnmount(): void {
        this.hideBuildingFloorsSelector();
    }

    private showBuildingFloorsSelector(): void {
        this.hideBuildingFloorsSelector();

        if (this.state.building && this.props.map) {
            const theMap = this.props.map.getMap();
            if (theMap) {
                const mainBar = new OlBar({ toggleOne: true });
                this.props.floors.forEach((floor, index) => {
                    const polygonControl = new OlToggle({
                        html: `<span style="font-size: 0.8em">${floor.floor.name}</span>`,
                        title: `${floor.floor.name}`,
                        active: floor.selected,
                    });

                    polygonControl.set('polygonNo', index);
                    mainBar.addControl(polygonControl);
                });

                // add building itself
                const polygonControl = new OlToggle({
                    html: `<span style="font-size: 0.8em">${this.state.building.name}</span>`,
                    title: `${this.state.building.name}`,
                    active: !this.props.floors.find(f => f.selected),
                    className: 'building',
                });
                polygonControl.set('polygonNo', -1);
                mainBar.addControl(polygonControl);

                mainBar.setPosition('bottom-right');
                theMap.addControl(mainBar);
                this.mainBar = mainBar;
                this.mainBar.setProperties({
                    building: this.state.building,
                });

                const feature = this.props.map.features.find(
                    (f: Feature<Geometry>) => f.getProperties().data?.id === this.state.building?.id
                );
                if (feature) {
                    const style = feature.getStyle() as Style;
                    this.originalStyle = style.clone();

                    style.setStroke(
                        new Stroke({
                            color: '#ff0000',
                            width: 4,
                            lineDash: [4, 1],
                            lineCap: 'butt',
                        })
                    );
                    feature.setStyle(style);
                }
            }
        }
    }

    private hideBuildingFloorsSelector(): void {
        if (this.mainBar) {
            const building = this.mainBar.getProperties()?.building;
            this.props.map?.getMap()?.removeControl(this.mainBar);
            this.mainBar = undefined;

            if (building && this.originalStyle) {
                const feature = this.props.map?.features.find(
                    (f: Feature<Geometry>) => f.getProperties().data?.id === building?.id
                );
                if (feature) {
                    feature.setStyle(this.originalStyle);
                }
            }

            this.originalStyle = undefined;
        }
    }

    render(): JSX.Element {
        return <></>;
    }
}

const mapStateToProps = (state: RootState, ownProps: MapFloorSelectorProps): MapFloorSelectorStateProps => ({
    floors: buildingFloors(ownProps.building as CompositeNode),
});

const MapFloorSelector = connect(mapStateToProps)(MapFloorSelectorImpl);
export default MapFloorSelector;
