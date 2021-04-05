package com.nerdscorner.android.view.utils.plugin.domain

import com.nerdscorner.android.view.utils.plugin.utils.UnitUtils

class UnitSet(pxValue: String) {
    val inches = UnitUtils.convertValue(pxValue, Unit.INCHES_FACTOR)
    val dp = UnitUtils.convertValue(pxValue, Unit.DP_FACTOR)
    val px = UnitUtils.convertValue(pxValue,  Unit.PX_FACTOR)
    val pt = UnitUtils.convertValue(pxValue, Unit.PT_FACTOR)
    val mm = UnitUtils.convertValue(pxValue, Unit.MM_FACTOR)

    fun asArray() = arrayOf(inches, dp, px, pt, mm)
}
