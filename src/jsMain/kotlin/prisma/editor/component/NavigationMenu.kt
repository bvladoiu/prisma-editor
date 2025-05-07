package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import web.html.HTMLElement
import org.w3c.dom.HTMLElement as W3CHTMLElement
import org.w3c.dom.css.*
import prisma.editor.styles.Theme

/**
 * Creates a navigation menu with the 'navigation-menu' attribute.
 */
class NavigationMenu(
    var brand: String = "Prisma-Software",
    var items: List<String> = listOf("Home", "News", "Blog", "Company")
) : HTMLElement {
    fun create(): W3CHTMLElement {
        return document.create.nav {
            // Add the 'navigation-menu' attribute for CSS targeting
            attributes["navigation-menu"] = ""

            div {
                a {
                    href = "#"
                    // Add the 'brand' class for CSS targeting
                    classes = setOf("brand")
                    +brand
                }

                ul {
                    items.forEach { item ->
                        li {
                            a {
                                href = "#"
                                +item
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates and returns a stylesheet for the NavigationMenu component.
     * This method uses CSSOM API to create a stylesheet with rules for the component.
     */
    fun stylesheet(): CSSStyleSheet {
        // Create a new style element
        val styleElement = document.createElement("style")
        document.head?.appendChild(styleElement)

        // Get the stylesheet from the document's styleSheets collection
        val stylesheet = document.styleSheets[document.styleSheets.length - 1] as CSSStyleSheet

        // Add rules to the stylesheet

        // Container rule
        val containerRule = """
            [navigation-menu] {
                background-color: rgba(0, 0, 0, 0.8);
                position: fixed;
                top: 0;
                left: 0;
                right: 0;
                z-index: 1000;
            }
        """.trimIndent()
        stylesheet.insertRule(containerRule, stylesheet.cssRules.length)

        // Content rule
        val contentRule = """
            [navigation-menu] > div {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: ${Theme.Spacing.md};
                max-width: ${Theme.Spacing.maxContentWidth};
                margin: 0 auto;
            }
        """.trimIndent()
        stylesheet.insertRule(contentRule, stylesheet.cssRules.length)

        // Brand rule
        val brandRule = """
            [navigation-menu] a.brand {
                color: ${Theme.Colors.primary};
                font-size: ${Theme.Typography.fontMd};
                font-weight: ${Theme.Typography.fontWeightBold};
                text-decoration: none;
            }
        """.trimIndent()
        stylesheet.insertRule(brandRule, stylesheet.cssRules.length)

        // Nav list rule
        val navListRule = """
            [navigation-menu] ul {
                display: flex;
                list-style-type: none;
                margin: 0;
                padding: 0;
                gap: ${Theme.Spacing.md};
            }
        """.trimIndent()
        stylesheet.insertRule(navListRule, stylesheet.cssRules.length)

        // Nav link rule
        val navLinkRule = """
            [navigation-menu] ul a {
                color: ${Theme.Colors.white};
                text-decoration: none;
                font-size: ${Theme.Typography.fontSm};
            }
        """.trimIndent()
        stylesheet.insertRule(navLinkRule, stylesheet.cssRules.length)

        return stylesheet
    }
}
