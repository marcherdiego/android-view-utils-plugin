package com.nerdscorner.android.view.utils.plugin.ui.mvp.presenter

import com.nerdscorner.android.view.utils.plugin.ui.mvp.model.UnitsPanelModel
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.UnitsPanelView
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.UnitsPanelView.UnitChangedEvent
import org.greenrobot.eventbus.Subscribe

class UnitsPanelPresenter(private val view: UnitsPanelView, private val model: UnitsPanelModel) {

    @Subscribe
    fun onUnitChanged(event: UnitChangedEvent) {

    }
}
