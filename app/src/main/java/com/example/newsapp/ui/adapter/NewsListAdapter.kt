package com.example.newsapp.ui.adapter


import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.newsapp.R
import com.example.newsapp.domain.news
import com.example.newsapp.net.AppService
import com.example.newsapp.session.Session
import com.example.newsapp.session.Session.now_index
import com.example.newsapp.ui.login.LoginActivity
import com.example.newsapp.ui.newsmain.NewsMainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewsListAdapter(val newsList: List<news>, val context: Context) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>(){

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val newsImage:ImageView = view.findViewById(R.id.newsImage)
        val newsAuthor:TextView = view.findViewById(R.id.newsAuthor)
        val newsTitle:TextView = view.findViewById(R.id.newsTitle)
        val newsPublishedAt:TextView = view.findViewById(R.id.newsPublishedAt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        val viewHolder = ViewHolder(view)

        //点击news_item
        viewHolder.itemView.setOnClickListener {
            now_index = viewHolder.adapterPosition
            move_to_news_main()
        }
        return viewHolder
    }

    fun move_to_news_main(){
        val intent = Intent(context, NewsMainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
        this.context.startActivity(intent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newsList[position]
        holder.newsTitle.text = news.title
        holder.newsAuthor.text = news.src
        holder.newsPublishedAt.text = news.time
        Glide.with(context)
            .load(news.pic)
            .placeholder(R.drawable.tools_placeholder)
            .error(R.drawable.tools_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.newsImage)
    }

    override fun getItemCount() = newsList.size


}
