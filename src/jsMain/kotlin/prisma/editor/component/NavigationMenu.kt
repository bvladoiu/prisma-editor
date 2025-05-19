package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.pages.Page as EditorPage // Alias to avoid conflict
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography
import kotlin.js.JSON


class NavigationMenu(
    var brand: String = "Prisma-Software",
    var items: List<String> = listOf("Home", "Press", "Dev-Blog", "Company")
) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        return document.create.nav {
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@NavigationMenu
            a {
                href = "#"
                attributes[BRAND_TAG] = ""
                attributes[Typography.HEADLINE] = ""
                +brand
            }
            ul {
                attributes[MENU_TAG] = ""
                items.forEach { item ->
                    li {
                        a {
                            href = "#"
                            attributes[Typography.MENU_LABEL] = ""
                            +item
                        }
                    }
                }
            }
        }
    }

    fun buildHtml(pages: List<EditorPage>, currentPageTag: String): String {
        return createHTML().nav {
            attributes[TAG] = ""
            a {
                href = "#" // Brand link can be # or home page
                attributes[BRAND_TAG] = ""
                attributes[Typography.HEADLINE] = ""
                +brand
            }
            ul {
                attributes[MENU_TAG] = ""
                pages.forEach { page ->
                    li {
                        a(href = page.tag + ".html", classes = if (page.tag == currentPageTag) "active" else null) {
                            attributes[Typography.MENU_LABEL] = ""
                            +page.name
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
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG")
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
                    backgroundColor = Theme.shadowHeavy
                    position = "fixed"
                    top = "0"
                    left = "0"
                    right = "0"
                    zIndex = "1000"
                    display = "flex"
                    justifyContent = "space-between"
                    alignItems = "center"
                    padding = Theme.spacing
                    maxWidth = "1200px"
                    margin = "0 auto"
                },

                "[$BRAND_TAG]" to {
                    color = Theme.primary
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
                }
            )
        }
    }
}
