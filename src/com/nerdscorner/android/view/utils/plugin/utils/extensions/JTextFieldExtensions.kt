package com.nerdscorner.android.view.utils.plugin.utils.extensions

import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

fun JTextField.addTextListener(
        changedUpdate: () -> Unit = {},
        insertUpdate: () -> Unit = {},
        removeUpdate: () -> Unit = {}
) {
    document.addDocumentListener(object : DocumentListener {
        override fun changedUpdate(e: DocumentEvent?) {
            changedUpdate()
        }

        override fun insertUpdate(e: DocumentEvent?) {
            insertUpdate()
        }

        override fun removeUpdate(e: DocumentEvent?) {
            removeUpdate()
        }
    })
}

fun JTextField.addTextListener(listener: () -> Unit = {}) {
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

fun JTextField.addTextListener(listener: DocumentListener) {
    document.addDocumentListener(listener)
}
