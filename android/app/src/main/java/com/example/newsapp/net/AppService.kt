package com.example.newsapp.net

import android.text.Editable
import com.example.newsapp.domain.news
import com.example.newsapp.domain.user
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AppService {
    @GET("index/index/index.html")
    fun test(): Call<ResponseBody>


    @POST("news/get_news_list")
    fun get_news_list(
        @Query(value="channel") channel:String,
        @Query(value="start") start:Int,
        @Query(value = "num") num:Int
    ): Call<List<news>>

    @POST("user/login")
    fun login(
        @Query(value = "username") username: String,
        @Query(value = "password") password:String
    ):Call<ResponseBody>

    @POST("news/get_personal_news")
    fun get_personal_news(
        @Query(value="session_key") session_key:String,
        @Query(value="start") start:Int,
    ):Call<List<news>>

    @POST("user/get")
    fun get(
        @Query(value = "session_key") session_key: String
    ):Call<user>

    @POST("user/create_user")
    fun register(
        @Query(value = "username") username: String,
        @Query(value = "password") password: String
    ):Call<String>

    @POST("news/saveNews")
    fun saveNews(
        @Query(value = "session_key") session_key: String,
        @Query(value = "title") title:String,
        @Query(value = "time") time:String,
        @Query(value = "src") src:String,
        @Query(value = "category") category:String,
        @Query(value = "pic") pic:String,
        @Query(value = "url") url:String
    ):Call<ResponseBody>

    @POST("news/save_con")
    fun saveCon(
        @Query(value = "session_key") session_key: String,
        @Query(value = "news_id") news_id:Int
    ):Call<ResponseBody>

    @POST("news/select_person_news")
    fun select_person_news(
        @Query(value = "session_key") session_key: String
    ):Call<List<news>>

    @POST("news/select_person_con")
    fun select_person_con(
        @Query(value = "session_key") session_key: String
    ):Call<List<news>>

    @POST("user/edit_favor")
    fun edit_favor(
        @Query(value = "session_key") session_key: String,
        @Query(value = "favor_1") favor_1:Int,
        @Query(value = "favor_2") favor_2:Int,
        @Query(value = "favor_3") favor_3:Int,
        @Query(value = "favor_4") favor_4:Int,
        @Query(value = "favor_5") favor_5:Int
        ):Call<ResponseBody>
}