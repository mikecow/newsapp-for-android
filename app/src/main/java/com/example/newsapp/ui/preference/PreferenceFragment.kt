package com.example.newsapp.ui.preference

import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.domain.news
import com.example.newsapp.net.AppService
import com.example.newsapp.session.Session
import com.example.newsapp.ui.adapter.PreferenceAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_preference.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PreferenceFragment : Fragment() {

    private lateinit var preferenceViewModel: PreferenceViewModel
    private val per_list = ArrayList<news>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceViewModel =
            ViewModelProviders.of(this).get(PreferenceViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_preference, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.hide()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val resources: Resources = this.resources
        val dm: DisplayMetrics = resources.displayMetrics
        preferenceList.layoutManager = LinearLayoutManager(context)
        init_data()
    }

    fun init_data(){
        Log.d("historyMsg", "ub3!")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.dorado.host/android/public/index.php/index/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)
        appService.select_person_con(Session.session_key).enqueue(object : Callback<List<news>> {
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
                        per_list.add(news_item)
                    }
                }
                Log.d("history_list: ", per_list.toString())
                init_rectcle_view()
            }

            override fun onFailure(call: Call<List<news>>, t: Throwable) {
                Log.d("historyMsg", "ub4!")
                t.printStackTrace()
            }

        })
    }

    fun init_rectcle_view(){
        val adapter = PreferenceAdapter(per_list, requireContext())
        preferenceList.adapter = adapter
    }
}