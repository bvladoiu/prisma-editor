package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography

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
            attributes["section-container"] = ""
            attributes["data-margin-top"] = marginTop

            article {
                attributes["section-article"] = ""

                if (title != null) {
                    if (isDivider) {
                        header {
                            attributes["section-header-with-divider"] = ""
                            h2 {
                                attributes["section-heading"] = ""
                                +title
                            }
                        }
                    } else {
                        header {
                            attributes["section-header-without-divider"] = ""
                            h2 {
                                attributes["section-heading"] = ""
                                +title
                            }
                        }
                    }
                }
                div {
                    +content
                }
            }
        }
    }

    companion object {
        const val TAG = "section-container"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[section-container]" to {
                    marginTop = "2rem"
                    marginBottom = "2rem"
                    padding = "0 ${Theme.spacing}"
                    maxWidth = "1200px"
                    marginLeft = "auto"
                    marginRight = "auto"
                },

                "[section-article]" to {
                    padding = Theme.spacing
                    backgroundColor = "var(--color-very-light-transparent)"
                    borderRadius = "4px"
                },

                "[section-header-with-divider]" to {
                    fontSize = Typography.fontLg
                    fontWeight = Typography.fontWeightBold
                    fontFamily = Typography.defaultFontFamily
                    marginBottom = Theme.spacing
                    paddingBottom = "8px"
                    borderBottom = "1px solid var(--color-medium-gray)"
                },

                "[section-header-without-divider]" to {
                    marginBottom = Theme.spacing
                },

                "[section-heading]" to {
                    fontSize = Typography.fontLg
                    fontWeight = Typography.fontWeightBold
                    fontFamily = Typography.defaultFontFamily
                    margin = "0"
                }
            )
        }
    }
}
