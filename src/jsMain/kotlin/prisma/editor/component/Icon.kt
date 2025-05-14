package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.FontsLoader
import prisma.editor.css.Typography

/**
 * Icon component that renders a Material Symbols icon.
 * @param name The name of the Material Symbols icon to display.
 * @param weight The weight of the icon (100-700).
 * @param fill Whether the icon should be filled (0 or 1).
 * @param grade The grade of the icon (-50 to 200).
 */
class Icon(
    var name: String,
) {
    init {
        FontsLoader.registerIcon(name)
        load()
    }

    fun preview(): HTMLElement {
        return document.create.span {
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@Icon
            +name
        }
    }

    /**
     * Renders the icon component as FlowContent that can be embedded in other components.
     * @return FlowContent that can be embedded in other components
     */
    fun render(): FlowContent.() -> Unit = {
        span {
            attributes[TAG] = ""
            +name
        }
    }

    fun commit() {
        val data = mapOf(
            "name" to name,
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
    }

    fun set(data: dynamic) {
        val newName = data.name ?: name
        if (newName != name) {
            name = newName
            FontsLoader.registerIcon(name)
        } else {
            name = newName
        }
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
        const val TAG = Typography.ICON
    }
}
