package com.oluwatimilehin.cryptoconverter.data.di

import com.oluwatimilehin.cryptoconverter.data.Constants
import com.oluwatimilehin.cryptoconverter.network.CryptoCompareService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
const val BASE_URL = "base_url"

@Module
class ApiServiceModule {

    @Provides
    @Named(BASE_URL)
    fun provideBaseUrl(): String = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(@Named(BASE_URL) baseUrl: String, okHttpClient: OkHttpClient):
            Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .build()
    }

    @Provides
    @Singleton
    fun provideCryptoCompareService(retrofit: Retrofit): CryptoCompareService = retrofit.create(CryptoCompareService::class.java)

}