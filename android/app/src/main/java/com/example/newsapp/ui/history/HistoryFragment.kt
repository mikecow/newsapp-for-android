package com.example.newsapp.ui.history

import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.domain.news
import com.example.newsapp.net.AppService
import com.example.newsapp.session.Session
import com.example.newsapp.session.Session.session_key
import com.example.newsapp.ui.adapter.HistoryAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    var his_list = ArrayList<news>();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyViewModel =
            ViewModelProviders.of(this).get(HistoryViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.hide()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        his_list.clear()
        val historyList = requireActivity().findViewById<RecyclerView>(R.id.historyList)
        historyList.layoutManager = LinearLayoutManager(context)
        init_data()
    }

    fun init_data(){
        val retrofit = Retrofit.Builder()
            .baseUrl(Session.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)
        appService.select_person_news(session_key).enqueue(object : Callback<List<news>> {
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
                            "null",
                            item.url
                        )
                        his_list.add(news_item)
                    }
                }
                init_rectcle_view()
            }

            override fun onFailure(call: Call<List<news>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun init_rectcle_view(){
        if(his_list.size > 0)
        {
            history_empty_view.isVisible = false
        }
        val adapter = HistoryAdapter(his_list, requireContext())
        historyList.adapter = adapter
    }
}