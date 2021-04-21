package com.pill.what

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.room.Room

class SearchActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val actionBar = supportActionBar
        actionBar!!.title = "의약품 정보 검색"
        actionBar.setDisplayHomeAsUpEnabled(true)

        val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries()
                .build()

        val result_text : TextView = findViewById(R.id.result_text)
        val add_button : ImageButton = findViewById(R.id.add_button)
        val todo_edit : EditText = findViewById(R.id.todo_edit)


        result_text.text= db.todoDao().getAll().toString()

        add_button.setOnClickListener{
            db.todoDao().insert(Todo(todo_edit.text.toString()))
            result_text.text = db.todoDao().getAll().toString()
        }


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}