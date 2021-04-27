package com.pill.what

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class PillInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_info)

        val intent = getIntent();
        val data = intent.getStringExtra("code")
        Log.e("as", data!!)

        supportActionBar?.apply {
            title = " What The Pill"

            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.ic_baseline_medical_services_24)
        }


    }
}