package com.example.whatthepill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PillListActivity : AppCompatActivity() {
    var pillList = arrayListOf<Pill>(
            Pill("약1", "제약사1", "성분", "ic_launcher_foreground"),
            Pill("약2", "제약사2", "성분", "ic_launcher_foreground"),
            Pill("약3", "제약사3", "성분", "ic_launcher_foreground"),
            Pill("약4", "제약사4", "성분", "ic_launcher_foreground"),
            Pill("약5", "제약사5", "성분", "ic_launcher_foreground"),
            Pill("약6", "제약사6", "성분", "ic_launcher_foreground"),
            Pill("약7", "제약사7", "성분", "ic_launcher_foreground")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_list)

        supportActionBar?.apply {
            title = " What The Pill"

            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.ic_baseline_medical_services_24)
        }

        val mAdapter = PillListRvAdapter(this, pillList)
        val rcv : RecyclerView = findViewById(R.id.mRecyclerView)
        rcv.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        rcv.layoutManager = lm
        rcv.setHasFixedSize(true)
    }
}