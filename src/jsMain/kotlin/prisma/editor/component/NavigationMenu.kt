package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import web.html.HTMLElement
import org.w3c.dom.HTMLElement as W3CHTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography


class NavigationMenu(
    var brand: String = "Prisma-Software",
    var items: List<String> = listOf("Home", "News", "Blog", "Company")
) : HTMLElement {
    fun create(): W3CHTMLElement {
        return document.create.nav {
            attributes["navigation-menu"] = ""
            div {
                a {
                    href = "#"
                    classes = setOf("brand")
                    +brand
                }
                ul {
                    items.forEach { item ->
                        li {
                            a {
                                href = "#"
                                +item
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "navigation-menu"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[navigation-menu]" to {
                    backgroundColor = "rgba(0, 0, 0, 0.8)"
                    position = "fixed"
                    top = "0"
                    left = "0"
                    right = "0"
                    zIndex = "1000"
                },

                "[navigation-menu] > div" to {
                    display = "flex"
                    justifyContent = "space-between"
                    alignItems = "center"
                    padding = Theme.spacing
                    maxWidth = "1200px"
                    margin = "0 auto"
                },

                "[navigation-menu] a.brand" to {
                    color = Theme.primary
                    fontSize = Typography.fontMd
                    fontWeight = Typography.fontWeightBold
                    textDecoration = "none"
                },

                "[navigation-menu] ul" to {
                    display = "flex"
                    listStyleType = "none"
                    margin = "0"
                    padding = "0"
                    setProperty("gap", Theme.spacing)
                },

                "[navigation-menu] ul a" to {
                    color = Theme.white
                    textDecoration = "none"
                    fontSize = Typography.fontSm
                }
            )
        }
    }
}
