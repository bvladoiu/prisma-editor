package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.Element
import prisma.editor.component.SectionsInitialData
import prisma.editor.css.FontsLoader
import prisma.editor.css.Main
import prisma.editor.css.CssRuleDefinition
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

 private val topBar = TopBar()

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

        val body = document.body!!

        // Add the top bar directly to the body
 body.innerHTML += topBar.buildStaticHtml()

        // Add the drawer to the page
        addDrawer(body)

        // Create and append the main element for sections
        val mainElement = document.createElement("main") as HTMLElement
 body.appendChild(mainElement)

        // Add sections from componentData if available
        if (componentData != null) {
 // This logic needs to be moved to buildEditorDom/buildStaticHtml or a dedicated render method
 // For now, we will add sections directly to the main element
            try {
                // Iterate through sections in componentData and add them to the main element
                for (key in js("Object").keys(componentData)) {
                    val sectionData = this.componentData[key]
                    val title = sectionData.title as? String ?: ""
                    val isDivider = sectionData.isDivider as? Boolean ?: false
                    val sectionId = sectionData.sectionId as? String

 addSection(mainElement, title, isDivider, sectionId)
                }
            } catch (e: Exception) {
                console.error("Error creating sections from component data", e)
            }
        }

        // Load scripts
        ScriptLoader.addScriptsToBody()

        return body
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
        mainElement.appendChild(sectionElement) // Append section to the main element
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
        if (existingElement != null && existingElement == document.body) { // Only refresh the body if it's the current page element
            val newPageElement = create() // This will rebuild and append to body
 topBar.renderTo(newPageElement) // Render the top bar within the new page element
        } else {
            console.warn("No existing element with attribute [$tag] found to refresh.")
 document.body?.appendChild(create()) // If no element found, just create and append
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

        private fun layoutCssRules(): List<CssRuleDefinition> {
            // Assuming TopBar.TAG will be defined elsewhere
            val topBarSelector = "[${TopBar.TAG}]"
            return listOf(
                "[page-container]" to {
                    // Adjust padding-top to accommodate the fixed top bar.
                    // Theme.topBarHeight should be a constant defining the height of the TopBar.
                    // For now, use a placeholder or calculate based on TopBar's defined height.
                    // Let's assume TopBar has a fixed height of 60px for this example.
                    // A better approach would be to read TopBar's actual height or have a shared constant.
                    paddingTop = "60px"
                    width = "100%"
                    minHeight = "100vh"
                },
                topBarSelector to {
                    position = "fixed"
                    top = "0"
                    left = "0"
                    right = "0"
                    zIndex = "100" // Ensure the top bar stays on top
                }
            )
        }

        /**
         * CSS rules for the page container.
         */
        fun cssRules(): List<prisma.editor.css.CssRuleDefinition> {
            return layoutCssRules() // Call the layoutCssRules method
        }
    }
}
