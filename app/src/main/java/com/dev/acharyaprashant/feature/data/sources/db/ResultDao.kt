package com.dev.acharyaprashant.feature.data.sources.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.acharyaprashant.feature.data.models.MediaResult

@Dao
interface ResultDao {

    @Query("SELECT * FROM Media")
    fun getmedias(): PagingSource<Int, MediaResult>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addmedias(medias: List<MediaResult>)

    @Query("DELETE FROM Media")
    suspend fun deletemedias()

}