package com.example.newsusapp.db

import com.example.newsusapp.api.RetrofitInstance
import com.example.newsusapp.model.Article

class NewsRepository(
    private val db : ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode : String , pageNumber : Int) =
        RetrofitInstance.api.getNews(countryCode,pageNumber)

    suspend fun searchNews(searchQuery : String, pageNumber: Int) =
        RetrofitInstance.api.searchNews(searchQuery, pageNumber)

    suspend fun insert(article: Article) =
        db.getArticleDao().insert(article)

    suspend fun delete(article: Article) =
        db.getArticleDao().delete(article)

    fun getSavedNews() =
        db.getArticleDao().getAllArticles()
}