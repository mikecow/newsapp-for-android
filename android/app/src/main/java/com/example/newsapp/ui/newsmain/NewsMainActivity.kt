package com.example.newsapp.ui.newsmain

import android.content.Context
import android.graphics.drawable.DrawableContainer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.newsapp.R
import com.example.newsapp.domain.news
import com.example.newsapp.domain.news_main
import com.example.newsapp.net.AppService
import com.example.newsapp.session.Session
import com.example.newsapp.session.Session.favor_1
import com.example.newsapp.session.Session.favor_2
import com.example.newsapp.session.Session.favor_3
import com.example.newsapp.session.Session.favor_4
import com.example.newsapp.session.Session.favor_5
import com.example.newsapp.session.Session.newsIdNOW
import com.example.newsapp.session.Session.news_context_list
import com.example.newsapp.session.Session.now_index
import com.example.newsapp.session.Session.session_key
import com.example.newsapp.ui.adapter.NewsMainAdapter
import com.example.newsapp.ui.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.news_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import kotlin.concurrent.thread

class NewsMainActivity : AppCompatActivity() {

    private lateinit var newsMainViewModel: NewsMainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_main)

        val layoutManager= LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recycleView.layoutManager = layoutManager
        val adapter = NewsMainAdapter(get_news_list())
        recycleView.adapter = adapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycleView)

        if(Session.loginFlag) {
            thread {
                try {
                    saveThisNews()
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }

        ///这里需要传入是否收藏的标记
        var isFavor = 0
        newsMainFab.setOnClickListener {
            if (isFavor == 0){
                record_news()
            }
            else
                Toast.makeText(this, "取消成功", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveThisNews(){
        val retrofit = Retrofit.Builder()
            .baseUrl(Session.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)
        val news = news_context_list[now_index]
        appService.saveNews(session_key, news.title, news.time, news.src, news.category, news.pic, news.url).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                newsIdNOW = response.body()!!.string().toInt()
                sayhello()
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {sayhello2(); t.printStackTrace() }

        })
    }

    fun sayhello(){
        Toast.makeText(this, newsIdNOW.toString(), Toast.LENGTH_SHORT).show()
    }
    fun sayhello2(){
        Toast.makeText(this, "hello2!", Toast.LENGTH_SHORT).show()
    }

    fun record_news(){
        if(session_key == "")
        {
            Toast.makeText(this, "您尚未登陆，收藏功能无法使用", Toast.LENGTH_SHORT).show()
            return
        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.dorado.host/android/public/index.php/index/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)
        appService.saveCon(session_key, newsIdNOW).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("record", response.body().toString())
                record_success()
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {record_err(); t.printStackTrace() }

        })
    }

    fun record_success(){
        Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show()
    }

    fun record_err(){
        Toast.makeText(this, "收藏失败", Toast.LENGTH_SHORT).show()
    }

    fun get_news_list():List<news>{
        var flag = false
        val news_list  = ArrayList<news>()
        update()
        for(item in news_context_list){
            if(item == news_context_list[now_index]){
                flag = true
            }

            if(flag){
                news_list.add(item)
            }
        }

        return news_list
    }

    fun update(){
        val now = news_context_list[now_index]
        var index = 0
        when (now.category) {
            "news" -> {
                if(favor_2 != 1){
                    favor_2--
                    index++
                }
                if(favor_3 != 1){
                    favor_3--
                    index++
                }
                if(favor_4 != 1){
                    favor_4--
                    index++
                }
                if(favor_5 != 1){
                    favor_5--
                    index++
                }

                favor_1 += index
            }
            "finance" -> {
                if(favor_1 != 1){
                    favor_1--
                    index++
                }
                if(favor_3 != 1){
                    favor_3--
                    index++
                }
                if(favor_4 != 1){
                    favor_4--
                    index++
                }
                if(favor_5 != 1){
                    favor_5--
                    index++
                }

                favor_2 += index
            }
            "sports" -> {
                if(favor_1 != 1){
                    favor_1--
                    index++
                }
                if(favor_2 != 1){
                    favor_2--
                    index++
                }
                if(favor_4 != 1){
                    favor_4--
                    index++
                }
                if(favor_5 != 1){
                    favor_5--
                    index++
                }

                favor_3 += index
            }
            "ent" -> {
                if(favor_1 != 1){
                    favor_1--
                    index++
                }
                if(favor_2 != 1){
                    favor_2--
                    index++
                }
                if(favor_3 != 1){
                    favor_3--
                    index++
                }
                if(favor_5 != 1){
                    favor_5--
                    index++
                }

                favor_4 += index
            }
            "mil" -> {
                if(favor_1 != 1){
                    favor_1--
                    index++
                }
                if(favor_2 != 1){
                    favor_2--
                    index++
                }
                if(favor_3 != 1){
                    favor_3--
                    index++
                }
                if(favor_4 != 1){
                    favor_4--
                    index++
                }

                favor_5 += index
            }
        }
    }
}