import React from 'react';
import { connect } from 'react-redux';
import ISchoolElement from 'app/domain/school.element';
import * as Selectors from 'app/store/selectors';
import { EntityMap, RootState } from 'app/store/appState';
import { CompositeNode } from 'app/domain/composite';
import { RegionType } from 'app/utils/domain.constants';
import VectorSource from 'ol/source/Vector';
import Zone from 'app/domain/zone';
import { Feature } from 'ol';
import Region, { RegionProp } from 'app/domain/region';
import Geometry from 'ol/geom/Geometry';
import { IMarkerComponentProps } from './MarkerComponentBase';
import MarkerComponent from './MarkerComponent';
import { ILayerHash } from './SchoolMapComponent';
import { addFeature, removeFeature } from './utils/MapUtils';

/**
 * Represents the build component and all functionalities and its behaviors should live in here
 * @returns
 */
interface FloorMarkerComponentReduxStoreProps {
    floor: CompositeNode;
    layerHash: ILayerHash;
    topZone?: Zone;
}
type FloorMarkerComponentProps = IMarkerComponentProps & FloorMarkerComponentReduxStoreProps;

interface FloorMarkerComponentState {
    source: VectorSource<any>;
}

class FloorMarkerComponent extends React.Component<FloorMarkerComponentProps, FloorMarkerComponentState> {
    constructor(props: FloorMarkerComponentProps) {
        super(props);

        this.state = {
            source: this.props.isOnEditMode ? this.props.source : this.props.layerHash.source,
        };
    }

    componentDidMount(): void {
        // console.log('!!! MOUNT FLOOR !!!', this.props.floor);
        this.addMarker();
    }

    componentDidUpdate(): void {
        // console.log('!!! UPDATED FLOOR !!!', this.props.floor.name);
        if (this.props.floor?.id !== this.props.currentEntity?.id)
            removeFeature(this.props.floor.id, this.props.map, this.state.source);
        this.addMarker();
    }

    componentWillUnmount(): void {
        // console.log('!!! UNMOUNT FLOOR !!!', this.props.floor.name);
        if (this.props.floor?.id !== this.props.currentEntity?.id)
            removeFeature(this.props.floor.id, this.props.map, this.state.source);
    }

    addMarker(): Feature<Geometry> | undefined {
        let region = this.props.floor as Region;
        if (this.props.topZone?.id) {
            const color = this.props.topZone.props?.find(p => p.key === 'color');
            const ownColor = region.props?.find(p => p.key === 'color');
            const props: RegionProp[] = [
                ...(region.props || []).filter(p => p.key !== 'color'),
                { key: 'color', value: color?.value || ownColor?.value || null },
            ];
            region = { ...region, props };
        }
        return addFeature(region, this.state.source);
    }

    render(): React.ReactNode {
        const node = this.props.floor as CompositeNode;
        const children = node?.children ? Object.values(node.children as EntityMap<CompositeNode>) : [];
        const otherElements = children.filter(c => c.type !== RegionType.FLOOR);
        // console.log('!!! RENDER FLOOR !!!', this.props.floor.name, otherElements);

        return (
            <>
                {/* Render the building elements */}
                {otherElements.map((element: ISchoolElement) => (
                    <MarkerComponent
                        key={element.id}
                        data={element}
                        source={this.props.isOnEditMode ? this.props.source : this.props.layerHash.source}
                        map={this.props.map}
                        trackDeviceChanges={this.props.trackDeviceChanges}
                        isOnEditMode={this.props.isOnEditMode}
                        currentEntity={this.props.currentEntity}
                    />
                ))}
            </>
        );
    }
}

const mapStateToProps = (state: RootState, ownProps: IMarkerComponentProps): FloorMarkerComponentReduxStoreProps => ({
    floor: Selectors.createEntitySelector(ownProps.data as CompositeNode)(state) as CompositeNode,
    layerHash: state.app?.map?.layersHashmap.FLOOR as ILayerHash,
    topZone: Selectors.createParentZoneSelector(ownProps.data as CompositeNode)(state) as Zone,
});

export default connect(mapStateToProps)(FloorMarkerComponent);
