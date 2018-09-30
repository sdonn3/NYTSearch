package com.donnelly.steve.nytsearch

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.donnelly.steve.nytsearch.services.RestClient
import com.donnelly.steve.nytsearch.services.models.SearchParameters
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var searchParameters = SearchParameters()

    private val newsService by lazy { RestClient().newsService }
    private val disposables by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        searchArticles()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem =  menu?.findItem(R.id.action_search)
        val searchView: SearchView = (searchItem?.actionView) as SearchView
        searchView.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(queryString: String?): Boolean {
                        searchView.clearFocus()
                        searchParameters.query = queryString
                        searchArticles()
                        return true
                    }

                    override fun onQueryTextChange(queryString: String?): Boolean {
                        searchParameters.query = queryString
                        return false
                    }
                }
        )

        return true
    }

    private fun searchArticles() {
        disposables += newsService
                .searchArticles(
                        query = searchParameters.query,
                        beginDate = searchParameters.beginDate,
                        sort = searchParameters.sort,
                        newsDesk = searchParameters.fq
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({search ->
                    search.response?.docs
                },{ e->
                    e.printStackTrace()
                })
    }
}