package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import prisma.editor.css.*

/**
 * FloatingActionButton component that renders a floating action button with an icon.
 * This is an editor-only component.
 * @param iconName The name of the Material Symbols icon to display.
 * @param onClick JavaScript function to execute when the button is clicked.
 */
class FloatingActionButton(
    var iconName: String,
    var onClick: () -> Unit = {}
) {

    private var rootElement: HTMLElement? = null // Root element is a button

    // No init block needed for load() as this component doesn't load data

    /**
     * Generates the HTML structure for the editor's live view.
     * Returns an HTMLElement (a button) using kotlinx.html.dom.
     * This component is editor-specific and does not have a static representation.
     */
    fun buildEditorDom(): HTMLElement {
        return document.create.button {
            attributes["TAG"] = TAG
            onClickFunction = { onClick() }
            asDynamic().kotlinInstance = this@FloatingActionButton

            val icon = Icon(iconName)
            icon.render().invoke(this)
        }
    }

    // No buildStaticHtml() needed as this is an editor-only component

    // No toData() needed as the primary state is iconName and onClick (behavior)

    /**
     * Renders the component into the given parentElement in the editor.
     * If the component was already rendered elsewhere, it will be moved.\n     * If called on an already rendered component within the same parent, it effectively refreshes it.
     */
    fun renderTo(parentElement: HTMLElement): HTMLElement {
        val newElement = buildEditorDom()

        // If this component instance is already associated with a DOM element,
        // remove it from its current parent before appending to the new parent.
        rootElement?.remove()

        parentElement.appendChild(newElement)
        rootElement = newElement

        return newElement
    }

    // No commit() needed for this simple editor UI component

    // No load() needed for this simple editor UI component

    /**
     * Updates the component's data and refreshes its DOM representation if it's already rendered.
     * @param data A dynamic object expected to have an optional iconName property.
     */
    fun set(data: dynamic) {
        var changed = false

        val newIconName = data.iconName as? String
        if (newIconName != null && newIconName != iconName) {
            iconName = newIconName
            changed = true
        }

        // The onClick function is typically set during component creation and not updated via set

        if (changed) {
            refreshDom()
        }
    }

    /**
     * Refreshes the DOM of the component in place if it has been rendered and is still in the DOM.
     * This is typically called after properties are updated via set().
     */
    fun refreshDom() {
        val currentElement = rootElement ?: return // Not rendered yet
        val parent = currentElement.parentElement ?: return // Rendered but detached from DOM

        val newElement = buildEditorDom()
        parent.replaceChild(newElement, currentElement)
        rootElement = newElement // Update the stored reference to the new element
    }

    /**
     * Removes the component's DOM element from the document in the editor.
     */
    fun detach() {
        rootElement?.remove()
        rootElement = null
    }

    companion object {
        const val TAG = "floating-action-button"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[TAG='$TAG']" to {
                    position = "fixed"
                    right = Theme.spacing
                    bottom = Theme.spacing
                    width = "56px"
                    height = "56px"
                    borderRadius = "50%"
                    backgroundColor = Theme.primary
                    color = Theme.white
                    border = "none"
                    setProperty("box-shadow", "0 ${Theme.spacing} ${Theme.spacing} ${Theme.shadowMedium}")
                    cursor = "pointer"
                    display = "flex"
                    justifyContent = "center"
                    alignItems = "center"
                    zIndex = "1000"
                    transition = "background-color 0.3s, transform 0.3s"
                },
                "[$TAG]:hover" to {
                    backgroundColor = Theme.secondary
                    transform = "scale(1.05)"
                },
                "[$TAG]:active" to {
                    transform = "scale(0.95)"
                }
            )
        }
    }
}
