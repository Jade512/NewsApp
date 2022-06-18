package com.example.newsusapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsusapp.model.Article

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao() : ArticleDao

    companion object{
        @Volatile
        private var instance : ArticleDatabase ?= null
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: Room.databaseBuilder(
                context,
                ArticleDatabase::class.java,
                "news.db"
            ).fallbackToDestructiveMigration().build().also {
                instance = it
            }
        }
    }
}