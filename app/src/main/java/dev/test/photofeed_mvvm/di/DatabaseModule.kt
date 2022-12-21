package dev.test.photofeed_mvvm.di

import android.content.Context
import androidx.room.Room
import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.test.photofeed_mvvm.data.local.AppDatabase
import dev.test.photofeed_mvvm.data.local.PhotoDao

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    /**
     * Build and return the global database singleton
     * @param appContext : [Context]
     * @return [AppDatabase]
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context) : AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "local.db"
        ).build()
    }

    /**
     * @param localDb : [AppDatabase]
     * @return [PhotoDao]
     */
    @Provides
    fun providePhotoDao(localDb: AppDatabase) : PhotoDao {
        return localDb.photoDao()
    }
}