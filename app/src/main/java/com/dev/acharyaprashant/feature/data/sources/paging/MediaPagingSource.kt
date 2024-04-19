package com.dev.acharyaprashant.feature.data.sources.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dev.acharyaprashant.core.utils.apiKey
import com.dev.acharyaprashant.feature.data.models.MediaModel
import com.dev.acharyaprashant.feature.data.models.MediaResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import java.lang.Exception

class MediaPagingSource(private val client: HttpClient) : PagingSource<Int, MediaResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaResult> {
        return try {
            val nextPageNumber  = params.key ?: 1
            val response = client.get{
                url("/3/discover/movie")
                parameter("page",nextPageNumber)
                parameter("api_key", apiKey)
            }.body<MediaModel>()

            return LoadResult.Page(
                data = response.results,
                prevKey = if (nextPageNumber  == 1) null else nextPageNumber  - 1,
                nextKey = if (nextPageNumber  == response.total_pages) null else nextPageNumber  + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MediaResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}