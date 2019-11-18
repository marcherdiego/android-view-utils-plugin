package com.nerdscorner.android.view.utils.plugin;

import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import com.nerdscorner.android.view.utils.plugin.domain.AndroidWidget;
import com.nerdscorner.android.view.utils.plugin.domain.Manifest;
import com.nerdscorner.android.view.utils.plugin.utils.Constants;
import com.nerdscorner.android.view.utils.plugin.utils.FileCreator;
import com.nerdscorner.android.view.utils.plugin.utils.LayoutScanner;
import com.nerdscorner.android.view.utils.plugin.utils.ManifestUtils;
import com.nerdscorner.android.view.utils.plugin.utils.StringUtils;

class LayoutFileSelectorDialog extends JDialog {
    private final Project project;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList<VirtualFile> layoutFilesList;
    private VirtualFile baseFolder;
    private JTextField packageName;
    private JTextField adapterName;

    public LayoutFileSelectorDialog(Project project, VirtualFile baseFolder) {
        this.project = project;
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

        restoreInputStates();

        loadLayoutFiles();
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

    private void onOK() {
        VirtualFile layoutFile = layoutFilesList.getSelectedValue();
        String adapterName = StringUtils.asCamelCase(this.adapterName.getText());
        String packageName = this.packageName.getText();
        if (layoutFile == null || StringUtils.isNullOrEmpty(adapterName) || StringUtils.isNullOrEmpty(packageName)) {
            String errorMessage = "Unknown error";
            if (layoutFile == null) {
                errorMessage = "Please select a layout from the list";
            } else if (StringUtils.isNullOrEmpty(adapterName)) {
                errorMessage = "Please enter a name for the adapter";
            } else if (StringUtils.isNullOrEmpty(packageName)) {
                errorMessage = "Please enter a package name";
            }
            //Show result
            ResultDialog resultDialog = new ResultDialog(errorMessage);
            resultDialog.pack();
            resultDialog.setLocationRelativeTo(null);
            resultDialog.setTitle("Adapter creation error");
            resultDialog.setResizable(false);
            resultDialog.setVisible(true);
            return;
        }
        onCancel();
        List<AndroidWidget> layoutWidgets = LayoutScanner.findWidgets(layoutFile);
        if (layoutWidgets != null) {
            InputStream template = getClass().getResourceAsStream("/templates/adapter_template");
            String basePath = baseFolder.getPath()
                    + File.separator
                    + packageName.replace(".", File.separator)
                    + "/ui/adapters";
            String relativePath = adapterName + ".kt";
            File component = new File(basePath, relativePath);
            try {
                FileCreator.createFile(
                        template,
                        component,
                        packageName,
                        adapterName,
                        layoutWidgets
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Show result
        ResultDialog resultDialog = new ResultDialog("Adapter created successfully!");
        resultDialog.pack();
        resultDialog.setLocationRelativeTo(null);
        resultDialog.setTitle("Adapter created");
        resultDialog.setResizable(false);
        resultDialog.setVisible(true);

        saveInputStates();
    }

    private void onCancel() {
        setVisible(false);
        dispose();
    }

    private void saveInputStates() {
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance(project);

        //Package name
        propertiesComponent.setValue(Constants.Properties.PROPERTY_PACKAGE_NAME, packageName.getText());
    }

    private void restoreInputStates() {
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance(project);

        //Package name
        Manifest manifest = ManifestUtils.getManifest(baseFolder);
        String savedPackageName = propertiesComponent.getValue(Constants.Properties.PROPERTY_PACKAGE_NAME, "");
        packageName.setText(savedPackageName.equals("") ? (manifest == null ? "" : manifest.getPkg()) : savedPackageName);
    }
}
