package com.stockanalyzer.di

import android.content.Context
import androidx.preference.PreferenceManager
import com.stockanalyzer.data.api.NewsApiService
import com.stockanalyzer.data.api.StockApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val DEFAULT_BASE_URL = "http://172.17.6.46:8000"

/**
 * Interceptor that rewrites the request host/port to whatever is currently
 * saved in SharedPreferences, so URL changes in Settings take effect
 * immediately without restarting the app.
 */
class DynamicBaseUrlInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val rawUrl = (prefs.getString("api_base_url", DEFAULT_BASE_URL) ?: DEFAULT_BASE_URL)
            .trimEnd('/')
        val newBase = "$rawUrl/".toHttpUrl()
        val original = chain.request()
        val newUrl = original.url.newBuilder()
            .scheme(newBase.scheme)
            .host(newBase.host)
            .port(newBase.port)
            .build()
        val newRequest: Request = original.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(DynamicBaseUrlInterceptor(context))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            // Placeholder base URL — DynamicBaseUrlInterceptor rewrites it per-request
            .baseUrl("$DEFAULT_BASE_URL/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideStockApiService(retrofit: Retrofit): StockApiService =
        retrofit.create(StockApiService::class.java)

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService =
        retrofit.create(NewsApiService::class.java)
}
