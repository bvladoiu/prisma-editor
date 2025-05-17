package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.button
import kotlinx.html.dom.create
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.p
import org.w3c.dom.HTMLElement
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Theme.spacing

class Hero(
    initialName: String = "",
    initialDescription: String = "",
    initialButtonText: String = ""
) {

    var name: String = initialName
        private set
    var description: String = initialDescription
        private set
    var buttonText: String = initialButtonText
        private set

    private var rootElement: HTMLElement? = null

    init {
        load()
    }

    private fun buildHtml(): HTMLElement {
        return document.create.header {
            attributes["data-component-tag"] = TAG
            attributes["role"] = "banner"
            this@header.asDynamic().kotlinInstance = this@Hero

            h1 { +name }
            p { +description }
            button { +buttonText }
        }
    }

    fun renderTo(parentElement: HTMLElement): HTMLElement {
        val newElement = buildHtml()

        rootElement?.remove()

        parentElement.appendChild(newElement)
        rootElement = newElement
        return newElement
    }

    fun preview(): HTMLElement {
        return buildHtml()
    }

    fun set(data: dynamic) {
        var changed = false

        val newName = data.name as? String
        if (newName != null && newName != name) {
            name = newName
            changed = true
        }

        val newDescription = data.description as? String
        if (newDescription != null && newDescription != description) {
            description = newDescription
            changed = true
        }

        val newButtonText = data.buttonText as? String
        if (newButtonText != null && newButtonText != buttonText) {
            buttonText = newButtonText
            changed = true
        }

        if (changed) {
            refreshDOM()
        }
    }

    fun refreshDOM() {
        val currentElement = rootElement ?: return
        val parent = currentElement.parentNode ?: return

        val newElement = buildHtml()
        parent.replaceChild(newElement, currentElement)
        rootElement = newElement
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
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG: (Simulated - component created with initial or default values)")
    }

    fun detach() {
        rootElement?.remove()
        rootElement = null
    }

    companion object {
        const val TAG = "hero"

        fun cssRules(): List<CssRuleDefinition> {
            val selectorBase = "[data-component-tag='$TAG']"
            return listOf(
                selectorBase to {
                    textAlign = "center"
                    padding = spacing
                },
                "$selectorBase h1" to {
                    margin = spacing
                },
                "$selectorBase p" to {
                    maxWidth = "600px"
                    marginLeft = "auto"
                    marginRight = "auto"
                },
                "$selectorBase button" to {
                    backgroundColor = Theme.secondary
                    color = Theme.white
                    padding = spacing
                    borderRadius = spacing
                    border = "none"
                    cursor = "pointer"
                }
            )
        }
    }
}
