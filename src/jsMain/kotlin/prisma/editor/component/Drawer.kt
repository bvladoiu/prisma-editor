package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography
import kotlin.js.JSON


class Drawer(var items: List<NavLink> = emptyList(), var opened: Boolean = false) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        val nav = document.create.nav {
            id = "drawer"
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@Drawer
            if (opened) {
                attributes["style"] = "transform: translateX(0px);"
            }
        }

        val itemsList = document.create.ul {
            attributes[ITEMS_TAG] = ""
        }

        items.forEach { navLink ->
            itemsList.appendChild(navLink.preview())
        }

        nav.appendChild(itemsList)

        return nav
    }

    fun commit() {
        val data = mapOf(
            "items" to items,
            "opened" to opened
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
    }

    fun set(data: dynamic) {
        items = data.items?.unsafeCast<List<NavLink>>() ?: items
        opened = data.opened ?: opened
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
        const val TAG = "drawer"
        const val HEADER_TAG = "drawer-header"
        const val TITLE_TAG = "drawer-title"
        const val ITEMS_TAG = "drawer-items"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    width = "240px"
                    height = "100%"
                    backgroundColor = "white"
                    boxShadow = "2px 0 4px rgba(0,0,0,0.2)"
                    setProperty("overflow", "auto")
                    setProperty("transform", "translateX(-240px)")
                    setProperty("transition", "transform 0.3s ease-in-out")
                },

                "[$HEADER_TAG]" to {
                    padding = Theme.spacing
                    backgroundColor = "#7D3DF3"
                    color = Theme.white
                },

                "[$TITLE_TAG]" to {
                    margin = "0"
                    fontSize = Typography.fontSm
                    fontFamily = Typography.defaultFontFamily
                },

                "[$ITEMS_TAG]" to {
                    listStyleType = "none"
                    padding = "0"
                    margin = "0"
                }
            )
        }
    }
}
