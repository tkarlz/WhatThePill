package com.pill.what

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pill.what.GlobalVariable.Companion.pillInfo
import kotlinx.android.synthetic.main.activity_search.*


class PillListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_list)

        val intent = getIntent();
        val data = intent.getStringExtra("name")

        supportActionBar?.apply {
            title = " What The Pill"

            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.ic_baseline_medical_services_24)

        }


        val pillList = pillInfo.filter{ it.name.contains(data?.trim()!!)}
        val mAdapter = PillListRvAdapter(this, pillList) { pill ->
            val nextIntent = Intent(this@PillListActivity, PillInfoActivity::class.java)
            nextIntent.putExtra("code", pill.code)
            startActivity(nextIntent)
        }
        val rcv : RecyclerView = findViewById(R.id.mRecyclerView)
        rcv.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        rcv.layoutManager = lm
        rcv.setHasFixedSize(true)
    }
}