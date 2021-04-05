package com.nerdscorner.android.view.utils.plugin.domain

class Unit(var value: String) {

    companion object {
        const val INCHES_FACTOR = 0.00625f
        const val DP_FACTOR = 1f
        const val PX_FACTOR = 1f
        const val PT_FACTOR = 0.45f
        const val MM_FACTOR = 0.15875f

        fun asArray(dimensionFactor: Float) = arrayOf(
                INCHES_FACTOR,
                DP_FACTOR,
                dimensionFactor,
                PT_FACTOR,
                MM_FACTOR
        )
    }
}
