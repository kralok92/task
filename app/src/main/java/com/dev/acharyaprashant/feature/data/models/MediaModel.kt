package com.dev.acharyaprashant.feature.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MediaModel(
    val page: Int,
    val results: List<MediaResult>,
    val total_pages: Int,
    val total_results: Int
)