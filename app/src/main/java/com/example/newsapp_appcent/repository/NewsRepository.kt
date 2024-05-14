package com.example.newsapp_appcent.repository

import androidx.lifecycle.LiveData
import com.example.newsapp_appcent.api.RetrofitInstance
import com.example.newsapp_appcent.db.ArticleDatabase
import com.example.newsapp_appcent.models.Article
import java.util.Locale.IsoCountryCode

class NewsRepository(val db:ArticleDatabase) {

    suspend fun getHeadlines(countryCode: String,pageNumber: Int) =
        RetrofitInstance.api.getHeadlines(countryCode,pageNumber)


//    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    suspend fun upsert(article: Article): Boolean {
        val existingArticle = db.getArticleDao().getArticleByUrl(article.url.toString())

        if (existingArticle == null) {
            db.getArticleDao().upsert(article)
            return true
        } else {
            return false
        }
    }
    
    fun getFavouriteNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteAllArticles(article)
}