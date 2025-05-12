package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.*

class ArticleCard(var title: String, var author: String, var date: String) {
    fun preview(): HTMLElement {
        return document.create.li {
            attributes["update-item"] = ""

            div {
                attributes["update-header"] = ""

                h3 {
                    +title
                }

                span {
                    +date
                }
            }

            div {
                attributes["update-author"] = ""
                +"By $author"
            }
        }
    }


    companion object {
        const val TAG = "update-item"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[update-item]" to {
                    padding = "${Theme.spacing} 0"
                    borderBottom = "1px solid var(--color-light-transparent)"
                    fontFamily = Typography.defaultFontFamily
                },

                "[update-header]" to {
                    display = "flex"
                    justifyContent = "space-between"
                    alignItems = "center"
                    marginBottom = "8px"
                },

                "[update-header] h3" to {
                    margin = "0"
                    fontSize = Typography.fontSm
                    fontFamily = Typography.defaultFontFamily
                    color = "var(--color-white)"
                },

                "[update-header] span" to {
                    fontSize = Typography.fontXs
                    fontFamily = Typography.defaultFontFamily
                    color = "var(--color-medium-transparent)"
                },

                "[update-author]" to {
                    fontSize = Typography.fontXs
                    fontFamily = Typography.defaultFontFamily
                    color = "var(--color-medium-transparent)"
                }
            )
        }
    }
}
