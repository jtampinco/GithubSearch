package com.jtampinco.githubsearch.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import com.jtampinco.githubsearch.R
import com.jtampinco.githubsearch.app.theme.GithubSearchTheme
import com.jtampinco.githubsearch.app.theme.typography
import com.jtampinco.githubsearch.resources.Github
import com.jtampinco.githubsearch.ui.components.CircularIndeterminateProgressBar
import com.jtampinco.githubsearch.ui.components.GithubRepoCard
import com.jtampinco.githubsearch.ui.components.LoadingRepoListShimmer
import com.jtampinco.githubsearch.ui.components.SearchAppBar
import com.jtampinco.githubsearch.viewmodel.SearchRepoViewModel
import com.jtampinco.githubsearch.viewmodel.SearchRepoViewModel.Companion.PAGE_SIZE
import com.jtampinco.githubsearch.viewmodel.ViewRepoViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchRepoScreen(
    viewModel: SearchRepoViewModel,
    navController: NavHostController? = null,
) {
    GithubSearchTheme() {
        Scaffold(
            content = {
                val isLoading = viewModel.loading.value
                val repositoryList = viewModel.repositories.value
                val hasError = viewModel.hasError.value

                Column {
                    RepositorySearchBar(viewModel)
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (repositoryList.isEmpty() && !isLoading) {
                            EmptyPlaceHolder(hasError = hasError,
                                retry = viewModel::onExecuteSearch)
                        }
                        RepositoryList(viewModel, navController)
                        if (isLoading && repositoryList.isEmpty()) {
                            LoadingRepoListShimmer(
                                isDisplayed = isLoading,
                                imageHeight = 150.dp
                            )
                        }
                        if (isLoading && repositoryList.isNotEmpty()) {
                            CircularIndeterminateProgressBar(isDisplayed = isLoading)
                        }
                    }
                    if (viewModel.searchSettingsVisibility.value) {
                        SearchSettingsScreen(
                            viewModel = viewModel,
                            onExecuteSave = viewModel::saveSearchPreferences,
                            onExecuteCancel = { viewModel.showSearchSettings(false) }
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun EmptyPlaceHolder(
    modifier: Modifier = Modifier,
    hasError: Boolean,
    retry: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.disabled,
        ) {
            Icon(
                modifier = Modifier.size(75.dp),
                imageVector = Icons.Github,
                contentDescription = stringResource(id = R.string.no_results_found),
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = if (hasError) {
                    stringResource(id = R.string.default_error)
                } else {
                    stringResource(id = R.string.no_results_found)
                },
                textAlign = TextAlign.Center,
                style = typography.subtitle1,
            )
            if (hasError) {
                TextButton(onClick = retry) {
                    Text(stringResource(id = R.string.try_again))
                }
            } else {
                Text(
                    text = stringResource(id = R.string.try_new_keyword),
                    textAlign = TextAlign.Center,
                    style = typography.caption,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RepositorySearchBar(
    viewModel: SearchRepoViewModel,
) {
    val query = viewModel.query.value
    val isSearchSettingsVisible = viewModel.searchSettingsVisibility.value

    Column {
        SearchAppBar(
            query = query,
            onQueryChange = viewModel::onQueryChanged,
            onExecuteSearch = viewModel::onExecuteSearch,
            onTrailingButtonClicked = viewModel::getSearchPreferences,
            isTrailingButtonEnabled = !isSearchSettingsVisible
        )
    }
}

@Composable
fun RepositoryList(
    viewModel: SearchRepoViewModel,
    navController: NavHostController?,
) {
    val data = viewModel.repositories.value
    val currentPage = viewModel.page.value
    val isLoading = viewModel.loading.value

    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(data) { index, repository ->
            viewModel.onChangeRepoListScrollPosition(index)
            if ((index + 1) >= (currentPage * PAGE_SIZE) && !isLoading) {
                viewModel.nextPage()
            }
            GithubRepoCard(
                repository = repository
            ) {
                navController?.navigate(
                    navController.findDestination("ViewRepoScreen")!!.id,
                    bundleOf(Pair(ViewRepoViewModel.REPOSITORY_URL, repository.url))
                )
            }
        }
    }
}
