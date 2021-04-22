package com.pill.what

import android.app.Application
import com.google.gson.Gson

class GlobalVariable: Application() {
    companion object {
        lateinit var pillData: List<PillData>
        lateinit var pillInfo: List<PillInfo>
    }

    override fun onCreate() {
        super.onCreate()
        val gson = Gson()

        var jsonString: String = applicationContext.assets.open("data.json")
            .bufferedReader()
            .use {it.readText()}

        pillData = gson.fromJson(jsonString, Array<PillData>::class.java).toList()

        jsonString = applicationContext.assets.open("info.json")
            .bufferedReader()
            .use {it.readText()}

        pillInfo = gson.fromJson(jsonString, Array<PillInfo>::class.java).toList()
    }
}