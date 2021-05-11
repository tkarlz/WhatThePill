package com.pill.what.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pill.what.R
import com.pill.what.data.DetailedData

class TextViewPager(val data: DetailedData) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_textview, container, false)
        val drugName: TextView = view.findViewById(R.id.drugName)
        val drugIngredient: TextView = view.findViewById(R.id.drugIngredient)
        val additives: TextView = view.findViewById(R.id.additives)
        val professional: TextView = view.findViewById(R.id.professional)
        val drugCompany: TextView = view.findViewById(R.id.drugCompany)
        val drugForm: TextView = view.findViewById(R.id.drugForm)
        val howEat: TextView = view.findViewById(R.id.howEat)
        val appearance: TextView = view.findViewById(R.id.appearance)
        val classification: TextView = view.findViewById(R.id.classification)
        val storeMethod: TextView = view.findViewById(R.id.storeMethod)
        drugName.text = data.drugName
        drugIngredient.text = data.drugIngredient
        additives.text = data.additives
        professional.text = data.professional
        drugCompany.text = data.drugCompany
        drugForm.text = data.drugForm
        howEat.text = data.howEat
        appearance.text = data.appearance
        classification.text = data.classification
        storeMethod.text = data.storeMethod
        return view
    }
    fun newInstant() : TextViewPager
    {
        val args = Bundle()
        val frag = TextViewPager(data)
        frag.arguments = args
        return frag
    }
}