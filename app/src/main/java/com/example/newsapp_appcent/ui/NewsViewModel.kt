package com.example.newsapp_appcent.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.newsapp_appcent.models.Article
import com.example.newsapp_appcent.models.NewsResponse
import com.example.newsapp_appcent.repository.NewsRepository
import com.example.newsapp_appcent.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(app:Application, val newsRepository: NewsRepository) : AndroidViewModel(app) {
    val favouriteArticles: LiveData<List<Article>> = newsRepository.getFavouriteNews()

    val isEmptyFavourites: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(favouriteArticles) { articles ->
            value = articles.isEmpty()
        }
    }

    val headlines: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var headlinesPage = 1
    var headlinesResponse : NewsResponse? = null

    init {
        getHeadlines("besiktas")
    }
    fun getHeadlines(countryCode: String) = viewModelScope.launch {
        headlinesInternet(countryCode)
    }

    private fun handleHeadlinesNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                headlinesPage++
                if(headlinesResponse == null){
                    headlinesResponse = resultResponse
                }
                else{
                    val oldArticles = headlinesResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(headlinesResponse  ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun addToFavourites(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }


     fun getFavouriteNews() = newsRepository.getFavouriteNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    fun internetConnection(context:Context):Boolean{
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            return getNetworkCapabilities(activeNetwork)?.run {
                when{
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        }
    }

    private suspend fun headlinesInternet(countryCode:String){
        headlines.postValue(Resource.Loading())
        try {
            if(internetConnection(this.getApplication())){
                val response = newsRepository.getHeadlines(countryCode,headlinesPage)
                headlines.postValue(handleHeadlinesNewsResponse(response))
            }
            else{
                headlines.postValue(Resource.Error("No internet connection"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException -> headlines.postValue(Resource.Error("Unable to connect"))
                else -> headlines.postValue(Resource.Error("No signal"))
            }

        }
    }

}