package com.example.newsapp_appcent.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.newsapp_appcent.R
import com.example.newsapp_appcent.db.ArticleDatabase
import com.example.newsapp_appcent.models.Article
import com.example.newsapp_appcent.repository.NewsRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class NewsDetailActivity : AppCompatActivity() {
    lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        // fab butonun çalışması için yazıldı
        val articleDatabase = ArticleDatabase(this)
        val newsRepository = NewsRepository(articleDatabase)
        val viewModelFactory = NewsViewModelFactory(application, newsRepository) // NewsViewModelFactory'nin oluşturulması
        val newsViewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java) // ViewModelProvider aracılığıyla NewsViewModel'ın oluşturulması


        val imageViewArticle = findViewById<ImageView>(R.id.imageViewArticle)
        val textViewTitle = findViewById<TextView>(R.id.textViewTitle)
        val textViewAuthor = findViewById<TextView>(R.id.textViewAuthor)
        val textViewDate = findViewById<TextView>(R.id.textViewDate)
        val textViewDescription = findViewById<TextView>(R.id.textViewDescription)


        val article = intent.getSerializableExtra("article") as Article
        // Gerekli görünümleri haber detaylarıyla güncelle
        Glide.with(this).load(article.urlToImage).into(imageViewArticle)
        textViewTitle.text = article.title
        textViewAuthor.text = article.author
        textViewDate.text = article.publishedAt
        textViewDescription.text = article.description

        val fabButton = findViewById<FloatingActionButton>(R.id.fab)

        fabButton.setOnClickListener {
            newsViewModel.addToFavourites(article)
            val rootView = findViewById<View>(android.R.id.content)
            Snackbar.make(rootView,"Added to favourites", Snackbar.LENGTH_LONG).show()
        }

        // NewsSource butonuna tıklandığında NewsWebActivity'e geçiş yap
        val buttonSource = findViewById<Button>(R.id.buttonSource)
        buttonSource.setOnClickListener {
            val intent = Intent(this, NewsWebActivity::class.java)
            intent.putExtra("url", article.url) // URL'yi intent ile aktar
            startActivity(intent)
        }
    }
}