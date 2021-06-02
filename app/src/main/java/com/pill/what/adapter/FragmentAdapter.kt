package com.pill.what.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.pill.what.pager.TextViewPager
import com.pill.what.pager.WebViewPager
import com.pill.what.data.DetailedData

class FragmentAdapter (fm : FragmentManager, val data: DetailedData): FragmentPagerAdapter(fm) {
    //position 에 따라 원하는 Fragment로 이동시키기
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TextViewPager(data).newInstant()
            1 -> WebViewPager(data.efficacy, getPageTitle(position).toString()).newInstant()
            2 -> WebViewPager(data.drugUsage, getPageTitle(position).toString()).newInstant()
            3 -> WebViewPager(data.medicationInfo, getPageTitle(position).toString()).newInstant()
            4 -> WebViewPager(data.precautions, getPageTitle(position).toString()).newInstant()
            else -> TextViewPager(data).newInstant()
        }
    }

    //tab의 개수만큼 return
    override fun getCount(): Int = 5

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "기본정보"
            1 -> "효능·효과"
            2 -> "용법·용량"
            3 -> "복약정보"
            4 -> "사용상의 주의사항"
            else -> "main"
        }
    }
}