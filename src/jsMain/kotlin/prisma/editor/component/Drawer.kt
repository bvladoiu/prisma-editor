package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography


class Drawer(var items: List<NavLink> = emptyList(), var opened: Boolean = false) {
    fun preview(): HTMLElement {
        val nav = document.create.nav {
            id = "drawer"
            attributes["drawer"] = ""
            if (opened) {
                attributes["style"] = "transform: translateX(0px);"
            }
        }

        val itemsList = document.create.ul {
            attributes["drawer-items"] = ""
        }

        items.forEach { navLink ->
            itemsList.appendChild(navLink.preview())
        }

        nav.appendChild(itemsList)

        return nav
    }

    companion object {
        const val TAG = "drawer"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[drawer]" to {
                    width = "240px"
                    height = "100%"
                    backgroundColor = "white"
                    boxShadow = "2px 0 4px rgba(0,0,0,0.2)"
                    setProperty("overflow", "auto")
                    setProperty("transform", "translateX(-240px)")
                    setProperty("transition", "transform 0.3s ease-in-out")
                },

                "[drawer-header]" to {
                    padding = Theme.spacing
                    backgroundColor = "#7D3DF3"
                    color = Theme.white
                },

                "[drawer-title]" to {
                    margin = "0"
                    fontSize = Typography.fontSm
                    fontFamily = Typography.defaultFontFamily
                },

                "[drawer-items]" to {
                    listStyleType = "none"
                    padding = "0"
                    margin = "0"
                }
            )
        }
    }
}
