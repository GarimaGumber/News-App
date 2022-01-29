package com.example.newsapp

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter: NewsAdapter

    var url: String = "https://newsapi.org/v2/top-headlines?country=in&apiKey=${BuildConfig.API_KEY}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recView.layoutManager = LinearLayoutManager(this)
        mAdapter = NewsAdapter(this)
        fetchData()
        recView.adapter = mAdapter
    }

    private fun fetchData(){
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray: ArrayList<News> = ArrayList()
                for (i in 0 until newsJsonArray.length()) {
                    val jsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        jsonObject.getString("title"),
                        jsonObject.getString("author"),
                        jsonObject.getString("url"),
                        jsonObject.getString("publishedAt"),
                        jsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {
                Log.d("NewsApp", "Failed Request")
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val colorInt: Int = Color.parseColor("#D50000")

        val defaultColors = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(colorInt)
            .build()
        builder.setDefaultColorSchemeParams(defaultColors)
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.category_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.general -> {
                url =
                    "https://newsapi.org/v2/top-headlines?country=in&category=general&apiKey=${BuildConfig.API_KEY}"
                fetchData()
                news_title.text = "General"
                true
            }
            R.id.business -> {
                url =
                    "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=${BuildConfig.API_KEY}"
                fetchData()
                news_title.text = "Business"
                true
            }
            R.id.entertainment -> {
                url =
                    "https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=${BuildConfig.API_KEY}"
                fetchData()
                news_title.text = "Entertainment"
                true
            }
            R.id.health -> {
                url =
                    "https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=${BuildConfig.API_KEY}"
                fetchData()
                news_title.text = "Health"
                true
            }
            R.id.science -> {
                url =
                    "https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=${BuildConfig.API_KEY}"
                fetchData()
                news_title.text = "Science"
                true
            }
            R.id.sports -> {
                url =
                    "https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=${BuildConfig.API_KEY}"
                fetchData()
                news_title.text = "Sports"
                true
            }
            R.id.technology -> {
                url =
                    "https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=${BuildConfig.API_KEY}"
                fetchData()
                news_title.text = "Technology"
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


