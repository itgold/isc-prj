package com.iscweb.search.utils;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.IndicesOptions;

public final class SearchUtils {
    public static SearchRequest createSearchRequest() {
        SearchRequest searchRequest = new SearchRequest();

        /* Use default settings for now ...
        searchRequest.indicesOptions(IndicesOptions.fromOptions(
                searchRequest.indicesOptions().ignoreUnavailable(),
                searchRequest.indicesOptions().allowNoIndices(),
                true,
                false,
                searchRequest.indicesOptions().allowAliasesToMultipleIndices(),
                searchRequest.indicesOptions().forbidClosedIndices(),
                searchRequest.indicesOptions().ignoreAliases(),
                false));
         */

        return searchRequest;
    }
}
