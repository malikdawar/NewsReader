package com.malik.newsreader.common

import android.Manifest.permission.RECORD_AUDIO
import android.content.Context
import androidx.core.content.ContextCompat

/**
 * The [PermissionManager].kt the Home UI component
 * @author Malik Dawar, malikdawar@hotmail.com
 */
object PermissionManager {

    fun hasAudioPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            RECORD_AUDIO
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }
}
