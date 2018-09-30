package com.donnelly.steve.nytsearch

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_webview.*

class WebviewActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val url = intent.getStringExtra(URL_STRING)

        wvArticle.webViewClient = WebViewClient()
        wvArticle.settings.javaScriptEnabled = true
        wvArticle.loadUrl(url)
    }

    companion object {
        const val URL_STRING = "urlString"
    }
}