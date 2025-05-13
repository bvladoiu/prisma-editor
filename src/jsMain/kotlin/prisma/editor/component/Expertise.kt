package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography
import kotlin.js.JSON

class Expertise(var name: String, var description: String) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        return document.create.div {
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@Expertise
            h3 {
                +name
            }
            p {
                +description
            }
        }
    }

    fun commit() {
        val data = mapOf(
            "name" to name,
            "description" to description
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
    }

    fun set(data: dynamic) {
        name = data.name ?: name
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
        const val TAG = "expertise"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    padding = Theme.spacing
                    backgroundColor = "var(--color-light-transparent)"
                    borderRadius = "4px"
                    height = "100%"
                },

                "[$TAG] h3" to {
                    fontSize = Typography.fontMd
                    fontFamily = Typography.defaultFontFamily
                    marginBottom = "8px"
                    color = Theme.white
                },

                "[$TAG] p" to {
                    margin = "0"
                    lineHeight = Typography.lineHeightNormal
                    fontFamily = Typography.defaultFontFamily
                }
            )
        }
    }
}
