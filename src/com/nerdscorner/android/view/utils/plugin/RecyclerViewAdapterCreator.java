package com.nerdscorner.android.view.utils.plugin;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;

class RecyclerViewAdapterCreator extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent actionEvent) {
        Project project = getEventProject(actionEvent);
        if (project == null) {
            return;
        }
        ProjectRootManager projectRootManager = ProjectRootManager.getInstance(project);
        if (projectRootManager.getContentSourceRoots().length > 1) {
            SourceFolderSelectorDialog sourceFolderSelectorDialog = new SourceFolderSelectorDialog(project);
            sourceFolderSelectorDialog.pack();
            sourceFolderSelectorDialog.setLocationRelativeTo(null);
            sourceFolderSelectorDialog.setResizable(true);
            sourceFolderSelectorDialog.setTitle("Select source folder (adapter will be created here)");
            sourceFolderSelectorDialog.setVisible(true);
        }
    }

    @Override
    public void update(final AnActionEvent actionEvent) {
        final Project project = actionEvent.getData(CommonDataKeys.PROJECT);
        actionEvent.getPresentation().setVisible(project != null);
    }
}
