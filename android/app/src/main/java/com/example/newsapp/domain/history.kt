package com.example.newsapp.domain

data class history(val title:String, val time:String, val src:String, val category:String, val pic:String, val content:String, val url: String) {
    override fun toString(): String {
        return "history(title=$title, time=$time, src='$src', category='$category', pic='$pic', content='$content')"
    }
}