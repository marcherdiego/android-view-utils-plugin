package com.nerdscorner.android.view.utils.plugin.ui.mvp.model

import com.nerdscorner.android.view.utils.plugin.domain.Dimension
import com.nerdscorner.android.view.utils.plugin.domain.DimensionSet
import com.nerdscorner.android.view.utils.plugin.domain.Unit
import com.nerdscorner.android.view.utils.plugin.domain.UnitSet
import com.nerdscorner.android.view.utils.plugin.utils.UnitUtils
import org.greenrobot.eventbus.EventBus

class UnitsPanelModel(private val bus: EventBus) {

    fun convertToPx(value: String, sourceUnit: Float): String {
        return if (sourceUnit == Unit.PX_FACTOR) {
            value
        } else {
            getPxValue(value, sourceUnit)
        }
    }

    fun getDimensionSet(value: String, base: Float) = DimensionSet(value, base)

    fun getUnitSet(dimension: Dimension): UnitSet {
        val pxValue = convertToPx(dimension.realDimension, Unit.DP_FACTOR)
        return UnitSet(pxValue, dimension.factor, Unit.PX_FACTOR)
    }

    private fun getPxValue(value: String, sourceUnit: Float): String {
        return UnitUtils.convertValue(value, sourceUnit, Unit.PX_FACTOR).value
    }
}