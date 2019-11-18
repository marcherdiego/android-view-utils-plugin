package com.nerdscorner.android.view.utils.plugin.domain;

public class AndroidWidget {
    private String id;
    private String type;
    private String className;

    public AndroidWidget(String id, String type, String className) {
        this.id = id;
        this.type = type;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
