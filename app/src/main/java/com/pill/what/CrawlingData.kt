package com.pill.what

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import android.webkit.*
import android.widget.Toast

class CrawlingData(val context: Context, val intent: Intent, private val webView: WebView, private val code: String) {
    val dlg = Dialog(context)

    init {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_progress)
        dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        webView.settings.javaScriptEnabled = true
        // 자바스크립트인터페이스 연결
        webView.addJavascriptInterface(JavascriptInterface(this), "Android")
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // 자바스크립트 인터페이스로 연결되어 있는 getHTML를 실행
                view?.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('body')[0].innerHTML);")
                view?.onPause()
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                view?.onPause()
                dlg.dismiss()
                Toast.makeText(context, "데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun start() {
        dlg.show()
        webView.loadUrl("https://www.health.kr/searchDrug/result_drug.asp?drug_cd=$code")
    }
}