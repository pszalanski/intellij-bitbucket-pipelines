package com.github.pszalanski.intellijbitbucketpipelines

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.layout.panel
import javax.swing.JPanel


class PipelinesToolWindow {

    val content: JPanel
    private var text: String = ""
    private var text2: String = ""

    init {
        content = panel {
            row {
                // These two components will occupy two columns in the grid
                label("Test")
                textField({ text }, { v -> text = v})

                // These two components will be placed in the same grid cell
                cell {
                    label("Another")
                    textField({ text2 }, { v -> text2 = v})
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
