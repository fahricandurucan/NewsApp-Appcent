package com.example.newsapp_appcent.api

import com.example.newsapp_appcent.models.NewsResponse
import com.example.newsapp_appcent.util.Constant.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/everything")
    fun getNews(
        @Query("q")
        query: String,
        @Query("apiKey")
        apiKey: String = API_KEY,
        @Query("page")
        page: Int = 1,
    ):Response<NewsResponse>

}