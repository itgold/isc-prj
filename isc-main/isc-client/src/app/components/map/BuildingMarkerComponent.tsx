import React from 'react';
import { connect } from 'react-redux';

import ISchoolElement from 'app/domain/school.element';
import * as Selectors from 'app/store/selectors';
import { EntityMap, RootState } from 'app/store/appState';
import { CompositeNode } from 'app/domain/composite';
import { RegionType } from 'app/utils/domain.constants';
import Region, { RegionProp } from 'app/domain/region';
import VectorSource from 'ol/source/Vector';
import Zone from 'app/domain/zone';
import { Feature } from 'ol';
import Geometry from 'ol/geom/Geometry';
import { IMarkerComponentProps } from './MarkerComponentBase';
import MarkerComponent from './MarkerComponent';
import { ILayerHash } from './SchoolMapComponent';
import { addFeature, removeFeature } from './utils/MapUtils';
import FloorMarkerComponent from './FloorMarkerComponent';

/**
 * Represents the build component and all functionalities and its behaviors should live in here
 * @returns
 */
interface BuildingMarkerComponentReduxStoreProps {
    building: CompositeNode;
    selectedFloor: Region;
    layerHash: ILayerHash;
    topZone?: Zone;
}
type BuildingMarkerComponentProps = IMarkerComponentProps & BuildingMarkerComponentReduxStoreProps;

interface BuildingMarkerComponentState {
    source: VectorSource<any>;
}

class BuildingMarkerComponent extends React.Component<BuildingMarkerComponentProps, BuildingMarkerComponentState> {
    constructor(props: BuildingMarkerComponentProps) {
        super(props);

        this.state = {
            source: this.props.isOnEditMode ? this.props.source : this.props.layerHash.source,
        };
    }

    componentDidMount(): void {
        // console.log(`!!! MOUNT BUILDING ${this.props.building?.name} !!!`);
        this.addMarker();
    }

    componentDidUpdate(): void {
        // console.log('!!! UPDATED BUILDING !!!', this.props.building?.name);
        if (this.props.building?.id !== this.props.currentEntity?.id)
            removeFeature(this.props.building.id, this.props.map, this.state.source);
        this.addMarker();
    }

    componentWillUnmount(): void {
        // console.log('!!! UNMOUNT BUILDING !!!', this.props.building?.name);
        if (this.props.building?.id !== this.props.currentEntity?.id)
            removeFeature(this.props.building.id, this.props.map, this.state.source);
    }

    addMarker(): Feature<Geometry> | undefined {
        let region = this.props.building as Region;
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
        const node = this.props.building as CompositeNode;
        const children = node?.children ? Object.values(node.children as EntityMap<CompositeNode>) : [];
        const otherElements = children.filter(c => c.type !== RegionType.FLOOR);
        const allFloors = children
            .filter(c => c.type === RegionType.FLOOR)
            .filter(floor =>
                this.props.selectedFloor ? floor.id === (this.props.selectedFloor as ISchoolElement).id : true
            );
        // console.log(`=== RENDER BUILDING ${this.props.building?.name} ===`);

        return (
            <>
                {/* Render the floors */}
                {(this.props.selectedFloor as ISchoolElement) !== undefined &&
                    allFloors.map((floor: ISchoolElement) => (
                        <FloorMarkerComponent
                            key={floor.id}
                            data={floor}
                            source={this.props.isOnEditMode ? this.props.source : this.props.layerHash.source}
                            map={this.props.map}
                            trackDeviceChanges={this.props.trackDeviceChanges}
                            isOnEditMode={this.props.isOnEditMode}
                            currentEntity={this.props.currentEntity}
                        />
                    ))}

                {/* Render the building elements */}
                {!this.props.selectedFloor &&
                    otherElements.map((element: ISchoolElement) => (
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

const mapStateToProps = (
    state: RootState,
    ownProps: IMarkerComponentProps
): BuildingMarkerComponentReduxStoreProps => ({
    building: Selectors.createEntitySelector(ownProps.data as CompositeNode)(state) as CompositeNode,
    selectedFloor: Selectors.getSelectedFloorByBuilding((ownProps.data as ISchoolElement).id || '')(state) as Region,
    layerHash: state.app?.map?.layersHashmap.BUILDING as ILayerHash,
    topZone: Selectors.createParentZoneSelector(ownProps.data as CompositeNode)(state) as Zone,
});

export default connect(mapStateToProps)(BuildingMarkerComponent);
