package com.jtampinco.githubsearch.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.jtampinco.githubsearch.app.theme.shapes
import com.jtampinco.githubsearch.app.theme.typography
import com.jtampinco.githubsearch.data.domain.model.Repository
import com.jtampinco.githubsearch.resources.ForkIcon
import com.jtampinco.githubsearch.resources.IssuesIcon

@OptIn(
    ExperimentalCoilApi::class,
    ExperimentalAnimationApi::class,
)
@Composable
fun GithubRepoCard(
    repository: Repository,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    BoxWithConstraints {
        val layoutWidth = maxWidth
        Surface(
            modifier = modifier.clickable(onClick = onClick),
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = repository.owner.avatar_url,
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(75.dp)
                            .clip(shapes.medium)
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = repository.fullName,
                            style = typography.subtitle2,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = repository.description ?: "",
                            style = typography.caption,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = repository.lastUpdated?.let {
                                "Last Updated: ${it.subSequence(0, 10)}"
                            } ?: "",
                            style = typography.overline,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
                AnimatedVisibility(visible = layoutWidth > 300.dp) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        GithubMetrics(
                            modifier = Modifier.wrapContentSize(),
                            icon = Icons.Rounded.Visibility,
                            contentDescription = "Contributors Count",
                            label = "Watchers",
                            value = repository.watchersCount.toString(),
                        )
                        GithubMetrics(
                            modifier = Modifier.wrapContentSize(),
                            icon = Icons.IssuesIcon,
                            contentDescription = "Issues Count",
                            label = "Issues",
                            value = repository.issuesCount.toString(),
                        )
                        GithubMetrics(
                            modifier = Modifier.wrapContentSize(),
                            icon = Icons.Rounded.StarOutline,
                            contentDescription = "Stars Count",
                            label = "Score",
                            value = repository.score.toString(),
                        )
                        GithubMetrics(
                            modifier = Modifier.wrapContentSize(),
                            icon = Icons.ForkIcon,
                            contentDescription = "Forks Count",
                            label = "Forks",
                            value = repository.forksCount.toString(),
                        )
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(colors.primary)
                        .padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun GithubMetrics(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String,
    label: String,
    value: String,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
            )
        }
        Column(
            modifier = Modifier.wrapContentSize(),
        ) {
            Text(
                text = value,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = typography.body2,
            )
            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.medium,
            ) {
                Text(
                    text = label,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = typography.caption,
                )
            }
        }
    }
}