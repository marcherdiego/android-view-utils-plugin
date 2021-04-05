package com.nerdscorner.android.view.utils.plugin.ui.mvp.presenter

import com.nerdscorner.android.view.utils.plugin.domain.Dimension
import com.nerdscorner.android.view.utils.plugin.ui.mvp.model.DensitiesPanelModel
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DensitiesPanelView
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DensitiesPanelView.HdpiTextChangedEvent
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DensitiesPanelView.LdpiTextChangedEvent
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DensitiesPanelView.MdpiTextChangedEvent
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DensitiesPanelView.TvdpiTextChangedEvent
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DensitiesPanelView.XhdpiTextChangedEvent
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DensitiesPanelView.XxhdpiTextChangedEvent
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DensitiesPanelView.XxxhdpiTextChangedEvent
import org.greenrobot.eventbus.Subscribe
import javax.swing.JTextField

class DensitiesPanelPresenter(private val view: DensitiesPanelView, private val model: DensitiesPanelModel) {

    @Subscribe
    fun onLdpiTextChanged(event: LdpiTextChangedEvent) {
        convertToBaseDp(event.field, Dimension.LDPI_FACTOR)
    }

    @Subscribe
    fun onMdpiTextChanged(event: MdpiTextChangedEvent) {
        convertToBaseDp(event.field, Dimension.MDPI_FACTOR)
    }

    @Subscribe
    fun onHdpiTextChanged(event: HdpiTextChangedEvent) {
        convertToBaseDp(event.field, Dimension.HDPI_FACTOR)
    }

    @Subscribe
    fun onXhdpiTextChanged(event: XhdpiTextChangedEvent) {
        convertToBaseDp(event.field, Dimension.XHDPI_FACTOR)
    }

    @Subscribe
    fun onXxhdpiTextChanged(event: XxhdpiTextChangedEvent) {
        convertToBaseDp(event.field, Dimension.XXHDPI_FACTOR)
    }

    @Subscribe
    fun onXxxhdpiTextChanged(event: XxxhdpiTextChangedEvent) {
        convertToBaseDp(event.field, Dimension.XXXHDPI_FACTOR)
    }

    @Subscribe
    fun onTvdpiTextChanged(event: TvdpiTextChangedEvent) {
        convertToBaseDp(event.field, Dimension.TVDPI_FACTOR)
    }

    private fun convertToBaseDp(field: JTextField, base: Float) {
        if (field.isEnabled.not()) {
            return
        }
        val dimensionSet = model.getDimensionSet(field.text ?: return, base)
        view.setAllExcept(field, dimensionSet)
    }
}
