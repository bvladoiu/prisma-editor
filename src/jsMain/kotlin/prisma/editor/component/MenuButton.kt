package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import kotlin.js.JSON
import org.w3c.dom.Element
import prisma.editor.css.Theme

class MenuButton(
    var isDrawerOpen: Boolean = false
) {
    private var rootElement: HTMLElement? = null

    init {
        load()
    }

    fun buildStaticHtml(): String {
        return document.create.button {
            id = "menu-button-$TAG" // Add ID to the button
            attributes["TAG"] = TAG
            attributes["aria-label"] = if (isDrawerOpen) "Close menu" else "Open menu" // Placeholder for i18n
            attributes["aria-expanded"] = isDrawerOpen.toString()

            span {
                id = "menu-icon" // ID for easy access
                +if (isDrawerOpen) "Close menu" else "Open menu" // Fallback text / Placeholder for i18n and icon
            }

            onClickFunction = {
                console.log("Menu button clicked. isDrawerOpen before click: $isDrawerOpen")
                // Placeholder for toggling drawer and updating state
                // set(!isDrawerOpen) // State update will happen via set
            }
        }.outerHTML
    }

    fun renderTo(parentElement: HTMLElement): HTMLElement {
        val newElement = document.create.div {
            unsafe {
                +buildStaticHtml()
            }
        }.firstElementChild as HTMLElement // Wrap in div to parse outerHTML and get the button

        rootElement?.remove()

        parentElement.appendChild(newElement)
        rootElement = newElement
        updateIcon() // Call updateIcon after rendering
        return newElement
    }

    fun detach() {
        rootElement?.remove()
        rootElement = null
    }

    fun commit() {
        val data = mapOf(
            "isDrawerOpen" to isDrawerOpen
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData) // Placeholder
    }

    fun load() {
        console.log("load:$TAG") // Placeholder
    }

    fun set(data: dynamic) {
        var changed = false
        val newIsDrawerOpen = data.isDrawerOpen as? Boolean
        if (newIsDrawerOpen != null && newIsDrawerOpen != isDrawerOpen) {
            isDrawerOpen = newIsDrawerOpen
            changed = true
        }

        if (changed) {
            refreshDOM()
            updateIcon() // Call updateIcon after state change and refresh
        }
    }

    private fun updateIcon() {
        val buttonElement = rootElement ?: return
        val iconSpan = buttonElement.querySelector("#menu-icon") as? HTMLElement ?: return

        val openIcon = "menu" // Material Symbols name for hamburger
        val closeIcon = "close" // Material Symbols name for close
        val openLabel = "Open menu" // Placeholder for i18n
        val closeLabel = "Close menu" // Placeholder for i18n

        buttonElement.attributes["aria-label"] = if (isDrawerOpen) closeLabel else openLabel
        buttonElement.attributes["aria-expanded"] = isDrawerOpen.toString()

        // Check if fonts are loaded before attempting to set icon
        if (jsTypeOf(document.fonts.ready) != "undefined") {
            document.fonts.ready.then {
                // Font is loaded, set the Material Symbol icon
                iconSpan.textContent = if (isDrawerOpen) closeIcon else openIcon
            }
        } else {
            // Fallback: Fonts are not supported or not loaded yet, use text
            iconSpan.textContent = if (isDrawerOpen) closeLabel else openLabel
        }
    }

    // Simple refresh logic
    private fun refreshDOM() {
        val currentElement = rootElement ?: return
        val parent = currentElement.parentElement ?: return
        detach() // Detach the old element
        renderTo(parent) // Render the new element in the same parent
    }


    companion object {
        const val TAG = "menu-button"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    border = "none"
                    background = "none"
                    padding = Theme.spacing // Use theme spacing
                    cursor = "pointer"
                    display = "flex"
                    alignItems = "center"
                    justifyContent = "center"
                    color = Theme.white // Use theme color
                },
                "[$TAG]:hover" to {
                    backgroundColor = Theme.lightTransparent // Use theme transparent color
                },
                "[$TAG] span" to {
                    fontFamily = "\"Material Symbols Outlined\"" // Apply Material Symbols font
                    fontSize = Theme.iconSize // Use theme icon size
                    fontWeight = "normal"
                    fontStyle = "normal"
                    lineHeight = "1"
                    letterSpacing = "normal"
                    textTransform = "none"
                }
            )
        }
    }
}