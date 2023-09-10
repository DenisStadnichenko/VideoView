package com.videoview.di

import android.content.Context
import com.videoview.data.db.VideoDatabase
import com.videoview.data.db.dao.FavoriteVideoDao
import com.videoview.data.mapper.EntityVideoMapper
import com.videoview.data.mapper.EntityVideoMapperImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [DatabaseModule.Mapper::class])
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDB(@ApplicationContext context: Context): VideoDatabase =
        VideoDatabase.buildDatabase(context)

    @Singleton
    @Provides
    fun provideFavoriteVideoDao(db: VideoDatabase): FavoriteVideoDao = db.favoriteVideoDao()

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class Mapper {

        @Binds
        @Singleton
        abstract fun bindVideoMapper(repository: EntityVideoMapperImpl): EntityVideoMapper
    }

}