package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.css.*
import prisma.editor.styles.Theme

/**
 * Creates a section with a title and content.
 */
class Section(
    var title: String? = null,
    var isDivider: Boolean = false,
    var marginTop: String = "2rem",
    var content: String = ""
) {
    fun preview(): HTMLElement {
        return document.create.section {
            // Add the 'section-container' attribute for CSS targeting
            attributes["section-container"] = ""
            // Add the margin-top as a data attribute for CSS targeting
            attributes["data-margin-top"] = marginTop

            article {
                // Add the 'section-article' attribute for CSS targeting
                attributes["section-article"] = ""

                if (title != null) {
                    if (isDivider) {
                        header {
                            // Add the 'section-header-with-divider' attribute for CSS targeting
                            attributes["section-header-with-divider"] = ""
                            h2 { 
                                // Add the 'section-heading' attribute for CSS targeting
                                attributes["section-heading"] = ""
                                +title 
                            }
                        }
                    } else {
                        header {
                            // Add the 'section-header-without-divider' attribute for CSS targeting
                            attributes["section-header-without-divider"] = ""
                            h2 { 
                                // Add the 'section-heading' attribute for CSS targeting
                                attributes["section-heading"] = ""
                                +title 
                            }
                        }
                    }
                }

                // Content
                div {
                    +content
                }
            }
        }
    }

    /**
     * Creates and returns a stylesheet for the Section component.
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
            [section-container] {
                margin-top: ${Theme.Spacing.rem2};
                margin-bottom: ${Theme.Spacing.rem2};
                padding: ${Theme.Spacing.none} ${Theme.Spacing.md};
                max-width: ${Theme.Spacing.maxContentWidth};
                margin-left: auto;
                margin-right: auto;
            }
        """.trimIndent()
        stylesheet.insertRule(containerRule, stylesheet.cssRules.length)

        // Article rule
        val articleRule = """
            [section-article] {
                padding: ${Theme.Spacing.md};
                background-color: ${Theme.Colors.veryLightTransparent};
                border-radius: ${Theme.Spacing.borderRadius};
            }
        """.trimIndent()
        stylesheet.insertRule(articleRule, stylesheet.cssRules.length)

        // Header with divider rule
        val headerWithDividerRule = """
            [section-header-with-divider] {
                font-size: ${Theme.Typography.fontLg};
                font-weight: ${Theme.Typography.fontWeightBold};
                font-family: ${Theme.Typography.defaultFontFamily};
                margin-bottom: ${Theme.Spacing.md};
                padding-bottom: ${Theme.Spacing.sm};
                border-bottom: 1px solid ${Theme.Colors.mediumGray};
            }
        """.trimIndent()
        stylesheet.insertRule(headerWithDividerRule, stylesheet.cssRules.length)

        // Header without divider rule
        val headerWithoutDividerRule = """
            [section-header-without-divider] {
                margin-bottom: ${Theme.Spacing.md};
            }
        """.trimIndent()
        stylesheet.insertRule(headerWithoutDividerRule, stylesheet.cssRules.length)

        // Heading rule
        val headingRule = """
            [section-heading] {
                font-size: ${Theme.Typography.fontLg};
                font-weight: ${Theme.Typography.fontWeightBold};
                font-family: ${Theme.Typography.defaultFontFamily};
                margin: ${Theme.Spacing.none};
            }
        """.trimIndent()
        stylesheet.insertRule(headingRule, stylesheet.cssRules.length)

        return stylesheet
    }
}
