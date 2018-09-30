package com.donnelly.steve.nytsearch.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestClient {

    val newsService: NewsService

    init {
        val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    var request = chain.request()
                    val url = request.url().newBuilder().addQueryParameter("api-key", API_KEY).build()
                    request = request.newBuilder().url(url).build()
                    chain.proceed(request)

                }.build()

        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()

        newsService = retrofit.create(NewsService::class.java)
    }


    companion object {
        private const val API_KEY = "acc6ad584fd54780a3514518f7a7ab86"
        private const val BASE_URL = "https://api.nytimes.com/"
    }
}