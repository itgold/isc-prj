import React, { Dispatch } from 'react';
import { createActions, handleActions } from 'redux-actions';

export interface SlideTabPanelState {
    opened: boolean;
    activeTab: number;
    tabs: TabDetails[];
}

const initialState: SlideTabPanelState = {
    opened: false,
    activeTab: -1,
    tabs: [],
};

export interface TabDetails {
    index: number;
    title: string;
}

interface ActionPayload {
    index: number;
    tabInfo?: TabDetails;
}

const { addTab, selectTab, openPanel } = createActions<ActionPayload>('ADD_TAB', 'SELECT_TAB', 'OPEN_PANEL');
const Actions = { addTab, selectTab, openPanel };

function mergeTabs(state: SlideTabPanelState, actionPayload: ActionPayload): SlideTabPanelState {
    if (actionPayload.tabInfo && !state.tabs[actionPayload.tabInfo.index]) {
        const allTabs: TabDetails[] = [];
        state.tabs.forEach(tab => {
            allTabs[tab.index] = tab;
        });
        if (actionPayload.tabInfo) {
            allTabs[actionPayload.tabInfo.index] = actionPayload.tabInfo;
        }

        return {
            ...state,
            tabs: allTabs,
        };
    }

    return state;
}

const slideTabPanelReducer = handleActions<SlideTabPanelState, ActionPayload>(
    {
        //   [addTab.toString()]: (state, action) => ({
        //     ...state,
        //     tabs: action.payload.tabInfo ? [...state.tabs, action.payload.tabInfo] : state.tabs,
        //   }),
        [addTab.toString()]: (state, action) => mergeTabs(state, action.payload),
        [selectTab.toString()]: (state, action) => ({
            ...state,
            activeTab: action.payload.index,
        }),
        [openPanel.toString()]: (state, action) => ({
            ...state,
            activeTab: action.payload.index,
            opened: action.payload.index >= 0,
        }),
    },
    initialState
);

const SlideTabPanelStateContext = React.createContext<SlideTabPanelState>(initialState);
const SlideTabPanelDispatchContext = React.createContext<Dispatch<any>>(() => null);

interface ISlideTabPanelProviderProps {
    children: React.ReactNode;
    state?: SlideTabPanelState;
}

function SlideTabPanelProvider(props: ISlideTabPanelProviderProps) {
    const { children, state } = props;
    const [currentState, dispatch] = React.useReducer(slideTabPanelReducer, state || initialState);

    return (
        <SlideTabPanelStateContext.Provider value={currentState}>
            <SlideTabPanelDispatchContext.Provider value={dispatch}>{children}</SlideTabPanelDispatchContext.Provider>
        </SlideTabPanelStateContext.Provider>
    );
}

function useSlidePanelState() {
    const context = React.useContext(SlideTabPanelStateContext);
    if (context === undefined) {
        throw new Error('useCountState must be used within a CountProvider');
    }
    return context;
}

function useSlidePanelDispatch() {
    const context = React.useContext(SlideTabPanelDispatchContext);
    if (context === undefined) {
        throw new Error('useCountDispatch must be used within a CountProvider');
    }
    return context;
}

export { SlideTabPanelProvider, useSlidePanelState, useSlidePanelDispatch, Actions };
