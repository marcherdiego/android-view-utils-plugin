package com.nerdscorner.android.view.utils.plugin.ui.mvp.view

import com.nerdscorner.android.view.utils.plugin.domain.Dimension
import com.nerdscorner.android.view.utils.plugin.domain.Unit
import com.nerdscorner.android.view.utils.plugin.domain.UnitSet
import com.nerdscorner.android.view.utils.plugin.utils.extensions.addTextListener
import com.nerdscorner.android.view.utils.plugin.utils.extensions.setTextNoNotify
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
    val ldpiRow = arrayOf(ldpiInches, ldpiDp, ldpiPx, ldpiPt, ldpiMm)
    val mdpiRow = arrayOf(mdpiInches, mdpiDp, mdpiPx, mdpiPt, mdpiMm)
    val hdpiRow = arrayOf(hdpiInches, hdpiDp, hdpiPx, hdpiPt, hdpiMm)
    val xhdpiRow = arrayOf(xhdpiInches, xhdpiDp, xhdpiPx, xhdpiPt, xhdpiMm)
    val xxhdpiRow = arrayOf(xxhdpiInches, xxhdpiDp, xxhdpiPx, xxhdpiPt, xxhdpiMm)
    val xxxhdpiRow = arrayOf(xxxhdpiInches, xxxhdpiDp, xxxhdpiPx, xxxhdpiPt, xxxhdpiMm)
    val tvdpiRow = arrayOf(tvdpiInches, tvdpiDp, tvdpiPx, tvdpiPt, tvdpiMm)
    val customRow = arrayOf(customInches, customDp, customPx, customPt, customMm)

    init {
        val units = Unit.asArray()
        ldpiRow.forEachIndexed { index, field ->
            field.addTextListener {
                bus.post(UnitChangedEvent(this, Dimension.LDPI_FACTOR, units[index]))
            }
        }
        mdpiRow.forEachIndexed { index, field ->
            field.addTextListener {
                bus.post(UnitChangedEvent(this, Dimension.MDPI_FACTOR, units[index]))
            }
        }
        hdpiRow.forEachIndexed { index, field ->
            field.addTextListener {
                bus.post(UnitChangedEvent(this, Dimension.HDPI_FACTOR, units[index]))
            }
        }
        xhdpiRow.forEachIndexed { index, field ->
            field.addTextListener {
                bus.post(UnitChangedEvent(this, Dimension.XHDPI_FACTOR, units[index]))
            }
        }
        xxhdpiRow.forEachIndexed { index, field ->
            field.addTextListener {
                bus.post(UnitChangedEvent(this, Dimension.XXHDPI_FACTOR, units[index]))
            }
        }
        xxxhdpiRow.forEachIndexed { index, field ->
            field.addTextListener {
                bus.post(UnitChangedEvent(this, Dimension.XXXHDPI_FACTOR, units[index]))
            }
        }
        tvdpiRow.forEachIndexed { index, field ->
            field.addTextListener {
                bus.post(UnitChangedEvent(this, Dimension.TVDPI_FACTOR, units[index]))
            }
        }
    }

    fun setRowValues(excludedField: JTextField, unitSet: UnitSet, row: Array<JTextField>) {
        val units = unitSet.asArray()
        row.forEachIndexed { index, field ->
            if (field == excludedField) {
                return@forEachIndexed
            }
            field.setTextNoNotify(units[index].value)
        }
    }

    class UnitChangedEvent(val field: JTextField, val dimension: Float, val unit: Float)
}