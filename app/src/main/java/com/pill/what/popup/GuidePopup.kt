package com.pill.what.popup

import android.content.Context
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.pill.what.R

class GuidePopup(val context: Context) {

    private val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
    private val isDoNotShow = pref.getBoolean("guide", false)

    fun show(execute: () -> Unit) {
        if (isDoNotShow) {
            execute()
            return
        }
        val guideDialog: AlertDialog = AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
            .setTitle(R.string.guide_name)
            .setMessage("인식률을 높이시려면 식별 문자가 보이게 찍어주세요.")
            .setNegativeButton("다시 보지 않기") { _, _ ->
                pref.edit().putBoolean("guide", true).apply()
                execute()
            }
            .setPositiveButton("확인") { _, _ ->
                execute()
            }.show()

        guideDialog.findViewById<TextView>(android.R.id.message)?.textSize = 20F
    }
}