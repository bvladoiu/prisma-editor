package prisma.editor.pages

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.Element
import prisma.editor.component.SectionsInitialData
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
    open var tag: String = TAG
    open var name: String = ""

    // Store component data for serialized page creation
    var componentData: dynamic = null

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

        // Add sections from componentData if available
        if (componentData != null) {
            try {
                // Iterate through sections in componentData
                for (key in js("Object").keys(componentData)) {
                    val sectionData = this.componentData[key]
                    val title = sectionData.title as? String ?: ""
                    val isDivider = sectionData.isDivider as? Boolean ?: false
                    val sectionId = sectionData.sectionId as? String

                    addSection(container, title, isDivider, sectionId)
                }
            } catch (e: Exception) {
                console.error("Error creating sections from component data", e)
            }
        }

        // Load scripts
        ScriptLoader.addScriptsToBody()

        return container
    }

    /**
     * Adds a section to the page container.
     * @param container The page container element.
     * @param title The title of the section.
     * @param isDivider Whether the section has a divider.
     * @param sectionId Optional identifier for the section to use predefined content.
     * @return The created section element.
     */
    protected open fun addSection(container: HTMLElement, title: String, isDivider: Boolean = false, sectionId: String? = null): HTMLElement {
        // Create a section based on sectionId if provided, otherwise create a basic section
        val section = when (sectionId) {
            "coreExpertise" -> SectionsInitialData.createCoreExpertiseSection()
            "keywordStrip" -> SectionsInitialData.createKeywordStripSection()
            "whyWorkWithUs" -> SectionsInitialData.createWhyWorkWithUsSection()
            "latestUpdates" -> SectionsInitialData.createLatestUpdatesSection()
            else -> prisma.editor.component.Section(
                initialTitle = title,
                initialIsDivider = isDivider
            )
        }

        val sectionElement = section.buildHtml()
        container.appendChild(sectionElement)
        return sectionElement
    }

    /**
     * Removes a section from the page container.
     * @param container The page container element.
     * @param sectionElement The section element to remove.
     */
    protected open fun removeSection(container: HTMLElement, sectionElement: HTMLElement) {
        container.removeChild(sectionElement)
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
            "tag" to tag,
            "name" to name
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
        const val HOME_TAG = "home-page"
        const val CATALOG_TAG = "catalog-page"

        /**
         * Creates a page with the specified tag and name.
         * @param tag The tag for the page.
         * @param name The name of the page.
         * @return The created page instance.
         */
        fun create(tag: String, name: String): Page {
            val page = Page()
            page.tag = tag
            page.name = name

            // Create empty component data
            page.componentData = kotlin.js.json()

            return page
        }

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
