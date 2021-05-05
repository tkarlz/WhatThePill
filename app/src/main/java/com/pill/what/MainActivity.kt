package com.pill.what

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.pill.what.GlobalVariable.Companion.pillData
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var curPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPermission()
        btn_camera.setOnClickListener {
            takeCapture()
        }

        supportActionBar?.apply {
            title = " What The Pill"

            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.ic_baseline_medical_services_24)
        }
        Log.e("tagsd", pillData.filter{ it.name == "가나릴정" }[0].toString())
    }

    private fun takeCapture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException){
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.pill.what.fileprovider",
                         it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun createImageFile(): File? {
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_",".jpg",storageDir)
            .apply { curPhotoPath = absolutePath }
    }

    private fun setPermission(){
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {
                Toast.makeText(this@MainActivity, "권한이 허용 되었습니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@MainActivity, "권한이 거부 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        TedPermission.with(this)
            .setPermissionListener(permission)
            .setRationaleMessage("카메라를 사용하시려면 권한을 허용해주세요.")
            .setDeniedMessage("권한을 거부하셨습니다. [앱 설정] -> [권한] 항목에서 허용해주세요.")
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
            .check()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val bitmap: Bitmap
            val file = File(curPhotoPath)
            if (Build.VERSION.SDK_INT < 28){
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(file))
                iv_profile.setImageBitmap(bitmap)
            } else {
                val decode = ImageDecoder.createSource(
                    this.contentResolver,
                    Uri.fromFile(file)
                )
                bitmap = ImageDecoder.decodeBitmap(decode)
                iv_profile.setImageBitmap(bitmap)
            }
            savePhoto(bitmap)
        }

    }


    private fun savePhoto(bitmap: Bitmap){
        val folderPath = Environment.getExternalStorageDirectory().absolutePath + "/Pictures/"
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "${timestamp}.jpeg"
        val folder = File(folderPath)
        if(!folder.isDirectory){
            folder.mkdirs()
        }
        val out = FileOutputStream(folderPath + fileName)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        Toast.makeText(this,"사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show()
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





