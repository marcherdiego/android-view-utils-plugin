package com.nerdscorner.android.view.utils.plugin.utils

import com.nerdscorner.android.view.utils.plugin.domain.Unit
import com.nerdscorner.android.view.utils.plugin.utils.extensions.roundToString
import java.lang.Exception
import javax.annotation.CheckReturnValue
import javax.annotation.Nonnull

object UnitUtils {
    @Nonnull
    @CheckReturnValue
    fun convertValue(pxValue: String, sourceUnit: Float, targetUnit: Float): Unit {
        return try {
            val unitValue = pxValue.toFloat() * (targetUnit / sourceUnit)
            Unit(unitValue.roundToString())
        } catch (_: Exception) {
            Unit("0")
        }
    }
}
