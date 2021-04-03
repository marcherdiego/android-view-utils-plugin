package com.nerdscorner.android.view.utils.plugin.utils

import com.nerdscorner.android.view.utils.plugin.ui.DimensionsWindow
import com.nerdscorner.android.view.utils.plugin.utils.DimensionUtils.convertValue
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class TextFieldDocumentListener(private val originalField: JTextField,
                                private val targetField: JTextField,
                                private val originalFactor: Float,
                                private val targetFactor: Float
) : DocumentListener {
    override fun insertUpdate(e: DocumentEvent) {
        onUpdate()
    }

    override fun removeUpdate(e: DocumentEvent) {
        onUpdate()
    }

    override fun changedUpdate(e: DocumentEvent) {
        onUpdate()
    }

    private fun onUpdate() {
        DimensionsWindow.setCurrentEditingField(originalField)
        if (enabled.not() || originalField.text.endsWith(".")) {
            return
        }
        val mdpiDimension = convertValue(originalField.text, originalFactor, targetFactor)
        targetField.text = mdpiDimension.realDimension
    }

    companion object {
        private var enabled = true

        @JvmStatic
        fun setEnabled(enabled: Boolean) {
            Companion.enabled = enabled
        }
    }
}
