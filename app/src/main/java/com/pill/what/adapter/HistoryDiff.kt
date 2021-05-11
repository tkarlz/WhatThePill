package com.pill.what.adapter

import androidx.recyclerview.widget.DiffUtil
import com.pill.what.room.History

class HistoryDiff : DiffUtil.ItemCallback<History>() {
    override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
        return true
    }
}