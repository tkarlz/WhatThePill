package com.pill.what

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pill.what.GlobalVariable.Companion.pillInfo
import kotlinx.android.synthetic.main.activity_pill_info.*


class PillListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_list)

        val data = intent.getStringExtra("name")

        supportActionBar?.apply {
            title = " What The Pill"

            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.ic_baseline_medical_services_24)

        }

        val pillList = pillInfo.filter { it.name.contains(data!!) }
        val mAdapter = PillListRvAdapter(this, pillList) { pill ->
            val nextIntent = Intent(this@PillListActivity, PillInfoActivity::class.java)
            val webView: WebView = findViewById(R.id.webView)
            CrawlingData(this, nextIntent, webView, pill.code).start()
        }
        val itemSearch: TextView = findViewById(R.id.itemSearch)
        val itemCount: TextView = findViewById(R.id.itemCount)

        if (mAdapter.itemCount == 0) {
            itemSearch.setText("\""+ data+ "\"" + "에 대한 검색결과가 없습니다.")
        } else {
            itemSearch.setText("\"" + data + "\"로 검색한 결과")
            itemCount.setText("총 " + mAdapter.itemCount + "건")
        }


        val rcv: RecyclerView = findViewById(R.id.mRecyclerView)
        rcv.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        rcv.layoutManager = lm
        rcv.setHasFixedSize(true)
    }
}