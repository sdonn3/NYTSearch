package com.donnelly.steve.nytsearch

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.donnelly.steve.nytsearch.adapters.ArticleAdapter
import com.donnelly.steve.nytsearch.services.RestClient
import com.donnelly.steve.nytsearch.services.models.SearchParameters
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FilterDialogFragment.OnCompleteListener {

    var searchParameters = SearchParameters()

    private val newsService by lazy { RestClient().newsService }
    private val disposables by lazy { CompositeDisposable() }
    private val adapter by lazy { ArticleAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        rvArticles.setHasFixedSize(true)
        rvArticles.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvArticles.adapter = adapter
        searchArticles()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.action_search)
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

    private fun launchFilterDialog() {
        val filterDialog = FilterDialogFragment.newInstance(searchParameters)
        filterDialog.listener = this
        filterDialog.show(supportFragmentManager, FILTER_DIALOG)
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
                .subscribe({ search ->
                    search.response?.docs?.let {
                        adapter.articles = search.response.docs
                        adapter.notifyDataSetChanged()
                    }
                }, { e ->
                    e.printStackTrace()
                })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_filter) {
            launchFilterDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onComplete(searchParameters: SearchParameters) {
        this.searchParameters = searchParameters
        searchArticles()
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    companion object {
        private const val FILTER_DIALOG = "filterDialogFragment"
    }
}
