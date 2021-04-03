package com.nerdscorner.android.view.utils.plugin.ui;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.nerdscorner.android.view.utils.plugin.domain.Dimension;
import com.nerdscorner.android.view.utils.plugin.ui.mvp.model.DimensionsWindowModel;
import com.nerdscorner.android.view.utils.plugin.ui.mvp.presenter.DimensionsWindowPresenter;
import com.nerdscorner.android.view.utils.plugin.ui.mvp.view.DimensionsWindowView;
import com.nerdscorner.android.view.utils.plugin.utils.DimensionUtils;
import com.nerdscorner.android.view.utils.plugin.utils.SimpleTextFieldDocumentListener;
import com.nerdscorner.android.view.utils.plugin.utils.TextFieldDocumentListener;

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

    public static JTextField currentEditingField;

    private DimensionsWindowPresenter presenter;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(myToolWindowContent, "", false);
        toolWindow.getContentManager().addContent(content);

        EventBus bus = new EventBus();
        presenter = new DimensionsWindowPresenter(
                new DimensionsWindowView(
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
                new DimensionsWindowModel(bus)
        );
        bus.register(presenter);

        final PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        float lastDimenLdpi = propertiesComponent.getFloat("last_dimen_ldpi", 0f);
    }

    public static void setCurrentEditingField(JTextField currentEditingField) {
        DimensionsWindow.currentEditingField = currentEditingField;
    }
}
