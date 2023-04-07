import { StyleProvider } from '@common/components/map/animation/animation.utils';
import { IMapComponent } from '@common/components/map/map';
import ISchoolElement from 'app/domain/school.element';
import Feature from 'ol/Feature';
import Geometry from 'ol/geom/Geometry';
import GeometryType from 'ol/geom/GeometryType';
import { GeometryFunction } from 'ol/interaction/Draw';
import VectorSource from 'ol/source/Vector';
import { Style } from 'ol/style';
import IDeviceAction from '../deviceActions/IDeviceAction';
import { FloatingWindowProps } from '../FloatingWindow';

export default interface ITool {
    /**
     * Icon to be displayed
     */
    icon: any;
    /**
     * THe type of geometry to used as reference to dran the mark
     */
    value?: string; // ol/geom/GeometryType
    /**
     * the entity Type that comes from DB which defines our mark
     */
    entityType: string;
    /**
     * the function that will be replacing the current one to drawn the mark
     */
    geometryFunction?: GeometryFunction;
    /**
     * Defines the style (colors, shapes, lines, etc) for this mark
     */
    style?: Style | Style[];

    fromModel?: (model: ISchoolElement) => Feature<Geometry> | undefined;

    getLabel?: (model: ISchoolElement) => string;

    updateModelState?: (
        map: IMapComponent,
        source: VectorSource<any>,
        model: ISchoolElement,
        feature?: Feature<Geometry>,
        ignoreState?: boolean
    ) => void;

    /**
     * Defines a default animation style for the current tool which may change according the input... when set
     */
    getAnimationStyle?: StyleProvider;

    getStateString?: (model: ISchoolElement) => string;

    getDeviceActions?: (model: ISchoolElement, userRoles: string[]) => IDeviceAction[];

    getLayerName?: (model: ISchoolElement) => string;

    floatingWindow?: () => React.ComponentType<FloatingWindowProps>;
}
