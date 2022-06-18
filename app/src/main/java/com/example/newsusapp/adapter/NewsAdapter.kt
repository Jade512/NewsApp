package com.example.newsusapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsusapp.R
import com.example.newsusapp.model.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val ivArticle : ImageView = itemView.findViewById(R.id.ivArticleImage)
        val source : TextView = itemView.findViewById(R.id.tvSource)
        val title : TextView = itemView.findViewById(R.id.tvTitle)
        val description : TextView = itemView.findViewById(R.id.tvDescription)
        val publish : TextView = itemView.findViewById(R.id.tvPublishedAt)
    }

    private val differentCallBack = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }


    val differ = AsyncListDiffer(this,differentCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        Glide.with(holder.itemView).load(article.urlToImage).into(holder.ivArticle)
        holder.source.text = article.source.name
        holder.title.text = article.title
        holder.description.text = article.description
        holder.publish.text = article.publishedAt
        holder.itemView.setOnClickListener{
            onItemClickListener?.let {
                it(article)
            }
        }
    }

    private var onItemClickListener : ((Article) -> Unit) ?= null

    fun  setOnItemClickListener(listener: (Article) -> Unit){
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}