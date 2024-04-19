package com.dev.acharyaprashant.feature.presentation.arch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dev.acharyaprashant.feature.data.models.MediaResult
import com.dev.acharyaprashant.feature.domain.repositories.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@ExperimentalPagingApi
@HiltViewModel
class MediaViewModel@Inject constructor(private val repository: MediaRepository):ViewModel() {


    private val _mediaLiveData = MutableLiveData<PagingData<MediaResult>>()
    val mediaLiveData : LiveData<PagingData<MediaResult>>
        get() = _mediaLiveData

    init {
        getMediaList()
    }

    private fun getMediaList() = viewModelScope.launch {
        val response = repository.getMediaList().cachedIn(viewModelScope)

        response.collect {
            _mediaLiveData.value = it
        }
    }


//    private val _mediaListFlow = MutableStateFlow<PagingData<MediaResult>>(PagingData.empty())
//    val mediaListFlow: StateFlow<PagingData<MediaResult>> = _mediaListFlow.asStateFlow()
//
//    fun fetchMediaList() {
//        viewModelScope.launch {
//            try {
//                val pagingData = repository.getMediaList().cachedIn(viewModelScope)
//                _mediaListFlow.value = pagingData
//            } catch (e: Exception) {
//                // Handle error (e.g., log, show error message)
//                Log.e("zxzxzxzxzxzxz", "Error fetching media list: ${e.message}", e)
//            }
//        }
//    }

}