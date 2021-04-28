package com.aquib.cricketscore.Fragment.Tabs

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import com.aquib.cricketscore.Adapter.LiveScoreAdapter
import com.aquib.cricketscore.Adapter.NewsAdapter
import com.aquib.cricketscore.ApiResponse.Article
import com.aquib.cricketscore.ApiResponse.NewsData
import com.aquib.cricketscore.ItemListener
import com.aquib.cricketscore.Network.RetrofitClient
import com.aquib.cricketscore.R
import kotlinx.android.synthetic.main.fragment_all.*
import kotlinx.android.synthetic.main.fragment_all.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Cricket : Fragment() ,ItemListener{
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cricket, container, false)
    }

    var newsList = ArrayList<Article>()
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsAdapter = NewsAdapter(requireContext(), newsList, this)
        rvScore.adapter = LiveScoreAdapter(requireContext())
        view.rvNews.adapter = newsAdapter
        getNews()

    }

    override fun onResume() {
        super.onResume()
    }

    fun startLoading(){
        shimmer?.visibility = View.VISIBLE
        rvScore?.visibility = View.GONE
        rvNews?.visibility = View.GONE
    }
    fun stopLoading(){
        shimmer?.visibility = View.GONE
        rvScore?.visibility = View.VISIBLE
        rvNews?.visibility = View.VISIBLE
    }


    private fun getNews() {
        startLoading()
        GlobalScope.launch {
            RetrofitClient.instance.getNews(
                    "cricket",
                    "c8d1ab1fd96c46e19ded7d8d1240bf68"
            )?.enqueue(
                    object :
                            Callback,
                            retrofit2.Callback<NewsData?> {
                        override fun onResponse(call: Call<NewsData?>, response: Response<NewsData?>) {
                            stopLoading()
                            if (response.isSuccessful) {
                                newsList.clear()
                                newsAdapter.notifyDataSetChanged()

                                for (article in response.body()!!.articles){
                                    newsList.add(article)
                                    newsAdapter.notifyItemInserted(newsList.size+1)
                                }

                            }else{
                                networkError()
                            }
                        }

                        override fun onFailure(call: Call<NewsData?>, t: Throwable) {
                            stopLoading()
                            networkError()
                        }
                    })
        }
    }
    private fun networkError() {
        rvNews.visibility =View.GONE
        error.visibility =View.VISIBLE
        rvScore.visibility =View.GONE
    }

    override fun clicked(position: Int) {
        val url = newsList.get(position).url
        val builder = CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url));
    }
}