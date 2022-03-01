package com.example.elitekingboard.ui.main.view

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import com.example.elitekingboard.data.preference.MySharedPreferences
import com.example.elitekingboard.ui.main.view.activity.LoginActivity

class WebAppInterface(private val context: Context, private val webView: WebView) {
    private val handler = Handler()

    @JavascriptInterface
    fun showToast(toast: String) {
        Toast.makeText(context, "로그아웃되셨습니다.", Toast.LENGTH_SHORT).show()
        context.startActivity(Intent(context, LoginActivity::class.java))
        MySharedPreferences.clearUser(context)
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
