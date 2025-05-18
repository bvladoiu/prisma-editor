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
import kotlin.js.JSON


class Drawer(var items: List<NavLink> = emptyList(), var opened: Boolean = false) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        val dialog = document.create.dialog {
            id = "drawer"
            attributes["TAG"] = TAG
            asDynamic().kotlinInstance = this@Drawer
            if (opened) {
                attributes["open"] = ""
            }
        }

        val itemsList = document.create.ul {
            attributes["TAG"] = ITEMS_TAG
        }

        items.forEach { navLink ->
            itemsList.appendChild(navLink.preview())
        }

        dialog.appendChild(itemsList)

        return dialog
    }

    fun toggle() {
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

    fun commit() {
        val data = mapOf(
            "items" to items,
            "opened" to opened
        )
        val jsonData = JSON.stringify(data)
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG")
    }

    fun set(data: dynamic) {
        // Update properties
        items = data.items?.unsafeCast<List<NavLink>>() ?: items
        opened = data.opened ?: opened

        // Refresh the DOM element
        refresh()

        // Ensure dialog state in DOM matches the 'opened' property after refresh
        val dialog = document.getElementById("drawer") as? HTMLDialogElement
        if (dialog != null) {
            if (opened && !dialog.hasAttribute("open")) {
                dialog.showModal()
            } else if (!opened && dialog.hasAttribute("open")) {
                dialog.close()
            }
        }
    }

    fun refresh() {
        val existingElement = document.querySelector("dialog[TAG='$TAG']") as? HTMLDialogElement
        if (existingElement != null) {
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
        }
    }

    companion object {
        const val TAG = "drawer"
        const val HEADER_TAG = "drawer-header"
        const val TITLE_TAG = "drawer-title"
        const val ITEMS_TAG = "drawer-items"

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
        }

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

                "[$HEADER_TAG]" to {
                    padding = Theme.spacing
                    backgroundColor = Theme.drawerPurple
                    color = Theme.white
                },

                "[$TITLE_TAG]" to {
                    margin = "0"
                },

                "[$ITEMS_TAG]" to {
                    listStyleType = "none"
                    padding = "0"
                    margin = "0"
                }
            )
        }
    }
}
