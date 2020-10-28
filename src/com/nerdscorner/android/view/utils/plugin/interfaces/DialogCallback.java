package com.nerdscorner.android.view.utils.plugin.interfaces;

import com.intellij.openapi.vfs.VirtualFile;

public interface DialogCallback {
    void onOk(VirtualFile selectedFile);
}
