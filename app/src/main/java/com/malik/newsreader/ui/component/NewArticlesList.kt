package com.malik.newsreader.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.malik.newsreader.dataaccess.models.NewsArticlesResponse
import com.malik.newsreader.ui.screens.home.model.HomeViewModel
import com.malik.newsreader.ui.screens.home.model.LoadMoreArticles
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun NewArticlesList(
    articles: List<NewsArticlesResponse.Article>,
    viewModel: HomeViewModel,
    onclick: (article: NewsArticlesResponse.Article) -> Unit,
) {
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        state = gridState,
        modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 10.dp),
        content = {
            items(items = articles) {
                Column(
                    modifier =
                        Modifier.padding(
                            start = 5.dp,
                            end = 5.dp,
                            top = 0.dp,
                            bottom = 10.dp,
                        ),
                ) {
                    CoilImage(
                        imageModel = { it.urlToImage },
                        imageOptions =
                            ImageOptions(
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center,
                                contentDescription = "Image item",
                                colorFilter = null,
                            ),
                        modifier =
                            Modifier.height(250.dp).fillMaxWidth()
                                .shimmerBackground(
                                    RoundedCornerShape(5.dp),
                                ).clickable {
                                    onclick(it)
                                },
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = it.source?.name?:"",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = it.description ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    )

    // Adding scroll listener
    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo }
            .collectLatest { visibleItems ->
                if (visibleItems.isNotEmpty()) {
                    val lastVisibleItem = visibleItems.last()
                    val totalItems = gridState.layoutInfo.totalItemsCount

                    if (lastVisibleItem.index == totalItems - 1) {
                        coroutineScope.launch {
                            viewModel.onIntent(LoadMoreArticles)
                        }
                    }
                }
            }
    }
}
