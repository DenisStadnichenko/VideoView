package com.videoview.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.videoview.data.db.dao.FavoriteVideoDao
import com.videoview.data.db.entity.VideoEntity

@Database(entities = [VideoEntity::class], version = 1)
abstract class VideoDatabase : RoomDatabase() {

    abstract fun favoriteVideoDao(): FavoriteVideoDao

    companion object {
        private const val DB_NAME = "video"

        fun buildDatabase(context: Context): VideoDatabase {
            return Room.databaseBuilder(context, VideoDatabase::class.java, DB_NAME)
                .build()
        }
    }
}
