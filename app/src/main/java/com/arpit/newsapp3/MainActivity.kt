package com.arpit.newsapp3

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var newsadapter : MyAdapter
    private var articlesList = mutableListOf<Article>()
    var pagenum = 1
    var totalResultsNews = -1
    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }

        }
//        val songsObject = mutableListOf<Song>()
//        songsObject.add(Song("aaaa", "1111"))
//        songsObject.add(Song("bbbb", "2222"))
//        songsObject.add(Song("cccc", "3333"))
//        songsObject.add(Song("dddd", "4444"))
//        songsObject.add(Song("eeee", "5555"))
//        songsObject.add(Song("ffff", "6666"))
//        songsObject.add(Song("gggg", "7777"))
//        songsObject.add(Song("hhhh", "8888"))
//        songsObject.add(Song("iiii", "9999"))
//        songList.adapter = MyAdapter(songsObject)
//        songList.layoutManager = LinearLayoutManager(this)
        newsadapter = MyAdapter(this@MainActivity, articlesList)
        rvNewsList.adapter = newsadapter
//        rvNewsList.layoutManager = LinearLayoutManager(this@MainActivity)

        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)
        rvNewsList.layoutManager = layoutManager

        layoutManager.setItemChangedListener(object : StackLayoutManager.ItemChangedListener {
            override fun onItemChanged(position: Int) {
                rvNewsList.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
                Log.d("MainActivity", "First Visible Item ${layoutManager.getFirstVisibleItemPosition()}")
                if (totalResultsNews > layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >= layoutManager.itemCount - 5) {
                    pagenum++
                    getNews()
                }
                if (position %5 ==0){
                    if (mInterstitialAd.isLoaded){
                        mInterstitialAd.show()
                    }
                }
            }
        })
        getNews()
    }

    private fun getNews() {
        Log.d("mainActivity" , "Requestsent for $pagenum")
        val news = newsService.apiService.getHeadlines("in" ,pagenum)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news!= null) {
                    // Log.d("Loading" , news.toString() )
                        totalResultsNews = news.totalResults
                    articlesList.addAll(news.articles)
                    newsadapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("ErrorLoading" , "Can't fetch news"  , t)
            }

        })
    }
}
// @GET("top-headlines?country=in&apiKey=87549592e55f41b986f248237a219d90#")
//const val base_url = "https://newsapi.org/v2/"