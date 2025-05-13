package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography
import kotlin.js.JSON


class Pitch(var title: String = "", var description: String = "") {
    init {
        load()
    }

    fun preview(): HTMLElement {
        return document.create.div {
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@Pitch
            h3 {
                +title
            }
            p {
                +description
            }
        }
    }

    fun commit() {
        val data = mapOf(
            "title" to title,
            "description" to description
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
    }

    fun set(data: dynamic) {
        title = data.title ?: title
        description = data.description ?: description
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
        const val TAG = "pitch"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    padding = Theme.spacing
                    backgroundColor = "var(--color-light-transparent)"
                    borderRadius = "4px"
                    fontFamily = Typography.defaultFontFamily
                },

                "[$TAG] h3" to {
                    fontSize = Typography.fontMd
                    fontWeight = Typography.fontWeightBold
                    fontFamily = Typography.defaultFontFamily
                    marginTop = "0"
                    marginBottom = "8px"
                    color = Theme.primary
                },

                "[$TAG] p" to {
                    margin = "0"
                    fontFamily = Typography.defaultFontFamily
                }
            )
        }
    }
}
