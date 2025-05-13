package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import kotlinx.html.stream.createHTML
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition

/**
 * Text component that renders text with appropriate typography styling.
 * @param content The text content to display.
 * @param type The type of typography to use (display, headline, regular, action).
 * @param tag The HTML tag to use for the text (h1, h2, p, span, etc.).
 */
class Text(
    var content: String,
    var type: TextType = TextType.REGULAR,
    var tag: String = "p"
) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        return when (tag.lowercase()) {
            "h1" -> document.create.h1 {
                createTextElement(this)
            }
            "h2" -> document.create.h2 {
                createTextElement(this)
            }
            "h3" -> document.create.h3 {
                createTextElement(this)
            }
            "h4" -> document.create.h4 {
                createTextElement(this)
            }
            "h5" -> document.create.h5 {
                createTextElement(this)
            }
            "h6" -> document.create.h6 {
                createTextElement(this)
            }
            "span" -> document.create.span {
                createTextElement(this)
            }
            else -> document.create.p {
                createTextElement(this)
            }
        }
    }

    private fun <T : Tag> createTextElement(tag: T) {
        tag.attributes[TAG] = ""
        tag.attributes["data-text-type"] = type.name.lowercase()
        tag.asDynamic().kotlinInstance = this@Text
        tag.text(content)
    }

    /**
     * Renders the text component as FlowContent that can be embedded in other components.
     * @param display Whether to use display style (largest text)
     * @param headline Whether to use headline style (medium-large text)
     * @param regular Whether to use regular style (normal text)
     * @return FlowContent that can be embedded in other components
     */
    fun render(
        display: Boolean = false,
        headline: Boolean = false,
        regular: Boolean = false
    ): FlowContent.() -> Unit = {
        val effectiveType = when {
            display -> TextType.DISPLAY
            headline -> TextType.HEADLINE
            regular -> TextType.REGULAR
            else -> type
        }

        val effectiveTag = tag

        when (effectiveTag.lowercase()) {
            "h1" -> h1 {
                attributes[TAG] = ""
                attributes["data-text-type"] = effectiveType.name.lowercase()
                +content
            }
            "h2" -> h2 {
                attributes[TAG] = ""
                attributes["data-text-type"] = effectiveType.name.lowercase()
                +content
            }
            "h3" -> h3 {
                attributes[TAG] = ""
                attributes["data-text-type"] = effectiveType.name.lowercase()
                +content
            }
            "h4" -> h4 {
                attributes[TAG] = ""
                attributes["data-text-type"] = effectiveType.name.lowercase()
                +content
            }
            "h5" -> h5 {
                attributes[TAG] = ""
                attributes["data-text-type"] = effectiveType.name.lowercase()
                +content
            }
            "h6" -> h6 {
                attributes[TAG] = ""
                attributes["data-text-type"] = effectiveType.name.lowercase()
                +content
            }
            "span" -> span {
                attributes[TAG] = ""
                attributes["data-text-type"] = effectiveType.name.lowercase()
                +content
            }
            else -> p {
                attributes[TAG] = ""
                attributes["data-text-type"] = effectiveType.name.lowercase()
                +content
            }
        }
    }

    fun commit() {
        val data = mapOf(
            "content" to content,
            "type" to type.name,
            "tag" to tag
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
    }

    fun set(data: dynamic) {
        content = data.content ?: content
        if (data.type != null) {
            try {
                type = TextType.valueOf(data.type.toString())
            } catch (e: Exception) {
                console.error("Invalid text type: ${data.type}")
            }
        }
        tag = data.tag ?: tag
        refresh()
    }

    fun refresh() {
        val existingElement = document.querySelector("[$TAG][data-text-type='${type.name.lowercase()}']")
        if (existingElement != null) {
            existingElement.parentElement?.replaceChild(preview(), existingElement)
        } else {
            console.warn("No existing element with attribute [$TAG] found to refresh.")
            document.body?.appendChild(preview())
        }
    }

    companion object {
        const val TAG = "typography-text"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG][data-text-type='display']" to {
                    fontFamily = "'Poppins', sans-serif"
                    fontSize = "36px"
                    fontWeight = "700"
                    lineHeight = "1.2"
                    margin = "0 0 16px 0"
                },
                "[$TAG][data-text-type='headline']" to {
                    fontFamily = "'Poppins', sans-serif"
                    fontSize = "24px"
                    fontWeight = "700"
                    lineHeight = "1.3"
                    margin = "0 0 12px 0"
                },
                "[$TAG][data-text-type='regular']" to {
                    fontFamily = "'Roboto Flex', sans-serif"
                    fontSize = "16px"
                    fontWeight = "400"
                    lineHeight = "1.5"
                    margin = "0 0 8px 0"
                },
                "[$TAG][data-text-type='action']" to {
                    fontFamily = "'Roboto Flex', sans-serif"
                    fontSize = "14px"
                    fontWeight = "500"
                    lineHeight = "1.4"
                    textTransform = "uppercase"
                    letterSpacing = "0.5px"
                    margin = "0"
                }
            )
        }
    }

    enum class TextType {
        DISPLAY, HEADLINE, REGULAR, ACTION
    }
}
