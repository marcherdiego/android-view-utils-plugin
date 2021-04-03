package com.nerdscorner.android.view.utils.plugin.utils

import com.nerdscorner.android.view.utils.plugin.domain.Dimension
import javax.annotation.CheckReturnValue
import javax.annotation.Nonnull

object DimensionUtils {
    const val LDPI_FACTOR = 0.75f
    const val MDPI_FACTOR = 1.0f
    const val HDPI_FACTOR = 1.5f
    const val XHDPI_FACTOR = 2.0f
    const val XXHDPI_FACTOR = 3.0f
    const val XXXHDPI_FACTOR = 4.0f
    const val TVDPI_FACTOR = 1.33f

    @JvmStatic
    @Nonnull
    @CheckReturnValue
    fun convertValue(value: String?, originalFactor: Float, targetFactor: Float): Dimension {
        return Dimension(
                try {
                    val originalValue = java.lang.Float.valueOf(value)
                    originalValue * (targetFactor / originalFactor)
                } catch (e: Exception) {
                    0f
                }
        )
    }
}
