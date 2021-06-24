package com.example.newsapp.net.user

import com.example.newsapp.domain.user
import com.example.newsapp.net.AppService
import com.example.newsapp.session.Session.loginFlag
import com.example.newsapp.session.Session.session_key
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserInternet {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.dorado.host/android/public/index.php/index/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val appService = retrofit.create(AppService::class.java)

    public fun login(username: String, password: String){
        appService.login(username, password).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val responser = response.body()?.string()
                if (responser == "false") {
                    loginFlag = false
                }else{
                    loginFlag = true
                    session_key = responser!!
                }
                println(loginFlag)
                println(session_key)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }

        })

    }

    public fun get(sessionn_key:String){
        appService.get(sessionn_key).enqueue(object : Callback<user> {
            override fun onResponse(call: Call<user>, response: Response<user>) {
                val response = response.body()

                println(response)
            }

            override fun onFailure(call: Call<user>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    public fun register(username: String, password: String){
        appService.register(username, password).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val response = response.body()
                println(response)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    public fun edit_favor(sessionn_key: String, favor_1:Int, favor_2:Int, favor_3:Int, favor_4:Int, favor_5:Int){
        appService.edit_favor(sessionn_key, favor_1, favor_2, favor_3, favor_4, favor_5).enqueue(object :Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                println(response.body()?.string())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    companion object {
        /** 我是main入口函数 **/
        @JvmStatic
        fun main(args: Array<String>) {
            val userInternet = UserInternet()
            userInternet.edit_favor("68d30a9594728bc39aa24be94b319d21", 1, 1, 4, 2, 2)
        }
    }
}