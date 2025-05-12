package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography

/**
 * Represents a navigation link component for the drawer.
 */
class NavLink(var text: String, var route: String, var icon: String = "", var selected: Boolean = false) {
    fun preview(): HTMLElement {
        return document.create.li {
            attributes["nav-item"] = ""

            a {
                attributes["nav-link"] = ""
                if (selected) {
                    attributes["nav-link-selected"] = ""
                }
                href = "#"
                onClickFunction = { event ->
                    event.preventDefault()
                    val routeJs = route // Capture the route in a local variable
                    js("navigateTo(arguments[0])")(routeJs)
                }

                if (icon.isNotEmpty()) {
                    span {
                        attributes["nav-icon"] = ""
                        attributes["class"] = "material-symbols-outlined"
                        +icon
                    }
                }

                span {
                    attributes["nav-text"] = ""
                    +text
                }
            }
        }
    }

    companion object {
        const val TAG = "nav-link"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[nav-item]" to {
                    padding = "0"
                    margin = "0"
                },

                "[nav-link]" to {
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

                "[nav-link]:hover" to {
                    backgroundColor = Theme.lightGray
                },

                "[nav-link-selected]" to {
                    backgroundColor = Theme.primary
                    color = Theme.white
                },

                "[nav-icon]" to {
                    marginRight = "8px"
                    fontSize = Typography.fontMd
                    display = "inline-flex"
                    alignItems = "center"
                },

                "[nav-text]" to {
                    fontSize = Typography.fontSm
                }
            )
        }
    }
}
