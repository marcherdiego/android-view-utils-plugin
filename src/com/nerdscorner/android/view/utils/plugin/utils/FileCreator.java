package com.nerdscorner.android.view.utils.plugin.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.nerdscorner.android.view.utils.plugin.domain.AndroidWidget;

public class FileCreator {

    private static final String PACKAGE_NAME_KEY = "$PACKAGE_NAME$";
    private static final String ADAPTER_NAME_KEY = "$ADAPTER_NAME$";
    private static final String WIDGETS_BINDING_KEY = "$WIDGETS_BINDING$";
    public static final String IMPORTS_BLOCK_KEY = "$IMPORTS_BLOCK$";
    private static final String LAYOUT_NAME_KEY = "$LAYOUT_NAME$";

    public static final String WIDGET_IMPORT_ROW = "import %s\n";
    private static final String WIDGET_BINDING_ROW = "\t\tval %s: %s = itemView.findViewById(R.id.%s)\n";

    public static void createFile(InputStream inputStream, File file, String packageName, String adapterName, List<AndroidWidget> widgets,
                                  String layoutName)
            throws IOException {
        if (!file.exists()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder baseComponentContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                baseComponentContent.append(line).append(System.lineSeparator());
            }

            StringBuilder widgetsBinding = new StringBuilder();
            List<String> imports = new ArrayList<>();
            widgets.forEach(
                    androidWidget -> {
                        widgetsBinding
                                .append(
                                        String.format(
                                                WIDGET_BINDING_ROW,
                                                StringUtils.snakeToCamel(androidWidget.getId()),
                                                androidWidget.getType(),
                                                androidWidget.getId()
                                        )
                                );
                        String className = androidWidget.getClassName();
                        if (!className.contains(".")) {
                            className = "android.widget." + className;
                        }
                        if (!imports.contains(className)) {
                            imports.add(className);
                        }
                    }
            );
            StringBuilder importsBlock = new StringBuilder();
            imports.forEach(importString -> importsBlock.append(String.format(WIDGET_IMPORT_ROW, importString)));
            //Parameters replacer
            String parsedContent = baseComponentContent
                    .toString()
                    .replace(IMPORTS_BLOCK_KEY, importsBlock)
                    .replace(PACKAGE_NAME_KEY, packageName)
                    .replace(ADAPTER_NAME_KEY, adapterName)
                    .replace(WIDGETS_BINDING_KEY, widgetsBinding.toString())
                    .replace(LAYOUT_NAME_KEY, layoutName);

            file.getParentFile().mkdirs();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(parsedContent);
            fileWriter.close();
        }
    }
}
