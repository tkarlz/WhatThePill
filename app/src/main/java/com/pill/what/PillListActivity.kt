package com.pill.what

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.widget.Adapter
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pill.what.adapter.FilterColorRvAdapter
import com.pill.what.adapter.PillListRvAdapter
import com.pill.what.function.CrawlingData
import com.pill.what.function.GlobalVariable.Companion.pillData
import com.pill.what.function.GlobalVariable.Companion.pillInfo
import com.pill.what.room.AppDatabase
import com.pill.what.room.History
import kotlinx.android.synthetic.main.activity_search.*
import java.text.SimpleDateFormat
import java.util.*

data class FilterColor(
    val color: Int,
    val name: String
)

class PillListActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase

    private lateinit var filterColorList: List<FilterColor>
    private var filteredColorList = mutableListOf<FilterColor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_list)

        filterColorList = listOf(
            FilterColor(ContextCompat.getColor(this, R.color.colorWhite), "하양"),
            FilterColor(ContextCompat.getColor(this, R.color.colorYellow), "노랑"),
            FilterColor(ContextCompat.getColor(this, R.color.colorOrange), "주황"),
            FilterColor(ContextCompat.getColor(this, R.color.colorPink), "분홍"),
            FilterColor(ContextCompat.getColor(this, R.color.colorRed), "빨강"),
            FilterColor(ContextCompat.getColor(this, R.color.colorBrown), "갈색"),
            FilterColor(ContextCompat.getColor(this, R.color.colorLightGreen), "연두"),
            FilterColor(ContextCompat.getColor(this, R.color.colorGreen), "초록"),
            FilterColor(ContextCompat.getColor(this, R.color.colorTurquoise), "청록"),
            FilterColor(ContextCompat.getColor(this, R.color.colorBlue), "파랑"),
            FilterColor(ContextCompat.getColor(this, R.color.colorIndigo), "남색"),
            FilterColor(ContextCompat.getColor(this, R.color.colorAmethyst), "자주"),
            FilterColor(ContextCompat.getColor(this, R.color.colorPurple), "보라"),
            FilterColor(ContextCompat.getColor(this, R.color.colorGray), "회색"),
            FilterColor(ContextCompat.getColor(this, R.color.colorBlack), "검정")
        )

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




    // filter button
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

    // filter popup
    private fun showSettingPopup() : Boolean{
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.filter_popup, null)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("필터 검색")
            .create()

        val prevList = filteredColorList.toMutableList()

        val butSave = view.findViewById<Button>(R.id.searchFilter)
        initRecyclerView(view)
        butSave.setOnClickListener {
            alertDialog.dismiss()
        }
        val butCancel = view.findViewById<Button>(R.id.cancleFilter)
        butCancel.setOnClickListener {
            filteredColorList = prevList
            alertDialog.dismiss()
        }
        alertDialog.setView(view)
        alertDialog.show()

        return true
    }

    private fun initRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.filterColorRecyclerView)
        val lm = GridLayoutManager(this, 5)
        val mAdapter = FilterColorRvAdapter(filterColorList, filteredColorList)

        recyclerView.apply {
            layoutManager = lm
            adapter = mAdapter
            setHasFixedSize(true)
        }
    }
}