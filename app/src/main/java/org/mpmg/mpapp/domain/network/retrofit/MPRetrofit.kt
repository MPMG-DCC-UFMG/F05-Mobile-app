package org.mpmg.mpapp.domain.network.retrofit

import okhttp3.OkHttpClient
import org.mpmg.mpapp.BuildConfig
import org.mpmg.mpapp.domain.network.api.MPApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).callFactory(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().build()
}


fun provideMPApi(retrofit: Retrofit): MPApi = retrofit.create(MPApi::class.java)
