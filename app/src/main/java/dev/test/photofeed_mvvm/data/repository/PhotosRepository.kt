package dev.test.photofeed_mvvm.data.repository

import dev.test.photofeed_mvvm.model.local.PhotoItem
import dev.test.photofeed_mvvm.util.state.Resource
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {

    suspend fun fetchPhotoFeed() : Flow<Resource<ArrayList<PhotoItem>>>
    suspend fun fetchPhotoFromGivenId(id : String) : Flow<Resource<PhotoItem>>

}