package com.github.pszalanski.intellijbitbucketpipelines.services

import com.intellij.openapi.project.Project
import com.github.pszalanski.intellijbitbucketpipelines.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
