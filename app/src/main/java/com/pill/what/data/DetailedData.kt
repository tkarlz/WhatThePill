package com.pill.what.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailedData(
    var drugName: String,  // 제품명
    var drugImage: String,  // 이미지 링크
    var drugIngredient: String, // 성분 / 함량
    var additives: String, // 첨가제
    var professional: String, // 전문/일반
    var drugCompany: String, // 제조/수입
    var drugForm: String, // 제형
    var howEat: String, // 투여경로
    var appearance: String, // 성상
    var classification: String, // 식약처 분류
    var storeMethod: String, // 저장방법
    var efficacy: String,  // 효능 효과
    var drugUsage: String,  // 용법 용량
    var precautions: String, // 사용상의 주의사항
    var medicationInfo: String // 복약정보
) : Parcelable