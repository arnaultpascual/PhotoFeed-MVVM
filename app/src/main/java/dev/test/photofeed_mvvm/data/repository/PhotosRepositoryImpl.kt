package dev.test.photofeed_mvvm.data.repository

import dev.test.photofeed_mvvm.data.local.PhotoDao
import dev.test.photofeed_mvvm.data.remote.PhotoRemoteDataSource
import dev.test.photofeed_mvvm.model.local.PhotoItem
import dev.test.photofeed_mvvm.util.state.Resource
import dev.test.photofeed_mvvm.util.state.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * The photo Repository
 * @param remoteDataSource : [PhotoRemoteDataSource]
 * @param photoDao : [PhotoDao]
 */
class PhotosRepositoryImpl @Inject constructor(
    private val remoteDataSource: PhotoRemoteDataSource,
    private val photoDao: PhotoDao
    ) : PhotosRepository
{

    /**
     * fetch photoFeed from the unsplash API and translate it to [ArrayList]<[PhotoItem]>
     * @return [Flow]<[Resource]<[ArrayList]<[PhotoItem]>>>
     */
    override suspend fun fetchPhotoFeed() : Flow<Resource<ArrayList<PhotoItem>>> {
        return flow {
            emit(Resource.loading())

            val distantResource = remoteDataSource.fetchPhotosFeed()
            if (distantResource.data != null
                && distantResource.status == Status.SUCCESS){
                    val translatedPhotoList = ArrayList<PhotoItem>()
                    distantResource.data.forEach{ it ->
                        val photoItem = it.translate()
                        translatedPhotoList.add(photoItem)
                    }
                    //before emitting to continue the flow we tore the result in the local DB
                    photoDao.insertAll(translatedPhotoList)

                    emit(Resource(Status.SUCCESS, photoDao.getAll() as ArrayList, distantResource.message, null))
            }else{
                emit(Resource(Status.ERROR, photoDao.getAll() as ArrayList, "retrieve local db", distantResource.errorMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

    /**
     * fetch specific photo from the unsplash API and translate it to [PhotoItem]
     * @return [Flow]<[Resource]<[ArrayList]<[PhotoItem]>>>
     */
    override suspend fun fetchPhotoFromGivenId(id : String) : Flow<Resource<PhotoItem>> {
        return flow {
            emit(Resource.loading())

            val distantResource = remoteDataSource.fetchGivenPhoto(id)

            if (distantResource.data != null
                && distantResource.status == Status.SUCCESS
            ) {

                emit(
                    Resource(
                        Status.SUCCESS,
                        distantResource.data.translate(),
                        distantResource.message,
                        null
                    )
                )
            } else {
                emit(Resource(Status.ERROR, null, "cannot load", distantResource.errorMessage))
            }
        }.flowOn(Dispatchers.IO)
    }
}
