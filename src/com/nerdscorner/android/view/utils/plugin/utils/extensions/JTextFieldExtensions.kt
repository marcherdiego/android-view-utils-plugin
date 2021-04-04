package com.nerdscorner.android.view.utils.plugin.utils.extensions

import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.PlainDocument

fun JTextField.addTextListener(listener: JTextField.() -> Unit = {}) {
    document.addDocumentListener(object : DocumentListener {
        override fun changedUpdate(e: DocumentEvent?) {
            listener()
        }

        override fun insertUpdate(e: DocumentEvent?) {
            listener()
        }

        override fun removeUpdate(e: DocumentEvent?) {
            listener()
        }
    })
}

fun JTextField.setTextNoNotify(text: String) {
    val documentListeners = (document as? PlainDocument)?.documentListeners
    documentListeners?.forEach {
        document.removeDocumentListener(it)
    }
    this.text = text
    documentListeners?.forEach {
        document.addDocumentListener(it)
    }
    repaint()
}
