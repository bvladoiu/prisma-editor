package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography
import kotlin.js.JSON


class KeywordStrip(var keywords: List<String> = emptyList()) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        return document.create.ul {
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@KeywordStrip
            keywords.forEach { keyword ->
                li {
                    classes = setOf(Typography.SMALL_TEXT)
                    +keyword
                }
            }
        }
    }

    fun commit() {
        val data = mapOf(
            "keywords" to keywords
        )
        val jsonData = JSON.stringify(data)
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG")
    }

    fun set(data: dynamic) {
        keywords = data.keywords?.unsafeCast<List<String>>() ?: keywords
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
        const val TAG = "keyword-strip"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    display = "flex"
                    flexWrap = "wrap"
                    setProperty("gap", Theme.spacing)
                    justifyContent = "center"
                    listStyleType = "none"
                    padding = "0"
                    margin = "0"
                },

                "[$TAG] li" to {
                    backgroundColor = "var(--color-light-transparent)"
                    color = Theme.primary
                    padding = "4px ${Theme.spacing}"
                    borderRadius = "4px"
                }
            )
        }
    }
}
