package org.mpmg.mpapp.domain.network.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import org.mpmg.mpapp.BuildConfig

class MPInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newRequest = original.newBuilder()
            .addHeader("X-TRENA-KEY", BuildConfig.TRENA_KEY)
            .method(original.method(), original.body())
            .build()
        return chain.proceed(newRequest)
    }
}