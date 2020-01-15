package com.nerdscorner.android.view.utils.plugin;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.nerdscorner.android.view.utils.plugin.domain.AndroidWidget;
import com.nerdscorner.android.view.utils.plugin.utils.FileCreator;
import com.nerdscorner.android.view.utils.plugin.utils.ManifestUtils;
import com.nerdscorner.android.view.utils.plugin.utils.StringUtils;

class ViewBindingCreator extends AnAction {

    private static final String JAVA = "java";
    private String packageName;

    @Override
    public void actionPerformed(@NotNull AnActionEvent actionEvent) {
        Project project = getEventProject(actionEvent);
        if (project == null) {
            return;
        }
        VirtualFile selectedFile = actionEvent.getData(CommonDataKeys.PSI_FILE).getVirtualFile();
        VirtualFile rootSourceFile = navigateToRootSourceFolder(selectedFile);
        packageName = ManifestUtils.getManifest(rootSourceFile).getPkg();
        ViewBindingLayoutFileSelectorDialog viewBindingLayoutFileSelectorDialog = new ViewBindingLayoutFileSelectorDialog(rootSourceFile);
        viewBindingLayoutFileSelectorDialog.setCallback(widgets -> createBindings(selectedFile, widgets));
        viewBindingLayoutFileSelectorDialog.pack();
        viewBindingLayoutFileSelectorDialog.setLocationRelativeTo(null);
        viewBindingLayoutFileSelectorDialog.setTitle("Select layout file...");
        viewBindingLayoutFileSelectorDialog.setResizable(true);
        viewBindingLayoutFileSelectorDialog.setVisible(true);
    }

    private VirtualFile navigateToRootSourceFolder(VirtualFile selectedFile) {
        if (selectedFile.getName().equals(JAVA)) {
            return selectedFile;
        }
        return navigateToRootSourceFolder(selectedFile.getParent());
    }

    private void createBindings(VirtualFile selectedFile, List<AndroidWidget> widgets) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile.getPath())));
            String line;
            StringBuilder fileContent = new StringBuilder();
            boolean firstBracketFound = false;
            List<String> imports = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.contains("{") && !firstBracketFound) {
                    fileContent.append(FileCreator.IMPORTS_BLOCK_KEY).append(System.lineSeparator());
                    fileContent.append(line).append(System.lineSeparator());
                    firstBracketFound = true;

                    widgets.forEach(
                            androidWidget -> {
                                fileContent
                                        .append("\tprivate val ")
                                        .append(StringUtils.snakeToCamel(androidWidget.getId()))
                                        .append(": ")
                                        .append(androidWidget.getType())
                                        .append(" = activity.findViewById(R.id.")
                                        .append(androidWidget.getId())
                                        .append(")")
                                        .append(System.lineSeparator());

                                String className = androidWidget.getClassName();
                                if (!className.contains(".")) {
                                    className = "android.widget." + className;
                                }
                                if (!imports.contains(className)) {
                                    imports.add(className);
                                }
                            }
                    );
                } else {
                    fileContent.append(line).append(System.lineSeparator());
                }
            }
            StringBuilder importsBlock = new StringBuilder();
            importsBlock
                    .append("import ")
                    .append(packageName)
                    .append(".R")
                    .append(System.lineSeparator());
            imports.forEach(importString -> importsBlock.append(String.format(FileCreator.WIDGET_IMPORT_ROW, importString)));
            String fileContentString = fileContent
                    .toString()
                    .replace(FileCreator.IMPORTS_BLOCK_KEY, importsBlock);

            FileWriter fileWriter = new FileWriter(selectedFile.getPath());
            fileWriter.write(fileContentString);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(final AnActionEvent actionEvent) {
        final Project project = actionEvent.getData(CommonDataKeys.PROJECT);
        actionEvent.getPresentation().setVisible(project != null);
    }
}
