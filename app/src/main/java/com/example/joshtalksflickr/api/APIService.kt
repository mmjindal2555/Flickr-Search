package com.example.joshtalksflickr.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {
    @GET("/services/rest/")
    fun listRepos(@Query("method") method: String,
                  @Query("text") text: String,
                  @Query("per_page") per_page: String,
                  @Query("page") page: String,
                  @Query("format") format: String,
                  @Query("nojsoncallback") nojsoncallback: Int,
                  @Query("api_key") apiKey: String): Call<SearchResult>
}