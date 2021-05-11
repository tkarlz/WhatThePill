package com.pill.what

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.pill.what.adapter.SearchHistoryAdapter
import com.pill.what.room.AppDatabase
import com.pill.what.room.History
import kotlinx.android.synthetic.main.activity_search.*
import java.text.SimpleDateFormat
import java.util.*

class SearchActivity : AppCompatActivity() {
    object SwitchChecked {
        var value: Boolean? = null
    }

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val actionBar = supportActionBar
        actionBar!!.title = "의약품 정보 검색"
        actionBar.setDisplayHomeAsUpEnabled(true)

        db = AppDatabase.getInstance(this)

        searchEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                startSearch(searchEdit.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }

        searchButton.setOnClickListener {
            startSearch(searchEdit.text.toString())
        }

        SwitchChecked.value = historySwitch.isChecked
        historySwitch.setOnCheckedChangeListener { _, isChecked ->
            SwitchChecked.value = isChecked
        }

        clearButton.setOnClickListener {
            db.historyDao().deleteAll()
        }

        val adapter = SearchHistoryAdapter { name ->
            startSearch(name)
        }

        historyRcv.layoutManager = LinearLayoutManager(this)
        historyRcv.setHasFixedSize(true)
        historyRcv.adapter = adapter

        db.historyDao().getAll().observe(this, androidx.lifecycle.Observer {
            adapter.submitList(it)
        })
    }

    private fun startSearch(searchString: String) {
        if(!searchString.isBlank()) {
            if(historySwitch.isChecked) {
                val currentDateTime = Calendar.getInstance().time
                val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(currentDateTime)
                db.historyDao().insert(History(searchString, dateFormat))
            }

            val nextIntent = Intent(this@SearchActivity, PillListActivity::class.java)
            nextIntent.putExtra("name", searchString.replace(" ", ""))
            startActivity(nextIntent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}