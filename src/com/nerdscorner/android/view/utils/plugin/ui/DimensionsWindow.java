package com.nerdscorner.android.view.utils.plugin.ui;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.nerdscorner.android.view.utils.plugin.ui.mvp.model.DensitiesPanelModel;
import com.nerdscorner.android.view.utils.plugin.ui.mvp.model.UnitsPanelModel;
import com.nerdscorner.android.view.utils.plugin.ui.mvp.presenter.DensitiesPanelPresenter;
import com.nerdscorner.android.view.utils.plugin.ui.mvp.presenter.UnitsPanelPresenter;
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DensitiesPanelView;
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.UnitsPanelView;

public class DimensionsWindow implements ToolWindowFactory {

    private JPanel myToolWindowContent;

    private JTextField ldpi;
    private JTextField mdpi;
    private JTextField hdpi;
    private JTextField xhdpi;
    private JTextField xxhdpi;
    private JTextField xxxhdpi;
    private JTextField tvdpi;
    private JLabel ldpiRoundedValue;
    private JLabel mdpiRoundedValue;
    private JLabel hdpiRoundedValue;
    private JLabel xhdpiRoundedValue;
    private JLabel xxhdpiRoundedValue;
    private JLabel xxxhdpiRoundedValue;
    private JLabel tvdpiRoundedValue;
    private JTabbedPane tabbedPane1;
    private JTextField ldpiInches;
    private JTextField ldpiDp;
    private JTextField ldpiPx;
    private JTextField ldpiPt;
    private JTextField ldpiMm;
    private JTextField mdpiInches;
    private JTextField mdpiDp;
    private JTextField mdpiPx;
    private JTextField mdpiPt;
    private JTextField mdpiMm;
    private JTextField hdpiInches;
    private JTextField hdpiDp;
    private JTextField hdpiPx;
    private JTextField hdpiPt;
    private JTextField hdpiMm;
    private JTextField xxhdpiInches;
    private JTextField xxhdpiDp;
    private JTextField xxhdpiPx;
    private JTextField xxhdpiPt;
    private JTextField xxhdpiMm;
    private JTextField xxxhdpiInches;
    private JTextField xxxhdpiDp;
    private JTextField xxxhdpiPx;
    private JTextField xxxhdpiPt;
    private JTextField xxxhdpiMm;
    private JTextField tvdpiInches;
    private JTextField tvdpiDp;
    private JTextField tvdpiPx;
    private JTextField tvdpiPt;
    private JTextField tvdpiMm;
    private JTextField customDensity;
    private JTextField customInches;
    private JTextField customDp;
    private JTextField customPx;
    private JTextField customPt;
    private JTextField customMm;
    private JTextField xhdpiInches;
    private JTextField xhdpiDp;
    private JTextField xhdpiPx;
    private JTextField xhdpiPt;
    private JTextField xhdpiMm;
    private JCheckBox roundAllValuesCheckBox;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(myToolWindowContent, "", false);
        toolWindow.getContentManager().addContent(content);

        EventBus bus = new EventBus();
        DensitiesPanelPresenter densitiesPresenter = new DensitiesPanelPresenter(
                new DensitiesPanelView(
                        ldpi,
                        mdpi,
                        hdpi,
                        xhdpi,
                        xxhdpi,
                        xxxhdpi,
                        tvdpi,
                        ldpiRoundedValue,
                        mdpiRoundedValue,
                        hdpiRoundedValue,
                        xhdpiRoundedValue,
                        xxhdpiRoundedValue,
                        xxxhdpiRoundedValue,
                        tvdpiRoundedValue,
                        bus
                ),
                new DensitiesPanelModel(bus)
        );
        bus.register(densitiesPresenter);

        UnitsPanelPresenter unitsPanelPresenter = new UnitsPanelPresenter(
                new UnitsPanelView(
                        ldpiInches,
                        ldpiDp,
                        ldpiPx,
                        ldpiPt,
                        ldpiMm,
                        mdpiInches,
                        mdpiDp,
                        mdpiPx,
                        mdpiPt,
                        mdpiMm,
                        hdpiInches,
                        hdpiDp,
                        hdpiPx,
                        hdpiPt,
                        hdpiMm,
                        xhdpiInches,
                        xhdpiDp,
                        xhdpiPx,
                        xhdpiPt,
                        xhdpiMm,
                        xxhdpiInches,
                        xxhdpiDp,
                        xxhdpiPx,
                        xxhdpiPt,
                        xxhdpiMm,
                        xxxhdpiInches,
                        xxxhdpiDp,
                        xxxhdpiPx,
                        xxxhdpiPt,
                        xxxhdpiMm,
                        tvdpiInches,
                        tvdpiDp,
                        tvdpiPx,
                        tvdpiPt,
                        tvdpiMm,
                        customDensity,
                        customInches,
                        customDp,
                        customPx,
                        customPt,
                        customMm,
                        roundAllValuesCheckBox,
                        bus
                ),
                new UnitsPanelModel(bus)
        );
        bus.register(unitsPanelPresenter);
    }
}
