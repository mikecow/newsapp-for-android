package com.example.newsapp.net.news

import android.util.Log
import com.example.newsapp.domain.news
import com.example.newsapp.net.AppService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask

class NewsInternet {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.dorado.host/android/public/index.php/index/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val appService = retrofit.create(AppService::class.java)


    fun get_news_list(channel:String, start:Int, num:Int): List<news>? {
        return appService.get_news_list(channel, start, num).execute().body()
    }

    fun get_person_list(session_key:String, start: Int): List<news>?{
        return appService.get_personal_news(session_key, start).execute().body()
    }

    fun save_news(session_key: String, news: news){
        appService.saveNews(session_key, news.title, news.time, news.src, news.category, news.pic, news.url).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                println(response.body()?.string())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun save_con(session_key: String, news_id:Int){
        appService.saveCon(session_key, news_id).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                println(response.body()?.string())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun get_person_news(session_key: String){
        appService.select_person_news(session_key).enqueue(object : Callback<List<news>> {
            override fun onResponse(call: Call<List<news>>, response: Response<List<news>>) {
                println(response.body())
            }

            override fun onFailure(call: Call<List<news>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun get_person_con(session_key: String){
        appService.select_person_con(session_key).enqueue(object : Callback<List<news>> {
            override fun onResponse(call: Call<List<news>>, response: Response<List<news>>) {
                println(response.body())
            }

            override fun onFailure(call: Call<List<news>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    companion object {
        /** 我是main入口函数 **/
        @JvmStatic
        fun main(args: Array<String>) {
            val newsInternet = NewsInternet()
            val newslist = newsInternet.get_person_con("14bfa6bb14875e45bba028a21ed38046")
        }
    }
}