package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import kotlinx.html.stream.createHTML
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme

/**
 * Icon component that renders a Material Symbols icon.
 * @param name The name of the Material Symbols icon to display.
 * @param weight The weight of the icon (100-700).
 * @param fill Whether the icon should be filled (0 or 1).
 * @param grade The grade of the icon (-50 to 200).
 * @param size The size of the icon in pixels. (Deprecated: Icon size is now controlled by Theme.iconSize)
 */
class Icon(
    var name: String,
    var weight: Int = 400,
    var fill: Int = 0,
    var grade: Int = 0,
    @Deprecated("Icon size is now controlled by Theme.iconSize", ReplaceWith(""))
    var size: Int = 24
) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        return document.create.span {
            attributes[TAG] = ""
            attributes["class"] = "material-symbols-outlined"
            attributes["style"] = "font-variation-settings: 'FILL' $fill, 'wght' $weight, 'GRAD' $grade; font-size: ${Theme.iconSize};"
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
            attributes["class"] = "material-symbols-outlined"
            attributes["style"] = "font-variation-settings: 'FILL' $fill, 'wght' $weight, 'GRAD' $grade; font-size: ${Theme.iconSize};"
            +name
        }
    }

    fun commit() {
        val data = mapOf(
            "name" to name,
            "weight" to weight,
            "fill" to fill,
            "grade" to grade,
            "size" to size
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
    }

    fun set(data: dynamic) {
        name = data.name ?: name
        weight = data.weight ?: weight
        fill = data.fill ?: fill
        grade = data.grade ?: grade
        size = data.size ?: size
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
        const val TAG = "material-icon"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    fontFamily = "'Material Symbols Outlined'"
                    fontWeight = "normal"
                    fontStyle = "normal"
                    fontSize = Theme.iconSize
                    lineHeight = "1"
                    letterSpacing = "normal"
                    textTransform = "none"
                    display = "inline-block"
                    whiteSpace = "nowrap"
                    wordWrap = "normal"
                    direction = "ltr"
                    verticalAlign = "middle"
                }
            )
        }
    }
}
