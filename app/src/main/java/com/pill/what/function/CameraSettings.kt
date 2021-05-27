package com.pill.what.function

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.google.gson.Gson
import com.pill.what.MainActivity
import com.pill.what.PillImageListActivity
import com.pill.what.R
import com.pill.what.data.APIResultData
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CameraSettings(private val activity: MainActivity) {
    private lateinit var curPhotoPath: String
    private val cameraStartForResult: ActivityResultLauncher<Intent>
    private val galleryStartForResult: ActivityResultLauncher<Intent>
    private val takeCapture: Intent
    private val pickImage: Intent
    private val dlg = Dialog(activity)
    private val setPermission = PermissionSettings(activity)

    init {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_progress)
        dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        cameraStartForResult = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                apiCommAndStartIntent()
            }
        }

        galleryStartForResult = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                saveImageFile(intent?.data!!)
                apiCommAndStartIntent()
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
        setPermission.camera {
            showGuidePopup {
                cameraStartForResult.launch(takeCapture)
            }
        }
    }

    fun galleryLaunch() {
        setPermission.gallery {
            galleryStartForResult.launch(pickImage)
        }
    }

    private fun apiCommAndStartIntent() {
        APICommunication(dlg).uploadImg(curPhotoPath) {
            if (it.length < 3) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(activity, "알약을 인식할 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                val nextIntent = Intent(activity, PillImageListActivity::class.java)
                Log.e("aaaa", it)
                val gson = Gson()
                val dataList = ArrayList(gson.fromJson(it, Array<APIResultData>::class.java).toList())

                nextIntent.putParcelableArrayListExtra("apiResult", dataList)
                activity.startActivity(nextIntent)
            }
        }
    }

    private fun createImageFile(): File? {
        val flist = activity.filesDir.listFiles { _, name ->
            name.endsWith(".jpg")
        }
        flist?.forEach {
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

    private fun showGuidePopup(execute: () -> Unit) : Boolean{
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.camera_popup, null)

        val alertDialog = AlertDialog.Builder(activity)
            .setTitle("카메라 이용방법")
            .create()

        val butConfirm = view.findViewById<Button>(R.id.cameraFilter)
        butConfirm.setOnClickListener {
            execute()
            alertDialog.dismiss()
        }
        alertDialog.setView(view)
        alertDialog.show()

        return true
    }
}