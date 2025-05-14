package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.*
import kotlin.js.JSON

class EditorScaffold {
    init {
        load()
    }

    fun preview(): HTMLElement {
        val scaffold = document.create.div {
            id = "editor-scaffold"
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@EditorScaffold
        }

        val appBar = createAppBar()
        scaffold.appendChild(appBar)

        val contentArea = document.create.div {
            id = "content-area"
            attributes[CONTENT_AREA_TAG] = ""
        }
        val mainContent = document.create.main {
            id = "main-content"
            attributes[MAIN_CONTENT_TAG] = ""
        }

        contentArea.appendChild(mainContent)
        scaffold.appendChild(contentArea)

        return scaffold
    }

    private fun createAppBar(): HTMLElement {
        val header = document.create.header {
            attributes[APP_BAR_TAG] = ""

            button {
                attributes[MENU_BUTTON_TAG] = ""
                id = "menu-button"
                attributes["onclick"] = "prisma.editor.component.Drawer.toggleDrawer()"

                // Use Icon component's render() method instead of directly creating a span
                val menuIcon = Icon("menu")
                menuIcon.render().invoke(this)
            }
            h1 {
                attributes[APP_BAR_TITLE_TAG] = ""
                classes = setOf(Typography.HEADLINE)
                +"Prisma Editor"
            }
        }

        return header
    }

    fun commit() {
        val data = mapOf(
            "scaffoldId" to "editor-scaffold"
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
    }

    fun set(data: dynamic) {
        // No properties to set in this component
        refresh()
    }

    fun refresh() {
        val existingElement = document.querySelector("[$TAG]")
        if (existingElement != null) {
            existingElement.parentElement?.replaceChild(preview(), existingElement)
        } else {
            console.warn("No existing element with attribute [$TAG] found to refresh.")
            document.body?.appendChild(preview())
        }
    }

    companion object {
        const val TAG = "editor-scaffold"
        const val CONTENT_AREA_TAG = "content-area"
        const val MAIN_CONTENT_TAG = "main-content"
        const val APP_BAR_TAG = "app-bar"
        const val MENU_BUTTON_TAG = "menu-button"
        const val MENU_ICON_TAG = "menu-icon"
        const val APP_BAR_TITLE_TAG = "app-bar-title"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    display = "flex"
                    flexDirection = "column"
                    height = "100vh"
                    width = "100%"
                },

                "[$CONTENT_AREA_TAG]" to {
                    display = "flex"
                    flexGrow = "1"
                    setProperty("overflow", "hidden")
                },

                "[$MAIN_CONTENT_TAG]" to {
                    flexGrow = "1"
                    padding = Theme.spacing
                    setProperty("overflow", "auto")
                },

                "[$APP_BAR_TAG]" to {
                    display = "flex"
                    alignItems = "center"
                    padding = "${Theme.spacing} ${Theme.spacing}"
                    backgroundColor = Theme.scaffoldPurple
                    color = Theme.white
                    height = "56px"
                    setProperty("box-shadow", "0 ${Theme.spacing} ${Theme.spacing} ${Theme.shadowLight}")
                },

                "[$MENU_BUTTON_TAG]" to {
                    backgroundColor = "transparent"
                    border = "0"
                    color = Theme.white
                    cursor = "pointer"
                    padding = Theme.spacing
                    marginRight = Theme.spacing
                },

                "[$MENU_ICON_TAG]" to {
                    fontSize = Theme.iconSize
                    lineHeight = "1"
                },

                "[$APP_BAR_TITLE_TAG]" to {
                    margin = "0"
                }
            )
        }
    }
}
