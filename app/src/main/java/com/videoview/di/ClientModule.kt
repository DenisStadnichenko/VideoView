package com.videoview.di


import com.videoview.remote.client.video.VideoClient
import com.videoview.remote.client.video.VideoClientImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ClientModule {

    @Binds
    @Singleton
    abstract fun bindVideoClient(client: VideoClientImpl): VideoClient

}