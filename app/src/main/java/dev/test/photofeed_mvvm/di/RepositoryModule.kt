package dev.test.photofeed_mvvm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.test.photofeed_mvvm.data.local.PhotoDao
import dev.test.photofeed_mvvm.data.remote.PhotoRemoteDataSource
import dev.test.photofeed_mvvm.data.repository.PhotosRepository
import dev.test.photofeed_mvvm.data.repository.PhotosRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providePhotoRepository(
        remoteDataSource: PhotoRemoteDataSource,
        photoDao: PhotoDao
    ): PhotosRepository {
        return PhotosRepositoryImpl(remoteDataSource, photoDao)
    }
}