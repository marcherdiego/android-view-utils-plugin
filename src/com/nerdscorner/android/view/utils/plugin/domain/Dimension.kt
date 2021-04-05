package com.nerdscorner.android.view.utils.plugin.domain

import kotlin.math.roundToInt

class Dimension(inputDimension: Float = 0f) {
    var realDimension = ""
    var roundedDimension = ""

    init {
        if (inputDimension > 0f) {
            val scaledValueString = String.format("%.2f", inputDimension)
            val dimension = scaledValueString.toFloat()
            realDimension = if (dimension.compareTo(dimension.toInt()) == 0){
                dimension.toInt().toString()
            } else {
                scaledValueString
            }
            roundedDimension = dimension.roundToInt().toString()
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
    }
}
