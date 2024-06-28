package com.example.kuwait.di

import com.example.kuwait.BuildConfig
import com.example.kuwait.Network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesUrl() = BuildConfig.baseUrl

    @Provides
    @Singleton
    fun providesApiService(url: String): ApiService =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(ApiService::class.java)


    val certPinner = CertificatePinner.Builder()
        .add(
            BuildConfig.DOMAIN,
            BuildConfig.SHA256
        ).build()

    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {

            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                .header("apikey", BuildConfig.apikey)
            val request = requestBuilder.build()
            return chain.proceed(request)
        }
    }

    val httpClient = OkHttpClient.Builder().apply {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        certificatePinner(certPinner)
        interceptors().add(HeaderInterceptor())
    }.build()


}