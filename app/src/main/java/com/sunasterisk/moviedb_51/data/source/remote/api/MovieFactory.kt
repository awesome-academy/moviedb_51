package com.sunasterisk.moviedb_51.data.source.remote.api

import android.content.Context
import com.sunasterisk.moviedb_51.BuildConfig
import com.sunasterisk.moviedb_51.R
import com.sunasterisk.moviedb_51.utils.Constant
import com.sunasterisk.moviedb_51.utils.NetworkUtil
import com.sunasterisk.moviedb_51.utils.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MovieFactory(context: Context) {
    val instance: MovieService by lazy {
        retrofitBuilder(context).create(MovieService::class.java)
    }

    companion object {
        private const val TIME_OUT = 15000L

        private fun client(context: Context) = OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(Interceptor { chain ->
                chain.proceed(buildRequest(chain))
            })
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(Interceptor { chain ->
                connectivityInterceptor(context, chain)
            })
            .build()

        private fun retrofitBuilder(context: Context) = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client(context))
            .build()

        private fun buildRequest(chain: Interceptor.Chain) = chain.request()
            .newBuilder()
            .url(addApiKey(chain))
            .build()

        private fun addApiKey(chain: Interceptor.Chain) = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()

        private fun connectivityInterceptor(context: Context, chain: Interceptor.Chain): Response {
            if (!NetworkUtil.isConnectedToNetwork(context))
                throw NoConnectivityException(context.getString(R.string.message_check_internet_fail))
            return chain.proceed(chain.request())
        }
    }
}
