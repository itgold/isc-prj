/* eslint-disable class-methods-use-this */
import GeometryType from 'ol/geom/GeometryType';
import Details from '@material-ui/icons/Details';
import { EntityType, RegionType } from 'app/utils/domain.constants';
import ISchoolElement from 'app/domain/school.element';
import Feature from 'ol/Feature';
import Geometry from 'ol/geom/Geometry';
import { Fill, Stroke, Style } from 'ol/style';
import { fromLonLat } from 'ol/proj';
import Polygon from 'ol/geom/Polygon';
import { StyleProviderOptions } from '@common/components/map/animation/animation.utils';
import Region from 'app/domain/region';
import { authRoles } from 'app/auth';
import FuseUtils from '@fuse/utils';
import { hexToRGB } from '@common/components/colors';
import { StyleLike } from 'ol/style/Style';
import { BaseTool } from './baseTool';
import IDeviceAction from '../deviceActions/IDeviceAction';
import ShowZone from '../deviceActions/ShowZone';
import ClearFloorSelection from '../deviceActions/ClearFloorSelection';
import SelectFloor from '../deviceActions/SelectFloor';
import ResetSchool from '../deviceActions/ResetSchool';
import LockDown from '../deviceActions/LockDown';
import EndEmergency from '../deviceActions/EndEmergency';
import EmergencyOpen from '../deviceActions/EmergencyOpen';
import OpenDoor from '../deviceActions/OpenDoor';

/**
 * Specifies the region element
 */
class RegionTool extends BaseTool {
    icon = Details;

    value = GeometryType.POLYGON;

    entityType = EntityType.REGION;

    defaultOpacity = 1;

    style = new Style({
        stroke: new Stroke({
            color: 'blue',
            width: 1,
        }),
        fill: new Fill({
            color: 'rgba(0, 0, 255, 0.1)',
        }),
        zIndex: 1,
    });

    calculateDefaultStyle = (color?: string | null): Style => {
        return new Style({
            stroke: new Stroke({
                color: 'blue',
                width: 1,
            }),
            fill: new Fill({
                color: color || 'rgba(0, 0, 255, 0.1)',
            }),
            zIndex: 1,
        });
    };

    entityTypeStyles = [
        {
            typeName: RegionType.SCHOOL,
            calculateStyle: (color?: string | null) => {
                return new Style({
                    stroke: new Stroke({
                        color: color || 'orange',
                        width: 3,
                        lineDash: [4, 2],
                        lineCap: 'butt',
                    }),
                });
            },
        },
        {
            typeName: RegionType.BUILDING,
            calculateStyle: (color?: string | null) => {
                return new Style({
                    stroke: new Stroke({
                        color: 'gray',
                        width: 3,
                        lineDash: [1, 5],
                        lineCap: 'square',
                    }),
                    fill: new Fill({
                        color: `rgba(${hexToRGB(color || '#F7F7F7')}, 0.5)`,
                    }),
                });
            },
        },
        {
            typeName: RegionType.FLOOR,
            calculateStyle: (color?: string | null) => {
                return new Style({
                    stroke: new Stroke({
                        color: 'blue',
                        width: 1,
                        lineDash: [1, 5],
                        lineCap: 'square',
                    }),
                    fill: new Fill({
                        color: `rgba(${hexToRGB(color || '#F7F7F7')}, 0.5)`,
                    }),
                });
            },
        },
        {
            typeName: RegionType.STAIRS,
            calculateStyle: (color?: string | null) => {
                return new Style({
                    stroke: new Stroke({
                        color: '#ffa700',
                        width: 3,
                        lineDash: [1, 2],
                        lineCap: 'butt',
                    }),
                    fill: new Fill({
                        color: `rgba(${hexToRGB(color || '#FFFF00')}, 0.5)`,
                    }),
                });
            },
        },
        {
            typeName: RegionType.ELEVATOR,
            calculateStyle: (color?: string | null) => {
                return new Style({
                    stroke: new Stroke({
                        color: 'gray',
                        width: 3,
                        lineDash: [1, 2],
                        lineCap: 'butt',
                    }),
                    fill: new Fill({
                        color: `rgba(${hexToRGB(color || '#808080')}, 0.5)`,
                    }),
                });
            },
        },
        {
            typeName: RegionType.ZONE,
            calculateStyle: (color?: string | null) => {
                return new Style({
                    stroke: new Stroke({
                        color: color || 'orange',
                        width: 4,
                        lineDash: [4, 4],
                        lineCap: 'butt',
                    }),
                });
            },
        },
    ];

    getLabel(model: ISchoolElement): string {
        const type = (model as Region)?.type;
        if (type) {
            return type.charAt(0).toUpperCase() + type.substring(1).toLowerCase();
        }

        return 'Region';
    }

    fromModel(model: ISchoolElement): Feature<Geometry> | undefined {
        let feature;
        const { geoBoundaries } = model;
        if (geoBoundaries && geoBoundaries.points) {
            const polygn = new Polygon([geoBoundaries.points.map(geo => fromLonLat([geo.x, geo.y]))]);
            feature = new Feature<Geometry>();
            feature.setGeometry(polygn);
            feature.setStyle(this.calculateStyle(model));
            feature.setProperties({
                entityType: EntityType.REGION,
                data: model,
            });
        }
        return feature;
    }

    private calculateStyle(model: ISchoolElement): StyleLike {
        const color = (model as Region).props?.find(p => p.key === 'color');

        const currentStyle = this.entityTypeStyles?.find(s => s.typeName === (model as Region).type);
        const style = currentStyle?.calculateStyle(color?.value) || this.calculateDefaultStyle(color?.value);

        return style;
    }

    getAnimationStyle(feature: Feature<Geometry>, options?: StyleProviderOptions): Style {
        const opacity = options?.opacity || this.defaultOpacity;

        return new Style({
            fill: new Fill({
                color: `rgba(255, 127, 22, ${opacity})`,
            }),
        });
    }

    getStateString(/* model: ISchoolElement */): string {
        return '';
    }

    getDeviceActions(model: ISchoolElement, userRoles: string[]): IDeviceAction[] {
        const actions = super.getDeviceActions(model, userRoles);
        const region = model as Region;
        switch (region.type) {
            case RegionType.ZONE:
                actions.push(new ShowZone());
                break;
            case RegionType.BUILDING:
                actions.push(new ClearFloorSelection());
                break;
            case RegionType.FLOOR:
                actions.push(new SelectFloor());
                break;
            case RegionType.SCHOOL:
                while (actions.length) actions.pop();
                actions.push(new ResetSchool());
                break;
            default:
        }

        if (
            region.type === RegionType.BUILDING ||
            region.type === RegionType.FLOOR ||
            region.type === RegionType.ROOM ||
            region.type === RegionType.WALL ||
            region.type === RegionType.STAIRS ||
            region.type === RegionType.ELEVATOR ||
            region.type === RegionType.SCHOOL
        ) {
            if (FuseUtils.hasPermission(authRoles.Analyst, userRoles)) {
                actions.push(new LockDown());
                actions.push(new EmergencyOpen());
                actions.push(new EndEmergency());
                actions.push(new OpenDoor());
            }
        }

        return actions;
    }

    getLayerName(model: ISchoolElement): string {
        let layerName;
        const region = model as Region;
        switch (region.type) {
            case RegionType.ROOM:
            case RegionType.FLOOR:
            case RegionType.BUILDING:
                layerName = region.type;
                break;
            default:
                layerName = 'DEFAULT';
        }

        return layerName;
    }
}

const region = new RegionTool();
export default region;
