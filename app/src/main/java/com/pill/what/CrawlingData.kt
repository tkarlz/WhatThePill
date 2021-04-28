package com.pill.what

import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class CrawlingData(_webView: WebView, _code: String, _load: EventListener) {
    val webView = _webView
    val code = _code
    val load = _load

    init {
        webView.settings.javaScriptEnabled = true
        // 자바스크립트인터페이스 연결
        // 이걸 통해 자바스크립트 내에서 자바함수에 접근할 수 있음.
        webView.addJavascriptInterface(MyJavascriptInterface(this), "Android")
        // 페이지가 모두 로드되었을 때, 작업 정의
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                // 자바스크립트 인터페이스로 연결되어 있는 getHTML를 실행
                // 자바스크립트 기본 메소드로 html 소스를 통째로 지정해서 인자로 넘김
                view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('body')[0].innerHTML);")
                view.onPause()
            }
        }
        //지정한 URL을 웹 뷰로 접근하기
        webView.loadUrl("https://www.health.kr/searchDrug/result_drug.asp?drug_cd=$code")
    }

}

class MyJavascriptInterface(val crawlingData: CrawlingData) {
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
                                        doc.select("#effect").html(),
                                        doc.select("#dosage").html() ,
                                        doc.select("#caution").html(),
                                        doc.select("#mediguide").html())

        crawlingData.load.onLoadData(detailedData)
    }
}