package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import kotlinx.html.stream.createHTML
import org.w3c.dom.HTMLElement
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography
import kotlin.js.JSON

/**
 * Image component that renders an image with optional alt text and caption.
 * @param src The source URL of the image.
 * @param alt The alternative text for the image (for accessibility).
 * @param caption Optional caption text to display below the image.
 * @param width Optional width of the image (can be in px, %, or other CSS units).
 * @param height Optional height of the image (can be in px, %, or other CSS units).
 */
class Image(
    var src: String,
    var alt: String = "",
    var caption: String = "",
    var width: String = "auto",
    var height: String = "auto"
) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        return document.create.figure {
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@Image

            img {
                attributes["src"] = src
                attributes["alt"] = alt
                attributes["width"] = width
                attributes["height"] = height
            }

            if (caption.isNotEmpty()) {
                figcaption {
                    attributes[Typography.CAPTION] = ""
                    +caption
                }
            }
        }
    }

    /**
     * Renders the image component as FlowContent that can be embedded in other components.
     * @return FlowContent that can be embedded in other components
     */
    fun render(): FlowContent.() -> Unit = {
        figure {
            attributes[TAG] = ""

            img {
                attributes["src"] = src
                attributes["alt"] = alt
                attributes["width"] = width
                attributes["height"] = height
            }

            if (caption.isNotEmpty()) {
                figcaption {
                    attributes[Typography.CAPTION] = ""
                    +caption
                }
            }
        }
    }

    fun commit() {
        val data = mapOf(
            "src" to src,
            "alt" to alt,
            "caption" to caption,
            "width" to width,
            "height" to height
        )
        val jsonData = JSON.stringify(data)
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG")
    }

    fun set(data: dynamic) {
        src = data.src ?: src
        alt = data.alt ?: alt
        caption = data.caption ?: caption
        width = data.width ?: width
        height = data.height ?: height
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
        const val TAG = "content-image"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    margin = "${Theme.spacing} 0"
                    maxWidth = "100%"
                    display = "block"
                },

                "[$TAG] img" to {
                    maxWidth = "100%"
                    height = "auto"
                    display = "block"
                    borderRadius = Theme.spacing
                },

                "[$TAG] figcaption" to {
                    color = Theme.mediumGray
                    marginTop = Theme.spacing
                    textAlign = "center"
                    fontStyle = "italic"
                }
            )
        }
    }
}
