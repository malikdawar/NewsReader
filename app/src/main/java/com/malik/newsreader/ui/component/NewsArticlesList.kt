package com.malik.newsreader.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.malik.newsreader.dataaccess.models.NewsArticle
import com.malik.newsreader.ui.screens.home.presentation.HomeViewModel
import com.malik.newsreader.ui.screens.home.presentation.LoadMoreArticles
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun NewsArticlesList(
    articles: List<NewsArticle>,
    viewModel: HomeViewModel,
    onclick: (article: NewsArticle) -> Unit,
) {
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    LazyVerticalGrid(columns = GridCells.Fixed(1),
        state = gridState,
        modifier = Modifier.padding(5.dp),
        content = {
            items(items = articles) { article ->
                Card(shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onclick(article) }) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        article.urlToImage?.let { url ->
                            CoilImage(
                                imageModel = { url }, imageOptions = ImageOptions(
                                    contentScale = ContentScale.Crop,
                                    alignment = Alignment.Center,
                                    contentDescription = "Image item"
                                ), modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth()
                            )
                        }
                        Text(
                            text = article.title,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = article.source,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = article.description ?: "",
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        })

    // Adding scroll listener
    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo }.collectLatest { visibleItems ->
            if (visibleItems.isNotEmpty()) {
                val lastVisibleItem = visibleItems.last()
                val totalItems = gridState.layoutInfo.totalItemsCount

                if (lastVisibleItem.index == totalItems - 2) {
                    coroutineScope.launch {
                        viewModel.onIntent(LoadMoreArticles)
                    }
                }
            }
        }
    }
}
