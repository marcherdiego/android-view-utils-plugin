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
    private static final String IMPORTS_BLOCK_KEY = "$IMPORTS_BLOCK$";

    private static final String WIDGET_IMPORT_ROW = "import %s\n";
    private static final String WIDGET_BINDING_ROW = "\t\tval %s: %s = itemView.findViewById(R.id.%s)\n";

    public static void createFile(InputStream inputStream, File file, String packageName, String adapterName, List<AndroidWidget> widgets)
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
                                                snakeToCamel(androidWidget.getId()),
                                                androidWidget.getType(),
                                                androidWidget.getId()
                                        )
                                );
                        String className = androidWidget.getClassName();
                        if (className.contains(".") && !imports.contains(className)) {
                            imports.add(androidWidget.getClassName());
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
                    .replace(WIDGETS_BINDING_KEY, widgetsBinding.toString());

            file.getParentFile().mkdirs();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(parsedContent);
            fileWriter.close();
        }
    }

    private static String snakeToCamel(String id) {
        if (StringUtils.isNullOrEmpty(id)) {
            return id;
        }
        StringBuilder result = new StringBuilder();
        boolean shouldWriteUppercase = false;
        for (int i = 0; i < id.length(); i++) {
            String nextChar = String.valueOf(id.charAt(i));
            if (nextChar.equals("_")) {
                shouldWriteUppercase = true;
                continue;
            }
            if (shouldWriteUppercase) {
                shouldWriteUppercase = false;
                result.append(nextChar.toUpperCase());
            } else {
                result.append(nextChar);
            }
        }
        return result.toString();
    }
}
