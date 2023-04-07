import React, { Fragment, useRef, useState } from 'react';
import ErrorDialog, { IErrorDialog } from 'app/components/app/ServerErrorDialog';
import IscPageSimple from 'app/components/layout/IscPageSimple';
import {
    Card,
    CardContent,
    CardActions,
    makeStyles,
    Typography,
    Button,
    FormControl,
    MenuItem,
    LinearProgress,
    DialogTitle,
    DialogContent,
    DialogContentText,
    DialogActions,
} from '@material-ui/core';
import clsx from 'clsx';
import FullSizeLayout from '@common/components/layout/fullsize.layout';
import Formsy from 'formsy-react';
import { CheckboxFormsy, LabelFormsy, SelectFormsy, TextFieldFormsy } from '@fuse/core/formsy';
import * as Selectors from 'app/store/selectors';
import * as Actions from 'app/store/actions/admin';
import * as FuseActions from 'app/store/actions/fuse';
import { useSelector, useDispatch } from 'react-redux';
import School from 'app/domain/school';
import { RootState } from 'app/store/appState';
import { convertRegionsTree } from 'app/components/map/utils/MapUtils';
import Region from 'app/domain/region';
import { TreeItemData } from '@common/components/tree.dropdown';
import queryService, { CrudResponse } from 'app/services/query.service';
import { IEntityField } from 'app/components/crud/types';
import { ObjectMap, RegionType } from 'app/utils/domain.constants';
import { CompositeNode } from 'app/domain/composite';

const useStyles = makeStyles(theme => ({
    importRoot: {
        backgroundColor: theme.palette.background.default,
        color: theme.palette.primary.contrastText,
    },
    panel: {
        maxWidth: '80%',
        minWidth: 400,
    },
    actions: {
        margin: 'auto',
        display: 'flex',
        outline: '0',
        position: 'relative',
        justifyContent: 'center',
    },
}));

interface FormModel {
    geoJSON: string;
    region: string[];
    school: string;
    regionType: string;
}

interface MigrationToolState {
    valid: boolean;
    isSubmitting: boolean;
}

interface MigrationToolProps {
    schools: School[];
}

function MigrationTool(props: MigrationToolProps): JSX.Element {
    const classes = useStyles();

    const errorRef = useRef<IErrorDialog>(null);
    const formRef = useRef<Formsy>(null);
    const uploadInputRef = useRef<HTMLInputElement>(null);
    const dispatch = useDispatch();

    const [schoolId, setSchoolId] = useState<string | undefined>();
    const [state, setState] = useState<MigrationToolState>({
        valid: false,
        isSubmitting: false,
    });
    const [geoFields, setGeoFields] = useState<IEntityField[]>([]);
    const [model, setModel] = useState<FormModel>({
        geoJSON: '',
        school: '',
        region: [],
        regionType: 'BUILDING',
    });
    const schoolRegion = useSelector<RootState, Region>(Selectors.createSchoolRegionsTreeSelector(schoolId));

    const topTreeNodes = schoolRegion
        ? convertRegionsTree('UNKNOWN', [
              {
                  ...(schoolRegion as TreeItemData),
              },
          ])
        : [];

    const enableButton = (enable: boolean): void => {
        setState({
            ...state,
            valid: enable,
        });
    };

    const handleGeoJsonChange = (text: string): void => {
        try {
            const parsed = JSON.parse(text);
            const tempFields: IEntityField[] = [];
            if (parsed.features.length > 0)
                tempFields.push({
                    name: 'lbSelectProperties',
                    type: 'group',
                    inputLabel: 'GeoJSON element properties',
                    tempField: true,
                });
            // get all unique properties
            parsed.features
                .reduce((accumulator: Array<any>, current: any) => {
                    const newKeys = Object.keys(current.properties).filter(
                        key => !accumulator.find(aKey => aKey === key)
                    );
                    return accumulator.concat(newKeys);
                }, [])
                .forEach((currentKey: string) => {
                    tempFields.push({
                        name: currentKey,
                        type: 'boolean',
                        inputLabel: currentKey,
                        optional: true,
                        tempField: true,
                    });
                });

            setGeoFields(tempFields);
            setModel({ ...model, geoJSON: text });
        } catch (error) {
            // doesn't do anything..
        }
    };

    const onGeoJsonChanged = (e: React.ChangeEvent<HTMLInputElement>): void => {
        if (e.target.value) {
            handleGeoJsonChange(e.target.value);
        }
    };
    const onSchoolChanged = (e: React.ChangeEvent<{ name?: string; value: unknown }>): void => {
        setSchoolId(e.target.value as string);
    };
    const onRegionChange = (
        event: React.ChangeEvent<{
            name?: string | undefined;
            value: unknown;
        }>
    ): void => {
        let regionIds: string[] = [];

        const formData = formRef.current?.getModel() || model;
        let { regionType } = formData;
        const { geoJSON } = formData;

        const value = event.target.value
            ? Array.isArray(event.target.value)
                ? event.target.value[0]
                : event.target.value
            : null;
        const currentNode = value ? (Selectors.findComposite(schoolRegion as CompositeNode, value) as Region) : null;
        if (currentNode) {
            if (currentNode.type) {
                if (currentNode.type === RegionType.SCHOOL) {
                    regionType = RegionType.BUILDING;
                } else if (currentNode.type === RegionType.BUILDING) {
                    regionType = RegionType.FLOOR;
                } else if (currentNode.type === RegionType.FLOOR) {
                    regionType = RegionType.ROOM;
                }
            }

            regionIds = [currentNode.id || ''];
        }

        setModel({
            ...model,
            regionType,
            geoJSON,
            region: regionIds,
        });
    };

    const doImportInternal = async (): Promise<CrudResponse[]> => {
        const formData = formRef.current?.getModel() || {};
        return new Promise(resolve => {
            resolve(formData);
        }).then(async (result: any) => {
            const allResults = [];
            try {
                const payloadFields = Object.keys(result).filter(
                    k =>
                        !['school', 'region', 'regionType', 'geoJSON', 'lbSelectProperties'].find(
                            // select all fields that are not in the default list and they're selected
                            field => field === k
                        ) && result[k] === true
                );
                const parsedJSON = JSON.parse(result.geoJSON);

                /* eslint-disable no-restricted-syntax */
                for (const [index, feature] of parsedJSON.features.entries()) {
                    const payload: any = {
                        regions: result.region ? (Array.isArray(result.region) ? result.region : [result.region]) : [],
                        type: feature.properties.type || result.regionType,
                        name: `${result.regionType} - ${index}`,
                        geoBoundaries: {
                            points: feature.geometry.coordinates[0][0].map((point: any) => ({
                                x: point[0],
                                y: point[1],
                            })),
                        },
                    };
                    // set all values from the selected properties
                    payloadFields.forEach(field => {
                        if (field.startsWith('props_')) {
                            const propName = field.substring('props_'.length);
                            if (!payload.props) {
                                payload.props = [];
                            }
                            payload.props.push({ key: propName, value: feature.properties[field] });
                        } else {
                            payload[field] = feature.properties[field];
                        }
                    });

                    allResults.push(queryService.upsertRegionByName(payload));
                }

                return await Promise.all(allResults);
            } catch (error) {
                return Promise.reject(error);
            }
        });
    };

    const displayMessage = (title: string, message: string): void => {
        dispatch(
            FuseActions.openDialog({
                children: (
                    <>
                        <DialogTitle id="alert-dialog-title">{title}</DialogTitle>
                        <DialogContent>
                            <DialogContentText id="alert-dialog-description">{message}</DialogContentText>
                        </DialogContent>
                        <DialogActions>
                            <Button onClick={() => dispatch(FuseActions.closeDialog())} color="primary" autoFocus>
                                OK
                            </Button>
                        </DialogActions>
                    </>
                ),
            })
        );
    };

    const resetEntityFields = (): void => {
        const form = formRef.current;
        if (form) {
            const formData = form.getModel() || {};
            const updatedModel = {
                geoJSON: '',
                school: formData.school,
                region: formData.region,
                regionType: formData.regionType,
            };

            setGeoFields([]);
            setModel(updatedModel);
            form.updateInputsWithValue(updatedModel);
        }
    };

    const doImport = (): void => {
        setState({
            ...state,
            isSubmitting: true,
        });

        doImportInternal()
            .then((values: CrudResponse[]) => {
                // values?.forEach(resultData => {
                //     dispatch(Actions.addRegion(resultData));
                // });

                displayMessage('Success', 'Your GeoJSON data was imported successfully!');
                setState({
                    ...state,
                    isSubmitting: false,
                });
                resetEntityFields();
                dispatch(Actions.loadRegionTree(true));
            })
            .catch(error => {
                displayMessage('Something went wrong', error.message);
                setState({
                    ...state,
                    isSubmitting: false,
                });
                resetEntityFields();
                console.log(error);
            });
    };

    const handleCapture = (event: React.ChangeEvent<HTMLInputElement>): void => {
        const failedFiles: ObjectMap = {};
        const results: Promise<unknown[]>[] = [];
        const { files } = event.target;
        if (files?.length) {
            for (let idx = 0; idx < files.length; idx += 1) {
                const file = files[idx];

                const fileReader = new FileReader();
                fileReader.readAsText(file);
                results.push(
                    new Promise(resolve => {
                        fileReader.onload = (e: ProgressEvent<FileReader>) => {
                            const content: string = e?.target?.result?.toString() || '';
                            try {
                                const parsedJSON = JSON.parse(content);
                                resolve(parsedJSON.features);
                            } catch (error) {
                                failedFiles[file.name] = error;
                            }
                        };
                    })
                );
            }
        }

        Promise.all(results).then((listOfFeatures: unknown[][]) => {
            const features: unknown[] = [];
            listOfFeatures.forEach((fl: unknown[]) => {
                fl.forEach(f => features.push(f));
            });

            if (features.length) {
                const geoJSON = {
                    type: 'FeatureCollection',
                    name: 'regions',
                    crs: { type: 'name', properties: { name: 'urn:ogc:def:crs:OGC:1.3:CRS84' } },
                    features,
                };

                const geoJSONString = JSON.stringify(geoJSON);
                setModel({
                    ...model,
                    geoJSON: geoJSONString,
                });
                formRef.current?.updateInputsWithValue({
                    geoJSON: geoJSONString,
                });
                handleGeoJsonChange(geoJSONString);
            }
        });
    };

    return (
        <IscPageSimple>
            <FullSizeLayout>
                <div
                    className={clsx(
                        classes.importRoot,
                        'flex flex-col flex-auto flex-shrink-0 items-center justify-center p-32'
                    )}
                >
                    <div className="flex flex-col items-center justify-center w-full">
                        <Card
                            className={clsx(classes.panel, 'w-full')}
                            variant="outlined"
                            style={{ overflow: 'visible' }}
                        >
                            <CardContent className="flex flex-col items-center justify-center p-32">
                                <Typography variant="h6" className="mb-32">
                                    Import Tool
                                </Typography>
                                <Formsy
                                    onValid={() => enableButton(true)}
                                    onInvalid={() => enableButton(false)}
                                    ref={formRef}
                                    className="flex flex-col justify-center w-full"
                                >
                                    <FormControl margin="dense" fullWidth>
                                        <TextFieldFormsy
                                            inputRef={(input: HTMLInputElement | null) => input && input.focus()}
                                            autoFocus
                                            type="text"
                                            name="geoJSON"
                                            label="Input your GeoJSON data"
                                            value={model.geoJSON}
                                            variant="outlined"
                                            margin="dense"
                                            multiline
                                            rows={10}
                                            required
                                            onChange={onGeoJsonChanged}
                                            disabled={state.isSubmitting}
                                        />
                                    </FormControl>
                                    <FormControl margin="dense" fullWidth>
                                        <input
                                            ref={uploadInputRef}
                                            type="file"
                                            accept=".geojson"
                                            style={{ display: 'none' }}
                                            multiple
                                            onChange={handleCapture}
                                            disabled={state.isSubmitting}
                                        />
                                        <Button
                                            onClick={() => uploadInputRef.current && uploadInputRef.current.click()}
                                            variant="contained"
                                        >
                                            Load
                                        </Button>
                                    </FormControl>
                                    <FormControl margin="dense" fullWidth>
                                        {props.schools && (
                                            <SelectFormsy
                                                name="school"
                                                label="School"
                                                value={model.school}
                                                required
                                                margin="dense"
                                                onChange={onSchoolChanged}
                                                disabled={state.isSubmitting}
                                            >
                                                {props.schools.map(option => (
                                                    <MenuItem key={option.id} value={option.id}>
                                                        {option.name}
                                                    </MenuItem>
                                                ))}
                                            </SelectFormsy>
                                        )}
                                    </FormControl>
                                    <FormControl margin="dense" fullWidth>
                                        <SelectFormsy
                                            name="region"
                                            label="Parent Region"
                                            value={model.region}
                                            showCheckboxes
                                            tree
                                            treeOptions={topTreeNodes}
                                            treeHandleExpand
                                            required
                                            margin="dense"
                                            onChange={onRegionChange}
                                            disabled={state.isSubmitting}
                                        />
                                    </FormControl>
                                    <FormControl margin="dense" fullWidth>
                                        <SelectFormsy
                                            name="regionType"
                                            label="Region Type"
                                            value={model.regionType}
                                            required
                                            margin="dense"
                                            disabled={state.isSubmitting}
                                        >
                                            {['BUILDING', 'FLOOR', 'ROOM', 'WALL', 'STAIRS', 'ELEVATOR', 'UNKNOWN'].map(
                                                option => (
                                                    <MenuItem key={option} value={option}>
                                                        {option}
                                                    </MenuItem>
                                                )
                                            )}
                                        </SelectFormsy>
                                    </FormControl>
                                    <FormControl margin="dense" fullWidth>
                                        {geoFields &&
                                            geoFields.map(
                                                option =>
                                                    (option.type === 'boolean' && (
                                                        <CheckboxFormsy
                                                            key={option.name}
                                                            type="text"
                                                            name={option.name}
                                                            label={option.inputLabel || option.name}
                                                            value
                                                            variant="outlined"
                                                            margin="dense"
                                                            multiline
                                                            rows={3}
                                                            disabled={state.isSubmitting}
                                                        />
                                                    )) ||
                                                    (option.type === 'group' && (
                                                        <LabelFormsy
                                                            key={option.name}
                                                            className="my-16 mb-0"
                                                            name={option.name}
                                                            label={option.inputLabel || option.name}
                                                        />
                                                    ))
                                            )}
                                    </FormControl>

                                    <br />
                                    {state.isSubmitting && <LinearProgress className="mt-16" />}
                                </Formsy>
                            </CardContent>
                            <CardActions className={clsx(classes.actions, 'mb-16')}>
                                <Button
                                    onClick={doImport}
                                    disabled={!state.valid || state.isSubmitting}
                                    variant="contained"
                                    color="primary"
                                >
                                    Import
                                </Button>
                            </CardActions>
                        </Card>
                    </div>
                </div>
            </FullSizeLayout>
            <ErrorDialog ref={errorRef} />
        </IscPageSimple>
    );
}

export default function MigrationToolPage(): JSX.Element {
    const schools = useSelector<RootState, School[]>(Selectors.schoolsSelector);

    return <MigrationTool schools={schools} />;
}
