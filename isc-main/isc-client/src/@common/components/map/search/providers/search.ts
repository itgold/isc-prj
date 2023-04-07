import { OpenStreetProvider } from './osm';
import { IJsonCallParams, json } from './ajax';

export interface IGeoData {
    name: string;
    lat?: number;
    lon?: number;
}

export interface GeoCoderSearchParams {
    query: string;
    limit: number;
    countrycodes: string;
    lang?: string;
    key?: string;
    callbackName?: string;
}

export interface GeoCoderProviderSettings {
    url: string;
    params: {
        q: string;
        format: string;
        addressdetails: number;
        limit: number;
        countrycodes: string;
        'accept-language': string;
    };
    callbackName?: string;
}

export interface IGeoCoderProvider {
    getParameters(opt: GeoCoderSearchParams): GeoCoderProviderSettings;
    handleResponse(results: any): IGeoData[];
}

export type OnSearchStartListener = () => void;
export type OnSearchCompleteListener = (results: IGeoData[]) => void;

export interface INominatimSearchServiceParams {
    // GeoCode search provider
    provider?: IGeoCoderProvider;

    // Provider API key
    key?: string;

    // Default language
    lang?: string;

    countrycodes: string;

    limit: number;

    onSearchStart?: OnSearchStartListener;
    onSearchComplete?: OnSearchCompleteListener;

    debug?: boolean;
}

class NominatimSearchService {
    provider: IGeoCoderProvider;

    lastQuery?: string;

    lastResults: IGeoData[];

    constructor(private options: INominatimSearchServiceParams) {
        this.provider = options && options.provider ? options.provider : new OpenStreetProvider();
        this.lastResults = [];
    }

    query(q: string) {
        const parameters = this.provider.getParameters({
            query: q,
            key: this.options.key,
            lang: this.options.lang,
            countrycodes: this.options.countrycodes,
            limit: this.options.limit,
        });

        if (this.lastQuery === q) {
            if (this.options.onSearchComplete) this.options.onSearchComplete(this.lastResults);
            return;
        }

        this.lastQuery = q;
        this.clearResults();
        if (this.options.onSearchStart) this.options.onSearchStart();

        const ajax: IJsonCallParams = {
            url: parameters.url,
            data: parameters.params,
        };
        if (parameters.callbackName) {
            ajax.jsonp = true;
            ajax.callbackName = parameters.callbackName;
        }

        const self = this;
        json(ajax)
            .then((res: any) => {
                // eslint-disable-next-line no-console
                if (self.options.debug) {
                    console.info(res);
                }

                // will be fullfiled according to provider
                this.lastResults = this.provider.handleResponse(res);

                if (self.options.onSearchComplete) self.options.onSearchComplete(this.lastResults);
            })
            .catch((err: any) => {
                console.error(err);
                this.lastResults = [];
                if (self.options.onSearchComplete)
                    self.options.onSearchComplete([{ name: 'Error! No internet connection?' }]);
            });
    }

    clearResults(): void {
        // clean up results
        this.lastResults = [];
        this.lastQuery = '';
    }
}

export default NominatimSearchService;
