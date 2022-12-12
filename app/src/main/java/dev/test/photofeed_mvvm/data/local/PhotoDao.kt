package dev.test.photofeed_mvvm.data.local

import androidx.room.*
import dev.test.photofeed_mvvm.model.local.PhotoItem

/**
 * The DAO where we will find all the needed request to store / get / delete [PhotoItem]
 */
@Dao
interface PhotoDao {

    @Query("SELECT * FROM photoitem")
    fun getAll(): List<PhotoItem>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photosFeed: ArrayList<PhotoItem>)

    @Delete
    fun deleteAll(photoFeed: ArrayList<PhotoItem>)
}
