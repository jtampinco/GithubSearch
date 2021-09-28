package com.jtampinco.githubsearch.viewmodel

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jtampinco.githubsearch.constants.SearchFilterBy
import com.jtampinco.githubsearch.constants.SearchOrderBy
import com.jtampinco.githubsearch.constants.SearchSortBy
import com.jtampinco.githubsearch.data.GithubSearch
import com.jtampinco.githubsearch.data.SearchSettings
import com.jtampinco.githubsearch.data.base.RepoResponse
import com.jtampinco.githubsearch.data.domain.model.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(ExperimentalMaterialApi::class)
@HiltViewModel
class SearchRepoViewModel @Inject constructor(
    private val repository: GithubSearch,
    private val settings: SearchSettings,
) : ViewModel() {

    private val _query = mutableStateOf("")
    val query: State<String> = _query

    private val _repositories = mutableStateOf(listOf<Repository>())
    val repositories: State<List<Repository>> = _repositories

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _searchSettingsVisibility = mutableStateOf(false)
    val searchSettingsVisibility: State<Boolean> = _searchSettingsVisibility

    private val _searchSortBy = mutableStateOf(SearchSortBy.BEST_MATCH)
    val searchSortBy: State<SearchSortBy> = _searchSortBy

    private val _searchOrderBy = mutableStateOf(SearchOrderBy.DESC)
    val searchOrderBy: State<SearchOrderBy> = _searchOrderBy

    private val _searchFilterBy = mutableStateOf(SearchFilterBy.NONE)
    val searchFilterBy: State<SearchFilterBy> = _searchFilterBy

    private val _page = mutableStateOf(1)
    val page: State<Int> = _page

    private val _hasError = mutableStateOf(false)
    val hasError: State<Boolean> = _hasError

    private var repoListScrollPosition = 0

    init {
        onExecuteSearch()
    }

    fun onExecuteSearch() = viewModelScope.launch {
        _loading.value = true
        if (_query.value.isNotEmpty()) {
            resetSearchState()

            // Adding a delay to prevent throttling the github api
            // TODO Build a better solution
            delay(1000)

            when (val response = repository.search(
                query = appendFilter(_query.value),
                page = _page.value,
                countPerPage = PAGE_SIZE,
                sort = _searchSortBy.value.value,
                order = _searchOrderBy.value.value
            )) {
                is RepoResponse.Success -> {
                    _repositories.value = response.data
                    _hasError.value = false
                }
                is RepoResponse.Failed -> {
                    // Handle server error
                    _hasError.value = true
                }
                is RepoResponse.Error -> {
                    // Handle internet issue
                    _hasError.value = true
                }
            }
        }
        _loading.value = false
    }

    fun nextPage() {
        viewModelScope.launch {
            // prevent duplicate events due to recompose happening too quickly
            if ((repoListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
                _loading.value = true
                incrementPage()
                if (page.value > 1) {
                    when (val response = repository.search(
                        query = appendFilter(_query.value),
                        page = page.value,
                        countPerPage = PAGE_SIZE,
                        sort = _searchSortBy.value.value,
                        order = _searchOrderBy.value.value
                    )) {
                        is RepoResponse.Success -> {
                            appendRepositories(response.data)
                            _hasError.value = false
                        }
                        is RepoResponse.Failed -> {
                            // Handle server error
                            _hasError.value = true
                        }
                        is RepoResponse.Error -> {
                            // Handle internet issue
                            _hasError.value = true
                        }
                    }
                }
                _loading.value = false
            }
        }
    }

    fun onQueryChanged(query: String) {
        _query.value = query
    }

    fun onChangeRepoListScrollPosition(position: Int) {
        repoListScrollPosition = position
    }

    fun showSearchSettings(visibility: Boolean) {
        _searchSettingsVisibility.value = visibility
    }

    fun onSelectedFilterChanged(setting: Any) {
        when (setting) {
            is SearchSortBy -> {
                _searchSortBy.value = setting
            }
            is SearchOrderBy -> {
                _searchOrderBy.value = setting
            }
            is SearchFilterBy -> {
                _searchFilterBy.value = setting
            }
        }
    }

    fun getSearchPreferences() = viewModelScope.launch {
        settings.getSearchPreferences().let {
            _searchSortBy.value = it.searchSortBy
            _searchOrderBy.value = it.searchOrderBy
            _searchFilterBy.value = it.searchFilterBy
        }
        showSearchSettings(true)
    }

    fun saveSearchPreferences() = viewModelScope.launch {
        settings.saveSearchPreferences(
            searchSortBy = _searchSortBy.value,
            searchOrderBy = _searchOrderBy.value,
            searchFilterBy = _searchFilterBy.value
        )
        showSearchSettings(false)
        onExecuteSearch()
    }

    private fun appendFilter(initialValue: String): String {
        // You can put more rules in filtering the query here...
        return when (_searchFilterBy.value) {
            SearchFilterBy.KOTLIN -> "$initialValue+language:Kotlin"
            SearchFilterBy.JAVA -> "$initialValue+language:Java"
            SearchFilterBy.NONE -> initialValue
        }
    }

    /**
     * Append new repositories to the current list of repositories
     */
    private fun appendRepositories(repositories: List<Repository>) {
        val current = ArrayList(this._repositories.value)
        current.addAll(repositories)
        this._repositories.value = current
    }

    private fun resetSearchState() {
        _repositories.value = listOf()
        _page.value = 1
        repoListScrollPosition = 0
    }

    private fun incrementPage() {
        _page.value = page.value + 1
    }

    companion object {
        const val PAGE_SIZE = 30
    }
}
