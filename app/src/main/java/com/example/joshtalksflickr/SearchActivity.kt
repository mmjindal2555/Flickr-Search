package com.example.joshtalksflickr

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView

class SearchActivity : AppCompatActivity() {

    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        context = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        supportActionBar?.title = "Flickr Photos"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(context, SearchResultsActivity::class.java)
                intent.action = Intent.ACTION_SEARCH
                intent.putExtra(SearchManager.QUERY, query ?: "")
                startActivity(intent)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        return true
    }
}
