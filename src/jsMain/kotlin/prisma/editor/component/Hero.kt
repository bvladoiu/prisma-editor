package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.Config
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
        return document.create.header {
            attributes["TAG"] = TAG
            attributes["role"] = "banner"
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
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG")
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

                "[TAG='$TAG']" to {
                    textAlign = "center"
                    padding = spacing
                },

                "[TAG='$TAG'] h1" to {
                    margin = spacing
                },

                "[TAG='$TAG'] p" to {
                    maxWidth = "600px"
                    marginLeft = "auto"
                    marginRight = "auto"
                },

                "[TAG='$TAG'] button" to {
                    backgroundColor = Theme.secondary
                    color = Theme.white
                    padding = spacing
                    borderRadius = spacing
                    cursor = "pointer"
                }
            )
        }
    }
}
