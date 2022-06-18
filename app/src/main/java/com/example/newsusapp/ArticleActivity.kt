package com.example.newsusapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import com.example.newsusapp.databinding.ActivityArticleBinding
import com.example.newsusapp.db.ArticleDatabase
import com.example.newsusapp.db.NewsRepository
import com.example.newsusapp.model.Article
import com.example.newsusapp.ui.NewsViewModel
import com.example.newsusapp.ui.NewsViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class ArticleActivity : AppCompatActivity() {
    lateinit var viewModel: NewsViewModel
    private lateinit var binding: ActivityArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory)[NewsViewModel::class.java]

        val article = intent.getSerializableExtra("article") as Article
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url!!)
        }
        binding.btnAdd.setOnClickListener {
            try {
                viewModel.savedArticle(article)
            }catch (e : Exception) {
                Log.e("AAA",e.message.toString())
            }

            Snackbar.make(binding.root, "Saved", Snackbar.LENGTH_SHORT).show()
        }
    }
}