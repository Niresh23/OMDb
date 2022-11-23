package com.niresh23.omdb.di

import com.niresh23.omdb.base.Constants
import com.niresh23.omdb.di.annotations.AuthOkHttpClient
import com.niresh23.omdb.service.OmdbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import java.lang.Exception

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun getAuthRetrofitClient(@AuthOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @AuthOkHttpClient
    @Provides
    fun getAuthHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(
            Interceptor { chain ->
                val request = chain.request()
                val urlRequest = request.url
                val newUrlRequest = urlRequest
                    .newBuilder()
                    .addQueryParameter("apikey", Constants.API_KEY).build()
                val newRequest = request.newBuilder().url(newUrlRequest).build()
                try {
                    chain.proceed(newRequest)
                } catch (exception: Exception) {
                    chain.proceed(request)
                }
            }
        ).addInterceptor(loggingInterceptor).build()
    }

    @Provides
    fun getService(retrofit: Retrofit): OmdbService {
        return retrofit.create(OmdbService::class.java)
    }

}