package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import kotlin.js.JSON

/**
 * Component for section headers.
 * @param title The title text to display in the header.
 * @param isDivider Whether to show a divider line below the header.
 */
class SectionHeader(
    var title: String,
    var isDivider: Boolean = false
) {
    init {
        load()
    }

    fun buildHtml(isEditing: Boolean = false): HTMLElement {
        val element = document.create.header {
            attributes[TAG] = ""
            attributes["data-divider"] = isDivider.toString()
            asDynamic().kotlinInstance = this@SectionHeader

            // Use typography class directly for the heading
            h2 {
                classes = setOf(prisma.editor.css.Typography.HEADLINE)
                +title
            }
        }

        if (isEditing) {
            // Make the title editable
            val titleElement = element.querySelector("h2")
            titleElement?.setAttribute("contenteditable", "true")

            // Add checkbox for isDivider
            val dividerCheckbox = document.create.div {
                style = "margin-top: 8px; display: flex; align-items: center;"

                input {
                    type = InputType.checkBox
                    checked = isDivider
                    id = "divider-checkbox"
                    onChange = "this.parentElement.parentElement.kotlinInstance.toggleDivider(this.checked)"
                }

                label {
                    htmlFor = "divider-checkbox"
                    style = "margin-left: 8px; font-size: 14px; color: var(--color-white);"
                    +"Show divider"
                }
            }
            element.appendChild(dividerCheckbox)
        }

        return element
    }

    fun commit() {
        val data = mapOf(
            "title" to title,
            "isDivider" to isDivider
        )
        val jsonData = JSON.stringify(data)
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG")
    }

    fun set(data: dynamic) {
        title = data.title ?: title
        if (data.isDivider != null) {
            isDivider = data.isDivider as Boolean
        }
        refresh()
    }

    fun refresh() {
        val existingElement = document.querySelector("[$TAG]")
        if (existingElement != null) {
            existingElement.parentElement?.replaceChild(buildHtml(), existingElement)
        } else {
            console.warn("No existing element with attribute [$TAG] found to refresh.")
            document.body?.appendChild(buildHtml())
        }
    }

    /**
     * Toggles the divider state.
     * This is called from the checkbox in the edit view.
     */
    @JsName("toggleDivider")
    fun toggleDivider(checked: Boolean) {
        isDivider = checked
        val element = document.querySelector("[$TAG]")
        element?.setAttribute("data-divider", isDivider.toString())
    }

    companion object {
        const val TAG = "section-header"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[TAG='$TAG']" to {
                    marginBottom = Theme.spacing
                },
                "[TAG='$TAG'][data-divider='true']" to {
                    paddingBottom = Theme.spacing
                    borderBottom = "1px solid var(--color-medium-gray)"
                }
            )
        }
    }
}
