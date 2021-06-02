package com.pill.what.popup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pill.what.R
import com.pill.what.adapter.FilterColorRvAdapter
import com.pill.what.data.FilterColor

class FilterPopup(val context: Context) {
    private var filterColorList: List<FilterColor> = listOf(
        FilterColor(ContextCompat.getColor(context, R.color.colorWhite), "하양"),
        FilterColor(ContextCompat.getColor(context, R.color.colorYellow), "노랑"),
        FilterColor(ContextCompat.getColor(context, R.color.colorOrange), "주황"),
        FilterColor(ContextCompat.getColor(context, R.color.colorPink), "분홍"),
        FilterColor(ContextCompat.getColor(context, R.color.colorRed), "빨강"),
        FilterColor(ContextCompat.getColor(context, R.color.colorBrown), "갈색"),
        FilterColor(ContextCompat.getColor(context, R.color.colorLightGreen), "연두"),
        FilterColor(ContextCompat.getColor(context, R.color.colorGreen), "초록"),
        FilterColor(ContextCompat.getColor(context, R.color.colorTurquoise), "청록"),
        FilterColor(ContextCompat.getColor(context, R.color.colorBlue), "파랑"),
        FilterColor(ContextCompat.getColor(context, R.color.colorIndigo), "남색"),
        FilterColor(ContextCompat.getColor(context, R.color.colorAmethyst), "자주"),
        FilterColor(ContextCompat.getColor(context, R.color.colorPurple), "보라"),
        FilterColor(ContextCompat.getColor(context, R.color.colorGray), "회색"),
        FilterColor(ContextCompat.getColor(context, R.color.colorBlack), "검정")
    )
    private var filteredColorList = mutableListOf<FilterColor>()

    fun showFilterPopup() : Boolean{
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.filter_popup, null)

        val alertDialog = AlertDialog.Builder(context)
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
        val lm = GridLayoutManager(context, 5)
        val mAdapter = FilterColorRvAdapter(filterColorList, filteredColorList)

        recyclerView.apply {
            layoutManager = lm
            adapter = mAdapter
            setHasFixedSize(true)
        }
    }
}