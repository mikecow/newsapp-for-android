package com.example.newsapp.session

import com.example.newsapp.domain.news
import com.example.newsapp.domain.user

object Session{
    var loginFlag = false
    var session_key:String = ""
    var startNum = 0
    var news_context_list:ArrayList<news> = ArrayList()
    var now_index = 0

    var favor_1:Int = 0
    var favor_2:Int = 0
    var favor_3:Int = 0
    var favor_4:Int = 0
    var favor_5:Int = 0

    var username:String = ""

    var newsIdNOW:Int = 0

    var url:String = ""

}