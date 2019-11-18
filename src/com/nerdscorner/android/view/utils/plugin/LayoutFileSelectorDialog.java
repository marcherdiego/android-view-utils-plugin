package com.nerdscorner.android.view.utils.plugin;

import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import com.nerdscorner.android.view.utils.plugin.utils.Constants;

class LayoutFileSelectorDialog extends JDialog {
    private final Project project;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList<VirtualFile> layoutFilesList;
    private FileSelectedListener fileSelectedListener;

    public LayoutFileSelectorDialog(Project project, FileSelectedListener fileSelectedListener) {
        this.project = project;
        this.fileSelectedListener = fileSelectedListener;
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

    private void loadLayoutFiles() {
        DefaultListModel<VirtualFile> model = new DefaultListModel<>();
        VirtualFile[] sourceFolders = ProjectRootManager.getInstance(project).getContentSourceRoots();
        for (VirtualFile sourceFolder : sourceFolders) {
            if (sourceFolder.getUrl().contains(Constants.GENERATED) || sourceFolder.getUrl().contains(Constants.BUILD)) {
                continue;
            }
            getAllLayoutFilesInFolder(sourceFolder).forEach(model::addElement);
        }
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

    private void onOK() {
        onCancel();
        fileSelectedListener.onFileSelected(layoutFilesList.getSelectedValue());
    }

    private void onCancel() {
        setVisible(false);
        dispose();
    }

    public interface FileSelectedListener {
        void onFileSelected(@Nullable VirtualFile file);
    }
}
