package com.malik.newsreader.di

import com.malik.newsreader.dataaccess.remote.ApiInterface
import com.malik.newsreader.dataaccess.repository.NewsArticlesRepository
import com.malik.newsreader.dataaccess.repository.NewsArticlesRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * [RepositoryModule].kt to handle the DI for repos
 * * @author Malik Dawar, malikdawar@hotmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideNewsArticlesRepository(apiService: ApiInterface): NewsArticlesRepository {
        return NewsArticlesRepositoryImp(apiService)
    }
}
