package com.example.newsapp_appcent.repository

import com.example.newsapp_appcent.api.RetrofitInstance
import com.example.newsapp_appcent.db.ArticleDatabase
import com.example.newsapp_appcent.models.Article

class NewsRepository(val db:ArticleDatabase) {

    suspend fun getNews(query:String,page:Int) =
        RetrofitInstance.api.getNews(query,page)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    suspend fun getFavouriteNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteAllArticles(article)
}