package com.jmzd.ghazal.storeappmvvm.utils.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jmzd.ghazal.storeappmvvm.BuildConfig
import com.jmzd.ghazal.storeappmvvm.data.api.ApiServices
import com.jmzd.ghazal.storeappmvvm.data.stored.SessionManager
import com.jmzd.ghazal.storeappmvvm.utils.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, gson: Gson, okHttpClient: OkHttpClient): ApiServices =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServices::class.java)

    @Provides
    @Singleton
    fun povideBaseUrl(): String = BASE_URL

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideOkHttpClinet(
        timeOutDuration: Long,
        @Named(NAMED_PING) pingIntervalDuration: Long,
        sessionManager: SessionManager,
        loggingInterceptor: HttpLoggingInterceptor,
    )
            : OkHttpClient =
        OkHttpClient.Builder()
            .writeTimeout(timeOutDuration, TimeUnit.SECONDS)
            .readTimeout(timeOutDuration, TimeUnit.SECONDS)
            .connectTimeout(timeOutDuration, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .pingInterval(pingIntervalDuration, TimeUnit.SECONDS)
            .addInterceptor { chain: Interceptor.Chain ->
                val token = runBlocking {
                    sessionManager.getToken.first().toString()
                }
                chain.proceed(chain.request().newBuilder()
                    .also {
                        it.addHeader(AUTHORIZATION, "$BEARER $token")
//                        it.addHeader(ACCEPT, APPLICATION_JSON)
                    }.build())
            }
            .also { client ->
                client.addInterceptor(loggingInterceptor)
            }
            .build()

    @Provides
    @Singleton
    fun provideTimeOut(): Long = CONNECTION_TIME_OUT

    @Provides
    @Singleton
    @Named(NAMED_PING)
    fun providePingInterval(): Long = PING_INTERVAL

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

}