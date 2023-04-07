/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable no-unused-vars */
/* eslint-disable class-methods-use-this */
import { AnimationColors, RGBColor } from '@common/components/colors';
import { StyleProviderOptions } from '@common/components/map/animation/animation.utils';
import { IMapComponent } from '@common/components/map/map';
import { PriorityHigh } from '@material-ui/icons';
import ISchoolElement from 'app/domain/school.element';
import { EntityType, hasGeoData } from 'app/utils/domain.constants';
import { Feature } from 'ol';
import { upAndDown } from 'ol/easing';
import Geometry from 'ol/geom/Geometry';
import GeometryType from 'ol/geom/GeometryType';
import VectorSource from 'ol/source/Vector';
import { Fill, Stroke, Style } from 'ol/style';
import CircleStyle from 'ol/style/Circle';
import FindOnTheMap from '../deviceActions/FindOnTheMap';
import IDeviceAction from '../deviceActions/IDeviceAction';
import ITool from './ITool';

export class BaseTool implements ITool {
    icon = PriorityHigh;

    value = GeometryType.POINT;

    entityType = EntityType.UNKNOWN;

    updateModelState(
        map: IMapComponent,
        source: VectorSource<any>,
        model: ISchoolElement,
        feature?: Feature<Geometry>
    ): void {
        const existingFeature = map.features.find((f: Feature<Geometry>) => f.getProperties()?.data?.id === model.id);
        if (existingFeature) {
            source.removeFeature(existingFeature);
        }

        if (feature && model.id && hasGeoData(model)) {
            source.addFeature(feature);
        }
    }

    getStateString(model: ISchoolElement): string {
        return 'UNKNOWN';
    }

    getDeviceActions(model: ISchoolElement, userRoles: string[]): IDeviceAction[] {
        return [new FindOnTheMap()];
    }

    getLayerName(model: ISchoolElement): string {
        return 'DEFAULT';
    }

    getLabel(model: ISchoolElement): string {
        return 'Unknown';
    }

    protected calculateAnimationColor(model: ISchoolElement): RGBColor | null {
        return AnimationColors.Green;
    }

    getAnimationStyle(feature: Feature<Geometry>, options?: StyleProviderOptions): Style {
        const effect = options?.effect || upAndDown;
        const elapsedRatio = options?.elapsedRatio || 1;
        const radius = effect(elapsedRatio) * 25 + 5;
        const opacity = effect(1 - elapsedRatio);
        const color =
            this.calculateAnimationColor?.(feature.getProperties().data as ISchoolElement) || AnimationColors.Blue;

        return new Style({
            image: new CircleStyle({
                radius,
                fill: new Fill({
                    color: `rgba(${color?.r}, ${color?.g}, ${color?.b}, ${opacity / 2} )`,
                }),
                stroke: new Stroke({
                    color: `rgba(${color?.r}, ${color?.g}, ${color?.b}, ${opacity} )`,
                    width: 0.25 + opacity,
                }),
            }),
        });
    }
}
