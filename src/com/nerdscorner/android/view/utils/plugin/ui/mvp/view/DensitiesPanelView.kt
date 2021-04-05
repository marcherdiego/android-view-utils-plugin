package com.nerdscorner.android.view.utils.plugin.ui.mvp.view

import com.nerdscorner.android.view.utils.plugin.domain.DimensionSet
import com.nerdscorner.android.view.utils.plugin.utils.extensions.addTextListener
import com.nerdscorner.android.view.utils.plugin.utils.extensions.setTextNoNotify
import org.greenrobot.eventbus.EventBus
import javax.swing.JLabel
import javax.swing.JTextField

class DensitiesPanelView(
        ldpi: JTextField,
        mdpi: JTextField,
        hdpi: JTextField,
        xhdpi: JTextField,
        xxhdpi: JTextField,
        xxxhdpi: JTextField,
        tvdpi: JTextField,
        ldpiRoundedValue: JLabel,
        mdpiRoundedValue: JLabel,
        hdpiRoundedValue: JLabel,
        xhdpiRoundedValue: JLabel,
        xxhdpiRoundedValue: JLabel,
        xxxhdpiRoundedValue: JLabel,
        tvdpiRoundedValue: JLabel,
        private val bus: EventBus
) {

    private val allFields = arrayOf(ldpi, mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi, tvdpi)
    private val allFieldsRoundedValues = arrayOf(
            ldpiRoundedValue, mdpiRoundedValue, hdpiRoundedValue, xhdpiRoundedValue, xxhdpiRoundedValue, xxxhdpiRoundedValue,
            tvdpiRoundedValue
    )

    init {
        ldpi.addTextListener {
            bus.post(LdpiTextChangedEvent(this))
        }
        mdpi.addTextListener {
            bus.post(MdpiTextChangedEvent(this))
        }
        hdpi.addTextListener {
            bus.post(HdpiTextChangedEvent(this))
        }
        xhdpi.addTextListener {
            bus.post(XhdpiTextChangedEvent(this))
        }
        xxhdpi.addTextListener {
            bus.post(XxhdpiTextChangedEvent(this))
        }
        xxxhdpi.addTextListener {
            bus.post(XxxhdpiTextChangedEvent(this))
        }
        tvdpi.addTextListener {
            bus.post(TvdpiTextChangedEvent(this))
        }
    }

    fun setAllExcept(excludedField: JTextField, dimensionSet: DimensionSet) {
        val dimensions = dimensionSet.asArray()
        allFields.forEachIndexed { index, field ->
            val dimension = dimensions[index]
            allFieldsRoundedValues[index].text = dimension.roundedDimension
            if (field == excludedField) {
                return@forEachIndexed
            }
            field.setTextNoNotify(dimension.realDimension)
        }
    }

    class LdpiTextChangedEvent(val field: JTextField)
    class MdpiTextChangedEvent(val field: JTextField)
    class HdpiTextChangedEvent(val field: JTextField)
    class XhdpiTextChangedEvent(val field: JTextField)
    class XxhdpiTextChangedEvent(val field: JTextField)
    class XxxhdpiTextChangedEvent(val field: JTextField)
    class TvdpiTextChangedEvent(val field: JTextField)
}