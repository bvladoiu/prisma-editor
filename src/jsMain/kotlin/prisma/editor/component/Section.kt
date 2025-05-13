package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography
import kotlin.js.JSON


class Section(
    var title: String? = null,
    var isDivider: Boolean = false,
    var marginTop: String = "2rem",
    var content: String = ""
) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        return document.create.section {
            attributes[TAG] = ""
            attributes["data-margin-top"] = marginTop
            asDynamic().kotlinInstance = this@Section

            article {
                attributes[ARTICLE_TAG] = ""

                if (title != null) {
                    if (isDivider) {
                        header {
                            attributes[HEADER_WITH_DIVIDER_TAG] = ""
                            h2 {
                                attributes[HEADING_TAG] = ""
                                +title
                            }
                        }
                    } else {
                        header {
                            attributes[HEADER_WITHOUT_DIVIDER_TAG] = ""
                            h2 {
                                attributes[HEADING_TAG] = ""
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

    fun commit() {
        val data = mapOf(
            "title" to title,
            "isDivider" to isDivider,
            "marginTop" to marginTop,
            "content" to content
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
    }

    fun set(data: dynamic) {
        title = data.title ?: title
        isDivider = data.isDivider ?: isDivider
        marginTop = data.marginTop ?: marginTop
        content = data.content ?: content
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
        const val TAG = "section-container"
        const val ARTICLE_TAG = "section-article"
        const val HEADER_WITH_DIVIDER_TAG = "section-header-with-divider"
        const val HEADER_WITHOUT_DIVIDER_TAG = "section-header-without-divider"
        const val HEADING_TAG = "section-heading"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    marginTop = "2rem"
                    marginBottom = "2rem"
                    padding = "0 ${Theme.spacing}"
                    maxWidth = "1200px"
                    marginLeft = "auto"
                    marginRight = "auto"
                },

                "[$ARTICLE_TAG]" to {
                    padding = Theme.spacing
                    backgroundColor = "var(--color-very-light-transparent)"
                    borderRadius = "4px"
                },

                "[$HEADER_WITH_DIVIDER_TAG]" to {
                    fontSize = Typography.fontLg
                    fontWeight = Typography.fontWeightBold
                    fontFamily = Typography.defaultFontFamily
                    marginBottom = Theme.spacing
                    paddingBottom = "8px"
                    borderBottom = "1px solid var(--color-medium-gray)"
                },

                "[$HEADER_WITHOUT_DIVIDER_TAG]" to {
                    marginBottom = Theme.spacing
                },

                "[$HEADING_TAG]" to {
                    fontSize = Typography.fontLg
                    fontWeight = Typography.fontWeightBold
                    fontFamily = Typography.defaultFontFamily
                    margin = "0"
                }
            )
        }
    }
}
