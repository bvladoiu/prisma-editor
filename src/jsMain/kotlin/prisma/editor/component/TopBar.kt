package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.create
import org.w3c.dom.HTMLElement
import prisma.editor.Config
import prisma.editor.component.LangSelect
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.component.MenuButton

import kotlin.js.JSON

class TopBar {

    // No state properties for now, as the elements within are placeholders

    private var rootElement: HTMLElement? = null

    private val logo = Logo("/images/logo.png", "Home", "/")
 private val langSelect = LangSelect()
    private val menuButton = MenuButton()

    init {
        load()
    }

    fun buildEditorDom(): HTMLElement {
        return document.create.header {
            attributes["TAG"] = TAG
            asDynamic().kotlinInstance = this@TopBar
            attributes["role"] = "banner" // Add semantic role

            unsafe { +menuButton.buildStaticHtml() }

            // Placeholder for Theme Select
            div {
                attributes["topbar-theme-select"] = ""
                +"[Theme Select Placeholder]"
            }

            div {
                attributes["topbar-language-select"] = ""
 unsafe { +langSelect.buildHtml().outerHTML }
            }

            unsafe { +logo.buildStaticHtml() }

            // Placeholder for CTA Button
            div {
                attributes["topbar-cta-button"] = ""
                +"[CTA Button Placeholder]"
            }
        }

    }

    fun renderTo(parentElement: HTMLElement): HTMLElement {
        val newElement = buildEditorDom()

        rootElement?.remove()

        parentElement.appendChild(newElement)
        logo.renderTo(newElement)
 menuButton.renderTo(newElement)
        rootElement = newElement
        return newElement
    }

    /**
     * Generates the static HTML string for the published site.
     * Does NOT include any editor-specific attributes or elements.
     */
    fun buildStaticHtml(): String {
        return document.create.header {
            attributes["TAG"] = TAG // Keep TAG for potential static CSS
            attributes["role"] = "banner" // Add semantic role

            unsafe { +menuButton.buildStaticHtml() }


            // Placeholder for Theme Select
            div { attributes["topbar-theme-select"] = "" }

            div { attributes["topbar-language-select"] = "" }
 unsafe { +langSelect.buildHtml().outerHTML }
            unsafe { +logo.buildStaticHtml() }
            a(href = "#") { attributes["topbar-home-link"] = "" }

            // Placeholder for CTA Button
            div { attributes["topbar-cta-button"] = "" }
        }.outerHTML // Get the HTML string
    }
    fun detach() {
        rootElement?.remove()
        rootElement = null
    }

    fun commit() {
        // Placeholder for committing data
        val data = mapOf(
            "componentType" to "TopBar"
            // Add any relevant state here if needed
            )
        val jsonData = JSON.stringify(data)
        console.log("commit:$TAG", jsonData)
    }

    fun load() {
        // Placeholder for loading initial data
        console.log("load:$TAG: (Simulated - component created with default values)")
    }

    fun set(data: dynamic) {
        // No state properties to update via set() for now
        console.log("set:$TAG", data)
    }

    private companion object {
        // Layout rules for elements within the TopBar
        fun layoutCssRules(): List<CssRuleDefinition> {
            val selectorBase = "[TAG='$TAG']"
            return listOf(
                "$selectorBase > *" to {
                    // Apply some basic spacing between items
                    margin = "0 ${Theme.spacing}"
                },
                // Optional: Specific styles for each placeholder if needed
                // "$selectorBase [topbar-theme-select]" to { ... },
                // "$selectorBase [topbar-language-select]" to { ... },
                // "$selectorBase [topbar-home-link]" to { ... },
                // "$selectorBase [topbar-cta-button]" to { ... }
            )
        }
    }


    companion object {
        const val TAG = "editor-top-bar"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[TAG='$TAG']" to {
                    backgroundColor = Theme.darkBackground
                    color = Theme.white
                    padding = Theme.spacing
                    display = "flex"
                    justifyContent = "space-between"
                    alignItems = "center"
                    setProperty("box-shadow", "0 ${Theme.spacing} ${Theme.spacing} ${Theme.shadowLight}")
                }

            ) + layoutCssRules() // Include layout rules
        }
    }
}