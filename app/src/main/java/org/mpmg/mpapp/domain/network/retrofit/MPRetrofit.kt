package org.mpmg.mpapp.domain.network.retrofit

import okhttp3.OkHttpClient
import org.mpmg.mpapp.BuildConfig
import org.mpmg.mpapp.domain.network.api.MPApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).callFactory(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create()).build()
}

fun provideClient(): OkHttpClient {
    return when (BuildConfig.ENVIRONMENT) {
        else -> provideUnsafeOkHttpClient()
//        "production" -> provideUnsafeOkHttpClient()
//        else -> provideOkHttpClient()
    }
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.MINUTES)
        .writeTimeout(10, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.MINUTES).addInterceptor(MPInterceptor()).build()
}

fun provideUnsafeOkHttpClient(): OkHttpClient {
    return try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                    //ignore
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                    //ignore
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

            }
        )

        // Install the all-trusting trust manager
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.getSocketFactory()
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.MINUTES)
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier(object : HostnameVerifier {
            override fun verify(hostname: String?, session: SSLSession?): Boolean = true
        })
        builder.addInterceptor(MPInterceptor()).build()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}


fun provideMPApi(retrofit: Retrofit): MPApi = retrofit.create(MPApi::class.java)
