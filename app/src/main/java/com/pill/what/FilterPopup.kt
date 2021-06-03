package com.pill.what

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pill.what.adapter.FilterColorRvAdapter
import com.pill.what.data.APIResultData
import com.pill.what.data.FilterColor
import java.lang.Exception

class FilterPopup(val activity: Activity, private val loadedPillData : APIResultData?) {
    private lateinit var view: View

    private var filterColorList: List<FilterColor> = listOf(
        FilterColor(ContextCompat.getColor(activity, R.color.colorWhite), "하양"),
        FilterColor(ContextCompat.getColor(activity, R.color.colorYellow), "노랑"),
        FilterColor(ContextCompat.getColor(activity, R.color.colorOrange), "주황"),
        FilterColor(ContextCompat.getColor(activity, R.color.colorPink), "분홍"),
        FilterColor(ContextCompat.getColor(activity, R.color.colorRed), "빨강"),
        FilterColor(ContextCompat.getColor(activity, R.color.colorBrown), "갈색"),
        FilterColor(ContextCompat.getColor(activity, R.color.colorLightGreen), "연두"),
        FilterColor(ContextCompat.getColor(activity, R.color.colorGreen), "초록"),
        FilterColor(ContextCompat.getColor(activity, R.color.colorTurquoise), "청록"),
        FilterColor(ContextCompat.getColor(activity, R.color.colorBlue), "파랑"),
        FilterColor(ContextCompat.getColor(activity, R.color.colorIndigo), "남색"),
        FilterColor(ContextCompat.getColor(activity, R.color.colorAmethyst), "자주"),
        FilterColor(ContextCompat.getColor(activity, R.color.colorPurple), "보라"),
        FilterColor(ContextCompat.getColor(activity, R.color.colorGray), "회색"),
        FilterColor(ContextCompat.getColor(activity, R.color.colorBlack), "검정")
    )
    private var filteredColorList = mutableListOf<FilterColor>()

    fun showFilterPopup() : Boolean{
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.filter_popup, null)


        val alertDialog = AlertDialog.Builder(activity)
            .setTitle("필터 검색")
            .create()

        val prevList = filteredColorList.toMutableList()

        val formulationSpinner: Spinner = initFilterSpinner(R.id.formulationSpinner, R.array.formulation)
        val shapeSpinner: Spinner = initFilterSpinner(R.id.shapeSpinner, R.array.shape)
        val dividingLineSpinner: Spinner = initFilterSpinner(R.id.dividingLineSpinner, R.array.dividing_line)

        val prints : EditText = view.findViewById(R.id.prints)
        prints.setText(loadedPillData?.prints?.joinToString(", "))

        val butSave = view.findViewById<Button>(R.id.searchFilter)
        initRecyclerView(view)
        butSave.setOnClickListener {
            val nextIntent = Intent(activity, PillListActivity::class.java)
            nextIntent.putExtra("apiResult", APIResultData(
                formulationSpinner.selectedItem.toString(),
                dividingLineSpinner.selectedItem.toString(),
                shapeSpinner.selectedItem.toString(),
                ArrayList(filteredColorList.map { it.name }),
                ArrayList(prints.text.split(", ")),
                ""
            ))
            activity.finish()
            activity.startActivity(nextIntent)

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
        val lm = GridLayoutManager(activity, 5)
        val mAdapter = FilterColorRvAdapter(filterColorList, filteredColorList, loadedPillData)

        recyclerView.apply {
            layoutManager = lm
            adapter = mAdapter
            setHasFixedSize(true)
        }


    }

    private fun initFilterSpinner(spinnerId : Int, arrayId : Int): Spinner {
        val spinner: Spinner = view.findViewById(spinnerId)
        ArrayAdapter.createFromResource(
            activity,
            arrayId,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = this
            spinner.setSelection(getPosition(spinnerId))
        }

        return spinner
    }
    private fun getPosition(spinnerId: Int): Int {
        if (loadedPillData == null) return 0

        val result = when(spinnerId) {
            R.id.formulationSpinner -> {
                activity.resources.getStringArray(R.array.formulation).indexOf(loadedPillData.form)
            }
            R.id.shapeSpinner -> {
                activity.resources.getStringArray(R.array.shape).indexOf(loadedPillData.shape)
            }
            R.id.dividingLineSpinner -> {
                activity.resources.getStringArray(R.array.dividing_line).indexOf(loadedPillData.line)
            }
            else -> throw Exception()
        }
        return result
    }
}