package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.create
import org.w3c.dom.HTMLElement
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography

class Section(
    initialTitle: String? = null,
    initialIsDivider: Boolean = false,
    initialMarginTop: String = Theme.spacing
) {
    var title: String? = initialTitle
        private set
    var isDivider: Boolean = initialIsDivider
        private set
    var marginTop: String = initialMarginTop
        private set

    // Store children as Any for now, assuming they have buildStaticHtml/buildEditorDom/toData
    private val contentItems = mutableListOf<Any>() // Use Any to hold component instances or data
    private val gridItems = mutableListOf<Any>() // Use Any to hold component instances or data
    private var rootElement: HTMLElement? = null // Root element is a section

    init {
        load()
    }

    fun addContent(element: HTMLElement) {
        contentItems.add(element)
    }

    fun addContent(flowContent: FlowContent.() -> Unit) {
        val element = document.create.div {
            apply(flowContent)
        }
        if (element.childElementCount == 1) {
            contentItems.add(element.firstElementChild as HTMLElement)
        } else {
            contentItems.add(element)
        }
    }

    fun addGridItem(element: HTMLElement) {
        gridItems.add(element)
    }

    fun addGridItems(elements: List<HTMLElement>) {
        gridItems.addAll(elements)
    }

    /**
     * Generates the HTML structure for the editor's live view.
     * Includes editor-specific attributes and elements based on `Config.isEditing`.
     */
    fun buildEditorDom(): HTMLElement {
        val sectionElement = document.create.section {
            asDynamic().kotlinInstance = this@Section
        }

        if (title != null) {
            val headerElement = document.create.header {
                attributes[HEADER_ATTR] = ""
                attributes["data-divider"] = isDivider.toString()

                h2 {
                    classes = setOf(Typography.HEADLINE)
                    // In editor mode, make title editable
                     if (Config.isEditing) {
                         attributes["contenteditable"] = "true"]
                    +title!!
                }
            }
            sectionElement.appendChild(headerElement)
        }

        if (contentItems.isNotEmpty()) {
            val contentElement = document.create.div {
                attributes[CONTENT_ATTR] = ""
 attributes["role"] = "region"
            }

            contentItems.forEach { item ->
                // Assuming child items are components with buildEditorDom or are raw HTMLElements
                when (item) {
                    is HTMLElement -> contentElement.appendChild(item)
                    else -> (item as? Any).asDynamic().buildEditorDom()?.let { contentElement.appendChild(it as HTMLElement) }
                }
            }

        if (gridItems.isNotEmpty()) {
            val gridElement = document.create.div {
                attributes[GRID_ATTR] = ""
            }

            gridItems.forEach { item ->
                when (item) {
                    is HTMLElement -> gridElement.appendChild(item)
                    else -> (item as? Any).asDynamic().buildEditorDom()?.let { gridElement.appendChild(it as HTMLElement) }
                }
            }
            sectionElement.appendChild(gridElement)
        }

        if (Config.isEditing) {
            val deleteButton = document.create.button {
                attributes["class"] = "delete-button"
                attributes["onclick"] = "this.parentElement.remove()"
                attributes["title"] = "Delete this section"
                +"-"
            }
 sectionElement.insertBefore(deleteButton, sectionElement.firstChild)
            val contentElement = sectionElement.querySelector("[data-component-tag='${CONTENT_ATTR}']")
            if (contentElement != null) {
                val addContentButton = document.create.button {
 attributes["class"] = "add-button"
 attributes["onclick"] = "prisma.editor.component.Section.addNewContent(this)"
 attributes["title"] = "Add new content"
 +"+\n"
                }
                contentElement.appendChild(addContentButton)
            }
            val gridElement = sectionElement.querySelector("[data-component-tag='${GRID_ATTR}']")
            if (gridElement != null) {
                val addGridItemButton = document.create.button {
 attributes["class"] = "add-button"
 attributes["onclick"] = "prisma.editor.component.Section.addNewGridItem(this)"
 attributes["title"] = "Add new grid item"
 +"+\n"
                }
                gridElement.appendChild(addGridItemButton)
            }
        }
        return sectionElement
    }

    /**
     * Generates the static HTML string for the published site.
     * Does NOT include any editor-specific attributes or elements.
     */
    fun buildStaticHtml(): String {
        val sectionElement = document.create.section {
            attributes[TAG] = "" // Keep existing TAG attribute for styling
            attributes["data-margin-top"] = marginTop
        }

        if (title != null) {
            val headerElement = document.create.header {
                attributes[HEADER_ATTR] = "" // Keep existing HEADER_ATTR attribute for styling
                attributes["data-divider"] = isDivider.toString()

            val contentElement = sectionElement.querySelector("[data-component-tag='${CONTENT_ATTR}']")
            if (contentElement != null) {
                val addContentButton = document.create.button {
                    attributes["class"] = "add-button"
                    attributes["onclick"] = "prisma.editor.component.Section.addNewContent(this)"
                    attributes["title"] = "Add new content"
                    +"+"
                }
                contentElement.appendChild(addContentButton)
            }

        }
            val gridElement = sectionElement.querySelector("[data-component-tag='${GRID_ATTR}']")
            if (gridElement != null) {
                val addGridItemButton = document.create.button {
                    attributes["class"] = "add-button"
                    attributes["onclick"] = "prisma.editor.component.Section.addNewGridItem(this)"
                    attributes["title"] = "Add new grid item"
                    +"+"
                }
                gridElement.appendChild(addGridItemButton)
            }
            sectionElement.appendChild(headerElement)
        }

        if (contentItems.isNotEmpty()) {
            val contentElement = document.create.div {
                attributes[CONTENT_ATTR] = "" // Keep existing CONTENT_ATTR attribute for styling
                attributes["role"] = "region"
            }

            contentItems.forEach { item ->
                 // Assuming child items are components with buildStaticHtml() or are raw HTMLElements' outerHTML
                 when (item) {
                    is HTMLElement -> contentElement.innerHTML += item.outerHTML
                     else -> (item as? Any).asDynamic().buildStaticHtml()?.let { contentElement.innerHTML += it as String }
                 }
            }

            sectionElement.appendChild(contentElement)
        }

        if (gridItems.isNotEmpty()) {
            val gridElement = document.create.div {
                attributes[GRID_ATTR] = "" // Keep existing GRID_ATTR attribute for styling
                // Keep existing grid styling applied via CSS rules using GRID_ATTR
            }

            gridItems.forEach { item ->
                 // Assuming child items are components with buildStaticHtml() or are raw HTMLElements' outerHTML
                 when (item) {
                    is HTMLElement -> gridElement.innerHTML += item.outerHTML
                     else -> (item as? Any).asDynamic().buildStaticHtml()?.let { gridElement.innerHTML += it as String }
                 }
            }

            sectionElement.appendChild(gridElement)
        }

        return sectionElement.outerHTML // Get the HTML string
    }

    /**
     * Returns the component's state as a serializable object,
     * including the structure and data of its child components.
     */
    fun toData(): Any {
        return mapOf(
            "componentType" to "Section", // Include component type for deserialization
            "title" to title,
            "isDivider" to isDivider,
            "marginTop" to marginTop,
            "contentItems" to contentItems.map { item ->
                // Assuming child items are components with toData() or can be represented as data
                when (item) {
                    is HTMLElement -> mapOf("type" to "html", "html" to item.outerHTML) // Simple representation for raw HTML
                    else -> (item as? Any).asDynamic().toData() ?: "UnknownItem" // Call toData() on components
                }
            },
             "gridItems" to gridItems.map { item ->
                // Assuming child items are components with toData() or can be represented as data
                 when (item) {
                    is HTMLElement -> mapOf("type" to "html", "html" to item.outerHTML) // Simple representation for raw HTML
                     else -> (item as? Any).asDynamic().toData() ?: "UnknownItem" // Call toData() on components
                 }
            }
        )
    }

    fun commit() {
        val data = mapOf(
 title = data.title ?: title
        if (data.isDivider != null) {
            isDivider = data.isDivider as Boolean
        }
        marginTop = data.marginTop ?: marginTop

        // Note: set() does NOT handle updating child components (contentItems, gridItems).
        // Child component state changes should ideally trigger their own refreshDOM()
        // or the entire Section might be reloaded from data.

        if (changed) {
            refreshDOM()
        }
    }

    /**
     * Refreshes the DOM of the component in place if it has been rendered and is still in the DOM.
     * This is typically called after properties are updated via set().
     */
    fun refreshDOM() {
        val currentElement = rootElement ?: return // Not rendered yet
        val parent = currentElement.parentElement ?: return // Rendered but detached from DOM

        // Pass the current editing state implicitly via Config.isEditing
        val newElement = buildEditorDom()
        parent.replaceChild(newElement, currentElement)
        rootElement = newElement // Update the stored reference to the new element
    }

    /**
     * Placeholder for committing data, e.g., saving to a backend.
     * Uses the toData() method to get the complete state.
     */
    fun commit() {
        val data = toData()
        val jsonData = JSON.stringify(data)
        // In a real app, this might be an API call to save the entire section state
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    /**
     * Placeholder for loading initial data.
     * This might involve fetching data and then reconstructing the component and its children.
     */
    fun load() {
        // Example: Fetch data and then reconstruct the component and its children.
        // val fetchedData = MyApi.fetchSectionData(Config.currentSite, Config.currentLanguage, sectionId)
        // if (fetchedData != null) {
        //     title = fetchedData.title
        //     isDivider = fetchedData.isDivider
        //     marginTop = fetchedData.marginTop
        //     contentItems.clear()
        //     fetchedData.contentItems.forEach { itemData ->
        //         // Logic to determine component type and instantiate/set it
        //         val childComponent = ComponentFactory.create(itemData.componentType, itemData)
        //         addContent(childComponent)
        //     }
        //     gridItems.clear()
        //     fetchedData.gridItems.forEach { itemData ->
        //          // Logic to determine component type and instantiate/set it
        //         val childComponent = ComponentFactory.create(itemData.componentType, itemData)
        //         addGridItem(childComponent)
        //     }
        // }
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG: (Simulated - component created with initial or default values)")
    }

    /**
     * Removes the component\'s DOM element from the document.
     * Also detaches child components if they have a detach() method.
     */
    fun detach() {
        rootElement?.remove()
        rootElement = null

        // Detach child components if they have a detach() method
        contentItems.forEach { item ->
             (item as? Any).asDynamic().detach?.invoke()
        }
         gridItems.forEach { item ->
             (item as? Any).asDynamic().detach?.invoke()
        }
    }

    companion object {
        // TAG is used for styling via data-component-tag and potentially for logging/identification.
        const val TAG = "section-container"
        const val HEADER_ATTR = "section-header"
        const val CONTENT_ATTR = "section-content"
        const val GRID_ATTR = "section-grid"

        @JsName("addNewContent")
        fun addNewContent(button: dynamic) {
            val contentElement = button.parentElement
            // In a real editor, this would likely involve a component picker and creating a new component instance
            val textElement = document.create.p {
                attributes["class"] = Typography.BODY
                attributes["contenteditable"] = "true"
                +"New content item. Click to edit."
            }

            val deleteButton = document.create.button {
                attributes["class"] = "delete-button"
                attributes["onclick"] = "this.parentElement.remove()" // Keep inline onclick for now
                attributes["title"] = "Delete this element"
                +"-"
            }
            textElement.appendChild(deleteButton)

            contentElement.insertBefore(textElement, button)
        }

        @JsName("addNewGridItem")
        fun addNewGridItem(button: dynamic) {
            val gridElement = button.parentElement
             // In a real editor, this would likely involve a component picker and creating a new component instance
            val textElement = document.create.p {
                attributes["class"] = Typography.BODY
                attributes["contenteditable"] = "true"
                +"New grid item. Click to edit."
            }

            val deleteButton = document.create.button {
                attributes["class"] = "delete-button"
                attributes["onclick"] = "this.parentElement.remove()" // Keep inline onclick for now
                attributes["title"] = "Delete this element"
                +"-"
            }
            textElement.appendChild(deleteButton)

            gridElement.insertBefore(textElement, button)
        }

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    marginTop = Theme.spacing
                    marginBottom = Theme.spacing
                    padding = Theme.spacing
                    maxWidth = "1200px"
                    marginLeft = "auto"
                    marginRight = "auto"
                    backgroundColor = "var(--color-very-light-transparent)"
                    borderRadius = Theme.spacing
                    position = "relative"
                },

                "[$HEADER_ATTR]" to {
                    marginBottom = Theme.spacing
                },

                "[$HEADER_ATTR][data-divider='true']" to {
                    paddingBottom = Theme.spacing
                    borderBottom = "1px solid var(--color-medium-gray)"
                },

                "[$CONTENT_ATTR]" to {
                    padding = Theme.spacing
                },

                "[$GRID_ATTR]" to {
                    display = "grid"
                    setProperty("grid-template-columns", "repeat(auto-fill, minmax(250px, 1fr))")
                    setProperty("gap", Theme.spacing)
                    marginTop = Theme.spacing
                },

                ".delete-button" to {
                    position = "absolute"
                    top = "5px"
                    right = "5px"
                    width = "24px"
                    height = "24px"
                    borderRadius = "50%"
                    backgroundColor = "var(--color-error)"
                    color = "white"
                    border = "none"
                    cursor = "pointer"
                    display = "flex"
                    justifyContent = "center"
                    alignItems = "center"
                    zIndex = "1"
                },

                ".add-button" to {
                    width = "30px"
                    height = "30px"
                    borderRadius = "50%"
                    backgroundColor = "var(--color-primary)"
                    color = "white"
                    border = "none"
                    cursor = "pointer"
                    display = "flex"
                    justifyContent = "center"
                    alignItems = "center"
                    margin = "10px auto"
                }
            )
        )
        val jsonData = JSON.stringify(data)
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG")
    }

    companion object {
        const val TAG = "section-container"
        const val HEADER_ATTR = "section-header"
        const val CONTENT_ATTR = "section-content"
        const val GRID_ATTR = "section-grid"

        @JsName("addNewContent")
        fun addNewContent(button: dynamic) {
            val contentElement = button.parentElement
            val textElement = document.create.p {
                attributes["class"] = Typography.BODY
                attributes["contenteditable"] = "true"
                +"New content item. Click to edit."
            }

            val deleteButton = document.create.button {
                attributes["class"] = "delete-button"
                attributes["onclick"] = "this.parentElement.remove()"
                attributes["title"] = "Delete this element"
                +"-"
            }
            textElement.appendChild(deleteButton)

            contentElement.insertBefore(textElement, button)
        }

        @JsName("addNewGridItem")
        fun addNewGridItem(button: dynamic) {
            val gridElement = button.parentElement
            val textElement = document.create.p {
                attributes["class"] = Typography.BODY
                attributes["contenteditable"] = "true"
                +"New grid item. Click to edit."
            }

            val deleteButton = document.create.button {
                attributes["class"] = "delete-button"
                attributes["onclick"] = "this.parentElement.remove()"
                attributes["title"] = "Delete this element"
                +"-"
            }
            textElement.appendChild(deleteButton)

            gridElement.insertBefore(textElement, button)
        }

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    marginTop = Theme.spacing
                    marginBottom = Theme.spacing
                    padding = Theme.spacing
                    maxWidth = "1200px"
                    marginLeft = "auto"
                    marginRight = "auto"
                    backgroundColor = "var(--color-very-light-transparent)"
                    borderRadius = Theme.spacing
                    position = "relative"
                },

                "[$HEADER_ATTR]" to {
                    marginBottom = Theme.spacing
                },

                "[$HEADER_ATTR][data-divider='true']" to {
                    paddingBottom = Theme.spacing
                    borderBottom = "1px solid var(--color-medium-gray)"
                },

                "[$CONTENT_ATTR]" to {
                    padding = Theme.spacing
                },

                "[$GRID_ATTR]" to {
                    display = "grid"
                    setProperty("grid-template-columns", "repeat(auto-fill, minmax(250px, 1fr))")
                    setProperty("gap", Theme.spacing)
                    marginTop = Theme.spacing
                },

                ".delete-button" to {
                    position = "absolute"
                    top = "5px"
                    right = "5px"
                    width = "24px"
                    height = "24px"
                    borderRadius = "50%"
                    backgroundColor = "var(--color-error)"
                    color = "white"
                    border = "none"
                    cursor = "pointer"
                    display = "flex"
                    justifyContent = "center"
                    alignItems = "center"
                    zIndex = "1"
                },

                ".add-button" to {
                    width = "30px"
                    height = "30px"
                    borderRadius = "50%"
                    backgroundColor = "var(--color-primary)"
                    color = "white"
                    border = "none"
                    cursor = "pointer"
                    display = "flex"
                    justifyContent = "center"
                    alignItems = "center"
                    margin = "10px auto"
                }
            )
        }
    }
}
