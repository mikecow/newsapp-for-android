package com.example.newsapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.newsapp.R
import com.example.newsapp.domain.news
import com.example.newsapp.session.Session

import com.example.newsapp.ui.newsmain.NewsMainActivity2


class PreferenceAdapter(val preferenceList: ArrayList<news>, val context: Context) :
    RecyclerView.Adapter<PreferenceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val preferenceImage: ImageView = view.findViewById(R.id.history_img)
        val preferenceTitle: TextView = view.findViewById(R.id.history_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)
        val viewHolder = ViewHolder(view)
        //点击preference_item
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val news = preferenceList[position] //获取新闻item的各种信息
            Session.url = news.url
            val intent = Intent(parent.context, NewsMainActivity2::class.java)
            this.context.startActivity(intent)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val preference = preferenceList[position]
        holder.preferenceTitle.text = preference.title
        Glide.with(context)
            .load(preference.pic)
            .placeholder(R.drawable.tools_placeholder)
            .error(R.drawable.tools_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.preferenceImage)
    }

    override fun getItemCount() = preferenceList.size

}
