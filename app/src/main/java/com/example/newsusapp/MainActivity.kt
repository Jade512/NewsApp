package com.example.newsusapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsusapp.databinding.ActivityMainBinding
import com.example.newsusapp.db.ArticleDatabase
import com.example.newsusapp.db.NewsRepository
import com.example.newsusapp.ui.NewsViewModel
import com.example.newsusapp.ui.NewsViewModelFactory
import com.example.newsusapp.ui.fragments.BreakingNewsFragment
import com.example.newsusapp.ui.fragments.SavedNewsFragment
import com.example.newsusapp.ui.fragments.SearchNewsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var viewModel: NewsViewModel
    private val breakingNewsFragment = BreakingNewsFragment()
    private val savedNewsFragment = SavedNewsFragment()
    private val searchNewsFragment = SearchNewsFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val newsViewModelProviderFactory = NewsViewModelFactory(application,newsRepository)
        viewModel = ViewModelProvider(this,newsViewModelProviderFactory)[NewsViewModel::class.java]
        Log.e("AAA",viewModel.breakingNews.toString())

        replaceFragment(breakingNewsFragment)
        binding.bottomBar.setOnItemSelectedListener {
            when(it){
                R.id.news -> {
                    replaceFragment(breakingNewsFragment)
                }
                R.id.favorite -> {
                    replaceFragment(savedNewsFragment)
                }
                R.id.search -> {
                    replaceFragment(searchNewsFragment)
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}