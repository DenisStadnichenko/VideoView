package com.videoview.di


import com.videoview.domain.VideoUseCase
import com.videoview.domain.VideoUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@InstallIn(ViewModelComponent::class)
@Module
abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindVideoUseCase(useCase: VideoUseCaseImpl): VideoUseCase
}