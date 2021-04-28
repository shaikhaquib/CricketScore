package com.aquib.cricketscore.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aquib.cricketscore.ApiResponse.Article
import com.aquib.cricketscore.ItemListener
import com.aquib.cricketscore.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import kotlinx.android.synthetic.main.item_news.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class NewsAdapter(
    private val context: Context,
    private val newsList: ArrayList<Article>?,
    private val listener: ItemListener
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
        val v = ViewHolder(view)
        view.setOnClickListener {
            listener.clicked(v.adapterPosition)
        }
        return v
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val articles = newsList!!.get(position)
        holder.title.text=articles.description
        val date = articles.publishedAt.getDateWithServerTimeStamp()
        holder.description.text = SimpleDateFormat("hh:mm a").format(date)
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(16))
        Glide.with(context)
                .load(articles.urlToImage)
                .thumbnail(0.5f) // .transition(GenericTransitionOptions.with(animationObject))
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).signature( ObjectKey(0)).skipMemoryCache(true).format(DecodeFormat.PREFER_RGB_565))
                .apply(requestOptions)
                .placeholder(R.drawable.noimage_found)
                .into(holder.thumb)
    }

    override fun getItemCount(): Int {
        return newsList!!.size
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.Title
        val description = itemView.Desc
        val thumb = itemView.Thumb
    }

    fun String.getDateWithServerTimeStamp(): Date? {
        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            Locale.getDefault()
        )
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")  // IMP !!!
        try {
            return dateFormat.parse(this)
        } catch (e: ParseException) {
            return null
        }
    }
}


