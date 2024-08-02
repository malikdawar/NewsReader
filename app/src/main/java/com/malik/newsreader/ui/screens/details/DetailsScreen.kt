package com.malik.newsreader.ui.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.malik.newsreader.dataaccess.models.NewsArticle
import com.malik.newsreader.ui.component.shimmerBackground
import com.malik.newsreader.ui.screens.SharedViewModel
import com.skydoves.landscapist.coil3.CoilImage

/**
 * The [NewsDetailScreen].kt the Home UI component
 * @author Malik Dawar, malikdawar@hotmail.com
 */
@Composable
fun DetailScreen(article: NewsArticle) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Shimmer effect for the image
            val imageModifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .shimmerBackground(shape = RoundedCornerShape(16.dp))

            if (article.urlToImage != null) {
                CoilImage(imageModel = { article.urlToImage }, modifier = imageModifier, loading = {
                    Box(modifier = imageModifier) {}
                })
            } else {
                Box(modifier = imageModifier) {}
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "By ${article.author}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

            Text(
                text = "Source: ${article.source}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

            Text(
                text = article.description.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Justify,
                lineHeight = 20.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 6
            )

            Text(
                text = article.content.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Justify,
                lineHeight = 20.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 6
            )
        }
    }
}

@Composable
fun NewsDetailScreen(sharedViewModel: SharedViewModel) {
    val selectedArticle by sharedViewModel.selectedArticle.collectAsState(initial = null)

    selectedArticle?.let { article ->
        DetailScreen(article)
    }
}
