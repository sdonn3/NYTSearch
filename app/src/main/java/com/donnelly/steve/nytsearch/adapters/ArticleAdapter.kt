package com.donnelly.steve.nytsearch.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.donnelly.steve.nytsearch.R
import com.donnelly.steve.nytsearch.WebviewActivity
import com.donnelly.steve.nytsearch.glide.GlideApp
import com.donnelly.steve.nytsearch.services.models.SearchDocument
import com.donnelly.steve.nytsearch.services.models.SearchMultimedia
import com.jakewharton.rxbinding2.view.clicks
import kotlinx.android.synthetic.main.item_article.view.*
import java.util.concurrent.TimeUnit

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    var articles = ArrayList<SearchDocument>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
            ArticleViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
            )

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    inner class ArticleViewHolder(articleView: View) : RecyclerView.ViewHolder(articleView) {
        fun bind(article: SearchDocument) {
            itemView.apply {
                val pictureUrl = article.multimedia?.let {
                    getThumbNail(it)
                }

                pictureUrl?.let {
                    GlideApp
                            .with(context)
                            .load(IMAGE_PREFIX + pictureUrl)
                            .into(ivArticle)
                    ivArticle.visibility = View.VISIBLE
                } ?: run {
                    ivArticle.visibility = View.GONE
                }

                tvTitle.text = article.headline?.main

                if (article.headline?.main.isNullOrEmpty()) {
                    tvTitle.visibility = View.GONE
                }

                tvSummary.text = article.snippet

                if (article.snippet.isNullOrEmpty()) {
                   tvSummary.visibility = View.GONE
                }

                clicks()
                        .throttleFirst(500L, TimeUnit.MILLISECONDS)
                        .subscribe{
                            val intent = Intent(context, WebviewActivity::class.java).apply{
                                putExtra(WebviewActivity.URL_STRING, article.webUrl)
                            }
                            context.startActivity(intent)
                        }

            }
        }
    }

    private fun getThumbNail(list: List<SearchMultimedia>): String? {
        list.forEach { multimedia ->
            if (multimedia.url.isNullOrEmpty().not())
                return multimedia.url
        }
        return null
    }

    companion object {
        private const val IMAGE_PREFIX = "http://www.nytimes.com/"
        private const val THUMBNAIL_TYPE = "thumbnail"
    }
}
