package com.donnelly.steve.nytsearch.services.models

data class SearchHeadline(
        val main: String?,
        val kicker: String?,
        val content_kicker: String?,
        val print_headline: String?,
        val name: String?,
        val sea: String?,
        val sub: String?
)