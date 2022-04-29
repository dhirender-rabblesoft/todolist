package com.app.todolist.network

import android.content.Context
import com.app.todolist.BuildConfig
import com.app.todolist.utils.Keys
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class RetrofitClient {
    val client: Retrofit?
        get() {
            var token = ""
            if (retrofit == null) retrofit = provideRetrofit(Keys.BASEURL,token)
            return retrofit
        }
    private fun provideRetrofit(baseUrl: String,tokens:String): Retrofit
    {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG)
        {
            httpClient.addInterceptor(interceptor)
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }

    private val dispatcher: Dispatcher
        private get() {
            val dispatcher = Dispatcher(Executors.newFixedThreadPool(1))
            dispatcher.maxRequests = 1
            dispatcher.maxRequestsPerHost = 1
            return dispatcher
        }

    companion object {
        private const val CACHE_CONTROL = "Cache-Control"
        var retrofit: Retrofit? = null
        private var mContext: Context? = null
        private var retofitClient: RetrofitClient? = null
        fun with(context: Context?): RetrofitClient? {
            if (retofitClient == null) retofitClient = RetrofitClient()
            mContext = context
            return retofitClient
        }
    }

}