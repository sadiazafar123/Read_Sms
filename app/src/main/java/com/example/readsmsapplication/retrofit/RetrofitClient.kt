package com.example.readsmsapplication.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    fun getInstance(): ApiInterface {
        var mHttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val gson = GsonBuilder().setLenient().create()
        val mOkHttpClient = OkHttpClient.Builder()
        val loggingInterceptor = getLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        mOkHttpClient.addInterceptor(loggingInterceptor)
        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl("http://www.google.com/").addConverterFactory(
                GsonConverterFactory.create(gson)
            ).client(mOkHttpClient.build()).build()
        return retrofit.create(ApiInterface::class.java)

    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }


}