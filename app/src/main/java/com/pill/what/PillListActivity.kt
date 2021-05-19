package com.pill.what

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pill.what.function.GlobalVariable.Companion.pillInfo
import com.pill.what.function.GlobalVariable.Companion.pillData
import com.pill.what.adapter.PillListRvAdapter
import com.pill.what.function.CrawlingData
import com.pill.what.room.AppDatabase
import com.pill.what.room.History
import java.text.SimpleDateFormat
import java.util.*


class PillListActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_list)

        val name = intent.getStringExtra("name")
        val form = intent.getStringExtra("form")
        val print = intent.getStringExtra("print")
        val shape = intent.getStringExtra("shape")
        val color = intent.getStringExtra("color")
        val line = intent.getStringExtra("line")

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
                    data.forms == form && data.shapes == shape &&
                            (data.color1 == color || data.color2 == color) &&
                            (data.line_front == line || data.line_back == line) &&
                            (data.print_front == print || data.print_back == print)
                }.map { it.name }
                pillInfo.filter { it.name in filterName }
            }
            else -> pillInfo.filter { it.name.contains(name) }
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
}