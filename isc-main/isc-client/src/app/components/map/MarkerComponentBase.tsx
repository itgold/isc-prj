import { IMapComponent } from '@common/components/map/map';
import ISchoolElement from 'app/domain/school.element';
import Zone from 'app/domain/zone';
import BaseLayer from 'ol/layer/Base';
import VectorSource from 'ol/source/Vector';

/**
 * Defines a shareable props to be used for Map/levels of elements which are responsible to be
 * rendering elements linked into it (Regions, for example. Zone could also be used on it)
 */
export interface IMarkerComponentProps {
    /**
     *  Data for the layer
     */
    source: VectorSource<any>;
    /**
     * A layer is a visual representation of data from a source. Defines whether the layer for the current marker
     */
    layer?: BaseLayer;
    /**
     * Defines whether the element/device should be tracked or not
     */
    trackDeviceChanges?: boolean;
    /**
     * Data to be rendered in the current marker component
     */
    data: ISchoolElement | ISchoolElement[];
    /**
     * Map component which will be used as reference to render the markers
     */
    map: IMapComponent;
    /**
     * Determines if the marker is being consumed from a Map Editor
     */
    isOnEditMode?: boolean;

    topZone?: Zone;

    currentEntity?: ISchoolElement;
}
