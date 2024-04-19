package com.dev.acharyaprashant.feature.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dev.acharyaprashant.core.local.MediaDatabase
import com.dev.acharyaprashant.feature.data.models.MediaResult
import com.dev.acharyaprashant.feature.data.sources.paging.MediaRemoteMediator
import com.dev.acharyaprashant.feature.domain.repositories.MediaRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class MediaRepositoryImpl@Inject constructor(
    private val client: HttpClient,
    private val mediaDatabase: MediaDatabase
) : MediaRepository {
    override suspend fun getMediaList(): Flow<PagingData<MediaResult>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100
            ),
            remoteMediator = MediaRemoteMediator(client, mediaDatabase),
            pagingSourceFactory = { mediaDatabase.resultDao().getmedias() }
        ).flow
    }


    //    fun getMedias() = Pager(
//        config = PagingConfig(pageSize = 20, maxSize = 100),
//        pagingSourceFactory = { MediaPagingSource(client) }
//    ).flow

}