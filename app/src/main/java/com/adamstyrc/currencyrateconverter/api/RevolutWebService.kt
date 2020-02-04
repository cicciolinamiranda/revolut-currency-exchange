package com.adamstyrc.currencyrateconverter.api

import android.content.Context
import com.adamstyrc.currencyrateconverter.BuildConfig
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RevolutWebService(
    private val applicationContext: Context
) {

    companion object {
        const val SERVER_ADDRESS = "https://revolut.duckdns.org/"
    }

    val api: RevolutApi

    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(provideHttpClient())
            .build()

        api = retrofit.create(RevolutApi::class.java)
}

    private fun provideHttpClient() : OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(90, TimeUnit.SECONDS)
            .connectTimeout(90, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(httpLoggingInterceptor)

            okHttpClient.addInterceptor(ChuckInterceptor(applicationContext))

        }
        return okHttpClient.build()
    }
}
