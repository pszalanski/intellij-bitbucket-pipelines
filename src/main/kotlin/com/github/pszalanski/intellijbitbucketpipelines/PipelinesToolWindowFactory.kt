package com.github.pszalanski.intellijbitbucketpipelines

import com.github.pszalanski.intellijbitbucketpipelines.services.PipelineService
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.layout.panel
import com.intellij.ui.treeStructure.Tree
import javax.swing.JPanel
import javax.swing.tree.DefaultMutableTreeNode


class PipelinesToolWindow {

    val content: JPanel

    init {
        val pipelinesService = service<PipelineService>()

        if (pipelinesService.canLoginToBitbucket()) {
            val root = DefaultMutableTreeNode("Pipeline runs")
            for (run in pipelinesService.getPipelinePage().values) {
                val runSummary = "#${run.build_number} - ${pipelinesService.getCommitMessage(run.target.commit.hash)}"
                root.add(DefaultMutableTreeNode(runSummary))
            }

            content = panel {
                row {
                    scrollPane(Tree(root))
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
