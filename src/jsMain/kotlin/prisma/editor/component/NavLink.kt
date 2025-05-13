package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography
import kotlin.js.JSON


class NavLink(var text: String, var route: String, var icon: String = "", var selected: Boolean = false) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        return document.create.li {
            attributes[ITEM_TAG] = ""
            asDynamic().kotlinInstance = this@NavLink

            a {
                attributes[TAG] = ""
                if (selected) {
                    attributes[SELECTED_TAG] = ""
                }
                href = "#"
                onClickFunction = { event ->
                    event.preventDefault()
                    Drawer.navigateTo(route)
                }

                if (icon.isNotEmpty()) {
                    span {
                        attributes[ICON_TAG] = ""
                        attributes["class"] = "material-symbols-outlined"
                        +icon
                    }
                }

                span {
                    attributes[TEXT_TAG] = ""
                    +text
                }
            }
        }
    }

    fun commit() {
        val data = mapOf(
            "text" to text,
            "route" to route,
            "icon" to icon,
            "selected" to selected
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
    }

    fun set(data: dynamic) {
        text = data.text ?: text
        route = data.route ?: route
        icon = data.icon ?: icon
        selected = data.selected ?: selected
        refresh()
    }

    fun refresh() {
        val existingElement = document.querySelector("[$ITEM_TAG]")
        if (existingElement != null) {
            existingElement.parentElement?.replaceChild(preview(), existingElement)
        } else {
            console.warn("No existing element with attribute [$ITEM_TAG] found to refresh.")
            document.body?.appendChild(preview())
        }
    }

    companion object {
        const val TAG = "nav-link"
        const val ITEM_TAG = "nav-item"
        const val SELECTED_TAG = "nav-link-selected"
        const val ICON_TAG = "nav-icon"
        const val TEXT_TAG = "nav-text"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$ITEM_TAG]" to {
                    padding = "0"
                    margin = "0"
                },

                "[$TAG]" to {
                    padding = Theme.spacing
                    cursor = "pointer"
                    backgroundColor = Theme.white
                    display = "flex"
                    alignItems = "center"
                    textDecoration = "none"
                    color = "black"
                    fontFamily = Typography.defaultFontFamily
                    setProperty("transition", "background-color 0.2s ease")
                },

                "[$TAG]:hover" to {
                    backgroundColor = Theme.lightGray
                },

                "[$SELECTED_TAG]" to {
                    backgroundColor = Theme.primary
                    color = Theme.white
                },

                "[$ICON_TAG]" to {
                    marginRight = "8px"
                    fontSize = Typography.fontMd
                    display = "inline-flex"
                    alignItems = "center"
                },

                "[$TEXT_TAG]" to {
                    fontSize = Typography.fontSm
                }
            )
        }
    }
}
