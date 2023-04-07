import React from 'react';
import { connect } from 'react-redux';

import * as Selectors from 'app/store/selectors';
import { CompositeNode } from 'app/domain/composite';
import School from 'app/domain/school';
import { EntityMap, RootState } from 'app/store/appState';
import { RegionType } from 'app/utils/domain.constants';
import VectorSource from 'ol/source/Vector';
import BuildingMarkerComponent from './BuildingMarkerComponent';
import MarkerComponent from './MarkerComponent';
import { IMarkerComponentProps } from './MarkerComponentBase';
import { ILayerHash } from './SchoolMapComponent';
import ISchoolElement from '../../domain/school.element';
import { addFeature, removeFeature } from './utils/MapUtils';
import ZoneMarkerComponent from './ZoneMarkerComponent';

interface SchoolMarkerComponentReduxStoreProps {
    school: School;
    layerHash: ILayerHash;
}
type SchoolMarkerComponentProps = IMarkerComponentProps & SchoolMarkerComponentReduxStoreProps;

interface SchoolMarkerComponentState {
    source: VectorSource<any>;
}

class SchoolMarkerComponent extends React.Component<SchoolMarkerComponentProps, SchoolMarkerComponentState> {
    constructor(props: SchoolMarkerComponentProps) {
        super(props);

        this.state = {
            source: this.props.isOnEditMode ? this.props.source : this.props.layerHash.source,
        };
    }

    componentDidMount(): void {
        // console.log('!!! MOUNT SCHOOL !!!', this.props.school.name);
        addFeature(this.props.school as ISchoolElement, this.state.source);
    }

    componentDidUpdate(): void {
        // console.log('!!! UPDATED SCHOOL !!!', this.props.school.name);
        if (this.props.school?.id !== this.props.currentEntity?.id)
            removeFeature(this.props.school.id, this.props.map, this.state.source);
        addFeature(this.props.school as ISchoolElement, this.state.source);
    }

    componentWillUnmount(): void {
        // console.log('!!! UNMOUNT SCHOOL !!!', this.props.school.name);
        if (this.props.school?.id !== this.props.currentEntity?.id)
            removeFeature(this.props.school.id, this.props.map, this.state.source);
    }

    render(): React.ReactNode {
        const node = this.props.school as CompositeNode;
        const children = node?.children ? Object.values(node.children as EntityMap<CompositeNode>) : [];
        // console.log('=== RENDER SCHOOL ===', this.props.school.name);

        return (
            <>
                {/* Render all buildings which belongs to the school */}
                {children.map(element =>
                    element.type === RegionType.BUILDING ? (
                        <BuildingMarkerComponent
                            key={element.id}
                            source={this.props.isOnEditMode ? this.props.source : this.props.layerHash.source}
                            layer={this.props.layer}
                            map={this.props.map}
                            data={element}
                            trackDeviceChanges={this.props.trackDeviceChanges}
                            isOnEditMode={this.props.isOnEditMode}
                            currentEntity={this.props.currentEntity}
                        />
                    ) : element.type !== RegionType.ZONE ? (
                        // render all external elements which belongs to the school and NOT to the building
                        <MarkerComponent
                            key={element.id}
                            map={this.props.map}
                            source={this.props.isOnEditMode ? this.props.source : this.props.layerHash.source}
                            data={element}
                            layer={this.props.layer}
                            trackDeviceChanges={this.props.trackDeviceChanges}
                            isOnEditMode={this.props.isOnEditMode}
                            currentEntity={this.props.currentEntity}
                        />
                    ) : (
                        // render all zones at last
                        <ZoneMarkerComponent
                            key={element.id}
                            source={this.props.isOnEditMode ? this.props.source : this.props.layerHash.source}
                            layer={this.props.layer}
                            map={this.props.map}
                            data={element}
                            trackDeviceChanges={this.props.trackDeviceChanges}
                            isOnEditMode={this.props.isOnEditMode}
                            currentEntity={this.props.currentEntity}
                        />
                    )
                )}
            </>
        );
    }
}

const mapStateToProps = (state: RootState, ownProps: IMarkerComponentProps): SchoolMarkerComponentReduxStoreProps => ({
    school: Selectors.createEntitySelector(ownProps.data as CompositeNode)(state) as School,
    layerHash: state.app?.map?.layersHashmap.DEFAULT as ILayerHash,
});

export default connect(mapStateToProps)(SchoolMarkerComponent);
