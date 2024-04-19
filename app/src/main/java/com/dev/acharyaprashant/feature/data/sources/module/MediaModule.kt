package com.dev.acharyaprashant.feature.data.sources.module

import androidx.paging.ExperimentalPagingApi
import com.dev.acharyaprashant.core.local.MediaDatabase
import com.dev.acharyaprashant.feature.data.repositories.MediaRepositoryImpl
import com.dev.acharyaprashant.feature.domain.repositories.MediaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalPagingApi
class MediaModule {

    @Provides
    fun providesRepository(client: HttpClient,database: MediaDatabase): MediaRepository = MediaRepositoryImpl(client, mediaDatabase = database)
}