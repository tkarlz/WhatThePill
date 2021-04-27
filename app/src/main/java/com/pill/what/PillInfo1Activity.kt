package com.pill.what

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pill.what.GlobalVariable.Companion.pillInfo
import com.pill.what.PillInfoActivity.detailedData
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class PillInfoActivity : AppCompatActivity() {
    companion object detailedData {
        var drugName: String = ""  // 제품명
        var drugImage: String = ""  // 이미지 링크
        var drugIngredient: String = "" // 성분 / 함량
        var additives: String = "" // 첨가제
        var professional: String = "" // 전문/일반
        var drugCompany: String = "" // 제조/수입
        var drugForm: String = "" // 제형
        var howEat: String = "" // 투여경로
        var appearance: String = "" // 성상
        var classification: String = "" // 식약처 분류
        var storeMethod: String = "" // 저장방법
        var efficacy: String = ""  // 효능 효과
        var durgUsage: String = ""  // 용법 용량
        var precautions: String = "" // 사용상의 주의사항
        var medicationInfo: String = ""  // 복약정보
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_info1)

        supportActionBar?.apply {
            title = " What The Pill"

            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.ic_baseline_medical_services_24)
        }

        val webView : WebView = findViewById(R.id.webView)
        val tv : TextView = findViewById(R.id.textView)
        tv.text = drugName

        webView.settings.javaScriptEnabled = true
        // 자바스크립트인터페이스 연결
        // 이걸 통해 자바스크립트 내에서 자바함수에 접근할 수 있음.
        webView.addJavascriptInterface(MyJavascriptInterface(), "Android")
        // 페이지가 모두 로드되었을 때, 작업 정의
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                // 자바스크립트 인터페이스로 연결되어 있는 getHTML를 실행
                // 자바스크립트 기본 메소드로 html 소스를 통째로 지정해서 인자로 넘김
                view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('body')[0].innerHTML);")
            }
        }
        //지정한 URL을 웹 뷰로 접근하기
        webView.loadUrl("https://www.health.kr/searchDrug/result_drug.asp?drug_cd=" + pillInfo[0].code)

    }

    fun Button(view: View) {
        Log.e("aaaaaaaa", drugName)
        Log.e("aaaaaaaa", drugImage)
        Log.e("aaaaaaaa", drugIngredient)
        Log.e("aaaaaaaa", additives)
        Log.e("aaaaaaaa", professional)
        Log.e("aaaaaaaa", drugCompany)
        Log.e("aaaaaaaa", drugForm)
        Log.e("aaaaaaaa", howEat)
        Log.e("aaaaaaaa", appearance)
        Log.e("aaaaaaaa", classification)
        Log.e("aaaaaaaa", storeMethod)
        Log.e("aaaaaaaa", efficacy)
        Log.e("aaaaaaaa", durgUsage)
        Log.e("aaaaaaaa", precautions)
        Log.e("aaaaaaaa", medicationInfo)
    }
}

class MyJavascriptInterface {
    @JavascriptInterface
    fun getHtml(html: String) {
        //위 자바스크립트가 호출되면 여기로 html이 반환됨

        var doc: Document = Jsoup.parse(html)
        detailedData.drugName = doc.select("#result_drug_name").text()
        detailedData.drugImage = doc.select("#idfy_img_small").attr("src")
        detailedData.drugIngredient = doc.select("#ingr_mg").text()
        detailedData.additives = doc.select("#additives").text()
        detailedData.professional = doc.select("#drug_cls").text()
        detailedData.drugCompany = doc.select("#upso_title").text()
        detailedData.drugForm = doc.select("#drug_form").text()
        detailedData.howEat = doc.select("#dosage_route").text()
        detailedData.appearance = doc.select("#charact").text()
        detailedData.classification = doc.select("#cls_code").text()
        detailedData.storeMethod = doc.select("#stmt").text()
        detailedData.efficacy = doc.select("#effect").html()
        detailedData.durgUsage = doc.select("#dosage").html()
        detailedData.precautions = doc.select("#caution").html()
        detailedData.medicationInfo = doc.select("#mediguide").html()
    }
}