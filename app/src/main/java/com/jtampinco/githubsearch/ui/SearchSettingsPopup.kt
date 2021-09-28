package com.jtampinco.githubsearch.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.jtampinco.githubsearch.app.theme.shapes
import com.jtampinco.githubsearch.app.theme.typography
import com.jtampinco.githubsearch.constants.*
import com.jtampinco.githubsearch.viewmodel.SearchRepoViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchSettingsScreen(
    viewModel: SearchRepoViewModel,
    onExecuteSave: () -> Unit,
    onExecuteCancel: () -> Unit,
) {
    val searchSortBy = viewModel.searchSortBy.value
    val searchOrderBy = viewModel.searchOrderBy.value
    val searchFilterBy = viewModel.searchFilterBy.value

    Popup(
        alignment = Alignment.BottomCenter,
        onDismissRequest = { onExecuteCancel() },
        content = {
            Surface(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.Bottom),
                color = colors.surface,
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                ),
                elevation = 24.dp
            ) {
                Column(modifier = Modifier
                    .wrapContentHeight(Alignment.Bottom)
                    .padding(horizontal = 24.dp, vertical = 20.dp)) {
                    Text(
                        text = "Settings",
                        style = typography.caption.copy(color = colors.onSurface)
                    )
                    Spacer(Modifier.height(8.dp))
                    Column(
                    ) {
                        SortBy(
                            selectedFilter = searchSortBy,
                            onSelectedFilterChanged = viewModel::onSelectedFilterChanged
                        )
                        Divider(Modifier.padding(vertical = 16.dp))
                        OrderBy(
                            selectedFilter = searchOrderBy,
                            onSelectedFilterChanged = viewModel::onSelectedFilterChanged
                        )
                        Divider(Modifier.padding(vertical = 16.dp))
                        FilterBy(
                            selectedFilter = searchFilterBy,
                            onSelectedFilterChanged = viewModel::onSelectedFilterChanged
                        )
                        Divider(modifier = Modifier.padding(vertical = 16.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = onExecuteCancel
                            ) {
                                Text(text = "Cancel")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = onExecuteSave
                            ) {
                                Text(text = "Save Preferences")
                            }
                        }
                    }
                }
            }
        })
}

@Composable
fun SearchSettingChip(
    selectedFilter: Any,
    isSelected: Boolean,
    name: String,
    onSelectedFilterChanged: (Any) -> Unit,
) {
    Surface(
        shape = shapes.small,
        color = if (isSelected) colors.primary else colors.surface
    ) {
        Row(
            modifier = Modifier.toggleable(
                value = isSelected,
                onValueChange = { onSelectedFilterChanged(selectedFilter) }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = typography.caption,
                color = if (isSelected) colors.onPrimary.copy(.87f) else colors.onSurface.copy(.6f),
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
    }
}

/**
 * TODO: Simplify SortBy, OrderBy, and FilterBy
 */

@Composable
fun SortBy(
    selectedFilter: SearchSortBy,
    onSelectedFilterChanged: (Any) -> Unit,
) {
    Column() {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = "Sort results by",
            style = typography.body1,
            color = colors.onSurface.copy(.87f)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(getAllSortByOptions()) { searchFilter ->
                SearchSettingChip(
                    selectedFilter = searchFilter,
                    isSelected = selectedFilter == searchFilter,
                    name = searchFilter.displayValue,
                    onSelectedFilterChanged = onSelectedFilterChanged
                )
            }
        }
    }
}

@Composable
fun OrderBy(
    selectedFilter: SearchOrderBy,
    onSelectedFilterChanged: (Any) -> Unit,
) {
    Column() {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = "Order results by",
            style = typography.body1,
            color = colors.onSurface.copy(.87f)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(getAllOrderByOptions()) { searchFilter ->
                SearchSettingChip(
                    selectedFilter = searchFilter,
                    isSelected = selectedFilter == searchFilter,
                    name = searchFilter.displayValue,
                    onSelectedFilterChanged = onSelectedFilterChanged
                )
            }
        }
    }
}

@Composable
fun FilterBy(
    selectedFilter: SearchFilterBy,
    onSelectedFilterChanged: (Any) -> Unit,
) {
    Column() {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = "Filter results by",
            style = typography.body2,
            color = colors.onSurface.copy(.87f)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(getAllFilterByOptions()) { searchFilter ->
                SearchSettingChip(
                    selectedFilter = searchFilter,
                    isSelected = selectedFilter == searchFilter,
                    name = searchFilter.displayValue,
                    onSelectedFilterChanged = onSelectedFilterChanged
                )
            }
        }
    }
}
