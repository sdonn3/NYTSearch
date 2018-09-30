package com.donnelly.steve.nytsearch.services.models

import com.google.gson.annotations.SerializedName

data class SearchDocument(
        @SerializedName("web_url")
        val webUrl: String?,
        val snippet: String?,
        @SerializedName("print_page")
        val printPage: String?,
        val source: String?,
        val multimedia: ArrayList<SearchMultimedia>?,
        val headline: SearchHeadline?,
        val keywords: ArrayList<SearchKeywords>?,
        @SerializedName("pub_date")
        val publicationDate: String?,
        @SerializedName("document_type")
        val documentType: String?,
        @SerializedName("news_desk")
        val newsDesk: String?,
        val byline: SearchByline?,
        @SerializedName("type_of_material")
        val typeOfMaterial: String?,
        @SerializedName("_id")
        val id: String?,
        @SerializedName("word_count")
        val wordCount: String?,
        val score: Double?
)