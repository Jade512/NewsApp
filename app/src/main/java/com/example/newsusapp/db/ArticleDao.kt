package com.example.newsusapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsusapp.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(article: Article)

    @Delete
     fun delete(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles() : LiveData<List<Article>>
}