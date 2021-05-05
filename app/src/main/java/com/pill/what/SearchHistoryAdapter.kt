package com.pill.what

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_history_item.view.*

class SearchHistoryAdapter(val itemClick: (String) -> Unit): ListAdapter<History, SearchHistoryAdapter.Holder>(HistoryDiff()) {

    private lateinit var mContext: Context
    lateinit var db: AppDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryAdapter.Holder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.search_history_item,parent,false)
        mContext = parent.context
        db = AppDatabase.getInstance(mContext)
        return Holder(v)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(history: History) {
            itemView.searchedText.text = history.name
            itemView.searchedTime.text = history.time
            itemView.deleteButton.setOnClickListener {
                db.historyDao().delete(history)
            }
            itemView.setOnClickListener {
                itemClick(history.name)
            }
        }
    }
}