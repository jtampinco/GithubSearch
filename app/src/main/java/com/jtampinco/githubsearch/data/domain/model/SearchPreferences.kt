package com.jtampinco.githubsearch.data.domain.model

import com.jtampinco.githubsearch.constants.SearchFilterBy
import com.jtampinco.githubsearch.constants.SearchOrderBy
import com.jtampinco.githubsearch.constants.SearchSortBy

data class SearchPreferences(
    val searchSortBy: SearchSortBy,
    val searchOrderBy: SearchOrderBy,
    val searchFilterBy: SearchFilterBy,
)