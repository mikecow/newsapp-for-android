package com.example.newsapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.domain.news
import com.example.newsapp.net.AppService
import com.example.newsapp.session.Session
import com.example.newsapp.session.Session.loginFlag
import com.example.newsapp.session.Session.news_context_list
import com.example.newsapp.session.Session.session_key
import com.example.newsapp.session.Session.startNum
import com.example.newsapp.ui.adapter.NewsListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ///给newsList设置监听
        news_List.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //获得recyclerView的线性布局管理器
                val manager = recyclerView.layoutManager as LinearLayoutManager?
                //获取到第一个item的显示的下标  不等于0表示第一个item处于不可见状态 说明列表没有滑动到顶部 显示回到顶部按钮
                //                // 当不滚动时
                val fab = activity?.findViewById<FloatingActionButton>(R.id.fab)
                if (newState == 0) {
                        fab?.show()
                } else { //拖动中
                    fab?.hide()
                }
            }
        }
        )
    }
    ///悬浮按钮的出现隐藏动画监听
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        materialheader.setColorSchemeColors(0xfffb5e5e.toInt())
        val news_List = requireActivity().findViewById<RecyclerView>(R.id.news_List)
        news_List.layoutManager = LinearLayoutManager(context)
        news_context_list.clear()
        initnews_list()

        refreshLayout.setOnRefreshListener(object : OnRefreshListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                initnews_list()
                refreshLayout.finishRefresh(1000)
            }
        })

        refreshLayout.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                bottom_add()
                refreshLayout.finishLoadMore(1000)
            }
        })
    }



    fun initRecyclerView(){
        val adapter = NewsListAdapter(news_context_list, requireActivity().applicationContext)
        news_List.adapter = adapter
    }

    fun initnews_list(){
        Toast.makeText(requireContext(), "正在请求数据，请等待", Toast.LENGTH_LONG).show()
        val retrofit = Retrofit.Builder()
            .baseUrl(Session.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)
        if(loginFlag == false) {
            appService.get_news_list("头条", startNum, 10).enqueue(object : Callback<List<news>> {
                override fun onFailure(call: Call<List<news>>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<List<news>>, response: Response<List<news>>) {
                    val list = response.body()
                    if (list != null) {
                        for (item in list) {
                            val news_item = news(
                                item.title,
                                item.time,
                                item.src,
                                item.category,
                                item.pic,
                                item.content,
                                item.url
                            )
                            news_context_list.add(0, news_item)
                        }
                    }
                    initRecyclerView()
                    startNum += 10
                }
            })
        }else{
            appService.get_personal_news(session_key, startNum).enqueue(object : Callback<List<news>>{
                override fun onResponse(call: Call<List<news>>, response: Response<List<news>>) {
                    val list = response.body()
                    if (list != null) {
                        for (item in list) {
                            val news_item = news(
                                item.title,
                                item.time,
                                item.src,
                                item.category,
                                item.pic,
                                item.content,
                                item.url
                            )
                            news_context_list.add(0, news_item)
                        }
                    }
                    initRecyclerView()
                    startNum += 10
                }

                override fun onFailure(call: Call<List<news>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }

    fun bottom_add(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.dorado.host/android/public/index.php/index/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)
        if(loginFlag == false) {
            appService.get_news_list("头条", startNum, 10).enqueue(object : Callback<List<news>> {
                override fun onFailure(call: Call<List<news>>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<List<news>>, response: Response<List<news>>) {
                    val list = response.body()
                    if (list != null) {
                        for (item in list) {
                            val news_item = news(
                                item.title,
                                item.time,
                                item.src,
                                item.category,
                                item.pic,
                                item.content,
                                item.url
                            )
                            news_context_list.add(news_item)
                        }
                    }
                    initRecyclerView()
                    Log.d("reloading", news_context_list.toString())
                    startNum += 10
                }
            })
        }else{
            appService.get_personal_news(session_key, startNum).enqueue(object : Callback<List<news>>{
                override fun onResponse(call: Call<List<news>>, response: Response<List<news>>) {
                    val list = response.body()
                    if (list != null) {
                        for (item in list) {
                            val news_item = news(
                                item.title,
                                item.time,
                                item.src,
                                item.category,
                                item.pic,
                                item.content,
                                item.url
                            )
                            news_context_list.add(news_item)
                        }
                    }
                    initRecyclerView()
                    startNum += 10
                }

                override fun onFailure(call: Call<List<news>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }

}




