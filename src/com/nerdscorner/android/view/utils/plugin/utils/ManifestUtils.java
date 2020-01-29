package com.nerdscorner.android.view.utils.plugin.utils;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.intellij.openapi.vfs.VirtualFile;
import com.nerdscorner.android.view.utils.plugin.domain.Manifest;


import static com.nerdscorner.android.view.utils.plugin.utils.Constants.MANIFEST_FILE;

public class ManifestUtils {
    @Nullable
    public static Manifest getManifest(VirtualFile sourceFolder) {
        try {
            sourceFolder.refresh(false, true);
            JAXBContext jaxbContext = JAXBContext.newInstance(Manifest.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            VirtualFile manifestFile = sourceFolder.getParent().findChild(MANIFEST_FILE);
            return (Manifest) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(manifestFile.contentsToByteArray()));
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getManifestString(VirtualFile sourceFolder) {
        try {
            sourceFolder.refresh(false, true);
            VirtualFile manifestFile = sourceFolder.getParent().findChild(MANIFEST_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(manifestFile.contentsToByteArray())));
            String line;
            StringBuilder manifestFileBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                manifestFileBuilder
                        .append(line)
                        .append(System.lineSeparator());
            }
            return manifestFileBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
