package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*


class NewsAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {
      private val newsList: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_news, parent, false)
        val newsViewHolder = NewsViewHolder(view)
        view.setOnClickListener(){
            @Suppress("DEPRECATION")
            listener.onItemClicked(newsList[newsViewHolder.adapterPosition])
        }
        return newsViewHolder
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val current: News = newsList[position]
        holder.title.text = current.title
        holder.author.text = current.author
        holder.date.text = dateFormatter(current.date)
        Glide.with(holder.itemView.context).load(current.imageUrl).into(holder.image)
    }

    fun updateNews(list: ArrayList<News>){
        newsList.clear()
        newsList.addAll(list)

        notifyDataSetChanged()

    }

    fun dateFormatter(date: String): String{
        return date.substring(0,10)
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)
    val date: TextView = itemView.findViewById(R.id.date)
}

interface NewsItemClicked{
    fun onItemClicked(item: News)
}