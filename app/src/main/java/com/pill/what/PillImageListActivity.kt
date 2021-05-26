package com.pill.what

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pill.what.adapter.PillImageListRvAdapter
import com.pill.what.data.APIResultData

class PillImageListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_image_list)

        val resultList: ArrayList<APIResultData>? = intent.getParcelableArrayListExtra("apiResult")

        supportActionBar?.apply {
            title = " What The Pill"

            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.ic_baseline_medical_services_24)
        }

        val mAdapter = PillImageListRvAdapter(this, resultList!!) { result ->
            val nextIntent = Intent(this@PillImageListActivity, PillListActivity::class.java)
            nextIntent.putExtra("apiResult", result)
            startActivity(nextIntent)
        }

        val rcv: RecyclerView = findViewById(R.id.mRecyclerView)
        rcv.adapter = mAdapter

        rcv.layoutManager = LinearLayoutManager(this)
        rcv.setHasFixedSize(true)
    }
}