package com.example.newsapp_appcent.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp_appcent.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article):Long

    @Query("SELECT * FROM articles")
     fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteAllArticles(article: Article)
}