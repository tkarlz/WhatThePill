package com.pill.what.adapter

import android.content.Context
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pill.what.R
import com.pill.what.data.APIResultData

class PillImageListRvAdapter(val context: Context, private val result: ArrayList<APIResultData>, val itemClick: (APIResultData) -> Unit)
    : RecyclerView.Adapter<PillImageListRvAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.ex_pill_list_rv_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(result[position], context)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private val pillImage = itemView?.findViewById<ImageView>(R.id.pillImage)
        private val pillForm = itemView?.findViewById<TextView>(R.id.pillForm)
        private val pillLine = itemView?.findViewById<TextView>(R.id.pillLine)
        private val pillShape = itemView?.findViewById<TextView>(R.id.pillShape)
        private val pillColor = itemView?.findViewById<TextView>(R.id.pillColor)
        private val pillPrint = itemView?.findViewById<TextView>(R.id.pillPrint)

        fun bind (result: APIResultData, context: Context) {
            if (result.base64img != "") {
                Glide.with(context).load(Base64.decode(result.base64img, Base64.DEFAULT)).into(pillImage!!)
            } else {
                Glide.with(context)
                    .load(result.base64img)
                    .error(R.drawable.ic_launcher_foreground) // ex) error(R.drawable.error)
                    .into(pillImage!!)
            }

            val pill = PillDataAdapter().engToKor(result)
            pillForm?.text = pill.form
            pillLine?.text = pill.line
            pillShape?.text = pill.shape
            pillColor?.text = pill.colors.joinToString(separator = ", ")
            pillPrint?.text = pill.prints.joinToString(separator = ", ")

            itemView.setOnClickListener {
                itemClick(pill)
            }
        }
    }
}