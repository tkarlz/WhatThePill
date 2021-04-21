package com.example.whatthepill

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PillListRvAdapter(val context: Context, val pillList: ArrayList<Pill>) :
    RecyclerView.Adapter<PillListRvAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.pill_list_rv_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return pillList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(pillList[position], context)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val pillPhoto = itemView?.findViewById<ImageView>(R.id.pillPhotoImg)
        val pillNmae = itemView?.findViewById<TextView>(R.id.pillNameTv)
        val pillIngredient = itemView?.findViewById<TextView>(R.id.pillIngredientTv)
        val pillPharmacist = itemView?.findViewById<TextView>(R.id.pillPharmacistTv)

        fun bind (pill: Pill, context: Context) {
            if (pill.photo != "") {
                val resourceId = context.resources.getIdentifier(pill.photo, "drawable", context.packageName)
                pillPhoto?.setImageResource(resourceId)
            } else {
                pillPhoto?.setImageResource(R.mipmap.ic_launcher)
            }
            pillNmae?.text = pill.pill_name
            pillIngredient?.text = pill.ingredient
            pillPharmacist?.text = pill.pharmacist
        }
    }

}