package com.github.pszalanski.intellijbitbucketpipelines.settings

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPasswordField
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel


class AppSettingsComponent {

    private val atlassianUsername = JBTextField()
    private val atlassianPassword = JBPasswordField()

    private var myMainPanel: JPanel? = FormBuilder.createFormBuilder()
            .addComponent(JBLabel("Atlassian credentials"))
            .addLabeledComponent(JBLabel("Username:"), atlassianUsername, 1, false)
            .addLabeledComponent(JBLabel("Password:"), atlassianPassword, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel

    fun getPanel(): JPanel? {
        return myMainPanel
    }

    fun getPreferredFocusedComponent(): JComponent? {
        return atlassianUsername
    }

    var userNameText: String
        get() {
            return atlassianUsername.text
        }
        set(value) {
            atlassianUsername.text = value
        }

    var passwordText: String?
        get() {
            return String(atlassianPassword.password)
        }
        set(value) {
            if (value != null && value.isNotEmpty()) {
                atlassianPassword.setPasswordIsStored(true)
            }
        }
}
