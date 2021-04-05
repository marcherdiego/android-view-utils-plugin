package com.nerdscorner.android.view.utils.plugin.ui.mvp.view

import com.nerdscorner.android.view.utils.plugin.domain.Dimension
import com.nerdscorner.android.view.utils.plugin.domain.Unit
import com.nerdscorner.android.view.utils.plugin.utils.extensions.addTextListener
import org.greenrobot.eventbus.EventBus
import javax.swing.JTextField

class UnitsPanelView(
        ldpiInches: JTextField,
        ldpiDp: JTextField,
        ldpiPx: JTextField,
        ldpiPt: JTextField,
        ldpiMm: JTextField,
        mdpiInches: JTextField,
        mdpiDp: JTextField,
        mdpiPx: JTextField,
        mdpiPt: JTextField,
        mdpiMm: JTextField,
        hdpiInches: JTextField,
        hdpiDp: JTextField,
        hdpiPx: JTextField,
        hdpiPt: JTextField,
        hdpiMm: JTextField,
        xhdpiInches: JTextField,
        xhdpiDp: JTextField,
        xhdpiPx: JTextField,
        xhdpiPt: JTextField,
        xhdpiMm: JTextField,
        xxhdpiInches: JTextField,
        xxhdpiDp: JTextField,
        xxhdpiPx: JTextField,
        xxhdpiPt: JTextField,
        xxhdpiMm: JTextField,
        xxxhdpiInches: JTextField,
        xxxhdpiDp: JTextField,
        xxxhdpiPx: JTextField,
        xxxhdpiPt: JTextField,
        xxxhdpiMm: JTextField,
        tvdpiInches: JTextField,
        tvdpiDp: JTextField,
        tvdpiPx: JTextField,
        tvdpiPt: JTextField,
        tvdpiMm: JTextField,
        private val customDensity: JTextField,
        customInches: JTextField,
        customDp: JTextField,
        customPx: JTextField,
        customPt: JTextField,
        customMm: JTextField,
        private val bus: EventBus
) {
    private val ldpiRow = arrayOf(ldpiInches, ldpiDp, ldpiPx, ldpiPt, ldpiMm)
    private val mdpiRow = arrayOf(mdpiInches, mdpiDp, mdpiPx, mdpiPt, mdpiMm)
    private val hdpiRow = arrayOf(hdpiInches, hdpiDp, hdpiPx, hdpiPt, hdpiMm)
    private val xhdpiRow = arrayOf(xhdpiInches, xhdpiDp, xhdpiPx, xhdpiPt, xhdpiMm)
    private val xxhdpiRow = arrayOf(xxhdpiInches, xxhdpiDp, xxhdpiPx, xxhdpiPt, xxhdpiMm)
    private val xxxhdpiRow = arrayOf(xxxhdpiInches, xxxhdpiDp, xxxhdpiPx, xxxhdpiPt, xxxhdpiMm)
    private val tvdpiRow = arrayOf(tvdpiInches, tvdpiDp, tvdpiPx, tvdpiPt, tvdpiMm)
    private val customRow = arrayOf(customInches, customDp, customPx, customPt, customMm)

    init {
        ldpiInches.addTextListener {
            bus.post(UnitChangedEvent(this, Dimension.LDPI_FACTOR, Unit.INCHES_FACTOR))
        }
    }

    class UnitChangedEvent(field: JTextField, dimension: Float, unit: Float)
}