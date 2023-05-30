package com.goda.elmenus.di


import com.goda.elmenus.util.NetworkConstant.OK_HTTP_TIMEOUT
import com.goda.elmenus.util.KEY_ADD_TOKEN
import com.goda.elmenus.util.KEY_TOKEN
import com.friendycar.owner.util.extension.pref
import com.goda.elmenus.util.getValue
import com.goda.recipesapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(
        @Named("BaseUrl")
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).baseUrl(
                baseUrl
            ).build()
    }

    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(
                Interceptor { chain ->
                    val original = chain.request()

                    val builder: Request.Builder =
                        original.newBuilder()
                    builder.addHeader("Content-Type", "application/json")
                    builder.addHeader("app-type", "friendy_owner")
                    val currentToken = pref.getValue(KEY_TOKEN, "anything1")
                    if (pref.getValue(KEY_ADD_TOKEN, true))
                        builder.addHeader("Authorization", "Bearer $currentToken")

                    val request: Request =builder.method(original.method, original.body).build()
                    chain.proceed(request)
                }
            )
            .build()
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }


}
