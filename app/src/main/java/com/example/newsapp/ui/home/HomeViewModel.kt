package com.example.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.domain.news
import com.example.newsapp.net.news.NewsInternet
import com.example.newsapp.session.Session.startNum
import java.lang.Thread.sleep
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread
import kotlin.concurrent.timerTask

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    val newsInternet = NewsInternet()

    val newslist = ArrayList<news>()


    fun getnews(): ArrayList<news> {
        val newslist = ArrayList<news>()
        newsInternet.get_news_list("新闻", 0, 10)
        return newslist
    }



}