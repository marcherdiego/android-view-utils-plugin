package com.nerdscorner.android.view.utils.plugin.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


import static com.nerdscorner.android.view.utils.plugin.utils.Constants.DOT;

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
        int dotIndex = name.lastIndexOf(DOT);
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
        return displayName.substring(displayName.lastIndexOf(DOT) + 1);
    }

    public String getFullName(String basePackage) {
        return fullName == null ? null : fullName.startsWith(DOT) ? basePackage + fullName : fullName;
    }
}
