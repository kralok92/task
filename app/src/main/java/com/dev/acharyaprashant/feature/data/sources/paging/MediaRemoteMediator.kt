package com.dev.acharyaprashant.feature.data.sources.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dev.acharyaprashant.core.local.MediaDatabase
import com.dev.acharyaprashant.core.utils.apiKey
import com.dev.acharyaprashant.feature.data.models.MediaRemoteKeys
import com.dev.acharyaprashant.feature.data.models.MediaModel
import com.dev.acharyaprashant.feature.data.models.MediaResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.utils.io.errors.IOException
import retrofit2.HttpException

@ExperimentalPagingApi
class MediaRemoteMediator(
    private val client: HttpClient,
    private val mediaDatabase: MediaDatabase
) : RemoteMediator<Int, MediaResult>() {

    private val mediaDao = mediaDatabase.resultDao()
    private val mediaRemoteKeysDao = mediaDatabase.remoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MediaResult>): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = client.get{
                url("/3/discover/movie")
                parameter("page",currentPage)
                parameter("api_key", apiKey)
            }.body<MediaModel>()

            val endOfPaginationReached = response.total_pages == currentPage

            val prevPage = if(currentPage == 1) null else currentPage -1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

            mediaDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    mediaDao.deletemedias()
                    mediaRemoteKeysDao.deleteAllRemoteKeys()
                }

                mediaDao.addmedias(response.results)
                val keys = response.results.map { media ->
                    MediaRemoteKeys(
                        id = media.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                mediaRemoteKeysDao.addAllRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)
        }
        catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MediaResult>
    ): MediaRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                mediaRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MediaResult>
    ): MediaRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { media ->
                mediaRemoteKeysDao.getRemoteKeys(id = media.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MediaResult>
    ): MediaRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { media ->
                mediaRemoteKeysDao.getRemoteKeys(id = media.id)
            }
    }
}







