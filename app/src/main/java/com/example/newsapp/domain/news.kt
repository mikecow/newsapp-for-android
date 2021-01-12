package com.example.newsapp.domain

import java.net.URL
import java.util.*


data class news(val title:String, val time:String, val src:String, val category:String, val pic:String, val content:String, val url: String){
    override fun toString(): String {
        return "new(title=$title, time=$time, src='$src', category='$category', pic='$pic', content='$content')"
    }

}
