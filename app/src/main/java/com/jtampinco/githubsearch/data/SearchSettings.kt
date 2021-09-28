package com.jtampinco.githubsearch.data

import com.jtampinco.githubsearch.constants.SearchFilterBy
import com.jtampinco.githubsearch.constants.SearchOrderBy
import com.jtampinco.githubsearch.constants.SearchSortBy
import com.jtampinco.githubsearch.data.domain.model.SearchPreferences

interface SearchSettings {

    suspend fun saveSearchPreferences(
        searchSortBy: SearchSortBy,
        searchOrderBy: SearchOrderBy,
        searchFilterBy: SearchFilterBy,
    )

    suspend fun getSearchPreferences(): SearchPreferences
}