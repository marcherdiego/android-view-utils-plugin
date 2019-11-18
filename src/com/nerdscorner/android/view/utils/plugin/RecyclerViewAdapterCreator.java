package com.nerdscorner.android.view.utils.plugin;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.nerdscorner.android.view.utils.plugin.domain.AndroidWidget;
import com.nerdscorner.android.view.utils.plugin.utils.FileCreator;
import com.nerdscorner.android.view.utils.plugin.utils.LayoutScanner;

class RecyclerViewAdapterCreator extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent actionEvent) {
        Project project = getEventProject(actionEvent);
        if (project == null) {
            return;
        }
        ProjectRootManager projectRootManager = ProjectRootManager.getInstance(project);
        if (projectRootManager.getContentSourceRoots().length > 1) {
            LayoutFileSelectorDialog layoutFileSelectorDialog = new LayoutFileSelectorDialog(project, layoutFile -> {
                if (layoutFile == null) {
                    return;
                }
                List<AndroidWidget> layoutWidgets = LayoutScanner.findWidgets(layoutFile);
                if (layoutWidgets != null) {
                    InputStream template = getClass().getResourceAsStream("/templates/adapter_template");
                    String basePath = layoutFile.getParent().getPath();
                    String relativePath = "MyAdapter.kt";
                    File component = new File(basePath, relativePath);
                    try {
                        FileCreator.createFile(
                                template,
                                component,
                                "com.example.diegomarcher.mvpplugintest",
                                "MyAdapter",
                                layoutWidgets
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            layoutFileSelectorDialog.pack();
            layoutFileSelectorDialog.setLocationRelativeTo(null);
            layoutFileSelectorDialog.setTitle("Select layout file...");
            layoutFileSelectorDialog.setResizable(true);
            layoutFileSelectorDialog.setVisible(true);
        }
    }

    @Override
    public void update(final AnActionEvent actionEvent) {
        final Project project = actionEvent.getData(CommonDataKeys.PROJECT);
        actionEvent.getPresentation().setVisible(project != null);
    }
}
