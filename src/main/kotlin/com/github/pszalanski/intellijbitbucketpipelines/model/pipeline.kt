package com.github.pszalanski.intellijbitbucketpipelines.model

data class Page (
        var page: Int,
        var pagelen: Int,
        var values: List<PipelineRun>)

data class PipelineRun (
        var type: String,
        var uuid: String,
        var repository: Repository,
        var state: PipelineState,
        var build_number: Int,
        var created_on: String,
        var completed_on: String?
)

data class PipelineState (
        var name: String,
        var type: String,
        var result: PipelineResult?,
        var stage: PipelineStage?
)

class PipelineStage (
        var name: String,
        var type:String
)

data class PipelineResult (
        var name: String,
        var type:String
)

data class Repository (
        var type: String,
        var name: String,
        var full_name: String,
        var uuid: String
)
