package com.pill.what

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pill.what.function.CameraSettings

class MainActivity : AppCompatActivity() {
    private lateinit var cameraSettings: CameraSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.apply {
            title = " What The Pill"

            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.ic_icon)
        }

        cameraSettings = CameraSettings(this)
    }

    fun cameraButton(view: View){
        cameraSettings.cameraLaunch()
    }

    fun imageLoadButton(view: View){
        cameraSettings.galleryLaunch()
    }

    fun searchButton(view: View){
        val nextIntent = Intent(this@MainActivity, SearchActivity::class.java)
        startActivity(nextIntent)
    }

    fun kpicButton(view: View) { //약학 정보원 이동
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.health.kr/"))
        startActivity(intent)
    }
}

