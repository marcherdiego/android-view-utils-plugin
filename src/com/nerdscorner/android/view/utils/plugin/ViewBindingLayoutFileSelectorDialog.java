package com.nerdscorner.android.view.utils.plugin;

import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import com.nerdscorner.android.view.utils.plugin.domain.AndroidWidget;
import com.nerdscorner.android.view.utils.plugin.utils.Constants;
import com.nerdscorner.android.view.utils.plugin.utils.LayoutScanner;

class ViewBindingLayoutFileSelectorDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList<VirtualFile> layoutFilesList;
    private VirtualFile baseFolder;
    private Callback callback;

    public ViewBindingLayoutFileSelectorDialog(VirtualFile baseFolder) {
        this.baseFolder = baseFolder;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        loadLayoutFiles();
    }

    private void onOK() {
        VirtualFile layoutFile = layoutFilesList.getSelectedValue();
        if (layoutFile == null) {
            ResultDialog resultDialog = new ResultDialog("Please select a layout from the list");
            resultDialog.pack();
            resultDialog.setLocationRelativeTo(null);
            resultDialog.setTitle("View binding creation error");
            resultDialog.setResizable(false);
            resultDialog.setVisible(true);
            return;
        }
        onCancel();
        List<AndroidWidget> layoutWidgets = LayoutScanner.findWidgets(layoutFile);
        ResultDialog resultDialog;
        if (layoutWidgets != null) {
            if (callback != null) {
                callback.onLayoutSelected(layoutWidgets);
            }
            resultDialog = new ResultDialog("View binding created successfully!");
        } else {
            resultDialog = new ResultDialog("No widgets with id were found...");
        }
        resultDialog.pack();
        resultDialog.setLocationRelativeTo(null);
        resultDialog.setTitle("View binding");
        resultDialog.setResizable(false);
        resultDialog.setVisible(true);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private void loadLayoutFiles() {
        DefaultListModel<VirtualFile> model = new DefaultListModel<>();
        getAllLayoutFilesInFolder(baseFolder.getParent()).forEach(model::addElement);
        this.layoutFilesList.setModel(model);
    }

    private List<VirtualFile> getAllLayoutFilesInFolder(VirtualFile folder) {
        final List<VirtualFile> result = new ArrayList<>();
        VfsUtilCore.visitChildrenRecursively(folder, new VirtualFileVisitor<Object>() {
            @Override
            public boolean visitFile(@NotNull VirtualFile file) {
                if (isLayoutFile(file)) {
                    result.add(file);
                }
                return super.visitFile(file);
            }
        });
        return result;
    }

    private boolean isLayoutFile(VirtualFile file) {
        String extension = file.getExtension();
        if (file.isDirectory() || extension == null) {
            return false;
        }
        return extension.toLowerCase().equals(Constants.XML_EXTENSION)
                && file.getParent().getName().equals(Constants.LAYOUT_FOLDER);
    }

    private void onCancel() {
        setVisible(false);
        dispose();
    }

    public interface Callback {
        void onLayoutSelected(List<AndroidWidget> layoutWidgets);
    }
}
