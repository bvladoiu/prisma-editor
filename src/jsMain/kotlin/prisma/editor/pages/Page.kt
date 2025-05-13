package prisma.editor.pages

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.Element
import org.w3c.dom.HTMLLinkElement
import prisma.editor.css.Main
import kotlin.js.JSON

/**
 * Base class for all pages in the application.
 * Provides common functionality such as links to Google fonts and CSS/JS required by all pages.
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
        // Add Google Fonts
        addGoogleFonts()

        // Add main stylesheet
        Main.stylesheet()

        // Create the page container
        val container = document.create.div {
            attributes["page-container"] = ""
            attributes[tag] = ""
            asDynamic().kotlinInstance = this@Page
        }

        // Add the drawer to the page
        addDrawer(container)

        return container
    }

    /**
     * Adds the side drawer to the page container.
     * @param container The page container element.
     */
    protected fun addDrawer(container: HTMLElement) {
        val homeLink = prisma.editor.component.NavLink("Home", "home", "home", true)
        val drawer = prisma.editor.component.Drawer(listOf(homeLink))
        val drawerElement = drawer.preview()
        container.appendChild(drawerElement)
    }

    /**
     * Adds Google Fonts links to the document head.
     */
    private fun addGoogleFonts() {
        // Add Poppins font for headlines and display
        val poppinsLink = document.createElement("link") as HTMLElement
        poppinsLink.setAttribute("rel", "stylesheet")
        poppinsLink.setAttribute("href", "https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap")
        document.head?.appendChild(poppinsLink)

        // Add Roboto Flex variable font for regular text and actions
        val robotoFlexLink = document.createElement("link") as HTMLElement
        robotoFlexLink.setAttribute("rel", "stylesheet")
        robotoFlexLink.setAttribute("href", "https://fonts.googleapis.com/css2?family=Roboto+Flex:wght@400..700&display=swap")
        document.head?.appendChild(robotoFlexLink)

        // Add Material Symbols for icons
        val materialIconsLink = document.createElement("link") as HTMLElement
        materialIconsLink.setAttribute("rel", "stylesheet")
        materialIconsLink.setAttribute("href", "https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200")
        document.head?.appendChild(materialIconsLink)
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