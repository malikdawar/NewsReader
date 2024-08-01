package com.malik.newsreader

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The [App].kt the Application class of the project
 * @author Malik Dawar, malikdawar@hotmail.com
 */

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
