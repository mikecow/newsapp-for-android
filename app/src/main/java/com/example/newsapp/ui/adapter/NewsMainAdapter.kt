package com.example.newsapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.domain.news
import com.example.newsapp.domain.news_main
import com.example.newsapp.session.Session.news_context_list
import com.example.newsapp.session.Session.now_index
import kotlinx.android.synthetic.main.news_item.view.*
import kotlinx.android.synthetic.main.news_main_item.view.*

class NewsMainAdapter(val newsMainList: List<news>) : RecyclerView.Adapter<NewsMainAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val NewsMain: WebView = view.findViewById(R.id.webview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_main_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsMain = newsMainList[position]
        Log.d("position", position.toString())
        holder.NewsMain.settings.javaScriptEnabled=true
        holder.NewsMain.webViewClient = WebViewClient()
        holder.NewsMain.loadUrl(newsMain.url)
    }

    override fun getItemCount() = newsMainList.size
}