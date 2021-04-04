package com.nerdscorner.android.view.utils.plugin.domain

import com.nerdscorner.android.view.utils.plugin.utils.DimensionUtils

class DimensionSet(value: String, base: Float) {
    private val ldpiDimension = DimensionUtils.convertValue(value, base, DimensionUtils.LDPI_FACTOR)
    private val mdpiDimension = DimensionUtils.convertValue(value, base, DimensionUtils.MDPI_FACTOR)
    private val hdpiDimension = DimensionUtils.convertValue(value, base, DimensionUtils.HDPI_FACTOR)
    private val xhdpiDimension = DimensionUtils.convertValue(value, base, DimensionUtils.XHDPI_FACTOR)
    private val xxhdpiDimension = DimensionUtils.convertValue(value, base, DimensionUtils.XXHDPI_FACTOR)
    private val xxxhdpiDimension = DimensionUtils.convertValue(value, base, DimensionUtils.XXXHDPI_FACTOR)
    private val tvdpiDimension = DimensionUtils.convertValue(value, base, DimensionUtils.TVDPI_FACTOR)

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
