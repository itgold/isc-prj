import React from 'react';
import { connect } from 'react-redux';
import _ from 'lodash';

import ISchoolElement from 'app/domain/school.element';
import * as Selectors from 'app/store/selectors';
import { EntityMap, RootState } from 'app/store/appState';
import { CompositeNode } from 'app/domain/composite';
import { RegionType } from 'app/utils/domain.constants';
import VectorSource from 'ol/source/Vector';
import Zone from 'app/domain/zone';
import Region, { RegionProp } from 'app/domain/region';
import { IMarkerComponentProps } from './MarkerComponentBase';
import { ILayerHash } from './SchoolMapComponent';
import { addFeature, removeFeature } from './utils/MapUtils';

/**
 * Represents the build component and all functionalities and its behaviors should live in here
 * @returns
 */
interface ZoneMarkerComponentReduxStoreProps {
    zone: CompositeNode;
    topZone?: Zone;
    layerHash: ILayerHash;
}
type ZoneMarkerComponentProps = IMarkerComponentProps & ZoneMarkerComponentReduxStoreProps;

interface ZoneMarkerComponentState {
    source: VectorSource<any>;
}

class ZoneMarkerComponentImpl extends React.Component<ZoneMarkerComponentProps, ZoneMarkerComponentState> {
    constructor(props: ZoneMarkerComponentProps) {
        super(props);

        this.state = {
            source: this.props.isOnEditMode ? this.props.source : this.props.layerHash.source,
        };
    }

    componentDidMount(): void {
        // console.log(`!!! MOUNT ZONE ${this.props.zone?.name} !!!`);
        this.addZoneMarker();
    }

    /*
    shouldComponentUpdate(nextProps: ZoneMarkerComponentProps): boolean {
        if (
            _.isEqual(this.props.zone, nextProps.zone) &&
            _.isEqual(this.props.selectedZones, nextProps.selectedZones)
        ) {
            return false;
        }

        // removeFeature(this.props.zone.id, this.props.map, this.state.source);
        return true;
    }
    */

    componentDidUpdate(): void {
        // console.log('!!! UPDATED ZONE !!!', this.props.zone?.name);
        if (this.props.zone?.id !== this.props.currentEntity?.id)
            removeFeature(this.props.zone.id, this.props.map, this.state.source);
        this.addZoneMarker();
    }

    componentWillUnmount(): void {
        // console.log('!!! UNMOUNT ZONE !!!', this.props.zone?.name);
        if (this.props.zone?.id !== this.props.currentEntity?.id)
            removeFeature(this.props.zone.id, this.props.map, this.state.source);
    }

    addZoneMarker(): void {
        if (this.props.topZone?.id) {
            let region = this.props.zone as Region;
            if (this.props.topZone) {
                const color = this.props.topZone.props?.find(p => p.key === 'color');
                const ownColor = region.props?.find(p => p.key === 'color');
                const props: RegionProp[] = [
                    ...(region.props || []).filter(p => p.key !== 'color'),
                    { key: 'color', value: color?.value || ownColor?.value || null },
                ];
                region = { ...region, props };
            }
            addFeature(region, this.state.source);
        }
    }

    render(): React.ReactNode {
        const node = this.props.data as CompositeNode;
        const children = node?.children ? Object.values(node.children as EntityMap<CompositeNode>) : [];
        const zones = children.filter(c => c.type === RegionType.ZONE);

        return (
            <>
                {/* Render the floors */}
                {zones.map((floor: ISchoolElement) => (
                    // eslint-disable-next-line @typescript-eslint/no-use-before-define
                    <ZoneMarkerComponent
                        key={floor.id}
                        data={floor}
                        source={this.props.isOnEditMode ? this.props.source : this.props.layerHash.source}
                        map={this.props.map}
                        trackDeviceChanges={this.props.trackDeviceChanges}
                        isOnEditMode={this.props.isOnEditMode}
                    />
                ))}
            </>
        );
    }
}

const mapStateToProps = (state: RootState, ownProps: IMarkerComponentProps): ZoneMarkerComponentReduxStoreProps => ({
    zone: Selectors.createEntitySelector(ownProps.data as CompositeNode)(state) as CompositeNode,
    topZone: Selectors.createParentZoneSelector(ownProps.data as CompositeNode)(state) as Zone,
    layerHash: state.app?.map?.layersHashmap.BUILDING as ILayerHash,
});

const ZoneMarkerComponent = connect(mapStateToProps)(ZoneMarkerComponentImpl);
export default ZoneMarkerComponent;
