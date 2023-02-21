package com.appdevcourse.bengaliappv2.di

import com.appdevcourse.bengaliappv2.data.repository.UserDbRepository
import com.appdevcourse.bengaliappv2.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesUserDbRepository(
        repo:UserDbRepository
    ):UserRepository
}