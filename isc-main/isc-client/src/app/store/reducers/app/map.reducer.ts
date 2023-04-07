import { ILayersHashmap } from 'app/components/map/SchoolMapComponent';
import { createLayer, getNewSource } from 'app/components/map/utils/MapUtils';
import Region from 'app/domain/region';
import * as Actions from 'app/store/actions';
import VectorLayer from 'ol/layer/Vector';
import { handleActions } from 'redux-actions';
import storageService from 'app/services/storage.service';
import userPreferencesService, { UserContexts } from 'app/services/userPreference.service';
import LayerTypePreference from 'app/services/preferenceTypes/LayerTypePreference';
import { CompositeNode } from '../../../domain/composite';

export interface IMapBuildingState {
    [buildingId: string]: Region;
    layersHashmap: ILayersHashmap;
}

const buildLayerHashmap = (): ILayersHashmap => {
    const getMinZoomByLayer = (layerName: string): number | undefined => {
        const layersConfig: { [key: string]: number } = {
            DOOR: 17,
            CAMERA: 17,
            DRONE: 17,
            SPEAKER: 17,
            SAFETY: 17,
            ROOM: 16,
            WALL: 16,
            STAIRS: 16,
            ELEVATOR: 16,
            FLOOR: 15,
            UTILITY: 17,
            ZONE: 16,
            BUILDING: 14,
        };

        return layersConfig[layerName];
    };

    const layerHashmap: ILayersHashmap = {};
    // Attention: The order of the elements of the array is important in order to set the right z-index
    // to not overlap other elements while trying to be reached by context-menu
    [
        'MAP_EDITOR',
        'BUILDING',
        'ZONE',
        'FLOOR',
        'UTILITY',
        'ROOM',
        'WALL',
        'STAIRS',
        'ELEVATOR',
        'DOOR',
        'CAMERA',
        'DRONE',
        'SPEAKER',
        'SAFETY',
        'RADIO',
        'DEFAULT',
    ].forEach((layerType, index) => {
        const newSource = getNewSource();
        const userPreferences = userPreferencesService.loadPreferences(UserContexts.layers) as LayerTypePreference;

        if (newSource) {
            const newLayer = createLayer(newSource, index + 1, getMinZoomByLayer(layerType)) as VectorLayer<any>;
            layerHashmap[layerType as string] = {
                source: newSource,
                layer: newLayer,
            };
            newLayer.set('name', layerType);
            // set the visibility according what was persisted in the user preference.. in the case that there's one
            if (userPreferences.hiddenLayers) {
                newLayer.setVisible(!userPreferences.hiddenLayers?.includes(layerType.toLowerCase()));
            }

            newSource.setProperties({
                ...newSource.getProperties(),
                name: layerType,
            });
        }
    });

    return layerHashmap;
};

// hashmap ref build and the current floor selected
const initialState: IMapBuildingState = {
    layersHashmap: buildLayerHashmap(),
};

export const MAP_STATE_COOKIE = 'IscDashboardBuildings';

function updateMapState(mapState: IMapBuildingState): void {
    const tempState: { [key: string]: unknown } = {};

    // just deleting the prop `layersHashmap` wont work because the contract of the interface
    Object.entries(mapState).forEach(([key, value]) => {
        if (key !== 'layersHashmap' && value)
            tempState[key] = {
                // only the id is stored in the session storage
                id: value.id,
            };
    });

    storageService.updateProperty(MAP_STATE_COOKIE, JSON.stringify(tempState));
}

const mapReducer = handleActions(
    {
        // set the floor to a specific building
        [Actions.setFloorSelection.toString()]: (state, action) => {
            const newState = {
                ...state,
                ...action.payload,
            };
            updateMapState(newState);

            return newState;
        },
        // removes the building which contains the selected floor
        [Actions.clearFloorSelection.toString()]: (state, action) => {
            const newState = { ...state };
            delete newState[(action.payload as unknown) as string];
            updateMapState(newState);

            return newState;
        },
        [Actions.setMapLayers.toString()]: (state, action) => ({
            ...state,
            // FIXME: Check with Denis how to cast it properly
            layersHashmap: action.payload as any,
        }),
        [Actions.showMapLayer.toString()]: (state, action) => {
            const layer = (state.layersHashmap as ILayersHashmap)[
                (action.payload as CompositeNode).entityType as string
            ]?.layer as VectorLayer<any>;

            layer?.setVisible(true);
            return state;
        },
    },
    initialState
);

export default mapReducer;
