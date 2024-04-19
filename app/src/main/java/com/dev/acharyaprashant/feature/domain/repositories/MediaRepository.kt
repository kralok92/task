package com.dev.acharyaprashant.feature.domain.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dev.acharyaprashant.feature.data.models.MediaResult
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun getMediaList():Flow<PagingData<MediaResult>>
}