/* eslint-disable @typescript-eslint/no-use-before-define */
import React from 'react';
import { connect } from 'react-redux';

import ISchoolElement from 'app/domain/school.element';
import * as Selectors from 'app/store/selectors';
import { DeviceState, EntityMap, RootState } from 'app/store/appState';
import { CompositeNode } from 'app/domain/composite';
import { RegionType } from 'app/utils/domain.constants';
import VectorSource from 'ol/source/Vector';
import { Feature } from 'ol';
import Zone from 'app/domain/zone';
import Region, { RegionProp } from 'app/domain/region';
import Geometry from 'ol/geom/Geometry';
import { IMarkerComponentProps } from './MarkerComponentBase';
import { ILayerHash, ILayersHashmap } from './SchoolMapComponent';
import { addFeature, getLayerHashMapByElementType, removeFeature, resolveCompositeDeviceState } from './utils/MapUtils';
import { resolveEntityTool } from './markers/toolsList';

/**
 * Represents the build component and all functionalities and its behaviors should live in here
 * @returns
 */
interface MarkerComponentReduxStoreProps {
    schoolElement?: CompositeNode;
    topZone?: Zone;
    layersHashmap?: ILayersHashmap;
    deviceState?: DeviceState;
}

interface MarkerComponentProps extends IMarkerComponentProps, MarkerComponentReduxStoreProps {}

interface MarkerComponentState {
    source: VectorSource<any>;
}

class MarkerComponentImpl extends React.Component<MarkerComponentProps, MarkerComponentState> {
    constructor(props: MarkerComponentProps) {
        super(props);

        const currentSource = getLayerHashMapByElementType(
            props.schoolElement as CompositeNode,
            this.props.layersHashmap as ILayersHashmap
        );
        this.state = {
            source: this.props.isOnEditMode ? this.props.source : (currentSource?.source as VectorSource<any>),
        };
    }

    componentDidMount(): void {
        if (this.props.schoolElement) {
            // console.log(`!!! MOUNT SCHOOL ELEMENT ${this.props.schoolElement?.name} !!!`, this.state.source);
            this.addMarker();
        }
    }

    componentDidUpdate(): void {
        // console.log('!!! UPDATED SCHOOL ELEMENT !!!', (this.props.schoolElement as CompositeNode).name);
        if (this.props.schoolElement?.id !== this.props.currentEntity?.id)
            removeFeature((this.props.schoolElement as CompositeNode).id, this.props.map, this.state.source);

        const currentSchoolFeature = this.addMarker();
        if (currentSchoolFeature) {
            // in the case that it should be updated according the event
            if (this.props.trackDeviceChanges) {
                if (currentSchoolFeature) {
                    const elementTool = resolveEntityTool(currentSchoolFeature.getProperties()?.entityType);
                    elementTool?.updateModelState?.(
                        this.props.map,
                        this.state.source as VectorSource<any>,
                        this.props.schoolElement as ISchoolElement,
                        currentSchoolFeature
                    );
                }
            }
        }
    }

    componentWillUnmount(): void {
        // console.log(`!!! UNMOUNT SCHOOL ELEMENT ${this.props.schoolElement?.name} !!!`);
        if (this.props.schoolElement && this.props.schoolElement?.id !== this.props.currentEntity?.id) {
            removeFeature((this.props.schoolElement as CompositeNode).id, this.props.map, this.state.source);
        }
    }

    addMarker(): Feature<Geometry> | undefined {
        let region =
            (this.props.schoolElement as Region).type === RegionType.POINT_REGION
                ? ({ ...this.props.schoolElement, state: this.props.deviceState } as Region)
                : (this.props.schoolElement as Region);
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
        const node = this.props.schoolElement as CompositeNode;
        const children = node?.children ? Object.values(node.children as EntityMap<CompositeNode>) : [];
        const otherElements = children.filter(c => c.type !== RegionType.FLOOR);

        if (node.type === RegionType.POINT_REGION) {
            // we do not render children devices markers.
            return <></>;
        }
        // for no reason in specific, the props does NOT comes for some cases with layersHashmap
        return !this.props.layersHashmap ? (
            <></>
        ) : (
            <>
                {/* Render the SCHOOL ELEMENT elements */}
                {otherElements.map((element: ISchoolElement) => (
                    <MarkerComponent
                        key={element.id}
                        data={element}
                        source={
                            this.props.isOnEditMode
                                ? this.props.source
                                : (getLayerHashMapByElementType(
                                      element as CompositeNode,
                                      this.props.layersHashmap as ILayersHashmap
                                  ) as ILayerHash).source
                        }
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

const mapStateToProps = (state: RootState, ownProps: IMarkerComponentProps): MarkerComponentReduxStoreProps => ({
    schoolElement: Selectors.createEntitySelector(ownProps.data as CompositeNode)(state) as CompositeNode,
    layersHashmap: state.app.map.layersHashmap as ILayersHashmap,
    topZone: Selectors.createParentZoneSelector(ownProps.data as CompositeNode)(state) as Zone,
    deviceState:
        (ownProps.data as Region).type === RegionType.POINT_REGION
            ? resolveCompositeDeviceState(state, ownProps.data as ISchoolElement).deviceState
            : undefined,
});

const MarkerComponent = connect(mapStateToProps)(MarkerComponentImpl);
export default MarkerComponent;
