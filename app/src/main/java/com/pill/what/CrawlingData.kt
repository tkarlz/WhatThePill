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
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class CrawlingData(val context: Context, val intent: Intent, private val webView: WebView, private val code: String) {
    val dlg = Dialog(context)

    init {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_progress)
        dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dlg.show()

        webView.settings.javaScriptEnabled = true
        // 자바스크립트인터페이스 연결
        webView.addJavascriptInterface(MyJavascriptInterface(this), "Android")
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
                Toast.makeText(context, "데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT)
            }
        }
    }

    fun start() {
        dlg.show()
        webView.loadUrl("https://www.health.kr/searchDrug/result_drug.asp?drug_cd=$code")
    }
}

class MyJavascriptInterface(private val crawlingData: CrawlingData) {
    @JavascriptInterface
    fun getHtml(html: String) {
        //위 자바스크립트가 호출되면 여기로 html이 반환됨

        val doc: Document = Jsoup.parse(html)
        var detailedData = DetailedData(doc.select("#result_drug_name").text(),
                                        doc.select("#idfy_img_small").attr("src"),
                                        doc.select("#ingr_mg").text(),
                                        doc.select("#additives").text(),
                                        doc.select("#drug_cls").text(),
                                        doc.select("#upso_title").text(),
                                        doc.select("#drug_form").text(),
                                        doc.select("#dosage_route").text(),
                                        doc.select("#charact").text(),
                                        doc.select("#cls_code").text(),
                                        doc.select("#stmt").text(),
                                        doc.select("#druginfo02 > div:nth-child(3) > div:nth-child(1)").html(),
                                        doc.select("#druginfo02 > div:nth-child(3) > div:nth-child(2)").html() ,
                                        doc.select("#druginfo02 > div:nth-child(3) > div:nth-child(3)").html(),
                                        doc.select("#mediguide").html())

        crawlingData.intent.putExtra("data", detailedData)
        crawlingData.context.startActivity(crawlingData.intent)
        crawlingData.dlg.dismiss()
    }
}