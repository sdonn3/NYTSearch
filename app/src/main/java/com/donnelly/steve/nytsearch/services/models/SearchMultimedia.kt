package com.donnelly.steve.nytsearch.services.models

data class SearchMultimedia(
        val rank: Int?,
        val subtype: String?,
        val caption: String?,
        val credit: String?,
        val type: String?,
        val url: String?,
        val height: Int?,
        val width: Int?,
        val legacy: SearchLegacy?,
        val subType: String?,
        val crop_name: String?
)