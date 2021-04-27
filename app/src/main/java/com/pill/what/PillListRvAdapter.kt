package com.pill.what

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PillListRvAdapter(val context: Context, val pillList: List<PillInfo>, val itemClick: (PillInfo) -> Unit) :
    RecyclerView.Adapter<PillListRvAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.pill_list_rv_item, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return pillList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder?.bind(pillList[position], context)
    }

    inner class Holder(itemView: View?, itemClick: (PillInfo) -> Unit) : RecyclerView.ViewHolder(itemView!!) {
        val pillImage = itemView?.findViewById<ImageView>(R.id.pillImage)
        val pillNmae = itemView?.findViewById<TextView>(R.id.pillNameTv)
        val pillIngredient = itemView?.findViewById<TextView>(R.id.pillIngredientTv)
        val pillCompany = itemView?.findViewById<TextView>(R.id.pillCompanyTv)

        fun bind (pill: PillInfo, context: Context) {
            if (pill.image != "") {

                Glide.with(context).load(pill.image).into(pillImage!!)
                //}

               // val resourceId = context.resources.getIdentifier(pill.image, "drawable", context.packageName)
                //pillImage?.setImageResource(resourceId)
            } else {
                Glide.with(context)
                        .load(pill.image)
                        .error(R.drawable.ic_launcher_foreground) // ex) error(R.drawable.error)
                        .into(pillImage!!)
                //pillImage?.setImageResource(R.mipmap.ic_launcher)
            }
            pillNmae?.text = pill.name
            pillIngredient?.text = pill.ingredient
            pillCompany?.text = pill.company

            itemView.setOnClickListener { itemClick(pill) }
        }
    }
}