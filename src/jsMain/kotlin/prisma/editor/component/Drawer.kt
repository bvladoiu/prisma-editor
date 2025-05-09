package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.css.*
import prisma.editor.styles.Theme

/**
 * Represents a navigation drawer component.
 */
class Drawer(var items: List<NavLink> = emptyList(), var opened: Boolean = false) {
    fun preview(): HTMLElement {
        val nav = document.create.nav {
            id = "drawer"
            attributes["drawer"] = ""
            if (opened) {
                attributes["style"] = "transform: translateX(0px);"
            }
        }

        // Drawer items
        val itemsList = document.create.ul {
            attributes["drawer-items"] = ""
        }

        items.forEach { navLink ->
            itemsList.appendChild(navLink.preview())
        }

        nav.appendChild(itemsList)

        return nav
    }

    /**
     * Creates and returns a stylesheet for the Drawer component.
     * This method uses CSSOM API to create a stylesheet with rules for the component.
     */
    fun stylesheet(): CSSStyleSheet {
        // Create a new style element
        val styleElement = document.createElement("style")
        document.head?.appendChild(styleElement)

        // Get the stylesheet from the document's styleSheets collection
        val stylesheet = document.styleSheets[document.styleSheets.length - 1] as CSSStyleSheet

        // Add rules to the stylesheet

        // Drawer rule
        val drawerRule = """
            [drawer] {
                width: 240px;
                height: 100%;
                background-color: white;
                box-shadow: 2px 0 4px rgba(0,0,0,0.2);
                overflow: auto;
                transform: translateX(-240px);
                transition: transform 0.3s ease-in-out;
            }
        """.trimIndent()
        stylesheet.insertRule(drawerRule, stylesheet.cssRules.length)

        // Drawer header rule
        val headerRule = """
            [drawer-header] {
                padding: ${Theme.Spacing.md};
                background-color: #7D3DF3;
                color: ${Theme.Colors.white};
            }
        """.trimIndent()
        stylesheet.insertRule(headerRule, stylesheet.cssRules.length)

        // Drawer title rule
        val titleRule = """
            [drawer-title] {
                margin: 0;
                font-size: ${Theme.Typography.fontSm};
                font-family: ${Theme.Typography.defaultFontFamily};
            }
        """.trimIndent()
        stylesheet.insertRule(titleRule, stylesheet.cssRules.length)

        // Drawer items rule
        val itemsRule = """
            [drawer-items] {
                list-style-type: none;
                padding: 0;
                margin: 0;
            }
        """.trimIndent()
        stylesheet.insertRule(itemsRule, stylesheet.cssRules.length)

        return stylesheet
    }
}
