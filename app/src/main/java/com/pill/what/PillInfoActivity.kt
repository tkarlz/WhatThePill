package com.pill.what

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.pill.what.adapter.FragmentAdapter
import com.pill.what.data.DetailedData
import kotlinx.android.synthetic.main.activity_pill_info.*

class PillInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pill_info)

        supportActionBar?.apply {
            title = " What The Pill"

            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.ic_icon)
        }

        val data = intent.getParcelableExtra<DetailedData>("data")!!

        // tablayout & viewpager
        val pagerAdapter = FragmentAdapter(supportFragmentManager, data)

        val pager = findViewById<ViewPager>(R.id.viewPager)
        pager.adapter = pagerAdapter

        val tab = findViewById<TabLayout>(R.id.tab)
        tab.setupWithViewPager(pager)

        Glide.with(applicationContext).load(data.drugImage).into(drugImage)
    }
}
