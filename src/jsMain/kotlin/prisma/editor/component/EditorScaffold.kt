package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.*
class EditorScaffold {
    fun create(): HTMLElement {
        val scaffold = document.create.div {
            id = "editor-scaffold"
            attributes["editor-scaffold"] = ""
        }

        val appBar = createAppBar()
        scaffold.appendChild(appBar)

        val contentArea = document.create.div {
            id = "content-area"
            attributes["content-area"] = ""
        }
        val mainContent = document.create.div {
            id = "main-content"
            attributes["main-content"] = ""
        }

        contentArea.appendChild(mainContent)
        scaffold.appendChild(contentArea)

        return scaffold
    }

    private fun createAppBar(): HTMLElement {
        val header = document.create.header {
            attributes["app-bar"] = ""

            button {
                attributes["menu-button"] = ""
                id = "menu-button"

                span {
                    id = "menu-icon"
                    attributes["class"] = "material-symbols-outlined"
                    attributes["menu-icon"] = ""
                    +"menu"
                }
            }
            h1 {
                attributes["app-bar-title"] = ""
                +"Prisma Editor"
            }
        }

        return header
    }

    companion object {
        const val TAG = "editor-scaffold"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[editor-scaffold]" to {
                    display = "flex"
                    flexDirection = "column"
                    height = "100vh"
                    width = "100%"
                },

                "[content-area]" to {
                    display = "flex"
                    flexGrow = "1"
                    setProperty("overflow", "hidden")
                },

                "[main-content]" to {
                    flexGrow = "1"
                    padding = Theme.spacing
                    setProperty("overflow", "auto")
                },

                "[app-bar]" to {
                    display = "flex"
                    alignItems = "center"
                    padding = "8px ${Theme.spacing}"
                    backgroundColor = "#6200EE"
                    color = Theme.white
                    height = "56px"
                    boxShadow = "0 2px 4px rgba(0,0,0,0.2)"
                },

                "[menu-button]" to {
                    backgroundColor = "transparent"
                    border = "0"
                    color = Theme.white
                    cursor = "pointer"
                    padding = "8px"
                    marginRight = Theme.spacing
                },

                "[menu-icon]" to {
                    fontSize = "24px"
                    lineHeight = "1"
                },

                "[app-bar-title]" to {
                    margin = "0"
                    fontSize = Typography.fontMd
                    fontWeight = "500"
                }
            )
        }
    }
}
