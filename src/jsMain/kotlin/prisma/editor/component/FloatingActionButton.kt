package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.*
import kotlin.js.JSON

/**
 * FloatingActionButton component that renders a floating action button with an icon.
 * @param iconName The name of the Material Symbols icon to display.
 * @param onClick JavaScript function to execute when the button is clicked.
 */
class FloatingActionButton(
    var iconName: String,
    var onClick: String = ""
) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        val button = document.create.button {
            attributes[TAG] = ""
            id = "floating-action-button"
            attributes["onclick"] = onClick
            asDynamic().kotlinInstance = this@FloatingActionButton
        }

        // Add the icon
        val icon = Icon(iconName, fill = 1)
        val iconElement = icon.preview()
        button.appendChild(iconElement)

        return button
    }

    fun commit() {
        val data = mapOf(
            "iconName" to iconName,
            "onClick" to onClick
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
    }

    fun set(data: dynamic) {
        iconName = data.iconName ?: iconName
        onClick = data.onClick ?: onClick
        refresh()
    }

    fun refresh() {
        val existingElement = document.querySelector("[$TAG]")
        if (existingElement != null) {
            existingElement.parentElement?.replaceChild(preview(), existingElement)
        } else {
            console.warn("No existing element with attribute [$TAG] found to refresh.")
            document.body?.appendChild(preview())
        }
    }

    companion object {
        const val TAG = "floating-action-button"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
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
