package com.example.joshtalksflickr

import android.app.SearchManager
import android.content.Intent
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demolib.api.Photo
import com.example.demolib.api.SearchResult
import com.example.demolib.api.getApis
import kotlinx.android.synthetic.main.activity_search_results.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchResultsActivity : AppCompatActivity() {

    private val vm: SearchResultsViewModel = SearchResultsViewModel()

    private var page = 1
    private var query = ""
    private var photos: MutableList<Photo> = mutableListOf()
    private var adapter: PhotoAdapter? = null
    private val layoutManager = (GridLayoutManager(this@SearchResultsActivity, 2))
    private var isLoading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        actionBar?.hide()
        supportActionBar?.hide()

        handleIntent(intent)
        handleListView()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleListView() {
        listView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = layoutManager.getChildCount()
                val totalItemCount: Int = layoutManager.getItemCount()
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE
                    ) {
                        makeApiCall(query)
                    }
                }
            }
        })

    }

    fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            query = intent.getStringExtra(SearchManager.QUERY)
            titleTV.text = query
            makeApiCall(query)
        }
    }

    fun makeApiCall(text: String){
        val response: Call<SearchResult> = getApis().listRepos(method = "flickr.photos.search", text = text,
            per_page = "10", page = page.toString(10), apiKey = "79e77fd2286f674d6e24c8701c6dbb09", format = "json", nojsoncallback=1)

        isLoading = true
        response.enqueue(object : Callback<SearchResult> {
            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                titleTV.text = "Some error"
                isLoading = false
            }

            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {

                val searchResult = response.body()
                searchResult?.let {
                    val tmpphotos : MutableList<Photo> = mutableListOf()
                    tmpphotos.addAll(photos)
                    tmpphotos.addAll(searchResult.photos.photo)

                    if(adapter == null) {
                        adapter = PhotoAdapter(this@SearchResultsActivity, tmpphotos)
                        listView.layoutManager = layoutManager
                        listView.adapter = adapter
                        photos = tmpphotos
                    } else {
                        (listView.adapter as PhotoAdapter).let {
                            it.photos = tmpphotos

                            it.notifyItemRangeChanged(photos.size, tmpphotos.size)
                            it.notifyItemRangeInserted(photos.size, tmpphotos.size)
                            photos = tmpphotos
                        }
                    }
                }
                isLoading = false
                page++

            }
        })
    }

}
