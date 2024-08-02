package com.malik.newsreader.common.extensions

import android.content.Context
import android.widget.Toast


/**
 * The StringExtension.kt
 */


/**
 * Extension function nothingFound
 */
fun nothingFound() = "Nothing found!"

/**
 * Extension function nothingFound
 */
fun noNetworkErrorMessage() = "No internet connection!"


/**
 * Extension function toast
 */
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}