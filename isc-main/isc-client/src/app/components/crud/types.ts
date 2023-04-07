import { InputProps } from '@material-ui/core';
import { RootState } from 'app/store/appState';
import Formsy from 'formsy-react';
import { ValidationError, Validations } from 'formsy-react/dist/interfaces';
import { RefObject } from 'react';
import { Action, ActionCreator } from 'redux';
import ISchoolElement from '../../domain/school.element';

export type DataSelector = (state: RootState, params?: any, context?: any) => any[];

export interface ValueOptions {
    queryAction?: ActionCreator<Action>;
    dataSelector: DataSelector;
}

export interface IEntityField {
    alias?: string;
    name: string;
    type?: 'string' | 'text' | 'enum' | 'asyncEnum' | 'boolean' | 'group' | 'label' | 'color';
    valueOptions?: any[];
    valueOptionsAsync?: ValueOptions;
    inputLabel?: string;
    inputProps?: InputProps;
    optional?: boolean;
    defaultValue?: any;
    dependsOn?: string;
    multi?: boolean;
    /**
     * Determines whether the field should be shown as tree
     */
    showAsTree?: boolean;
    hidden?: (entity: ISchoolElement) => boolean;
    validations?: Validations<any>;
    validationErrors?: { [key: string]: ValidationError };

    /**
     * Triggers the `onChange` event that was aggregated by the EntityField model
     */
    onChange?: (event: any, formRef: RefObject<Formsy>) => void;
    /**
     * Defines if a field is a belongs to the entity or is a temporary field. This is used to be inserted dynamically
     */
    tempField?: boolean;
}

export interface IUpdateEntityDialog {
    openDialog(entity?: any, error?: string): void;
}

export interface ISelectOption {
    id: string;
    name: string;
}

export const EMPTY_SELECT_OPTION: ISelectOption = { id: '', name: '' };
export const EMPTY_MULTISELECT_OPTION: ISelectOption[] = [];
