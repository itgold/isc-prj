import _ from '@lodash';
import { FormControlLabel, FormGroup, makeStyles } from '@material-ui/core';
import clsx from 'clsx';
import { withFormsy } from 'formsy-react';
import { WrapperInstanceMethods } from 'formsy-react/dist/withFormsy';
import { Color, ColorPicker, ColorPickerProps } from 'material-ui-color';
import React from 'react';

const useStyles = makeStyles(() => ({
    colorPicker: {
        display: 'flex',
        justifyContent: 'space-between',
        marginLeft: '0px',
        marginTop: '4px',
        paddingTop: '4px',
    },
}));

interface ColorPickerFormsyProps extends ColorPickerProps {
    label?: string;
    name?: string;
    className?: string;
}

function ColorPickerFormsy(props: ColorPickerFormsyProps): JSX.Element {
    const classes = useStyles();
    const importedProps = _.pick(props, [
        'defaultValue',
        'children',
        'className',
        'disableTextfield',
        'id',
        'hideTextfield',
        'deferred',
        'palette',
        'inputFormats',
        'disableAlpha',
        'disablePlainColor',
        'hslGradient',
    ]);

    const changeValue = (color: Color): void => {
        ((props as unknown) as WrapperInstanceMethods<string>).setValue(`#${color.hex}`);
        if (props.onChange) {
            props.onChange(color);
        }
    };

    /*
    <TextField
            {...importedProps}
            onChange={changeValue}
            value={value}
            error={Boolean((!props.isPristine && props.showRequired) || errorMessage)}
            helperText={errorMessage}
        />
    */
    return (
        <FormGroup row>
            <FormControlLabel
                name={props.name}
                labelPlacement="start"
                className={clsx(classes.colorPicker, props.className)}
                control={
                    <ColorPicker
                        {...importedProps}
                        deferred={props.deferred}
                        defaultValue={props.defaultValue}
                        value={props.value}
                        onChange={changeValue}
                        disableAlpha
                    />
                }
                label={props.label}
            />
        </FormGroup>
    );
}

export default React.memo(withFormsy(ColorPickerFormsy));
