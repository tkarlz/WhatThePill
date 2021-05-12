package com.pill.what.function

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.pill.what.MainActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraSettings(val activity: MainActivity) {
    private lateinit var curPhotoPath: String
    private val cameraStartForResult: ActivityResultLauncher<Intent>
    private val galleryStartForResult: ActivityResultLauncher<Intent>
    private val takeCapture: Intent
    private val pickImage: Intent

    init {
        cameraStartForResult = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
//            val intent = result.data
                // TODO server communication
            }
        }

        galleryStartForResult = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                saveImageFile(intent?.data!!)
                // TODO server communication
            }
        }

        takeCapture = Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity.packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException){
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        activity,
                        "com.pill.what.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                }
            }
        }

        pickImage = Intent.createChooser(Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }, "")
    }

    fun cameraLaunch() {
        setCameraPermission {
            cameraStartForResult.launch(takeCapture)
        }
    }

    fun galleryLaunch() {
        setGalleryPermission {
            galleryStartForResult.launch(pickImage)
        }
    }

    private fun setCameraPermission(launch: () -> Unit){
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {
                launch()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(activity, "카메라 사용 권한이 거부 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        TedPermission.with(activity)
            .setPermissionListener(permission)
            .setRationaleMessage("카메라를 사용하시려면 권한을 허용해주세요.")
            .setDeniedMessage("권한을 거부하셨습니다. [앱 설정] -> [권한] 항목에서 허용해주세요.")
            .setPermissions(android.Manifest.permission.CAMERA)
            .check()
    }

    private fun setGalleryPermission(launch: () -> Unit){
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {
                launch()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(activity, "갤러리 접근 권한이 거부 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        TedPermission.with(activity)
            .setPermissionListener(permission)
            .setRationaleMessage("갤러리에 접근하시려면 권한을 허용해주세요.")
            .setDeniedMessage("권한을 거부하셨습니다. [앱 설정] -> [권한] 항목에서 허용해주세요.")
            .setPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }

    private fun createImageFile(): File? {
        val flist = activity.filesDir.listFiles { _, name ->
            name.endsWith(".jpg")
        }
        flist.forEach {
            it.delete()
        }
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(Date())
        val storageDir: File? = activity.filesDir
        return File.createTempFile("JPEG_${timestamp}_",".jpg",storageDir)
            .apply { curPhotoPath = absolutePath }
    }

    private fun saveImageFile(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(activity.contentResolver.openInputStream(uri))
        val out = FileOutputStream(curPhotoPath)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
    }
}