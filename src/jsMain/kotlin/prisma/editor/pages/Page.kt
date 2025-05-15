package prisma.editor.pages

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.Element
import prisma.editor.css.FontsLoader
import prisma.editor.css.Main
import prisma.editor.css.ScriptLoader
import kotlin.js.JSON

/**
 * Base class for all pages in the application.
 * Provides common functionality such as CSS/JS required by all pages.
 * Note: Font loading is handled by FontsLoader within the Page component.
 */
open class Page {
    open val tag: String = TAG

    init {
        load()
    }

    /**
     * Creates the page container with common resources.
     * @return The page container element.
     */
    open fun create(): HTMLElement {
        // Add font links to head
        FontsLoader.addFontLinksToHead()

        // Add main stylesheet
        Main.stylesheet()

        // Create the page container
        val container = document.create.div {
            attributes["page-container"] = ""
            attributes["TAG"] = tag
            asDynamic().kotlinInstance = this@Page
        }

        // Add the drawer to the page
        addDrawer(container)

        // Load scripts
        ScriptLoader.addScriptsToBody()

        return container
    }

    /**
     * Adds the side drawer to the page container.
     * @param container The page container element.
     */
    protected fun addDrawer(container: HTMLElement) {
        val homeLink = prisma.editor.component.NavLink("Home", "home", "home", false)
        val catalogLink = prisma.editor.component.NavLink("Catalog", "catalog", "view_list", true)
        val drawer = prisma.editor.component.Drawer(listOf(homeLink, catalogLink))
        val drawerElement = drawer.preview()
        container.appendChild(drawerElement)
    }


    /**
     * Commits the page's state to be saved.
     */
    open fun commit() {
        val data = mapOf(
            "tag" to tag
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$tag", jsonData)
    }

    /**
     * Loads the page's state.
     */
    open fun load() {
        console.log("load:$tag")
    }

    /**
     * Sets the page's properties from data.
     */
    open fun set(data: dynamic) {
        // No properties to set in the base class
    }

    /**
     * Refreshes the page's view.
     */
    open fun refresh() {
        val existingElement = document.querySelector("[$tag]")
        if (existingElement != null) {
            existingElement.parentElement?.replaceChild(create(), existingElement)
        } else {
            console.warn("No existing element with attribute [$tag] found to refresh.")
        }
    }

    companion object {
        const val TAG = "page"

        /**
         * CSS rules for the page container.
         */
        fun cssRules(): List<prisma.editor.css.CssRuleDefinition> {
            return listOf(
                "[page-container]" to {
                    width = "100%"
                    minHeight = "100vh"
                }
            )
        }
    }
}
