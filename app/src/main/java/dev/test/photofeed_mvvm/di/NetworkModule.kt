package dev.test.photofeed_mvvm.di

import android.os.Build
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.test.photofeed_mvvm.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provide the http log level to better debugging
     * @return [HttpLoggingInterceptor]
     */
    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor;
    }

    /**
     * Provide the [OkHttpClient] and add needed header
     * @param loggingInterceptor : [HttpLoggingInterceptor]
     * @return [OkHttpClient]
     */
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(newRequest)
        }
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("X-Client-SDK-INT", "${Build.VERSION.SDK_INT}")
                    .addHeader("X-Client-Name", "dev.test.photofeed_mvvm")
                    .addHeader("Authorization", "Client-ID KEGCgF0jbivpTEZTo6_XUZw5rnPx49CFYIFga33dUA4")
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    /**
     * Provide the [Retrofit] Unsplash API client needed for making request
     * @param okHttpClient : [OkHttpClient]
     * @return [Retrofit]
     */
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}