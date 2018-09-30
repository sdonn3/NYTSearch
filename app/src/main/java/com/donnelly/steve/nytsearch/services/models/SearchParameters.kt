package com.donnelly.steve.nytsearch.services.models

class SearchParameters(
        var query: String? = null,
        var beginDate: String? = null,
        var sort: String? = null,
        var fq: String? = null
) {
    fun setNewsdeskValues(listOfValues: List<String>) {
        val combineValues = StringBuilder()
        if (listOfValues.isNotEmpty()) {
            listOfValues.forEach { currentString ->
                if (combineValues.isEmpty()) {
                    combineValues.append(currentString)
                } else {
                    combineValues.append("$currentString ")
                }
            }
        }
        fq = "news_desk:($combineValues)"
    }
}