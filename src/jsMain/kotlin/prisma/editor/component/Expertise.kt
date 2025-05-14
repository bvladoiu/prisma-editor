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

class Expertise(var name: String, var description: String) {
    init {
        load()
    }

    /**
     * Turns the component into an editable state.
     * @return The editable HTMLElement
     */
    fun edit(): HTMLElement {
        val element = preview()

        // Make the name editable
        val nameElement = element.querySelector("h3")
        nameElement?.setAttribute("contenteditable", "true")

        // Make the description editable
        val descriptionElement = element.querySelector("p")
        descriptionElement?.setAttribute("contenteditable", "true")

        // Add delete button
        val deleteButton = document.create.button {
            attributes["class"] = "delete-button"
            attributes["onclick"] = "this.parentElement.remove()"
            attributes["title"] = "Delete this expertise"
            +"-"
        }
        element.insertBefore(deleteButton, element.firstChild)

        return element
    }

    fun preview(): HTMLElement {
        return document.create.article {
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@Expertise
            h3 {
                attributes[Typography.TAGLINE] = ""
                +name
            }
            p {
                attributes[Typography.BODY] = ""
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
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG")
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
                    marginBottom = "8px"
                    color = Theme.white
                },

                "[$TAG] p" to {
                    margin = "0"
                }
            )
        }
    }
}
