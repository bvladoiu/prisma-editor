package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Theme.spacing

class Hero(
    var name: String = "",
    var description: String = "",
    var buttonText: String = ""
) {

    init {
        load()
    }

    fun preview(): HTMLElement {
        return document.create.div {
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@Hero
            h1 {
                +name
            }
            p {
                +description
            }
            button {
                +buttonText
            }
        }
    }

    fun commit() {
        val data = mapOf(
            "name" to name,
            "description" to description,
            "buttonText" to buttonText
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
        buttonText = data.buttonText ?: buttonText
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

        const val TAG = "hero"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(

                "[$TAG]" to {
                    textAlign = "center"
                    padding = spacing
                },

                "[$TAG] h1" to {
                    margin = spacing
                },

                "[$TAG] p" to {
                    maxWidth = "600px"
                    marginLeft = "auto"
                    marginRight = "auto"
                },

                "[$TAG] button" to {
                    backgroundColor = Theme.secondary
                    color = Theme.white
                    padding = spacing
                    borderRadius = "4px"
                    cursor = "pointer"
                }
            )
        }
    }
}