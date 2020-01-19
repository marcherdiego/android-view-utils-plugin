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
    private static final String ACTIVITY = "activity";

    private static final String JAVA = "java";
    private static final String KOTLIN = "kotlin";
    private static final String SPACE = " ";
    private static final String OPEN_BRACKET = "{";
    private static final String TAB = "\t";
    private static final String CLOSE_BRACKET = TAB + "}";
    private static final String _EQUALS_ = " = ";
    private static final String COLON_ = ": ";
    private static final String SEMICOLON = ";";
    private static final String EMPTY = "";
    private static final String QUESTION_MARK = "?";

    private static final String JAVA_PRIVATE_ATTRIBUTE = "\tprivate ";
    private static final String JAVA_ACTIVITY_FIND_VIEW = "activity.";
    private static final String JAVA_FRAGMENT_FIND_VIEW = "fragment.getView().";
    private static final String JAVA_WIDGET_IMPORT_ROW = "import %s;";

    private static final String KOTLIN_PRIVATE_VAL = "\tprivate val ";
    private static final String KOTLIN_ACTIVITY_FIND_VIEW = "activity.";
    private static final String KOTLIN_FRAGMENT_FIND_VIEW = "fragment.view?.";
    private static final String KOTLIN_WIDGET_IMPORT_ROW = "import %s";

    private static final String FIND_VIEW_BY_ID = "findViewById(R.id.";
    private static final String CLOSE_PARENTHESES = ")";
    private static final String DOT = ".";
    private static final String IMPORT_ = "import ";
    private static final String DOT_R = ".R";
    private static final String ANDROID_WIDGET_DOT = "android.widget.";

    private static final String INIT_METHOD_SIGNATURE_ACTIVITY = "\tprivate void init(Activity activity) {";
    private static final String INIT_METHOD_SIGNATURE_FRAGMENT = "\tprivate void init(Fragment fragment) {";

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
        ViewBindingLayoutFileSelectorDialog viewBindingLayoutFileSelectorDialog = new ViewBindingLayoutFileSelectorDialog(isActivity(selectedFile), rootSourceFile);
        viewBindingLayoutFileSelectorDialog.setCallback((isActivity, widgets) -> {
            if (JAVA.equals(selectedFile.getExtension())) {
                createJavaBindings(isActivity, selectedFile, widgets);
            } else {
                createKotlinBindings(isActivity, selectedFile, widgets);
            }
        });
        viewBindingLayoutFileSelectorDialog.pack();
        viewBindingLayoutFileSelectorDialog.setLocationRelativeTo(null);
        viewBindingLayoutFileSelectorDialog.setTitle("Select layout file...");
        viewBindingLayoutFileSelectorDialog.setResizable(true);
        viewBindingLayoutFileSelectorDialog.setVisible(true);
    }

    private VirtualFile navigateToRootSourceFolder(VirtualFile selectedFile) {
        if (selectedFile.getName().equals(JAVA) || selectedFile.getName().equals(KOTLIN)) {
            return selectedFile;
        }
        return navigateToRootSourceFolder(selectedFile.getParent());
    }

    private void createJavaBindings(boolean isActivity, VirtualFile selectedFile, List<AndroidWidget> widgets) {
        if (widgets == null || widgets.isEmpty()) {
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile.getPath())));
            String line;
            StringBuilder fileContent = new StringBuilder();
            boolean firstBracketFound = false;
            List<String> imports = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.contains(OPEN_BRACKET) && !firstBracketFound) {
                    fileContent.append(FileCreator.IMPORTS_BLOCK_KEY).append(System.lineSeparator());
                    fileContent.append(line).append(System.lineSeparator());
                    firstBracketFound = true;

                    StringBuilder initMethod = new StringBuilder();
                    initMethod
                            .append(isActivity ? INIT_METHOD_SIGNATURE_ACTIVITY : INIT_METHOD_SIGNATURE_FRAGMENT)
                            .append(System.lineSeparator());
                    widgets.forEach(
                            androidWidget -> {
                                fileContent
                                        .append(JAVA_PRIVATE_ATTRIBUTE)
                                        .append(androidWidget.getType())
                                        .append(SPACE)
                                        .append(StringUtils.snakeToCamel(androidWidget.getId()))
                                        .append(SEMICOLON)
                                        .append(System.lineSeparator());
                                initMethod
                                        .append(TAB)
                                        .append(TAB)
                                        .append(StringUtils.snakeToCamel(androidWidget.getId()))
                                        .append(_EQUALS_)
                                        .append(isActivity ? JAVA_ACTIVITY_FIND_VIEW : JAVA_FRAGMENT_FIND_VIEW)
                                        .append(FIND_VIEW_BY_ID)
                                        .append(androidWidget.getId())
                                        .append(CLOSE_PARENTHESES)
                                        .append(SEMICOLON)
                                        .append(System.lineSeparator());

                                String className = androidWidget.getClassName();
                                if (!className.contains(DOT)) {
                                    className = ANDROID_WIDGET_DOT + className;
                                }
                                if (!imports.contains(className)) {
                                    imports.add(className);
                                }
                            }
                    );
                    fileContent
                            .append(System.lineSeparator())
                            .append(initMethod.toString())
                            .append(CLOSE_BRACKET)
                            .append(System.lineSeparator())
                            .append(System.lineSeparator());
                } else {
                    fileContent.append(line).append(System.lineSeparator());
                }
            }
            StringBuilder importsBlock = new StringBuilder();
            importsBlock
                    .append(IMPORT_)
                    .append(packageName)
                    .append(DOT_R);
            importsBlock.append(SEMICOLON);
            importsBlock.append(System.lineSeparator());
            imports.forEach(importString -> importsBlock
                    .append(
                            String.format(JAVA_WIDGET_IMPORT_ROW, importString)
                    )
                    .append(System.lineSeparator()));
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

    private void createKotlinBindings(boolean isActivity, VirtualFile selectedFile, List<AndroidWidget> widgets) {
        if (widgets == null || widgets.isEmpty()) {
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile.getPath())));
            String line;
            StringBuilder fileContent = new StringBuilder();
            boolean firstBracketFound = false;
            List<String> imports = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.contains(OPEN_BRACKET) && !firstBracketFound) {
                    fileContent.append(FileCreator.IMPORTS_BLOCK_KEY).append(System.lineSeparator());
                    fileContent.append(line).append(System.lineSeparator());
                    firstBracketFound = true;

                    widgets.forEach(
                            androidWidget -> {
                                fileContent
                                        .append(KOTLIN_PRIVATE_VAL)
                                        .append(StringUtils.snakeToCamel(androidWidget.getId()))
                                        .append(COLON_)
                                        .append(androidWidget.getType())
                                        .append(isActivity ? EMPTY : QUESTION_MARK)
                                        .append(_EQUALS_)
                                        .append(isActivity ? KOTLIN_ACTIVITY_FIND_VIEW : KOTLIN_FRAGMENT_FIND_VIEW)
                                        .append(FIND_VIEW_BY_ID)
                                        .append(androidWidget.getId())
                                        .append(CLOSE_PARENTHESES)
                                        .append(System.lineSeparator());

                                String className = androidWidget.getClassName();
                                if (!className.contains(DOT)) {
                                    className = ANDROID_WIDGET_DOT + className;
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
                    .append(IMPORT_)
                    .append(packageName)
                    .append(DOT_R);
            importsBlock.append(System.lineSeparator());
            imports.forEach(importString -> importsBlock
                    .append(
                            String.format(KOTLIN_WIDGET_IMPORT_ROW, importString)
                    )
                    .append(System.lineSeparator()));
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

    private boolean isActivity(VirtualFile selectedFile) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile.getPath())));
            String line;
            StringBuilder fileContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.contains(OPEN_BRACKET)) {
                    fileContent.append(line);
                    break;
                }
            }
            return fileContent.toString().toLowerCase().contains(ACTIVITY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void update(final AnActionEvent actionEvent) {
        final Project project = actionEvent.getData(CommonDataKeys.PROJECT);
        actionEvent.getPresentation().setVisible(project != null);
    }
}
