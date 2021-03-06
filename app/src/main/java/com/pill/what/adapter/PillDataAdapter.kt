package com.pill.what.adapter

import com.pill.what.data.APIResultData
import com.pill.what.data.PillData

class PillDataAdapter {
    fun engToKor(data: APIResultData): APIResultData {
        val newData: APIResultData = data
        newData.form = when(data.form) {
            "tablet" -> "정제"
            "hard capsule" -> "경질캡슐"
            "soft capsule" -> "연질캡슐"
            else -> "기타"
        }
        newData.line = when(data.line) {
            "plus" -> "'+'형"
            "minus" -> "'-'형"
            else -> "없음"
        }
        newData.shape = when(data.shape) {
            "circle" -> "원형"
            "oval" -> "타원형"
            "oblong" -> "장방형"
            "semicircle" -> "반원형"
            "triangle" -> "삼각형"
            "rectangle" -> "사각형"
            "rhombus" -> "마름모형"
            "pentagon" -> "오각형"
            "hexagon" -> "육각형"
            "octagon" -> "팔각형"
            else -> "기타"
        }
        newData.colors = ArrayList(data.colors.map {
            when(it) {
                "white" -> "하양"
                "yellow" -> "노랑"
                "orange" -> "주황"
                "pink" -> "분홍"
                "red" -> "빨강"
                "brown" -> "갈색"
                "light green" -> "연두"
                "green" -> "초록"
                "teal" -> "청록"
                "blue" -> "파랑"
                "indigo" -> "남색"
                "purple" -> "자주"
                "violet" -> "보라"
                "grey" -> "회색"
                "black" -> "검정"
                else -> ""
            }
        }.filter { it.isNotBlank() })
        newData.prints = ArrayList(data.prints.filter {
            it.isNotBlank()
        })

        return newData
    }

    fun colorCompare(data: PillData, result: APIResultData?): Boolean {
        var isContains = true
        val dataColor = if (data.color2.isNotBlank()) {
            "${data.color1}, ${data.color2}"
        } else {
            data.color1
        }
        result?.colors?.forEach {
            if(!dataColor.contains(it!!)) {
                isContains = false
                return@forEach
            }
        }

        return isContains
    }
    fun printCompare(data: PillData, result: APIResultData?): Boolean {
        var isContains = false
        result?.prints?.forEach {
            if (data.print_front.replace("\\[.*]".toRegex(), "").contains(it.replace(" ", "")) ||
                data.print_back.replace("\\[.*]".toRegex(), "").contains(it.replace(" ", ""))
            ) {
                isContains = true
                return@forEach
            }
        }

        if(result?.prints == null || result.prints.size < 1) isContains=true
        return isContains
    }
}