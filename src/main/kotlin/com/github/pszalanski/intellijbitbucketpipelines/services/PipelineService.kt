package com.github.pszalanski.intellijbitbucketpipelines.services

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.pszalanski.intellijbitbucketpipelines.model.Page
import com.github.pszalanski.intellijbitbucketpipelines.settings.AppSettingsState
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.Logger
import khttp.responses.Response
import khttp.structures.authorization.BasicAuthorization

@Service
class PipelineService {

    private val repositoryUrl = "https://api.bitbucket.org/2.0/repositories/ecoprojectsglobal/contribute-app"
    private val logger = Logger.getInstance(PipelineService::class.java)

    fun canLoginToBitbucket(): Boolean {
        val settings = AppSettingsState.instance
        val response = khttp.get(url = repositoryUrl, auth = BasicAuthorization(settings.user, settings.password))
        return response.statusCode == 200
    }

    fun getPipelinePage(pageNum: Int = 1): Page {
        val settings = AppSettingsState.instance
        val response : Response = khttp.get(
                url = "${repositoryUrl}/pipelines/?page=${pageNum}&sort=-created_on",
                auth = BasicAuthorization(settings.user, settings.password)
        )
        val mapper = jacksonObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false)
        val pipelinePage: Page = mapper.readValue(response.text)
        logger.warn("Got ${pipelinePage.values.size} pipeline entries")

        return pipelinePage
    }
}
