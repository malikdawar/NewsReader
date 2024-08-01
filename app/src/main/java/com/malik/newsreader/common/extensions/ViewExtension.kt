package com.malik.newsreader.common.extensions

import android.content.Context
import android.widget.Toast


/**
 * The StringExtension.kt
 */


/**
 * Extension function to show toast
 */
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}