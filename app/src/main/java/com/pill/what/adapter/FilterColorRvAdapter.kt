package com.pill.what.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pill.what.FilterColor
import com.pill.what.R
import com.pill.what.data.PillInfo

class FilterColorRvAdapter(private val filterColorList: List<FilterColor>, private val filteredColorList: MutableList<FilterColor>) :
    RecyclerView.Adapter<FilterColorRvAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.filter_color_rv_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return filterColorList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(filterColorList[position], filteredColorList)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val filterColorToggleButton: ToggleButton = itemView.findViewById(R.id.filterColorToggleButton)
        private val filterColorBorderLayout: ConstraintLayout = itemView.findViewById(R.id.filterColorBorderLayout)

        fun bind (filterColor: FilterColor, filteredColorList: MutableList<FilterColor>) {
            if (filterColor.name == "하양" || filterColor.name == "노랑" || filterColor.name == "분홍" || filterColor.name == "주황" ||
                filterColor.name == "연두" || filterColor.name == "초록"||filterColor.name == "청록"){
                filterColorToggleButton.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorBlack))
            }

            if (isFiltered(filterColor)) {
                filterColorBorderLayout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
                filterColorToggleButton.isChecked = true
            }

            filterColorToggleButton.setBackgroundColor(filterColor.color)
            filterColorToggleButton.textOn = filterColor.name
            filterColorToggleButton.textOff = filterColor.name
            filterColorToggleButton.text = filterColor.name

            filterColorToggleButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    filterColorBorderLayout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
                    filteredColorList.add(filterColor)
                } else {
                    filterColorBorderLayout.background = null
                    filteredColorList.remove(filterColor)
                }
            }
        }
        private fun isFiltered(filterColor: FilterColor): Boolean {
            return filteredColorList.any { filterColor == it }
        }
    }
}