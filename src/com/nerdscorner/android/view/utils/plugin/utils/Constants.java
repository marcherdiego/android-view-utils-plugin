package com.nerdscorner.android.view.utils.plugin.utils;

public class Constants {
    public static final String BUILD = "build";
    public static final String GENERATED = "generated";

    public static final String MANIFEST_FILE = "AndroidManifest.xml";

    public static final String XML_EXTENSION = "xml";
    public static final String LAYOUT_FOLDER = "layout";
    public static final String RES = "res";
    public static final String RESOURCES = "resources";

    public static final String ACTIVITY = "activity";

    public static final String JAVA = "java";
    public static final String KOTLIN = "kotlin";
    public static final String SPACE = " ";
    public static final String OPEN_BRACKET = "{";
    public static final String TAB = "\t";
    public static final String CLOSE_BRACKET = TAB + "}";
    public static final String _EQUALS_ = " = ";
    public static final String COLON_ = ": ";
    public static final String SEMICOLON = ";";
    public static final String EMPTY = "";
    public static final String DOT = ".";
    public static final String QUESTION_MARK = "?";
    public static final String SLASH = "/";
    public static final String UNDERSCORE = "_";

    public static final String JAVA_PRIVATE_ATTRIBUTE = "\tprivate ";
    public static final String JAVA_ACTIVITY_FIND_VIEW = "activity.";
    public static final String JAVA_FRAGMENT_FIND_VIEW = "fragment.getView().";
    public static final String JAVA_WIDGET_IMPORT_ROW = "import %s;";

    public static final String KOTLIN_PRIVATE_VAL = "\tprivate val ";
    public static final String KOTLIN_ACTIVITY_FIND_VIEW = "activity.";
    public static final String KOTLIN_FRAGMENT_FIND_VIEW = "fragment.view?.";
    public static final String KOTLIN_WIDGET_IMPORT_ROW = "import %s";

    public static final String FIND_VIEW_BY_ID = "findViewById(R.id.";
    public static final String CLOSE_PARENTHESES = ")";
    public static final String IMPORT_ = "import ";
    public static final String DOT_R = ".R";
    public static final String ANDROID_WIDGET_DOT = "android.widget.";

    public static final String INIT_METHOD_SIGNATURE_ACTIVITY = "\tprivate void init(Activity activity) {";
    public static final String INIT_METHOD_SIGNATURE_FRAGMENT = "\tprivate void init(Fragment fragment) {";

    public static final String PACKAGE_NAME_KEY = "$PACKAGE_NAME$";
    public static final String ADAPTER_NAME_KEY = "$ADAPTER_NAME$";
    public static final String WIDGETS_BINDING_KEY = "$WIDGETS_BINDING$";
    public static final String IMPORTS_BLOCK_KEY = "$IMPORTS_BLOCK$";
    public static final String LAYOUT_NAME_KEY = "$LAYOUT_NAME$";

    public static final String WIDGET_IMPORT_ROW = "import %s\n";
    public static final String WIDGET_BINDING_ROW = "\t\tval %s: %s = itemView.findViewById(R.id.%s)\n";

    public static final String KEY_ELEMENT_ID = "android:id";

    public static class Properties {
        public static final String PROPERTY_PACKAGE_NAME = "package_name";
    }
}
