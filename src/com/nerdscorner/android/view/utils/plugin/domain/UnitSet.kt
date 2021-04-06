package com.nerdscorner.android.view.utils.plugin.domain

import com.nerdscorner.android.view.utils.plugin.utils.UnitUtils

class UnitSet(pxValue: String, dimensionFactor: Float, sourceFactor: Float) {
    val inches = UnitUtils.convertValue(pxValue, sourceFactor, Unit.INCHES_FACTOR)
    val dp = UnitUtils.convertValue(pxValue, sourceFactor, Unit.DP_FACTOR)
    val px = UnitUtils.convertValue(pxValue, sourceFactor / dimensionFactor, Unit.PX_FACTOR)
    val pt = UnitUtils.convertValue(pxValue, sourceFactor, Unit.PT_FACTOR)
    val mm = UnitUtils.convertValue(pxValue, sourceFactor, Unit.MM_FACTOR)

    private val valuesArray = arrayOf(inches, dp, px, pt, mm)

    fun asArray() = valuesArray
}
