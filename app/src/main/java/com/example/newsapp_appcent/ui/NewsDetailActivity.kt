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
import com.example.newsapp_appcent.util.Constant
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class NewsDetailActivity : AppCompatActivity() {
    lateinit var newsViewModel: NewsViewModel
    private lateinit var backButton: ImageView
    private lateinit var shareButton: ImageView
    private lateinit var favButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        // fav butonun çalışması için yazıldı
        val articleDatabase = ArticleDatabase(this)
        val newsRepository = NewsRepository(articleDatabase)
        val viewModelFactory = NewsViewModelFactory(application, newsRepository)
        newsViewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)


        val imageViewArticle = findViewById<ImageView>(R.id.imageViewArticle)
        val textViewTitle = findViewById<TextView>(R.id.textViewTitle)
        val textViewSource = findViewById<TextView>(R.id.textViewSource)
        val textViewDate = findViewById<TextView>(R.id.textViewDate)
        val textViewDescription = findViewById<TextView>(R.id.textViewDescription)


        val article = intent.getSerializableExtra("article") as Article
        Glide.with(this).load(article.urlToImage).into(imageViewArticle)
        textViewTitle.text = article.title
        textViewSource.text = article.source.name
        textViewDate.text = Constant.convertDate(article.publishedAt)
        textViewDescription.text = article.description


        val buttonSource = findViewById<Button>(R.id.buttonSource)
        buttonSource.setOnClickListener {
            val intent = Intent(this, NewsWebActivity::class.java)
            intent.putExtra("url", article.url)
            startActivity(intent)
        }


        backButton = findViewById(R.id.backButton)
        shareButton = findViewById(R.id.shareButton)
        favButton = findViewById(R.id.favButton)

        backButton.setOnClickListener {
            finish()
        }
        shareButton.setOnClickListener {
            shareNews()
        }
        favButton.setOnClickListener {
            newsViewModel.addToFavourites(article)
            val rootView = findViewById<View>(android.R.id.content)
            Snackbar.make(rootView,"Added to favourites", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun shareNews() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Haber Başlığı")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Haber Metni")

        startActivity(Intent.createChooser(shareIntent, "Haberi Paylaş"))
    }
}