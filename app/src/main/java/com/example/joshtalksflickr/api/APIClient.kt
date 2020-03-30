package com.example.joshtalksflickr.api

import com.facebook.stetho.Stetho
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.facebook.stetho.okhttp3.StethoInterceptor



private var apiService: APIService? = null

fun getApis() : APIService {
    if(apiService == null) {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val okHttpClient = OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build()
        apiService =
            Retrofit.Builder().baseUrl("https://www.flickr.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(APIService::class.java)
    }
    return apiService!!
}

