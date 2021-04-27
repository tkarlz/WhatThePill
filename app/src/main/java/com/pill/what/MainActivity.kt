package com.pill.what

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pill.what.GlobalVariable.Companion.pillData


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.apply {
            title = " What The Pill"

            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.ic_baseline_medical_services_24)
        }
        Log.e("tagsd", pillData.filter{ it.name == "가나릴정" }[0].toString())
    }

    fun cameraButton(view: View){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent)
    }

    fun imageLoadButton(view: View){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent)
    }

    fun searchButton(view: View){
        val nextIntent = Intent(this@MainActivity, SearchActivity::class.java)
        startActivity(nextIntent)
    }

    fun KPICButton(view: View) { //약학 정보원 이동
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.health.kr/"))
        startActivity(intent)
    }
}

