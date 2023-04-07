import React from 'react';
import { Pagination, IscDataProvider } from './IscTableDataProvider';
import { IColumnOrder } from './IscTableHead';

export interface Filter {
    [key: string]: string;
}

export interface IscTableContext {
    pagination: Pagination;
    sortOrder: IColumnOrder;
    filter: Filter;
    tags: string[];
}

export const createEmptyContext = (): IscTableContext => {
    return {
        pagination: {
            showPagination: true,
            rowsPerPage: 25,
            currentPage: 0,
        },
        sortOrder: {
            direction: 'asc',
            id: null,
        },
        filter: {},
        tags: [],
    };
};

export const DEFAULT_CONTEXT = createEmptyContext();

const IscTableContextContext = React.createContext<IscTableContext>(DEFAULT_CONTEXT);
const IscTableDataProviderContext = React.createContext<IscDataProvider>({
    context: DEFAULT_CONTEXT,
    data: [],
    totalRecords: 0,
    pureData: [],
});

interface ISlideTabPanelProviderProps {
    children: React.ReactNode;
    dataProvider?: IscDataProvider;
    context?: IscTableContext;
}

function IscTableDataAdaptor(props: ISlideTabPanelProviderProps): JSX.Element {
    const { children, dataProvider } = props;
    const tableContext = props.context || DEFAULT_CONTEXT;

    if (!dataProvider) {
        throw new Error('Either dataProvider or dataProviderFactory is required!');
    }

    return (
        <IscTableContextContext.Provider value={tableContext}>
            <IscTableDataProviderContext.Provider value={dataProvider}>{children}</IscTableDataProviderContext.Provider>
        </IscTableContextContext.Provider>
    );
}

function useIscTableContext(): IscTableContext {
    const context = React.useContext(IscTableContextContext);
    if (context === undefined) {
        throw new Error('useIscTableContext must be used within a IscTableDataAdaptor');
    }
    return context;
}

function useIscTableDataProvider(): IscDataProvider {
    const context = React.useContext<IscDataProvider>(IscTableDataProviderContext);
    if (context === undefined) {
        throw new Error('useuseIscTableDataProvider must be used within a IscTableDataAdaptor');
    }
    return context;
}

export {
    IscTableDataAdaptor,
    useIscTableContext,
    useIscTableDataProvider,
    IscTableContextContext,
    IscTableDataProviderContext,
};

/*
Another way to use context (useful in React class components):
<IscTableContextContext.Consumer>
    {context => (
        <h1>{context.totalRecords}</h1>
    )}
</IscTableContextContext.Consumer>
*/
