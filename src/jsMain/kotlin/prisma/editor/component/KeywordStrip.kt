package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography
import kotlin.js.JSON


class KeywordStrip(var keywords: List<String> = emptyList()) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        return document.create.div {
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@KeywordStrip
            keywords.forEach { keyword ->
                span {
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
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
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
                },

                "[$TAG] span" to {
                    backgroundColor = "var(--color-light-transparent)"
                    color = Theme.primary
                    padding = "4px ${Theme.spacing}"
                    borderRadius = "4px"
                    fontSize = Typography.fontXs
                }
            )
        }
    }
}
