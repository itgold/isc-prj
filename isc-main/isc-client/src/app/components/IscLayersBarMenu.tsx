import React, { useState } from 'react';
import { Checkbox, FormControlLabel } from '@material-ui/core';
import { Healing, LockOpen, Speaker, Storage, Toys, Videocam, DirectionsWalk } from '@material-ui/icons';
import { RootState } from 'app/store/appState';
import { useSelector } from 'react-redux';
import { allLayersSelector } from 'app/store/selectors';
import VectorLayer from 'ol/layer/Vector';
import { useLocation } from 'react-router';
import userPreferenceService, { UserContexts } from 'app/services/userPreference.service';
import LayerTypePreference from 'app/services/preferenceTypes/LayerTypePreference';
import { ILayersHashmap } from './map/SchoolMapComponent';

const IscLayersBarMenu = (): JSX.Element | null => {
    console.log('layers updated');
    const userPreferences = userPreferenceService.loadPreferences(UserContexts.layers) as LayerTypePreference;

    const currentLocation = useLocation();
    const [forceRefresh, setForceRefresh] = useState(false);
    const hashmapLayers = useSelector<RootState>(allLayersSelector);
    const setLayerVisibility = (layer: VectorLayer<any>, layerName: string): void => {
        if (layer) {
            layer.setVisible(!layer.getVisible());
            let hiddenLayers = userPreferences.hiddenLayers || [];
            if (!layer.getVisible()) {
                hiddenLayers.push(layerName);
            } else {
                hiddenLayers = hiddenLayers.filter(l => l !== layerName);
            }

            userPreferenceService.updatePreference(UserContexts.layers, {
                hiddenLayers,
            } as LayerTypePreference);
            setForceRefresh(!forceRefresh);
        }
    };

    const getVisibility = (layer: VectorLayer<any>, layerName: string): boolean =>
        (!userPreferences.hiddenLayers?.includes(layerName) && layer && layer.getVisible()) || layer.getVisible();

    return !currentLocation.pathname.toLowerCase().includes('dashboards/map') ? null : (
        <div style={{ padding: 10 }}>
            <FormControlLabel
                control={
                    <Checkbox
                        icon={<Videocam />}
                        checkedIcon={<Videocam />}
                        checked={getVisibility(
                            (hashmapLayers as ILayersHashmap)?.CAMERA?.layer as VectorLayer<any>,
                            'camera'
                        )}
                        onChange={() =>
                            setLayerVisibility(
                                (hashmapLayers as ILayersHashmap)?.CAMERA?.layer as VectorLayer<any>,
                                'camera'
                            )
                        }
                    />
                }
                label="Camera"
                color="secondary"
            />
            <FormControlLabel
                control={
                    <Checkbox
                        icon={<LockOpen />}
                        checkedIcon={<LockOpen />}
                        checked={getVisibility(
                            (hashmapLayers as ILayersHashmap)?.DOOR?.layer as VectorLayer<any>,
                            'door'
                        )}
                        onChange={() =>
                            setLayerVisibility(
                                (hashmapLayers as ILayersHashmap)?.DOOR?.layer as VectorLayer<any>,
                                'door'
                            )
                        }
                    />
                }
                label="Door"
            />
            <FormControlLabel
                control={<Checkbox icon={<Toys />} checkedIcon={<Toys />} name="" />}
                label="Drone"
                checked={getVisibility((hashmapLayers as ILayersHashmap)?.DRONE?.layer as VectorLayer<any>, 'drone')}
                onChange={() =>
                    setLayerVisibility((hashmapLayers as ILayersHashmap)?.DRONE?.layer as VectorLayer<any>, 'drone')
                }
            />
            <FormControlLabel
                control={
                    <Checkbox
                        icon={<Speaker />}
                        checkedIcon={<Speaker />}
                        checked={getVisibility(
                            (hashmapLayers as ILayersHashmap)?.SPEAKER?.layer as VectorLayer<any>,
                            'speaker'
                        )}
                        onChange={() =>
                            setLayerVisibility(
                                (hashmapLayers as ILayersHashmap)?.SPEAKER?.layer as VectorLayer<any>,
                                'speaker'
                            )
                        }
                    />
                }
                label="Speaker"
            />
            <FormControlLabel
                control={
                    <Checkbox
                        icon={<Storage />}
                        checkedIcon={<Storage />}
                        checked={getVisibility(
                            (hashmapLayers as ILayersHashmap)?.UTILITY?.layer as VectorLayer<any>,
                            'utility'
                        )}
                        onChange={() =>
                            setLayerVisibility(
                                (hashmapLayers as ILayersHashmap)?.UTILITY?.layer as VectorLayer<any>,
                                'utility'
                            )
                        }
                    />
                }
                label="Utility"
            />
            <FormControlLabel
                control={
                    <Checkbox
                        icon={<Healing />}
                        checkedIcon={<Healing />}
                        checked={getVisibility(
                            (hashmapLayers as ILayersHashmap)?.SAFETY?.layer as VectorLayer<any>,
                            'safety'
                        )}
                        onChange={() =>
                            setLayerVisibility(
                                (hashmapLayers as ILayersHashmap)?.SAFETY?.layer as VectorLayer<any>,
                                'safety'
                            )
                        }
                    />
                }
                label="Safety"
            />

            <FormControlLabel
                control={
                    <Checkbox
                        icon={<DirectionsWalk />}
                        checkedIcon={<DirectionsWalk />}
                        checked={getVisibility(
                            (hashmapLayers as ILayersHashmap)?.RADIO?.layer as VectorLayer<any>,
                            'radio'
                        )}
                        onChange={() =>
                            setLayerVisibility(
                                (hashmapLayers as ILayersHashmap)?.RADIO?.layer as VectorLayer<any>,
                                'radio'
                            )
                        }
                    />
                }
                label="Radio"
            />
        </div>
    );
};

export default IscLayersBarMenu;
