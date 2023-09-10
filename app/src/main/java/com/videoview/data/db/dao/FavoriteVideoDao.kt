package com.videoview.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.videoview.data.db.entity.VideoEntity

@Dao
abstract class FavoriteVideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(videoEntity: VideoEntity)

    @Delete
    abstract suspend fun remove(videoEntity: VideoEntity)

    @Query("SELECT * FROM favorite_video ORDER BY date DESC")
    abstract fun getFavorite(): PagingSource<Int, VideoEntity>

}