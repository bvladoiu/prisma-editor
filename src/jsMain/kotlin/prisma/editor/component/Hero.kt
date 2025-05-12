package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.css.*
import prisma.editor.styles.Theme

@OptIn(ExperimentalJsExport::class)
@JsExport
class Hero(
    var name: String = "",
    var description: String = "",
    var buttonText: String = ""
) {
    val TAG = "hero"

    init {
        load()
    }

    fun preview(): HTMLElement {
        return document.create.div {
            attributes[TAG] = ""
            asDynamic().kotlinComponent = this@Hero
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

    /**
     * Creates and returns a stylesheet for the Hero component.
     * This method uses CSSOM API to create a stylesheet with rules for the component.
     */
    fun stylesheet(): CSSStyleSheet {
        // Create a new style element
        val styleElement = document.createElement("style")
        document.head?.appendChild(styleElement)

        val stylesheet = document.styleSheets[document.styleSheets.length - 1] as CSSStyleSheet

        val containerRule = """
            [$TAG] {
                text-align: center;
                padding: ${Theme.Spacing.xl} ${Theme.Spacing.md};
            }
        """.trimIndent()
        stylesheet.insertRule(containerRule, stylesheet.cssRules.length)

        val titleRule = """
            [$TAG] h1 {
                font-size: ${Theme.Typography.fontXl};
                font-weight: ${Theme.Typography.fontWeightNormal};
                font-family: ${Theme.Typography.defaultFontFamily};
                margin-top: ${Theme.Spacing.rem2};
                margin-bottom: ${Theme.Spacing.md};
                color: ${Theme.Colors.primary};
            }
        """.trimIndent()
        stylesheet.insertRule(titleRule, stylesheet.cssRules.length)

        val descriptionRule = """
            [$TAG] p {
                font-size: ${Theme.Typography.fontSm};
                line-height: ${Theme.Typography.lineHeightLarge};
                font-family: ${Theme.Typography.defaultFontFamily};
                margin-bottom: ${Theme.Spacing.lg};
                max-width: 600px;
                margin-left: auto;
                margin-right: auto;
            }
        """.trimIndent()
        stylesheet.insertRule(descriptionRule, stylesheet.cssRules.length)

        val buttonRule = """
            [$TAG] button {
                background-color: ${Theme.Colors.secondary};
                color: ${Theme.Colors.white};
                padding: ${Theme.Spacing.sm} ${Theme.Spacing.md};
                border: ${Theme.Spacing.none};
                border-radius: ${Theme.Spacing.borderRadius};
                font-size: ${Theme.Typography.fontSm};
                cursor: pointer;
            }
        """.trimIndent()
        stylesheet.insertRule(buttonRule, stylesheet.cssRules.length)

        return stylesheet
    }
}
