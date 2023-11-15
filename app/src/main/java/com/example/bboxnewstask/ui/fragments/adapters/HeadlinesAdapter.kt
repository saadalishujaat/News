package com.example.bboxnewstask.ui.fragments.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bboxnewstask.R
import com.example.bboxnewstask.models.Article
import com.example.bboxnewstask.utils.ExtensionFunctions.toTimeAgo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HeadlinesAdapter(private val onClick: (Article) -> Unit) :
    ListAdapter<Article, HeadlinesAdapter.RatesViewHolder>(HeadlinesDiffCallback) {


    class RatesViewHolder(itemView: View, val onClick: (Article) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val image: ImageFilterView = itemView.findViewById(R.id.image)
        private val source: TextView = itemView.findViewById(R.id.source_name)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val description: TextView = itemView.findViewById(R.id.description)
        private val publishedAt: TextView = itemView.findViewById(R.id.published_at)
        private var article: Article? = null


        init {
            itemView.setOnClickListener {
                Log.d("clicktest", "click testing : ")

                article?.let {
                    Log.d("clicktest", "click testing $it: ")
                    onClick(it)
                }
            }
        }

        /* Bind exchange rate data. */
        fun bind(item: Article?) {

            article = item
            item?.let {

                // show image
                CoroutineScope(Dispatchers.IO).launch {
                    Glide.with(itemView.context)
                        .load(it.urlToImage)
                        .also {
                            withContext(Dispatchers.Main) {
                                it.into(image)
                            }
                        }
                }

                source.text = it.source.name
                title.text = it.title
                description.text= it.description
                publishedAt.text = it.publishedAt.toTimeAgo()

            }

        }
    }

    /* Creates and inflates view and return FlowerViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.headline_rates_item, parent, false)
        return RatesViewHolder(view, onClick)
    }

    /* Gets current flower and uses it to bind view. */
    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        val article: Article? = getItem(position)
        holder.bind(article)
    }

}

object HeadlinesDiffCallback : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.publishedAt == newItem.publishedAt
    }
}