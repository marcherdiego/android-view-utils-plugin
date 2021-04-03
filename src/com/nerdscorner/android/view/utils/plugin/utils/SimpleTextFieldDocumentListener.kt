package com.nerdscorner.android.view.utils.plugin.utils

import javax.swing.SwingUtilities
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

abstract class SimpleTextFieldDocumentListener : DocumentListener {
    override fun insertUpdate(e: DocumentEvent) {
        delayedUpdate()
    }

    override fun removeUpdate(e: DocumentEvent) {
        delayedUpdate()
    }

    override fun changedUpdate(e: DocumentEvent) {
        delayedUpdate()
    }

    private fun delayedUpdate() {
        SwingUtilities.invokeLater {
            onUpdate()
        }
    }

    abstract fun onUpdate()
}
