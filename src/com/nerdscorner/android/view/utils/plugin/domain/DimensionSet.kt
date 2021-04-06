package com.nerdscorner.android.view.utils.plugin.domain

import com.nerdscorner.android.view.utils.plugin.utils.DimensionUtils

class DimensionSet(value: String, base: Float) {
    val ldpiDimension = DimensionUtils.convertValue(value, base, Dimension.LDPI_FACTOR)
    val mdpiDimension = DimensionUtils.convertValue(value, base, Dimension.MDPI_FACTOR)
    val hdpiDimension = DimensionUtils.convertValue(value, base, Dimension.HDPI_FACTOR)
    val xhdpiDimension = DimensionUtils.convertValue(value, base, Dimension.XHDPI_FACTOR)
    val xxhdpiDimension = DimensionUtils.convertValue(value, base, Dimension.XXHDPI_FACTOR)
    val xxxhdpiDimension = DimensionUtils.convertValue(value, base, Dimension.XXXHDPI_FACTOR)
    val tvdpiDimension = DimensionUtils.convertValue(value, base, Dimension.TVDPI_FACTOR)
    val customDimension = DimensionUtils.convertValue(value, base, Dimension.customFactor)

    private val valuesArray = arrayOf(
            ldpiDimension,
            mdpiDimension,
            hdpiDimension,
            xhdpiDimension,
            xxhdpiDimension,
            xxxhdpiDimension,
            tvdpiDimension,
            customDimension
    )

    fun asArray() = valuesArray
}
