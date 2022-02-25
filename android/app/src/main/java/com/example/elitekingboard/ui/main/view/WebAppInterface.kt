package com.example.elitekingboard.ui.main.view

import android.content.Context
import android.os.Handler
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast

class WebAppInterface(private val context: Context, private val webView: WebView) {
    private val handler = Handler()

    @JavascriptInterface
    fun showToast(toast: String) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun receiveToken() {
        handler.post(Runnable {
            Log.d("HybridApp", "데이터 요청")
            val test: String = "dd"
            Log.d("HybridApp", test)
            webView.loadUrl("javascript:receiveToken('$test')")
        })
    }
}
