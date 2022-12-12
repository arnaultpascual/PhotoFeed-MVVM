package dev.test.photofeed_mvvm.network

import dev.test.photofeed_mvvm.model.remote.RemotePhotoListItem
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*

/**
 * Retrofit PhotoService from Unsplash API
 */
interface PhotoUnsplashService {

    /**
     * Function needed to get the photoFeed
     * @param nbItemPerPage : [Int] => default value 100
     * @return [Response]<[List]<[RemotePhotoListItem]>>
     */
    @GET("photos")
    suspend fun getPhotoFeed(
        @Query("per_page") nbItemPerPage : Int = 100
    ) : Response<List<RemotePhotoListItem>>

}