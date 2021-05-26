package com.pill.what

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pill.what.function.GlobalVariable.Companion.pillInfo
import com.pill.what.function.GlobalVariable.Companion.pillData
import com.pill.what.adapter.PillListRvAdapter
import com.pill.what.data.APIResultData
import com.pill.what.data.PillData
import com.pill.what.function.CrawlingData
import com.pill.what.room.AppDatabase
import com.pill.what.room.History
import kotlinx.android.synthetic.main.activity_search.*
import java.text.SimpleDateFormat
import java.util.*


class PillListActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private var name: String? = null
    private var result: APIResultData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_list)

        name = intent.getStringExtra("name")
        result = intent.getParcelableExtra("apiResult")

        if (name == null) {
            Log.e("aaaa", result?.form!!)
            Log.e("aaaa", result?.shape!!)
            Log.e("aaaa", result?.colors.toString())
            Log.e("aaaa", result?.line!!)
            Log.e("aaaa", result?.prints.toString())
        }

        supportActionBar?.apply {
            title = " What The Pill"

            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.ic_baseline_medical_services_24)
        }

        db = AppDatabase.getInstance(this)

        val pillList = when(name) {
            null -> {
                val filterName = pillData.filter { data ->
                    data.forms == result?.form && data.shapes == result?.shape &&
                            colorCompare(data) &&
                            (data.line_front == result?.line || data.line_back == result?.line) &&
                            printCompare(data)
                }.map { it.name }
                pillInfo.filter { it.name in filterName }
            }
            else -> pillInfo.filter { it.name.contains(name!!) }
        }
        val mAdapter = PillListRvAdapter(this, pillList) { pill ->
            val nextIntent = Intent(this@PillListActivity, PillInfoActivity::class.java)
            val webView: WebView = findViewById(R.id.webView)
            if(SearchActivity.SwitchChecked.value!!) {
                val currentDateTime = Calendar.getInstance().time
                val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(currentDateTime)
                db.historyDao().insert(History(pill.name, dateFormat))
            }
            CrawlingData(this, nextIntent, webView, pill.code).start()
        }
        val itemSearch: TextView = findViewById(R.id.itemSearch)
        val itemCount: TextView = findViewById(R.id.itemCount)

        val displayText = if (name != null) "\"${name}\"" else "이미지"
        if (mAdapter.itemCount == 0) {
            itemSearch.text = "${displayText}에 대한 검색결과가 없습니다."
        } else {
            itemSearch.text = "${displayText}로 검색한 결과"
            itemCount.text = "총 ${mAdapter.itemCount}건"
        }

        val rcv: RecyclerView = findViewById(R.id.mRecyclerView)
        rcv.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        rcv.layoutManager = lm
        rcv.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        return when(item.itemId){
            R.id.action_btn1 -> {
                showSettingPopup()
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
    private fun showSettingPopup() : Boolean {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.filter_popup, null)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("필터 검색")
            .create()

        val butSave = view.findViewById<Button>(R.id.searchFilter)
        butSave.setOnClickListener {
            alertDialog.dismiss()
        }
        val butCancel = view.findViewById<Button>(R.id.cancleFilter)
        butCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.setView(view)
        alertDialog.show()

        return true
    }

    private fun colorCompare(data: PillData): Boolean {
        var isContains = true
        val dataColor = if (data.color2.isBlank()) {
            "${data.color1}, ${data.color2}"
        } else {
            data.color1
        }
        result?.colors?.forEach {
            if(!dataColor.contains(it)) {
                isContains = false
                return@forEach
            }
        }

        return isContains
    }
    private fun printCompare(data: PillData): Boolean {
        var isContains = true
        result?.prints?.forEach {
            if(!data.print_front.replace("\\[.*]".toRegex(), "").contains(it.replace(" ", "")) &&
                !data.print_back.replace("\\[.*]".toRegex(), "").contains(it.replace(" ", ""))) {
                isContains = false
                return@forEach
            }
        }

        return isContains
    }
}