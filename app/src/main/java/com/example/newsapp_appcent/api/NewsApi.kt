package com.example.newsapp_appcent.api

import com.example.newsapp_appcent.models.NewsResponse
import com.example.newsapp_appcent.util.Constant.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale.IsoCountryCode

interface NewsApi {

    @GET("v2/everything")
    suspend fun getHeadlines(
        @Query("q")
        countryCode: String = "besiktas",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ):Response<NewsResponse>
}