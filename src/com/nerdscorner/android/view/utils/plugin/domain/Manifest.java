package com.nerdscorner.android.view.utils.plugin.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Manifest {
    private String pkg;
    private Application application;

    public Application getApplication() {
        return application;
    }

    @XmlElement
    public void setApplication(Application name) {
        this.application = name;
    }

    public String getPkg() {
        return pkg;
    }

    @XmlAttribute(name = "package")
    public void setPkg(String pkg) {
        this.pkg = pkg;
    }
}
