package pl.adrianczerwinski.events.di

import android.media.metrics.Event
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pl.adrianczerwinski.events.network.EventsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideEventsApi(retrofit: Retrofit): EventsApi = retrofit.create(EventsApi::class.java)
}
private const val BASE_URL = "https://us-central1-dazn-sandbox.cloudfunctions.net"
private const val READ_TIMEOUT = 30L
private const val CONNECTION_TIMEOUT = 30L
