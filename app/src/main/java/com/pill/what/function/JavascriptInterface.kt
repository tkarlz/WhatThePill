package com.pill.what.function

import android.webkit.JavascriptInterface
import com.pill.what.data.DetailedData
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class JavascriptInterface(private val crawlingData: CrawlingData) {
    @JavascriptInterface
    fun getHtml(html: String) {
        //위 자바스크립트가 호출되면 여기로 html이 반환됨

        val doc: Document = Jsoup.parse(html)
        val detailedData = DetailedData(doc.select("#result_drug_name").text(),
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
                doc.select("#druginfo02 > div:nth-child(3) > div:nth-child(1)").html().replace("^<h4 class=.*</h4> \n".toRegex(), ""),
                doc.select("#druginfo02 > div:nth-child(3) > div:nth-child(2)").html().replace("^<h4 class=.*</h4> \n".toRegex(), ""),
                doc.select("#druginfo02 > div:nth-child(3) > div:nth-child(3)").html().replace("^<h4 class=.*</h4> \n".toRegex(), ""),
                doc.select("#mediguide").html().split("\n")[0].replace("^<br>".toRegex(), ""))

        crawlingData.intent.putExtra("data", detailedData)
        crawlingData.context.startActivity(crawlingData.intent)
        crawlingData.dlg.dismiss()
    }
}