package com.example.elitekingboard.ui.main.view.fragment

import android.content.Context
import android.os.Handler
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.elitekingboard.R
import com.example.elitekingboard.databinding.FragmentMainBinding
import com.example.elitekingboard.ui.base.BaseFragment
import com.example.elitekingboard.ui.main.view.WebAppInterface

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private lateinit var callback: OnBackPressedCallback
    private var backKeyPressedTime: Long = 0
    private lateinit var toast: Toast
    val handler = Handler()

    override fun init() {
        super.init()
        initWebView()
    }

    private fun initWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()

        }

        binding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            setSupportMultipleWindows(true)
        }
        var a = "Abc"
//                binding.webView.loadUrl("file:///android_asset/simple.html")

        binding.webView.loadUrl("https://elite-king-board.web.app/")
//        binding.webView.loadUrl("javascript:receiveToken('$a')")


        handler.post(Runnable {
            try {
                binding.webView.loadUrl("javascript:receiveToken('${a}')")
                Log.d("TAG", "initWebView:넣어주세여 ")

            } catch (e: Exception) {
                Log.d("TAG", "initWebView:넣어주세여 ${e.message} ")
            }
        })
        binding.webView.addJavascriptInterface(WebAppInterface(requireContext(), binding.webView), "Android")

//        binding.webView.setOnKeyListener(object : View.OnKeyListener {
//            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
//                if (event.action == KeyEvent.ACTION_DOWN) {
//                    Log.d("TAG", "onKey: 눌림")
//                    if (binding.webView.canGoBack()) {
//                        Log.d("TAG", "onKey: 뒤")
//
//                        binding.webView.goBack() //?
//                        return true //?
//                    }
//                }
//                return false
//            }
//        })
    }

    // BackPressed 이벤트 정의
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                    backKeyPressedTime = System.currentTimeMillis();
                    toast = Toast.makeText(
                        requireContext(),
                        "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                    return
                }

                if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                    requireActivity().finish();
                    toast.cancel();
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    // 프래그먼트 떨어질 때 callback메서드 삭제
    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

}
