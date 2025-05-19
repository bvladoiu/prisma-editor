package prisma.editor.component

import kotlinx.html.*
import prisma.editor.css.CssRuleDefinition
import kotlinx.html.stream.createHTML
import prisma.editor.css.Theme
import org.w3c.dom.HTMLElement
import kotlinx.browser.document
import prisma.editor.css.Typography
import kotlin.js.JSON
import prisma.editor.Config

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

    private var rootElement: HTMLElement? = null

    fun buildEditorDom(): HTMLElement {
        return document.create.figure {
            attributes["TAG"] = TAG
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

    fun buildStaticHtml(): String {
        return createHTML().figure {
            attributes["TAG"] = TAG
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
            attributes["TAG"] = TAG

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

    fun renderTo(parentElement: HTMLElement): HTMLElement {
        val newElement = buildEditorDom()

        rootElement?.remove()

        parentElement.appendChild(newElement)
        rootElement = newElement
        return newElement
    }

    fun detach() {
        rootElement?.remove()
        rootElement = null
    }

    /**
     * Refreshes the DOM of the component in place if it has been rendered and is still in the DOM.
     */
    fun refresh() {
        val parent = rootElement?.parentElement ?: document.body ?: return // Can't refresh if not in DOM or no body
        detach()
        renderTo(parent)

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
