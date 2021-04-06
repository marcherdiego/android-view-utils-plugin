package com.nerdscorner.android.view.utils.plugin.ui.mvp.presenter

import com.nerdscorner.android.view.utils.plugin.domain.Dimension
import com.nerdscorner.android.view.utils.plugin.ui.mvp.model.UnitsPanelModel
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.UnitsPanelView
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.UnitsPanelView.CustomDensityChangedEvent
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.UnitsPanelView.RoundValuesClickedEvent
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.UnitsPanelView.UnitChangedEvent
import org.greenrobot.eventbus.Subscribe

class UnitsPanelPresenter(private val view: UnitsPanelView, private val model: UnitsPanelModel) {

    @Subscribe
    fun onUnitChanged(event: UnitChangedEvent) {
        val field = event.field
        val dimension = event.dimension
        val unit = event.unit

        val inputPx = model.convertToPx(field.text, unit)
        val dimensionSet = model.getDimensionSet(inputPx, dimension)

        val ldpiUnitSet = model.getUnitSet(dimensionSet.ldpiDimension)
        view.setRowValues(field, ldpiUnitSet, view.ldpiRow)

        val mdpiUnitSet = model.getUnitSet(dimensionSet.mdpiDimension)
        view.setRowValues(field, mdpiUnitSet, view.mdpiRow)

        val hdpiUnitSet = model.getUnitSet(dimensionSet.hdpiDimension)
        view.setRowValues(field, hdpiUnitSet, view.hdpiRow)

        val xhdpiUnitSet = model.getUnitSet(dimensionSet.xhdpiDimension)
        view.setRowValues(field, xhdpiUnitSet, view.xhdpiRow)

        val xxhdpiUnitSet = model.getUnitSet(dimensionSet.xxhdpiDimension)
        view.setRowValues(field, xxhdpiUnitSet, view.xxhdpiRow)

        val xxxhdpiUnitSet = model.getUnitSet(dimensionSet.xxxhdpiDimension)
        view.setRowValues(field, xxxhdpiUnitSet, view.xxxhdpiRow)

        val tvdpiUnitSet = model.getUnitSet(dimensionSet.tvdpiDimension)
        view.setRowValues(field, tvdpiUnitSet, view.tvdpiRow)

        val customUnitSet = model.getUnitSet(dimensionSet.customDimension)
        view.setRowValues(field, customUnitSet, view.customRow)
    }

    @Subscribe
    fun onCustomDensityChanged(event: CustomDensityChangedEvent) {
        Dimension.setCustomFactor(event.value)
    }

    @Subscribe
    fun onRoundValuesClicked(event: RoundValuesClickedEvent) {
        model.roundValues = event.checked
    }
}
