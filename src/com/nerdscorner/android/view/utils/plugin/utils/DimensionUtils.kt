package com.nerdscorner.android.view.utils.plugin.utils

import com.nerdscorner.android.view.utils.plugin.domain.Dimension
import javax.annotation.CheckReturnValue
import javax.annotation.Nonnull

object DimensionUtils {
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
