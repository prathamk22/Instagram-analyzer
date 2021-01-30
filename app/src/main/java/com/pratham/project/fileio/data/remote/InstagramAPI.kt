package com.pratham.project.fileio.data.remote

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pratham.project.fileio.data.utils.InstagramAnalyzerInterceptor
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class InstagramAPI(val context: Context) {
    companion object {
        private const val PROD = "i.instagram.com"
        const val CONNECT_TIMEOUT = 15
        const val READ_TIMEOUT = 15
    }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val clientInterceptor = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
        .addInterceptor(InstagramAnalyzerInterceptor(context))
        .addInterceptor(logging)
        .build()

    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .client(clientInterceptor)
        .baseUrl("https://$PROD/api/v1/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val api: InstagramAPICalls = retrofit.create(
        InstagramAPICalls::class.java
    )
}
