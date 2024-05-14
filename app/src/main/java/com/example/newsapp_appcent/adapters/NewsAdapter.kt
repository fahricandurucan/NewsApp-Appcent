package com.example.newsapp_appcent.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp_appcent.R
import com.example.newsapp_appcent.models.Article
import com.example.newsapp_appcent.util.Constant

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    private var favoriteArticles: HashSet<String> = HashSet()

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    lateinit var articleImage: ImageView
    lateinit var articleSource: TextView
    lateinit var articleTitle: TextView
    lateinit var articleDescription: TextView
    lateinit var articleDateTime: TextView


    private val differCallback = object :DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
    }

    val differ = AsyncListDiffer(this,differCallback)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsAdapter.ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        )
    }

    override fun onBindViewHolder(holder: NewsAdapter.ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        articleImage = holder.itemView.findViewById(R.id.articleImage)
        articleSource = holder.itemView.findViewById(R.id.articleSource)
        articleTitle = holder.itemView.findViewById(R.id.articleTitle)
        articleDescription = holder.itemView.findViewById(R.id.articleDescription)
        articleDateTime = holder.itemView.findViewById(R.id.articleDateTime)

        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(articleImage)
            articleSource.text = article.source.name
            articleTitle.text = article.title
            articleDescription.text = article.description
            articleDateTime.text = Constant.convertDateTime(article.publishedAt)

            setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }
    fun setOnItemClickListener(listener:(Article) -> Unit){
        onItemClickListener = listener
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null


    

}