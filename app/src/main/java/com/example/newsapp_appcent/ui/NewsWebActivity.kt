package com.example.newsapp_appcent.ui

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp_appcent.R

class NewsWebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_web)

        val webView = findViewById<WebView>(R.id.webView)

        val url = intent.getStringExtra("url")

        webView.apply {
            webViewClient = WebViewClient()
            url?.let {
                loadUrl(it)
            }
        }
    }
}
