package com.nerdscorner.android.view.utils.plugin.ui.mvp.view

import com.nerdscorner.android.view.utils.plugin.ui.DimensionsWindow
import com.nerdscorner.android.view.utils.plugin.utils.DimensionUtils
import com.nerdscorner.android.view.utils.plugin.utils.DimensionUtils.convertValue
import com.nerdscorner.android.view.utils.plugin.utils.SimpleTextFieldDocumentListener
import com.nerdscorner.android.view.utils.plugin.utils.TextFieldDocumentListener
import com.nerdscorner.android.view.utils.plugin.utils.TextFieldDocumentListener.Companion.setEnabled
import com.nerdscorner.android.view.utils.plugin.utils.extensions.addTextListener
import org.greenrobot.eventbus.EventBus
import javax.swing.JLabel
import javax.swing.JTextField

class DimensionsWindowView(
        private val ldpi: JTextField,
        private val mdpi: JTextField,
        private val hdpi: JTextField,
        private val xhdpi: JTextField,
        private val xxhdpi: JTextField,
        private val xxxhdpi: JTextField,
        private val tvdpi: JTextField,
        private val ldpiRoundedValue: JLabel,
        private val mdpiRoundedValue: JLabel,
        private val hdpiRoundedValue: JLabel,
        private val xhdpiRoundedValue: JLabel,
        private val xxhdpiRoundedValue: JLabel,
        private val xxxhdpiRoundedValue: JLabel,
        private val tvdpiRoundedValue: JLabel,
        private val bus: EventBus
) {

    init {
        initClickListeners()
    }

    private fun initClickListeners() {
        //Set listeners for all except for mdpi, which is the base case
        ldpi.addTextListener(listener = {
            bus.post(LdpiTextChangedEvent(ldpi.text))
        })

        hdpi
                .document
                .addDocumentListener(
                        TextFieldDocumentListener(
                                hdpi, mdpi, DimensionUtils.HDPI_FACTOR, DimensionUtils.MDPI_FACTOR
                        )
                )
        xhdpi
                .document
                .addDocumentListener(
                        TextFieldDocumentListener(
                                xhdpi, mdpi, DimensionUtils.XHDPI_FACTOR, DimensionUtils.MDPI_FACTOR
                        )
                )
        xxhdpi
                .document
                .addDocumentListener(
                        TextFieldDocumentListener(
                                xxhdpi, mdpi, DimensionUtils.XXHDPI_FACTOR, DimensionUtils.MDPI_FACTOR
                        )
                )
        xxxhdpi
                .document
                .addDocumentListener(
                        TextFieldDocumentListener(
                                xxxhdpi, mdpi, DimensionUtils.XXXHDPI_FACTOR, DimensionUtils.MDPI_FACTOR
                        )
                )
        tvdpi
                .document
                .addDocumentListener(
                        TextFieldDocumentListener(
                                tvdpi, mdpi, DimensionUtils.TVDPI_FACTOR, DimensionUtils.MDPI_FACTOR
                        )
                )

        //mdpi value is the base case and we treat is independently
        mdpi
                .document
                .addDocumentListener(object : SimpleTextFieldDocumentListener() {
                    override fun onUpdate() {
                        setEnabled(false)
                        val ldpiDimension = convertValue(
                                mdpi.text, DimensionUtils.MDPI_FACTOR, DimensionUtils.LDPI_FACTOR
                        )
                        val mdpiDimension = convertValue(
                                mdpi.text, DimensionUtils.MDPI_FACTOR, DimensionUtils.MDPI_FACTOR
                        )
                        val hdpiDimension = convertValue(
                                mdpi.text, DimensionUtils.MDPI_FACTOR, DimensionUtils.HDPI_FACTOR
                        )
                        val xhdpiDimension = convertValue(
                                mdpi.text, DimensionUtils.MDPI_FACTOR, DimensionUtils.XHDPI_FACTOR
                        )
                        val xxhdpiDimension = convertValue(
                                mdpi.text, DimensionUtils.MDPI_FACTOR, DimensionUtils.XXHDPI_FACTOR
                        )
                        val xxxhdpiDimension = convertValue(
                                mdpi.text, DimensionUtils.MDPI_FACTOR, DimensionUtils.XXXHDPI_FACTOR
                        )
                        val tvdpiDimension = convertValue(
                                mdpi.text, DimensionUtils.MDPI_FACTOR, DimensionUtils.TVDPI_FACTOR
                        )
                        if (hdpi !== DimensionsWindow.currentEditingField) {
                            hdpi.text = hdpiDimension.realDimension
                        }
                        if (xhdpi !== DimensionsWindow.currentEditingField) {
                            xhdpi.text = xhdpiDimension.realDimension
                        }
                        if (xxhdpi !== DimensionsWindow.currentEditingField) {
                            xxhdpi.text = xxhdpiDimension.realDimension
                        }
                        if (xxxhdpi !== DimensionsWindow.currentEditingField) {
                            xxxhdpi.text = xxxhdpiDimension.realDimension
                        }
                        if (tvdpi !== DimensionsWindow.currentEditingField) {
                            tvdpi.text = tvdpiDimension.realDimension
                        }
                        ldpiRoundedValue.text = ldpiDimension.roundedDimension
                        mdpiRoundedValue.text = mdpiDimension.roundedDimension
                        hdpiRoundedValue.text = hdpiDimension.roundedDimension
                        xhdpiRoundedValue.text = xhdpiDimension.roundedDimension
                        xxhdpiRoundedValue.text = xxhdpiDimension.roundedDimension
                        xxxhdpiRoundedValue.text = xxxhdpiDimension.roundedDimension
                        tvdpiRoundedValue.text = tvdpiDimension.roundedDimension
                        setEnabled(true)
                    }
                })
    }

    fun setMdpiValue(value: String) {
        mdpi.text = value
    }

    class LdpiTextChangedEvent(val value: String)
}