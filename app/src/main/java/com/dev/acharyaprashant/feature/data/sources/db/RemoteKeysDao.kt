package com.dev.acharyaprashant.feature.data.sources.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.acharyaprashant.feature.data.models.MediaRemoteKeys

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM MediaRemoteKeys WHERE id =:id")
    suspend fun getRemoteKeys(id: String): MediaRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<MediaRemoteKeys>)

    @Query("DELETE FROM MediaRemoteKeys")
    suspend fun deleteAllRemoteKeys()
}