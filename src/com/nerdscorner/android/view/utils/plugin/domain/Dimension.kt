package com.nerdscorner.android.view.utils.plugin.domain

import kotlin.math.roundToInt

class Dimension(realDimension: Float) {
    var realDimension = ""
    var roundedDimension = ""

    init {
        if (realDimension > 0f) {
            val scaledValueString = String.format("%.2f", realDimension)
            val realDimension = scaledValueString.toFloat()
            this.realDimension = if (realDimension.compareTo(realDimension.toInt()) == 0){
                realDimension.toInt().toString()
            } else {
                scaledValueString
            }
            roundedDimension = realDimension.roundToInt().toString()
        }
    }
}
