package com.malik.newsreader.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * The [ProgressDialog].kt to handle the progress while async in progress
 * @author Malik Dawar, malikdawar@hotmail.com
 */
@Composable
fun ProgressDialog(isVisible: Boolean) {
    if (isVisible) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                color = Color.Gray,
                modifier = Modifier.size(48.dp),
            )
        }
    }
}

@Preview
@Composable
fun PreviewProgressDialog() {
    ProgressDialog(isVisible = true)
}
