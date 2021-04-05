package com.nerdscorner.android.view.utils.plugin.utils.extensions

fun Float.roundToString(digits: Int = 2): String {
    val valueString = String.format("%.${digits}f", this)
    val floatValue = valueString.toFloat()
    return if (floatValue.compareTo(floatValue.toInt()) == 0) {
        floatValue.toInt().toString()
    } else {
        valueString
    }
}
