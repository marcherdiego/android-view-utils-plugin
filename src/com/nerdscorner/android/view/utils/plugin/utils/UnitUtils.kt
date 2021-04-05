package com.nerdscorner.android.view.utils.plugin.utils

import com.nerdscorner.android.view.utils.plugin.domain.Unit
import java.lang.Exception
import javax.annotation.CheckReturnValue
import javax.annotation.Nonnull

object UnitUtils {
    @Nonnull
    @CheckReturnValue
    fun convertValue(pxValue: String, targetUnit: Float): Unit {
        return try {
            val unitValue = pxValue.toFloat() * targetUnit
            val unitStringValue = if (unitValue.compareTo(unitValue.toInt()) == 0) {
                unitValue.toInt().toString()
            } else {
                String.format("%.2f", unitValue)
            }
            Unit(unitStringValue)
        } catch (_: Exception) {
            Unit("0")
        }
    }
}
