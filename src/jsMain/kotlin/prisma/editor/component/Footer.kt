package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.*
import kotlinx.html.stream.createHTML
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme.spacing
import kotlin.js.JSON

class Footer(
    var copyright: String = "Prisma-Software © 2024, All rights reserved.",
    var links: List<String> = listOf("Privacy Policy", "Terms of Service")
) {
 private var rootElement: HTMLElement? = null

    init {
        load()
    }

    fun buildEditorDom(): HTMLElement {
        return document.create.footer {
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@Footer
            p { +copyright }
            nav {
                attributes[CONTENT_TAG] = ""
                links.forEach { link ->
                    a {
                        href = "#"
                        +link
                    }
                }
            }
        }
    }

    fun buildStaticHtml(): String {
 return createHTML().footer {
 attributes[TAG] = "" // Keep TAG for potential static CSS
            p { +copyright }
 nav {
 attributes[CONTENT_TAG] = "" // Keep CONTENT_TAG for potential static CSS
 links.forEach { link ->
 a {
 href = "#"
 +link
 }
 }
 }
 }
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


    fun commit() {
        val data = mapOf(
            "copyright" to copyright,
            "links" to links
        )
        val jsonData = JSON.stringify(data)
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG")
    }

    fun set(data: dynamic) {
        copyright = data.copyright ?: copyright
        links = data.links?.unsafeCast<List<String>>() ?: links
        refresh()
    }

    fun refresh() {
        val parent = rootElement?.parentElement
 if (parent != null) {
 detach()
 renderTo(parent)
        }
    }

    companion object {
        const val TAG = "footer"
        const val CONTENT_TAG = "footer-content"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[TAG='$TAG']" to {
                    backgroundColor = "var(--color-dark-background)"
                    padding = spacing
                    color = "var(--color-light-gray)"
                    textAlign = "center"
                    marginTop = spacing
                },

                "[TAG='$CONTENT_TAG']" to {
                    maxWidth = "600px"
                    margin = "0 auto"
                    padding = spacing
                },
                "[TAG='$TAG'] a" to {
                    color = "var(--color-white)"
                    textDecoration = "underline"
                }
            )
        }
    }
}
