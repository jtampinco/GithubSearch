package com.jtampinco.githubsearch.data.repository

import com.jtampinco.githubsearch.constants.SearchFilterBy
import com.jtampinco.githubsearch.constants.SearchOrderBy
import com.jtampinco.githubsearch.constants.SearchSortBy
import com.jtampinco.githubsearch.data.SearchSettings
import com.jtampinco.githubsearch.data.domain.model.SearchPreferences
import javax.inject.Inject

class SearchSettingsImpl @Inject constructor(
    private val repository: SearchSettingsRepo,
) : SearchSettings {

    override suspend fun saveSearchPreferences(
        searchSortBy: SearchSortBy,
        searchOrderBy: SearchOrderBy,
        searchFilterBy: SearchFilterBy,
    ) {
        repository.saveSearchPreferences(
            searchSortBy = searchSortBy,
            searchOrderBy = searchOrderBy,
            searchFilterBy = searchFilterBy
        )
    }

    override suspend fun getSearchPreferences(): SearchPreferences {
        return repository.getSearchPreferences()
    }
}