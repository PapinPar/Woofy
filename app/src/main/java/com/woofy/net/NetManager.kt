package com.woofy.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.woofy.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetManager {
    private var restApi: RestAPI
    private var gson: Gson
    private var readTimeout = 1L
    private var writeimeout = 2L

    init {
        gson = createGson()

        restApi = Retrofit.Builder().apply {
            baseUrl(BuildConfig.SERVER_URL)
            client(initClient(false))
            addConverterFactory(GsonConverterFactory.create(gson))
            addCallAdapterFactory(CoroutineCallAdapterFactory())
        }.build().create(RestAPI::class.java)
    }

    fun getRestApi(): RestAPI {
        return restApi
    }

    private fun createGson(): Gson {
        return GsonBuilder().apply {
            setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            setLenient()
        }.create()
    }

    fun getGson(): Gson {
        return gson
    }

    private fun initClient(needToken: Boolean): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().apply {
            addNetworkInterceptor(interceptor)
            readTimeout(readTimeout, TimeUnit.MINUTES)
            connectTimeout(readTimeout, TimeUnit.MINUTES)
            writeTimeout(writeimeout, TimeUnit.MINUTES)
            addNetworkInterceptor { chain ->
                val request: Request
                val original = chain.request()
                val originalBuilder = original.newBuilder()
                originalBuilder.addHeader("Content-PresentationsType", "application/json")
                request = originalBuilder.build()
                chain.proceed(request)
            }
        }.build()
    }
}