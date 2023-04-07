import React, { useEffect } from 'react';
import Select, { Styles } from 'react-select';
import chroma from 'chroma-js';
import { RootState } from 'app/store/appState';
import * as Selectors from 'app/store/selectors';
import { useSelector, useDispatch } from 'react-redux';
import * as Actions from 'app/store/actions/admin';
import Tag from 'app/domain/tag';

export interface SelectValue {
    label: string;
    value: string;
}

interface IscTagListProps {
    variant?: 'light' | 'dark';
    fullWidth?: boolean;
    value?: SelectValue;
    onChange?: (value: SelectValue[]) => void;

    className?: string;
    selectedTags?: string[];
}

const dataColor = '#000071';

const colourStyles: Partial<Styles<SelectValue, true>> = {
    control: styles => ({ ...styles, backgroundColor: 'white' }),
    option: (styles: any, { isDisabled, isFocused, isSelected }) => {
        const color = chroma(dataColor);
        return {
            ...styles,
            backgroundColor: isDisabled ? null : isSelected ? dataColor : isFocused ? color.alpha(0.1).css() : null,
            color: isDisabled
                ? '#ccc'
                : isSelected
                ? chroma.contrast(color, 'white') > 2
                    ? 'white'
                    : 'black'
                : dataColor,
            cursor: isDisabled ? 'not-allowed' : 'default',

            ':active': {
                ...styles[':active'],
                backgroundColor: !isDisabled && (isSelected ? dataColor : color.alpha(0.3).css()),
            },
        };
    },
    multiValue: styles => {
        const color = chroma(dataColor);
        return {
            ...styles,
            backgroundColor: color.alpha(0.1).css(),
        };
    },
    multiValueLabel: styles => ({
        ...styles,
        color: dataColor,
    }),
    multiValueRemove: styles => ({
        ...styles,
        color: dataColor,
        ':hover': {
            backgroundColor: dataColor,
            color: 'white',
        },
    }),
};

export default function IscTagList(props: IscTagListProps): JSX.Element {
    const { value, onChange, selectedTags, ...rest } = props;
    const dispatch = useDispatch();
    const tagsList = useSelector<RootState, Tag[]>(Selectors.tagsSelector).map(tag => tag.name);
    const tags: SelectValue[] = tagsList.map<SelectValue>(tag => {
        return { value: tag, label: tag };
    });
    const defaultValue = (selectedTags || []).map<SelectValue>(tag => {
        return { value: tag, label: tag };
    });

    const handleOnChange = (e: any): void => {
        if (onChange) {
            onChange(e);
        }
    };

    useEffect(() => {
        dispatch(Actions.queryTags());
    }, [dispatch]);

    return (
        <Select
            className="basic-single"
            classNamePrefix="select"
            defaultValue={defaultValue}
            value={defaultValue}
            isClearable
            isSearchable
            name="tagList"
            options={tags}
            onChange={e => handleOnChange(e)}
            isMulti
            styles={colourStyles}
            size="small"
            {...rest}
        />
    );
}
