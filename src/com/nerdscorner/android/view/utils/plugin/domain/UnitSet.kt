package com.nerdscorner.android.view.utils.plugin.domain

import com.nerdscorner.android.view.utils.plugin.utils.UnitUtils

class UnitSet(value: String, base: Float) {
    private val inches = UnitUtils.convertValue(value, base, Unit.INCHES_FACTOR)
    private val dp = UnitUtils.convertValue(value, base, Unit.DP_FACTOR)
    private val px = UnitUtils.convertValue(value, base, Unit.PX_FACTOR)
    private val pt = UnitUtils.convertValue(value, base, Unit.PT_FACTOR)
    private val mm = UnitUtils.convertValue(value, base, Unit.MM_FACTOR)

    fun asArray() = arrayOf(inches, dp, px, pt, mm)
}
