package com.pill.what

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_pill_info.*

class PillInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_info)

        val data = intent.getParcelableExtra<DetailedData>("data")!!

        supportActionBar?.apply {
            title = " What The Pill"

            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.ic_baseline_medical_services_24)
        }


        Glide.with(applicationContext).load(data.drugImage).into(drugImage)
        drugName.text = data.drugName
        drugIngredient.text = data.drugIngredient
        additives.text = data.additives
        professional.text = data.professional
        drugCompany.text = data.drugCompany
        drugForm.text = data.drugForm
        howEat.text = data.howEat
        appearance.text = data.appearance
        classification.text = data.classification
        storeMethod.text = data.storeMethod
        efficacy.text = Html.fromHtml(data.efficacy)
        drugUsage.text = Html.fromHtml(data.drugUsage)
        medicationInfo.text = Html.fromHtml(data.medicationInfo)
        precautions.text = Html.fromHtml(data.precautions)
    }
}
