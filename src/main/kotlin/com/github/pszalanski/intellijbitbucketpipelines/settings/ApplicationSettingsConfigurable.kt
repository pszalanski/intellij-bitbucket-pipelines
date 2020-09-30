package com.github.pszalanski.intellijbitbucketpipelines.settings

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.options.Configurable
import javax.swing.JComponent


class ApplicationSettingsConfigurable : Configurable {

    private var appSettingsComponent: AppSettingsComponent? = null

    override fun createComponent(): JComponent? {
        appSettingsComponent = AppSettingsComponent()
        return appSettingsComponent!!.getPanel()
    }

    override fun isModified(): Boolean {
        val settings: AppSettingsState = AppSettingsState.instance
        var modified: Boolean = !appSettingsComponent?.userNameText.equals(settings.user)
        modified = modified or !appSettingsComponent?.passwordText.equals(settings.password)
        return modified
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return appSettingsComponent?.getPreferredFocusedComponent()
    }

    override fun apply() {
        val settings: AppSettingsState = AppSettingsState.instance
        settings.user = appSettingsComponent?.userNameText ?: ""

        val credentialAttributes = CredentialAttributes(
                generateServiceName("Bitbucket", "password"))
        PasswordSafe.instance.setPassword(credentialAttributes, appSettingsComponent?.passwordText)
        settings.password = appSettingsComponent?.passwordText ?: ""
    }

    override fun getDisplayName(): String {
        return "Bitbucket Pipelines"
    }

    override fun reset() {
        val settings: AppSettingsState = AppSettingsState.instance
        appSettingsComponent?.userNameText = settings.user
        appSettingsComponent?.passwordText = settings.password
    }

    override fun disposeUIResources() {
        appSettingsComponent = null
    }
}
