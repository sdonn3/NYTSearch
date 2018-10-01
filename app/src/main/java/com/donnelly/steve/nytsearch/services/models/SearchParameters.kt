package com.donnelly.steve.nytsearch.services.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SearchParameters(
        var query: String? = null,
        var beginDate: String? = null,
        var sort: String? = null,
        var fq: String? = null,
        var newsDeskValues: List<String>? = null
) : Parcelable {
    fun convertList() {
        val combineValues = StringBuilder()
        newsDeskValues?.let{
            if (newsDeskValues!!.isNotEmpty()) {
                newsDeskValues!!.forEach { currentString ->
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
}