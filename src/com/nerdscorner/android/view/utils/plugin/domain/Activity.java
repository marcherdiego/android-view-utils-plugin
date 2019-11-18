package com.nerdscorner.android.view.utils.plugin.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Activity extends ScreenComponent {
    private String fullName;
    private String displayName;

    @Override
    public String getName() {
        return name;
    }

    @XmlAttribute(namespace = "http://schemas.android.com/apk/res/android")
    public void setName(String name) {
        this.displayName = name;
        int dotIndex = name.lastIndexOf(".");
        if (dotIndex == -1) {
            return;
        }
        this.name = name.substring(dotIndex + 1);
        this.fullName = name;
    }

    @Override
    public String toString() {
        if (displayName.equals(NONE)) {
            return NONE;
        }
        return displayName.substring(displayName.lastIndexOf(".") + 1);
    }

    public String getFullName(String basePackage) {
        return fullName == null ? null : fullName.startsWith(".") ? basePackage + fullName : fullName;
    }
}
