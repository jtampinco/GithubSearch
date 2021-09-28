package com.jtampinco.githubsearch.data.repository

import com.jtampinco.githubsearch.constants.SearchFilterBy
import com.jtampinco.githubsearch.constants.SearchOrderBy
import com.jtampinco.githubsearch.constants.SearchSortBy
import com.jtampinco.githubsearch.data.domain.model.SearchPreferences
import javax.inject.Inject

class SearchSettingsRepo @Inject constructor(
    // Connect to a data source
) {

    // TODO: Replace with a value from a data source
    private var searchSortPreferences = SearchPreferences(
        searchSortBy = SearchSortBy.BEST_MATCH,
        searchOrderBy = SearchOrderBy.DESC,
        searchFilterBy = SearchFilterBy.NONE
    )

    /**
     * Function to save the preferences either from SharedPref/Database
     */
    fun saveSearchPreferences(
        searchSortBy: SearchSortBy,
        searchOrderBy: SearchOrderBy,
        searchFilterBy: SearchFilterBy,
    ) {
        this.searchSortPreferences = SearchPreferences(
            searchSortBy = searchSortBy,
            searchOrderBy = searchOrderBy,
            searchFilterBy = searchFilterBy
        )
    }

    /**
     * Function to retrieve the preferences either from SharedPref/Database
     */
    fun getSearchPreferences(): SearchPreferences {
        return searchSortPreferences
    }

}