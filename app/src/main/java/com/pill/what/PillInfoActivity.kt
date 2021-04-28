package com.pill.what

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

interface EventListener {
    fun onLoadData(data: DetailedData)
}

class PillInfoActivity : AppCompatActivity(), EventListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_info)

        val intent = getIntent();
        val code = intent.getStringExtra("code")
        val image = intent.getStringExtra("image")
        Log.e("asdf", code!!)

        supportActionBar?.apply {
            title = " What The Pill"

            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.ic_baseline_medical_services_24)
        }

        val webView: WebView = findViewById(R.id.webView)
        CrawlingData(webView, code!!, this)

        val drugImageView: ImageView = findViewById(R.id.drugImage)
        Glide.with(applicationContext).load(image!!).into(drugImageView)
    }

    override fun onLoadData(data: DetailedData) {
        val drugName: TextView = findViewById(R.id.drugName)
        val drugIngredient: TextView = findViewById(R.id.drugIngredient)
        val additives: TextView = findViewById(R.id.additives)
        val professional: TextView = findViewById(R.id.professional)
        val drugCompany: TextView = findViewById(R.id.drugCompany)
        val drugForm: TextView = findViewById(R.id.drugForm)
        val howEat: TextView = findViewById(R.id.howEat)
        val appearance: TextView = findViewById(R.id.appearance)
        val classification: TextView = findViewById(R.id.classification)
        val storeMethod: TextView = findViewById(R.id.storeMethod)
        val efficacy: TextView = findViewById(R.id.efficacy)
        val drugUsage: TextView = findViewById(R.id.drugUsage)
        val medicationInfo: TextView = findViewById(R.id.medicationInfo)
        val precautions: TextView = findViewById(R.id.precautions)

        drugName.text = drugName.text.toString() + data.drugName
        drugIngredient.text = drugIngredient.text.toString() + data.drugIngredient
        additives.text = additives.text.toString() + data.additives
        professional.text = professional.text.toString() + data.professional
        drugCompany.text = drugCompany.text.toString() + data.drugCompany
        drugForm.text = drugForm.text.toString() + data.drugForm
        howEat.text = howEat.text.toString() + data.howEat
        appearance.text = appearance.text.toString() + data.appearance
        classification.text = classification.text.toString() + data.classification
        storeMethod.text = storeMethod.text.toString() + data.storeMethod
        efficacy.text = efficacy.text.toString() + Html.fromHtml(data.efficacy)
        drugUsage.text = drugUsage.text.toString() + Html.fromHtml(data.drugUsage)
        medicationInfo.text = medicationInfo.text.toString() + Html.fromHtml(data.medicationInfo)
        precautions.text = precautions.text.toString() + Html.fromHtml(data.precautions)
    }

}
