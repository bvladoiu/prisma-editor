package prisma.editor.component

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLDialogElement
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.element.NavLink
import kotlin.js.JSON


class Drawer(var items: List<NavLink> = emptyList(), var opened: Boolean = false) {

    private var rootElement: HTMLDialogElement? = null // Root element is a dialog

    init {
        load()
    }

    /**
     * Generates the HTML structure for the editor's live view.
     * Returns an HTMLElement (a dialog) using kotlinx.html.dom.
     * This component is editor-specific and does not have a static representation.
     */
    fun buildEditorDom(): HTMLElement {
        val dialog = document.create.dialog {
            id = "drawer" // Keep ID for easy access
            attributes["TAG"] = TAG // Keep existing TAG attribute for styling
            asDynamic().kotlinInstance = this@Drawer
            // The 'open' attribute reflects the opened state and is managed by showModal/close
            // if (opened) { attributes["open"] = "" }
        }

        val itemsList = document.create.ul {
            // This TAG is used for styling the list container
            attributes["TAG"] = ITEMS_TAG
        }

        items.forEach { navLink ->
            itemsList.appendChild(navLink.preview())
        }

        dialog.appendChild(itemsList)

         // Attach event listeners after the dialog element is created
         // Event listeners for NavLinks are handled within the NavLink component itself

        return dialog
    }

    /**
     * Returns the component's state as a serializable object.
     * Includes the data from child NavLink components.
     */
    fun toData(): Any {
        return mapOf(
            "items" to items.map { it.toData() }, // Map NavLink objects to their data
            "opened" to opened
        )
    }

    /**
     * Toggles the open/closed state of the drawer dialog.
     * Directly interacts with the dialog element's showModal/close methods.
     * Also updates the menu icon.
     */
    fun toggle() {
        // Update internal state
        opened = !opened
        val dialog = document.getElementById("drawer") as? HTMLDialogElement
        if (dialog != null) {
            if (opened) {
                dialog.showModal() // Use showModal() for a modal dialog with backdrop
            } else {
                dialog.close() // This sets dialog.open = false
            }
        }

        val menuIcon = document.getElementById("menu-icon") as? HTMLElement
        menuIcon?.textContent = if (opened) "close" else "menu"
    }

    /**
     * Placeholder for committing data, e.g., saving the drawer's state and nav links.
     * Uses the toData() method to get the current state.
     */
    fun commit() {
        val data = toData() // Get the state to be saved
        val jsonData = JSON.stringify(data)
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)

        // In a real app, you might save this data to a backend
    }

    /**
     * Placeholder for loading initial data.
     * This might involve fetching data and then calling set() to update the component.
     */
    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG: (Simulated - component created with initial or default values)")
        // In a real app, fetch data and call set(fetchedData)
    }

    /**
     * Updates the component's state and refreshes its DOM representation if it's already rendered.
     * Deserializes NavLink data back into NavLink objects.
     * @param data A dynamic object expected to have optional items (list of NavLink data) and opened properties.
     */
    fun set(data: dynamic) {
        var changed = false

        // Handle updating the list of NavLink items
        val newItemsData = data.items
        if (newItemsData != null) {
            // Assuming newItemsData is an array of objects representing NavLink data
            val newItems = newItemsData.asList().mapNotNull { itemData ->
                 // Assuming NavLink has a constructor or factory method that takes data
                 try {
                     NavLink(itemData.title as? String ?: "", itemData.route as? String ?: "")
                 } catch (e: Throwable) {
                     console.error("Error deserializing NavLink data:", itemData, e)
                     null
                 }
            }
             // Check if the list of items has actually changed (shallow comparison)
            if (newItems.size != items.size || newItems.zip(items).any { (newItem, oldItem) -> newItem.title != oldItem.title || newItem.route != oldItem.route }) {
                items = newItems
                changed = true
            }
        }

        // Handle updating the opened state
        val newOpened = data.opened as? Boolean
        if (newOpened != null && newOpened != opened) {
            opened = newOpened
            changed = true
        }

        // Refresh the DOM element if any state changed
        if (changed) {
            refreshDom()
        }
    }

    /**
     * Refreshes the DOM of the component in place if it has been rendered and is still in the DOM.
     * Handles preserving and restoring the dialog's open state.
     * This is typically called after properties are updated via set().
     */
    fun refreshDom() {
        val currentElement = rootElement ?: return // Not rendered yet
        val parent = currentElement.parentElement ?: return // Rendered but detached from DOM

            // Store the current open state before replacing
            val wasOpen = existingElement.hasAttribute("open")
            val newElement = preview()

            // Replace the element
            existingElement.parentElement?.replaceChild(newElement, existingElement)

            // Restore the open state on the new element if it was open
            if (wasOpen) {
                // Need a slight delay to re-show after replacement
                window.setTimeout({
                    (document.getElementById("drawer") as? HTMLDialogElement)?.showModal()
                }, 0) // Use a 0ms delay to allow DOM update cycle
            }
        } else {
            console.warn("No existing element with attribute [TAG='$TAG'] found to refresh.")
            document.body?.appendChild(preview())

            // If appending and it should be open, show it
            if (opened) {
                (document.getElementById("drawer") as? HTMLDialogElement)?.showModal()
            }

        val newElement = buildEditorDom() as HTMLDialogElement
        parent.replaceChild(newElement, currentElement)
        rootElement = newElement // Update the stored reference to the new element

        // Restore the open state on the new element if it was open
        if (wasOpen) {
            // Use a 0ms delay to allow the new element to be fully in the DOM before showing
            window.setTimeout({ rootElement?.showModal() }, 0)
        }
    }

    /**
     * Removes the component's DOM element from the document in the editor.
     */
    fun detach() {
        rootElement?.remove()
        rootElement = null
    }

    companion object {
        const val TAG = "drawer"
        const val HEADER_TAG = "drawer-header" // This tag is not currently used in buildEditorDom but kept for potential future header
        const val TITLE_TAG = "drawer-title" // This tag is not currently used in buildEditorDom but kept for potential future title
        const val ITEMS_TAG = "drawer-items"

        /**
         * Toggles the drawer open or closed.
         * Called via @JsName from external events (e.g., a button click).
         */
        @JsName("toggleDrawer")
        fun toggleDrawer() {
            // Query the dialog element and call toggle() on the Kotlin instance
            val drawer = document.querySelector("dialog[TAG='$TAG']")?.asDynamic()?.kotlinInstance as? Drawer
            drawer?.toggle()
        }

        @JsName("navigateTo")
        fun navigateTo(route: String) {
            // First toggle the drawer
            toggleDrawer()

            // Then navigate to the route after a short delay
            kotlinx.browser.window.setTimeout({
                prisma.editor.EditorJs.openPage(route)
            }, 300)
        } // 300ms delay, should match drawer closing transition

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "dialog[TAG='$TAG']" to {
                    position = "fixed"
                    top = "0"
                    right = "-240px" // Start off-screen to the right
                    width = "240px"
                    height = "100%"
                    border = "none" // Remove default dialog border
                    padding = "0" // Remove default dialog padding
                    margin = "0" // Remove default dialog margin
                    backgroundColor = Theme.white
                    setProperty("box-shadow", "${Theme.spacing} 0 ${Theme.spacing} ${Theme.shadowLight}")
                    setProperty("overflow", "auto")
                    setProperty("transition", "right 0.3s ease-in-out") // Add transition for animation
                },

                // Style the dialog when it's open
                "dialog[TAG='$TAG'][open]" to {
                    right = "0" // Slide in to view when open
                },

                // Style the backdrop created by showModal()
                "dialog[TAG='$TAG']::backdrop" to {
                    backgroundColor = "rgba(0, 0, 0, 0.5)" // Semi-transparent black overlay
                },

                // The following rules for HEADER_TAG and TITLE_TAG are kept
                // for consistency but the tags are not currently used in buildEditorDom.
                // If a header is added, these rules will apply.
                 "[$HEADER_TAG]" to {
                     padding = Theme.spacing
                     backgroundColor = Theme.drawerPurple
                     color = Theme.white
                 },

                "[$TITLE_TAG]" to {
                    margin = "0"
                },

                "[$ITEMS_TAG]" to {
                    // Style the list container
                    listStyleType = "none"
                    padding = "0"
                    margin = "0"
                }
            )
        }
    }
}
