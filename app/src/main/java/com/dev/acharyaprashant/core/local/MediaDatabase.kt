package com.dev.acharyaprashant.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev.acharyaprashant.feature.data.sources.db.RemoteKeysDao
import com.dev.acharyaprashant.feature.data.sources.db.ResultDao
import com.dev.acharyaprashant.feature.data.models.MediaRemoteKeys
import com.dev.acharyaprashant.feature.data.models.MediaResult

@Database(
    entities = [MediaResult::class, MediaRemoteKeys::class],
    version = 1
)
abstract class MediaDatabase : RoomDatabase() {
    abstract fun resultDao() : ResultDao
    abstract fun remoteKeysDao() : RemoteKeysDao
}