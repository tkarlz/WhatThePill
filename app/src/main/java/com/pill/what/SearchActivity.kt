package com.pill.what

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.annotation.RequiresApi
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    lateinit var db: AppDatabase

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val actionBar = supportActionBar
        actionBar!!.title = "의약품 정보 검색"
        actionBar.setDisplayHomeAsUpEnabled(true)

        db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries()
                .build()

        result_text.text= db.todoDao().getAll().toString()

        add_button.setOnClickListener {
            buttonClick()
        }

        todo_edit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                buttonClick()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun buttonClick() {
        if(!todo_edit.text.isBlank()) {
            db.todoDao().insert(Todo(todo_edit.text.toString()))
            result_text.text = db.todoDao().getAll().toString()
            val nextIntent = Intent(this@SearchActivity, PillListActivity::class.java)
            nextIntent.putExtra("name", todo_edit.text.toString().replace(" ", ""))
            startActivity(nextIntent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}