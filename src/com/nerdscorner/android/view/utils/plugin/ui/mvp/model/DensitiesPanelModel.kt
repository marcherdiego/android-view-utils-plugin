package com.nerdscorner.android.view.utils.plugin.ui.mvp.model

import com.nerdscorner.android.view.utils.plugin.domain.DimensionSet
import org.greenrobot.eventbus.EventBus

class DensitiesPanelModel(private val bus: EventBus) {
    fun getDimensionSet(text: String, base: Float) = DimensionSet(text, base)
}