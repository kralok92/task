package com.dev.acharyaprashant.feature.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MediaRemoteKeys(

    @PrimaryKey(autoGenerate = false)
    val id: String,

    val prevPage: Int?,
    val nextPage: Int?
)