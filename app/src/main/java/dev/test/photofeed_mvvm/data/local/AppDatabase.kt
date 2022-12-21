package dev.test.photofeed_mvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.test.photofeed_mvvm.model.local.PhotoItem


/**
 * The global database where we will add all DAO
 */
@Database(entities = [PhotoItem::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun photoDao(): PhotoDao
}