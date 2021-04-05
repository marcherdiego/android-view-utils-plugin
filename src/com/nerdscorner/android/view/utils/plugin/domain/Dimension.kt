package com.nerdscorner.android.view.utils.plugin.domain

import com.nerdscorner.android.view.utils.plugin.utils.extensions.roundToString
import kotlin.math.roundToInt

class Dimension(val factor: Float, inputDimension: Float = 0f) {
    var realDimension = ""
    var roundedDimension = ""

    init {
        if (inputDimension > 0f) {
            realDimension = inputDimension.roundToString()
            roundedDimension = inputDimension.roundToInt().toString()
        }
    }

    companion object {
        const val LDPI_FACTOR = 0.75f
        const val MDPI_FACTOR = 1.0f
        const val HDPI_FACTOR = 1.5f
        const val XHDPI_FACTOR = 2.0f
        const val XXHDPI_FACTOR = 3.0f
        const val XXXHDPI_FACTOR = 4.0f
        const val TVDPI_FACTOR = 1.33f

        const val MDPI = 160f
        var customFactor = 1f

        fun setCustomFactor(value: String): Float {
            customFactor = try {
                value.toFloat() / MDPI
            } catch (_: Exception) {
                1f
            }
            return customFactor
        }
    }
}
