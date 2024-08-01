package com.malik.newsreader.dataaccess

/**
 * [DataState].kt is A Sealed class to maintain the flow of data in repo/use-case/ui
 * * @author Malik Dawar, malikdawar@hotmail.com
 */
sealed class DataState<T> {
    data class Success<T>(val data: T) : DataState<T>()

    data class Error<T>(val exception: String) : DataState<T>()
}
