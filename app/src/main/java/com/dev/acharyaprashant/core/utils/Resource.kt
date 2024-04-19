package com.dev.acharyaprashant.core.utils

import com.dev.acharyaprashant.core.exception.ApiException

sealed class Resource<out T>  {
    data class Loading<T>(val isLoading: Boolean) : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val exception: ApiException) : Resource<Nothing>()
}