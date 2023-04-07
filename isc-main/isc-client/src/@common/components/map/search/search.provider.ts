import { debounce } from '@material-ui/core';
import NominatimSearchService, { OnSearchStartListener, OnSearchCompleteListener } from './providers/search';

export default class SearchServiceHolder {
    searchService?: NominatimSearchService;

    dquery?: (query: string) => void;

    private instance = (onSearchStart: OnSearchStartListener, onSearchComplete: OnSearchCompleteListener) => {
        if (!this.searchService) {
            const service = new NominatimSearchService({
                lang: 'en-US',
                countrycodes: 'US',
                limit: 10,
                onSearchStart,
                onSearchComplete,
            });
            this.dquery = debounce((query: string) => service.query(query), 1000);
            this.searchService = service;
        }

        return this.searchService;
    };

    query = (
        query: string,
        onSearchStart: OnSearchStartListener,
        onSearchComplete: OnSearchCompleteListener,
        doNotWait: boolean
    ): void => {
        this.instance(onSearchStart, onSearchComplete);
        if (!doNotWait && this.dquery) {
            this.dquery(query);
        } else if (doNotWait && this.searchService) {
            this.searchService.query(query);
        }
    };
}
