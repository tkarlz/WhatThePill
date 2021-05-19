package com.pill.what.function

import android.app.Dialog
import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.net.URISyntaxException

class APICommunication(val dialog: Dialog) {

    fun uploadImg(imgPath: String, launch: (String) -> Unit) {
        var file: File? = null
        try {
            file = File(imgPath)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        val mOkHttpClient = OkHttpClient()
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "img", file?.name,
                file!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            )   // Upload files
            .build()

        val request = Request.Builder()
            .url("http://54.180.81.20:5000/fileUpload")
            .post(requestBody)
            .build()

        dialog.show()
        val call = mOkHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                dialog.dismiss()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    launch(response.body!!.string())
                }
                dialog.dismiss()
            }
        })
    }
}