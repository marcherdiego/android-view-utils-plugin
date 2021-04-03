package com.nerdscorner.android.view.utils.plugin.ui.mvp.presenter

import com.nerdscorner.android.view.utils.plugin.ui.mvp.model.DimensionsWindowModel
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DimensionsWindowView
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DimensionsWindowView.LdpiTextChangedEvent
import com.nerdscorner.android.view.utils.plugin.utils.DimensionUtils
import org.greenrobot.eventbus.Subscribe

class DimensionsWindowPresenter(private val view: DimensionsWindowView, private val model: DimensionsWindowModel) {

    @Subscribe
    fun onLdpiTextChanged(event: LdpiTextChangedEvent) {
        val mdpiDimension = DimensionUtils.convertValue(event.value, DimensionUtils.LDPI_FACTOR, DimensionUtils.MDPI_FACTOR)
        view.setMdpiValue(mdpiDimension.realDimension)
    }
}