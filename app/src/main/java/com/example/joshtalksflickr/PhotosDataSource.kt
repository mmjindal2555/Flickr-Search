package com.example.joshtalksflickr

import android.app.SearchManager.QUERY
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.joshtalksflickr.api.Photo
import com.example.joshtalksflickr.api.Photos
import com.example.joshtalksflickr.api.SearchResult
import com.example.joshtalksflickr.api.getApis
import kotlinx.android.synthetic.main.activity_search_results.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class PhotoDataSource : PageKeyedDataSource<Long, Photo?>() {

    val API_KEY = "79e77fd2286f674d6e24c8701c6dbb09"

    /*
   * Step 2: This method is responsible to load the data initially
   * when app screen is launched for the first time.
   * We are fetching the first page data from the api
   * and passing it via the callback method to the UI.
   */
    override fun loadInitial(
        @NonNull params: LoadInitialParams<Long>,
        @NonNull callback: LoadInitialCallback<Long, Photo?>
    ) {

        getApis().listRepos(
            method = "flickr.photos.search",
            text = QUERY,
            apiKey = API_KEY,
            page = "1",
            per_page = "10",
            format = "json",
            nojsoncallback = 1
        )
            .enqueue(object : retrofit2.Callback<SearchResult> {
                override fun onFailure(call: Call<SearchResult>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<SearchResult?>?,
                    response: Response<SearchResult?>
                ) {
                    if (response.isSuccessful()) {

                        val searchResult = response.body()
                        searchResult?.let {
                            callback.onResult(it.photos.photo, null, 2L)
                        }
                    }
                }
            })
    }

    override fun loadBefore(
        @NonNull params: LoadParams<Long>,
        @NonNull callback: LoadCallback<Long, Photo?>
    ) {
    }

    /*
   * Step 3: This method it is responsible for the subsequent call to load the data page wise.
   * This method is executed in the background thread
   * We are fetching the next page data from the api
   * and passing it via the callback method to the UI.
   * The "params.key" variable will have the updated value.
   */
    override fun loadAfter(
        @NonNull params: LoadParams<Long>,
        @NonNull callback: LoadCallback<Long, Photo?>
    ) {
        getApis().listRepos(
            method = "flickr.photos.search",
            text = QUERY,
            apiKey = API_KEY,
            page = params.key.toString(),
            per_page = "10",
            format = "json",
            nojsoncallback = 1
        )
            .enqueue(object : retrofit2.Callback<SearchResult> {
                override fun onFailure(call: Call<SearchResult>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<SearchResult?>?,
                    response: Response<SearchResult?>
                ) {
                    if (response.isSuccessful()) {

                        val searchResult = response.body()
                        searchResult?.let {
                            callback.onResult(it.photos.photo, params.key + 1)
                        }
                    }
                }
            })
    }

    companion object {
        private val TAG = PhotoDataSource::class.java.simpleName
    }
}