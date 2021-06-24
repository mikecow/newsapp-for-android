package com.example.newsapp.ui.newsmain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.newsapp.R
import com.example.newsapp.session.Session.url
import kotlinx.android.synthetic.main.activity_news_main2.*

class NewsMainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_main2)

        Hiswebview.settings.javaScriptEnabled=true
        Hiswebview.webViewClient = WebViewClient()
        Hiswebview.loadUrl(url)
    }
}