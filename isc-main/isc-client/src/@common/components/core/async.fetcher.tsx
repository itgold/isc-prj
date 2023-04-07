import React, { useEffect } from 'react';
import { EMPTY_LIST } from 'app/utils/domain.constants';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from 'app/store/appState';
import { Action, ActionCreator } from 'redux';
import { DataSelector } from 'app/components/crud/types';
import { IEntity } from '../../../app/domain/entity';

const ctxt = React.createContext<unknown[]>(EMPTY_LIST);
const FetcherContextProvider = ctxt.Provider;
export const FetcherContextConsumer = ctxt.Consumer;

interface AsyncFetcherProps {
    queryAction?: ActionCreator<Action>;
    queryParams?: unknown;
    context?: unknown;
    dataSelector?: DataSelector;

    children: React.ReactNode;
}

export default function AsyncFetcher(props: AsyncFetcherProps): JSX.Element {
    const { queryAction, queryParams, context, dataSelector } = props;
    const data = useSelector<RootState, IEntity[]>(
        state => (dataSelector && dataSelector(state, queryParams, context)) || EMPTY_LIST
    );
    const dispatch = useDispatch();

    useEffect(() => {
        if (queryAction) {
            dispatch(queryAction(queryParams));
        }
    }, [dispatch, queryAction, queryParams]);

    return <FetcherContextProvider value={data}>{props.children}</FetcherContextProvider>;
}

/*
<AsyncFetcher
	queryAction={queryAction}
	dataSelector={dataSelector}
	key={entityField.name}
>
	<FetcherContextConsumer>
		{(values: any[]) =>
			values && (
				<>
					{values.map(option => (
						<MenuItem key={option.id} value={option.name}>
							{option.name}
						</MenuItem>
					))}
				</>
			)
		}
	</FetcherContextConsumer>
</AsyncFetcher>
*/
