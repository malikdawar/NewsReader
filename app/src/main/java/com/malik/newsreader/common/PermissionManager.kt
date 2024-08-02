package com.malik.newsreader.common

import android.Manifest
import android.content.Context
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * The [PermissionManager].kt the Home UI component
 * @author Malik Dawar, malikdawar@hotmail.com
 */
class PermissionManager(private val context: Context) {

    companion object {
        private const val RECORD_AUDIO_PERMISSION = Manifest.permission.RECORD_AUDIO
        private const val PERMISSION_REQUEST_CODE = 1
    }

    fun hasAudioPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            RECORD_AUDIO_PERMISSION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }

    fun requestAudioPermission(activity: androidx.activity.ComponentActivity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(RECORD_AUDIO_PERMISSION),
            PERMISSION_REQUEST_CODE
        )
    }

    fun handlePermissionResult(requestCode: Int, grantResults: IntArray): Boolean {
        return if (requestCode == PERMISSION_REQUEST_CODE) {
            grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
    }
}
