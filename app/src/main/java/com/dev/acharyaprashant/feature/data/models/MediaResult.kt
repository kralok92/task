package com.dev.acharyaprashant.feature.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "Media")
@Serializable
data class MediaResult(
    @PrimaryKey(autoGenerate = false)
    val id: String,

    val poster_path: String,
)