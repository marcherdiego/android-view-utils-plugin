package com.nerdscorner.android.view.utils.plugin;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.nerdscorner.android.view.utils.plugin.utils.Constants;

public class SourceFolderSelectorDialog extends JDialog {
    private static final String PROPERTY_SOURCE_FOLDER_NAME = "source_folder_name";

    private final Project project;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList<VirtualFile> sourceFolders;

    public SourceFolderSelectorDialog(Project project) {
        this.project = project;
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
        loadSrcFolders();
    }

    private void loadSrcFolders() {
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance(project);
        String sourceFolderName = propertiesComponent.getValue(PROPERTY_SOURCE_FOLDER_NAME, "");

        DefaultListModel<VirtualFile> model = new DefaultListModel<>();
        VirtualFile[] sourceFolders = ProjectRootManager.getInstance(project).getContentSourceRoots();
        int currentIndex = 0;
        int selectedIndex = 0;
        for (VirtualFile sourceFolder : sourceFolders) {
            String url = sourceFolder.getUrl();
            if (url.contains(Constants.GENERATED)
                    || url.contains(Constants.BUILD)
                    || url.endsWith(Constants.RES)
                    || url.endsWith(Constants.RESOURCES)) {
                continue;
            }
            if (sourceFolder.getPath().equals(sourceFolderName)) {
                selectedIndex = currentIndex;
            }
            model.addElement(sourceFolder);
            currentIndex++;
        }
        this.sourceFolders.setModel(model);
        this.sourceFolders.setSelectedIndex(selectedIndex);
    }

    private void onOK() {
        // add your code here
        onCancel();

        VirtualFile baseFolder = sourceFolders.getSelectedValue();

        //Save state
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance(project);
        propertiesComponent.setValue(PROPERTY_SOURCE_FOLDER_NAME, baseFolder.getPath());

        LayoutFileSelectorDialog layoutFileSelectorDialog = new LayoutFileSelectorDialog(project, baseFolder);
        layoutFileSelectorDialog.pack();
        layoutFileSelectorDialog.setLocationRelativeTo(null);
        layoutFileSelectorDialog.setTitle("Select layout file...");
        layoutFileSelectorDialog.setResizable(true);
        layoutFileSelectorDialog.setVisible(true);
    }

    private void onCancel() {
        setVisible(false);
        dispose();
    }
}
