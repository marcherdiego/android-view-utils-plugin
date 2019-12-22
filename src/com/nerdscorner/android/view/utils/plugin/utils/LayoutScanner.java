package com.nerdscorner.android.view.utils.plugin.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.intellij.openapi.vfs.VirtualFile;
import com.nerdscorner.android.view.utils.plugin.domain.AndroidWidget;

public class LayoutScanner {
    private static final String KEY_ELEMENT_ID = "android:id";

    @Nullable
    public static List<AndroidWidget> findWidgets(VirtualFile layoutFile) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(layoutFile.getInputStream());
            Element root = document.getDocumentElement();
            return findWidgets(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<AndroidWidget> findWidgets(Element element) {
        List<AndroidWidget> result = new ArrayList<>();
        String elementAttributeId = element.getAttribute(KEY_ELEMENT_ID);
        if (!StringUtils.isNullOrEmpty(elementAttributeId)) {
            String elementId = StringUtils.extractElementId(elementAttributeId);
            String elementClass = element.getTagName();
            String elementType = StringUtils.extractSimpleClassName(elementClass);
            result.add(new AndroidWidget(elementId, elementType, elementClass));
        }
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); ++i) {
            Node node = children.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                result.addAll(
                        findWidgets((Element) node)
                );
            }
        }
        return result;
    }
}
