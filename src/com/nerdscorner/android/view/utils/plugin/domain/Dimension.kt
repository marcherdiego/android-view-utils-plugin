package com.nerdscorner.android.view.utils.plugin.domain

import kotlin.math.roundToInt

class Dimension(inputDimension: Float) {
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
}
