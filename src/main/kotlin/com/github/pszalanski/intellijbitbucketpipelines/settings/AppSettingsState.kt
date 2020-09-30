package com.github.pszalanski.intellijbitbucketpipelines.settings

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.util.xmlb.XmlSerializerUtil


@State(
        name = "org.intellij.sdk.settings.AppSettingsState",
        storages = [Storage("BitbucketPipelines.xml")]
)
class AppSettingsState : PersistentStateComponent<AppSettingsState> {
    var user = ""

    @Transient
    var password = ""

    override fun getState(): AppSettingsState? {
        return this
    }

    override fun loadState(state: AppSettingsState) {
        XmlSerializerUtil.copyBean(state, this)

        val credentialAttributes = CredentialAttributes(
                generateServiceName("Bitbucket", "password"))
        password = PasswordSafe.instance.getPassword(credentialAttributes) ?: ""
    }

    companion object {
        val instance: AppSettingsState
            get() = service()
    }
}
