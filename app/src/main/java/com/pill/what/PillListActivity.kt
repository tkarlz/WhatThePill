package com.pill.what

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pill.what.adapter.PillDataAdapter
import com.pill.what.adapter.PillListRvAdapter
import com.pill.what.data.APIResultData
import com.pill.what.function.CrawlingData
import com.pill.what.function.GlobalVariable.Companion.pillData
import com.pill.what.function.GlobalVariable.Companion.pillInfo
import com.pill.what.popup.FilterPopup
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
            setLogo(R.drawable.ic_icon)
        }

        db = AppDatabase.getInstance(this)

        val pda = PillDataAdapter()
        val pillList = when(name) {
            null -> {
                val filterName = pillData.filter { data ->
                    data.forms == result?.form && data.shapes == result?.shape &&
                            pda.colorCompare(data, result) &&
                            (data.line_front == result?.line || data.line_back == result?.line) &&
                            pda.printCompare(data, result)
                }.map { it.name }
                pillInfo.filter { it.name in filterName }
            }
            else -> pillInfo.filter { it.name.contains(name!!) }
        }
        val mAdapter = PillListRvAdapter(this, pillList) { pill ->
            val nextIntent = Intent(this@PillListActivity, PillInfoActivity::class.java)
            val webView: WebView = findViewById(R.id.webView)
            if(getSharedPreferences("pref", Context.MODE_PRIVATE).getBoolean("save", true)) {
                val currentDateTime = Calendar.getInstance().time
                val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(currentDateTime)
                db.historyDao().insert(History(pill.name, dateFormat))
            }
            CrawlingData(this, nextIntent, webView, pill.code).start()
        }
        val itemSearch: TextView = findViewById(R.id.itemSearch)
        val itemCount: TextView = findViewById(R.id.itemCount)

        val displayText = if (name != null) "\"${name}\"" else "?????????"
        if (mAdapter.itemCount == 0) {
            itemSearch.text = "${displayText}??? ?????? ??????????????? ????????????."
        } else {
            itemSearch.text = "${displayText}??? ????????? ??????"
            itemCount.text = "??? ${mAdapter.itemCount}???"
        }

        val rcv: RecyclerView = findViewById(R.id.mRecyclerView)
        rcv.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        rcv.layoutManager = lm
        rcv.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return name == null
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        return when(item.itemId){
            R.id.action_btn1 -> {
                FilterPopup(this, result).showFilterPopup()
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}