package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.pages.Page as EditorPage // Alias to avoid conflict
import prisma.editor.Link
import prisma.editor.Config
import prisma.editor.EditorJs
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography

class NavigationMenu(
    private val editor: EditorJs,
    var brand: String = "Prisma-Software",
    var items: List<String> = listOf("Home", "Press", "Dev-Blog", "Company"),
) {

    var rootElement: HTMLElement? = null

    init {
        load() // Load data on initialization
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

    fun buildEditorDom(): HTMLElement {
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

    fun buildStaticHtml(): String {
        // For static export, we might want to include actual links based on pages
        // This is a simplified version that just outputs the current items
        return createHTML().ul { items.forEach { item -> li { a(href = "#") { +item } } } }
    }

    fun buildHtml(links: List<Link>, currentLinkUrl: String): String {
        return createHTML().nav {
            attributes[TAG] = ""
            a {
                // Brand link can be # or home page - using # for now
                href = "#"
                attributes[BRAND_TAG] = ""
                attributes[Typography.HEADLINE] = ""
                +brand
            }
            ul {
                attributes[MENU_TAG] = ""
                links.forEach { link ->
                    li {
                        // Use link.url for the href and link.localizedName for the text
                        a(href = "/" + link.url, classes = if (link.url == currentLinkUrl) "active" else null) {
                            attributes[Typography.MENU_LABEL] = ""
                            +link.localizedName
                        }
                    }
                }
            }
        }
    }

    fun renderTo(parentElement: HTMLElement) {
        rootElement = buildEditorDom()
        parentElement.appendChild(rootElement!!)
    }

    fun refreshDom() {
        val newElement = buildEditorDom()
        rootElement?.parentElement?.replaceChild(newElement, rootElement!!)
        rootElement = newElement
    }

    fun detach() {
        rootElement?.remove()
        rootElement = null
    }

    fun load() {
        // Placeholder for loading data
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG")
        // Example: Load from local storage or an API
        // val jsonData = localStorage.getItem("data:${Config.currentSite}_${Config.currentLanguage}_$TAG")
        // if (jsonData != null) {
        //     val data = JSON.parse<dynamic>(jsonData)
        //     brand = data.brand ?: brand
        //     items = data.items?.unsafeCast<List<String>>() ?: items
        // }
    }

    fun commit() {
        val data = mapOf(
            "brand" to brand,
            "items" to items
        )
        val jsonData = JSON.stringify(data)
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }
    fun set(data: dynamic) {
        brand = data.brand ?: brand
        items = data.items?.unsafeCast<List<String>>() ?: items
        refreshDom()
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
