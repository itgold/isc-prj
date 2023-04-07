import React from 'react';
import Select from 'react-select';

export interface SelectValue {
    label: string;
    value: string;
}

interface IscToolbarSelectProps {
    name: string;
    options: SelectValue[];
    fullWidth?: boolean;
    value?: SelectValue;
    onChange?: (value: SelectValue | null) => void;
}

export default function IscToolbarSelect(props: IscToolbarSelectProps) {
    const { options, name, value, onChange, ...rest } = props;

    const handleOnChange = (e: any) => {
        if (onChange) {
            onChange(
                e
                    ? {
                          label: e.label,
                          value: e.value,
                      }
                    : null
            );
        }
    };

    return (
        <Select
            className="basic-single"
            classNamePrefix="select"
            defaultValue={value || options[0]}
            isClearable
            isSearchable
            name={name}
            options={options}
            onChange={e => handleOnChange(e)}
            {...rest}
        />
    );
}
