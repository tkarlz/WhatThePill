package com.pill.what

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
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
            setLogo(R.drawable.ic_baseline_medical_services_24)
        }

        cameraSettings = CameraSettings(this)
    }

    fun cameraButton(view: View){
        showSettingPopup()

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

    private fun showSettingPopup() : Boolean{
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.camera_popup, null)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("카메라 이용방법")
            .create()

        val butCancel = view.findViewById<Button>(R.id.cameraFilter)
        butCancel.setOnClickListener {
            cameraSettings.cameraLaunch()
            alertDialog.dismiss()
        }
        alertDialog.setView(view)
        alertDialog.show()

        return true
    }
}

