package com.github.pszalanski.intellijbitbucketpipelines

import com.github.pszalanski.intellijbitbucketpipelines.services.PipelineService
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.layout.panel
import javax.swing.JPanel


class PipelinesToolWindow {

    val content: JPanel

    init {
        val pipelinesService = service<PipelineService>()

        if (pipelinesService.canLoginToBitbucket()) {
            val pipelines = pipelinesService.getPipelinePage();
            content = panel {
                row {
                    scrollPane(com.intellij.ui.layout.panel {
                        for (pipelineRun in pipelines.values) {
                            row {
                                label("#" + pipelineRun.build_number)
                                label("Status: " + pipelineRun.state.name)
                                val result = pipelineRun.state.result
                                if (result != null) {
                                    label("Result: ${result.name}")
                                }

                                val stage = pipelineRun.state.stage
                                if (stage != null) {
                                    label("Stage: ${stage.name}")
                                }
                            }
                        }
                    })
                }
            }
        } else {
            content = panel {
                row {
                    label("Could not login to bitbucket!")
                }
            }
        }
    }
}


class PipelinesToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = PipelinesToolWindow()
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content: Content = contentFactory.createContent(myToolWindow.content, "", false)
        toolWindow.contentManager.addContent(content)
    }
}
