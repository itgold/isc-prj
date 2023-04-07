import _ from '@lodash';
import FilledInput from '@material-ui/core/FilledInput';
import FormControl from '@material-ui/core/FormControl';
import FormHelperText from '@material-ui/core/FormHelperText';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import OutlinedInput from '@material-ui/core/OutlinedInput';
import Select, { SelectProps } from '@material-ui/core/Select';
import { withFormsy } from 'formsy-react';
import React, { useEffect, useState } from 'react';
import TreeDropDown, { TreeItemData } from '@common/components/tree.dropdown';
import { WrapperInstanceMethods } from 'formsy-react/dist/withFormsy';
import { EMPTY_LIST } from 'app/utils/domain.constants';

interface SelectFormsyProps extends SelectProps {
    errorMessage?: string;
    name: string;
    label?: string;

    isPristine?: boolean;
    showRequired?: boolean;

    showCheckboxes?: boolean;
    tree?: boolean;
    treeOptions?: TreeItemData[];
    treeHandleExpand?: boolean;
}

function extractNodeId(nodeId: string): string {
    return nodeId.indexOf('_') >= 0 ? nodeId.split('_')[0] : nodeId;
}

interface SelectFormsyState {
    source?: TreeItemData[];
    data: TreeItemData[];
    selected: string[];
}

function valueToArray(value: unknown): string[] {
    return Array.isArray(value) ? (value as string[]) : [value as string];
}

function SelectFormsy(props: SelectFormsyProps): JSX.Element {
    const importedProps = _.pick(props, [
        'autoWidth',
        'children',
        'classes',
        'displayEmpty',
        'input',
        'inputProps',
        'MenuProps',
        'multiple',
        'native',
        'onClose',
        'onOpen',
        'open',
        'renderValue',
        'SelectDisplayProps',
        'value',
        'variant',
        'margin',
        'label',
    ]);

    // An error message is returned only if the component is invalid
    const { errorMessage, value } = props;

    function input(): JSX.Element {
        const label = props.label || '';
        switch (importedProps.variant) {
            case 'outlined':
                return <OutlinedInput labelWidth={label.length * 8} id={props.name} />;
            case 'filled':
                return <FilledInput id={props.name} />;
            default:
                return <Input id={props.name} />;
        }
    }

    const fixValue = (selectValue: unknown): unknown => {
        if (selectValue) {
            if (props.multiple) {
                return Array.isArray(selectValue)
                    ? selectValue.map(id => extractNodeId(id))
                    : [extractNodeId(selectValue as string)];
            }
            return Array.isArray(selectValue) && selectValue.length
                ? extractNodeId(selectValue[0])
                : extractNodeId(selectValue as string);
        }

        return selectValue;
    };

    const changeValue = (event: React.ChangeEvent<{ name?: string; value: unknown }>, child: React.ReactNode): void => {
        if (event?.target) {
            let newValue = event.target.value;
            if (newValue && props.tree) {
                newValue = fixValue(newValue);
                event.target.value = newValue;
            }

            ((props as unknown) as WrapperInstanceMethods<string>).setValue(newValue as string);
            if (props.onChange) {
                props.onChange(event, child);
            }
        }
    };

    const enhanceOptions = (
        treeOptions: TreeItemData[],
        handleExpand: boolean,
        parentId: string,
        selectValue: string[],
        selected: string[]
    ): SelectFormsyState => {
        let data: TreeItemData[] = treeOptions;

        if (treeOptions && handleExpand) {
            data = treeOptions.map(option => {
                const id = option.id || '';
                const optionId = `${id}_${parentId}`;
                const children = enhanceOptions(
                    option.children || EMPTY_LIST,
                    handleExpand,
                    optionId,
                    selectValue,
                    selected
                ).data;

                if (selected.length < selectValue.length && selectValue.includes(id)) {
                    selected.push(optionId);
                }

                return {
                    ...option,
                    id: optionId,
                    children,
                };
            });
        }

        return {
            data,
            source: treeOptions,
            selected,
        };
    };

    const enhanceOptionsRoot = (
        treeOptions: TreeItemData[],
        handleExpand: boolean,
        selectValue: unknown
    ): SelectFormsyState => {
        const state = enhanceOptions(treeOptions, handleExpand, '', valueToArray(selectValue), []);
        return state;
    };

    const handleExpand = !!(props.tree && props.treeHandleExpand);
    const [state, setState] = useState<SelectFormsyState>(
        enhanceOptionsRoot(props.treeOptions || EMPTY_LIST, handleExpand, value)
    );

    useEffect(() => {
        if (!_.isEqual(state.source, props.treeOptions)) {
            const newState = enhanceOptionsRoot(props.treeOptions || EMPTY_LIST, handleExpand, value);
            setState(newState);
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [props.treeOptions]);

    return (
        <FormControl
            error={Boolean((!props.isPristine && props.showRequired) || errorMessage)}
            className={props.className}
            variant={importedProps.variant}
        >
            {props.tree ? (
                <TreeDropDown
                    name={props.name}
                    showCheckboxes={props.showCheckboxes}
                    options={state.data}
                    value={state.selected}
                    selected={state.selected}
                    onChange={changeValue}
                    {...importedProps}
                />
            ) : (
                <>
                    {props.label && <InputLabel htmlFor={props.name}>{props.label}</InputLabel>}
                    <Select {...importedProps} value={value} onChange={changeValue} input={input()} />
                    {Boolean(errorMessage) && <FormHelperText>{errorMessage}</FormHelperText>}
                </>
            )}
        </FormControl>
    );
}

export default React.memo(withFormsy(SelectFormsy), (prevProps: SelectFormsyProps, nextProps: SelectFormsyProps) => {
    const valueChanged = !_.isEqual(prevProps.value, nextProps.value);
    const optionsChanged = !_.isEqual(prevProps.treeOptions, nextProps.treeOptions);
    const childrenChanged =
        React.Children.toArray(prevProps.children).length !== React.Children.toArray(nextProps.children).length;
    return !(valueChanged || optionsChanged || childrenChanged);
});
