package dev.test.photofeed_mvvm.data.remote

import dev.test.photofeed_mvvm.model.remote.RemotePhotoListItem
import dev.test.photofeed_mvvm.network.PhotoUnsplashService
import dev.test.photofeed_mvvm.util.state.Resource
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject

/**
 * fetch photo from the unsplash API
 * @param retrofit : [Retrofit]
 */
class PhotoRemoteDataSource @Inject constructor(private val retrofit: Retrofit) {

    /**
     * fetch photoFeed from the unsplash API
     * @return [Resource]<[List]<[RemotePhotoListItem]>>
     */
    suspend fun fetchPhotosFeed(): Resource<List<RemotePhotoListItem>> {
        val photoService = retrofit.create((PhotoUnsplashService::class.java))
        return getResponse(
            request = {photoService.getPhotoFeed()},
            defaultErrorMessage = "Error while getting the photo feed"
        )
    }

    /**
     * Generic response getter
     * @return [Resource]<[T]>
     */
    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Resource<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                if (result.body() != null)
                    return Resource.success(result.body()!!)
                else
                    if (result.errorBody() != null)
                        Resource.error("${result.code()} : ${result.errorBody().toString()}", null)
                    else
                        Resource.error("Unknown error", null)
            } else {
                if (result.errorBody() != null)
                    Resource.error("${result.code()} : ${result.errorBody().toString()}", null)
                else
                    Resource.error(defaultErrorMessage, null)
            }
        } catch (e: Throwable) {
            Resource.error("${e.message}", null)
        }
    }
}