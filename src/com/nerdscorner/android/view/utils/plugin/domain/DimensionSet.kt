package com.nerdscorner.android.view.utils.plugin.domain

import com.nerdscorner.android.view.utils.plugin.utils.DimensionUtils

class DimensionSet(value: String, base: Float) {
    private val ldpiDimension = DimensionUtils.convertValue(value, base, Dimension.LDPI_FACTOR)
    private val mdpiDimension = DimensionUtils.convertValue(value, base, Dimension.MDPI_FACTOR)
    private val hdpiDimension = DimensionUtils.convertValue(value, base, Dimension.HDPI_FACTOR)
    private val xhdpiDimension = DimensionUtils.convertValue(value, base, Dimension.XHDPI_FACTOR)
    private val xxhdpiDimension = DimensionUtils.convertValue(value, base, Dimension.XXHDPI_FACTOR)
    private val xxxhdpiDimension = DimensionUtils.convertValue(value, base, Dimension.XXXHDPI_FACTOR)
    private val tvdpiDimension = DimensionUtils.convertValue(value, base, Dimension.TVDPI_FACTOR)

    fun asArray() = arrayOf(
            ldpiDimension,
            mdpiDimension,
            hdpiDimension,
            xhdpiDimension,
            xxhdpiDimension,
            xxxhdpiDimension,
            tvdpiDimension
    )
}
