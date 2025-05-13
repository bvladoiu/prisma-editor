package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography
import kotlin.js.JSON


class NavigationMenu(
    var brand: String = "Prisma-Software",
    var items: List<String> = listOf("Home", "News", "Blog", "Company")
) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        return document.create.nav {
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@NavigationMenu
            div {
                a {
                    href = "#"
                    attributes[BRAND_TAG] = ""
                    +brand
                }
                ul {
                    attributes[MENU_TAG] = ""
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

    fun commit() {
        val data = mapOf(
            "brand" to brand,
            "items" to items
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
    }

    fun set(data: dynamic) {
        brand = data.brand ?: brand
        items = data.items?.unsafeCast<List<String>>() ?: items
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
        const val TAG = "navigation-menu"
        const val BRAND_TAG = "navigation-brand"
        const val MENU_TAG = "navigation-menu-list"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    backgroundColor = "rgba(0, 0, 0, 0.8)"
                    position = "fixed"
                    top = "0"
                    left = "0"
                    right = "0"
                    zIndex = "1000"
                },

                "[$TAG] > div" to {
                    display = "flex"
                    justifyContent = "space-between"
                    alignItems = "center"
                    padding = Theme.spacing
                    maxWidth = "1200px"
                    margin = "0 auto"
                },

                "[$BRAND_TAG]" to {
                    color = Theme.primary
                    fontSize = Typography.fontMd
                    fontWeight = Typography.fontWeightBold
                    textDecoration = "none"
                },

                "[$MENU_TAG]" to {
                    display = "flex"
                    listStyleType = "none"
                    margin = "0"
                    padding = "0"
                    setProperty("gap", Theme.spacing)
                },

                "[$MENU_TAG] a" to {
                    color = Theme.white
                    textDecoration = "none"
                    fontSize = Typography.fontSm
                }
            )
        }
    }
}
