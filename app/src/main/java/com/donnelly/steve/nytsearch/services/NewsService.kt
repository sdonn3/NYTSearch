package com.donnelly.steve.nytsearch.services

import com.donnelly.steve.nytsearch.services.models.Search
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("/svc/search/$API_VERSION/articlesearch.json")
    fun searchArticles(
            @Query(value = "page") page: Int,
            @Query(value = "q", encoded = true) query: String?,
            @Query("begin_date") beginDate: String?,
            @Query("sort") sort: String?,
            @Query(value = "fq", encoded = true) newsDesk: String?
    ): Observable<Search>

    companion object {
        private const val API_VERSION = "v2"
        const val SORT_TYPE_OLDEST = "oldest"
        const val SORT_TYPE_NEWEST = "newest"
        const val NEWS_DESK_ARTS = "arts"
        const val NEWS_DESK_EDUCATION = "education"
        const val NEWS_DESK_SPORTS = "sports"
    }
}