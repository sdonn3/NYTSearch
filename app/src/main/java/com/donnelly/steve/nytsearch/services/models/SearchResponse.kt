package com.donnelly.steve.nytsearch.services.models

data class SearchResponse(
        val docs: ArrayList<SearchDocument>?,
        val meta: SearchMeta?
)