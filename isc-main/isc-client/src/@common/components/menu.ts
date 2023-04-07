export type OnItemSelected = () => void;

export interface IMenuItem {
    label: string;
    onSelected: OnItemSelected;
}
