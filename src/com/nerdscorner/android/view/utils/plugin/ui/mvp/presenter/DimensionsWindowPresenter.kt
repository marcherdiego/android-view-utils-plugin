package com.nerdscorner.android.view.utils.plugin.ui.mvp.presenter

import com.nerdscorner.android.view.utils.plugin.domain.DimensionSet
import com.nerdscorner.android.view.utils.plugin.ui.mvp.model.DimensionsWindowModel
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DimensionsWindowView
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DimensionsWindowView.HdpiTextChangedEvent
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DimensionsWindowView.LdpiTextChangedEvent
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DimensionsWindowView.MdpiTextChangedEvent
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DimensionsWindowView.TvdpiTextChangedEvent
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DimensionsWindowView.XhdpiTextChangedEvent
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DimensionsWindowView.XxhdpiTextChangedEvent
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DimensionsWindowView.XxxhdpiTextChangedEvent
import com.nerdscorner.android.view.utils.plugin.utils.DimensionUtils
import org.greenrobot.eventbus.Subscribe
import javax.swing.JTextField

class DimensionsWindowPresenter(private val view: DimensionsWindowView, private val model: DimensionsWindowModel) {

    @Subscribe
    fun onLdpiTextChanged(event: LdpiTextChangedEvent) {
        convertToBaseDpi(event.field, DimensionUtils.LDPI_FACTOR)
    }

    @Subscribe
    fun onMdpiTextChanged(event: MdpiTextChangedEvent) {
        convertToBaseDpi(event.field, DimensionUtils.MDPI_FACTOR)
    }

    @Subscribe
    fun onHdpiTextChanged(event: HdpiTextChangedEvent) {
        convertToBaseDpi(event.field, DimensionUtils.HDPI_FACTOR)
    }

    @Subscribe
    fun onXhdpiTextChanged(event: XhdpiTextChangedEvent) {
        convertToBaseDpi(event.field, DimensionUtils.XHDPI_FACTOR)
    }

    @Subscribe
    fun onXxhdpiTextChanged(event: XxhdpiTextChangedEvent) {
        convertToBaseDpi(event.field, DimensionUtils.XXHDPI_FACTOR)
    }

    @Subscribe
    fun onXxxhdpiTextChanged(event: XxxhdpiTextChangedEvent) {
        convertToBaseDpi(event.field, DimensionUtils.XXXHDPI_FACTOR)
    }

    @Subscribe
    fun onTvdpiTextChanged(event: TvdpiTextChangedEvent) {
        convertToBaseDpi(event.field, DimensionUtils.TVDPI_FACTOR)
    }

    private fun convertToBaseDpi(field: JTextField, base: Float) {
        if (field.isEnabled.not()) {
            return
        }
        val dimensionSet = DimensionSet(field.text, base)
        view.setAllExcept(field, dimensionSet)
    }
}
