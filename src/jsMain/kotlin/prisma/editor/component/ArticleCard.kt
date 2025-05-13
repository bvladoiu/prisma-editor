package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.*
import kotlin.js.JSON

class ArticleCard(var title: String, var author: String, var date: String) {
    init {
        load()
    }

    fun preview(): HTMLElement {
        return document.create.li {
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@ArticleCard

            div {
                attributes[HEADER_TAG] = ""

                h3 {
                    +title
                }

                span {
                    +date
                }
            }

            div {
                attributes[AUTHOR_TAG] = ""
                +"By $author"
            }
        }
    }

    fun commit() {
        val data = mapOf(
            "title" to title,
            "author" to author,
            "date" to date
        )
        val jsonData = JSON.stringify(data)
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
    }

    fun set(data: dynamic) {
        title = data.title ?: title
        author = data.author ?: author
        date = data.date ?: date
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
        const val TAG = "update-item"
        const val HEADER_TAG = "update-header"
        const val AUTHOR_TAG = "update-author"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    padding = "${Theme.spacing} 0"
                    borderBottom = "1px solid var(--color-light-transparent)"
                    fontFamily = Typography.defaultFontFamily
                },

                "[$HEADER_TAG]" to {
                    display = "flex"
                    justifyContent = "space-between"
                    alignItems = "center"
                    marginBottom = "8px"
                },

                "[$HEADER_TAG] h3" to {
                    margin = "0"
                    fontSize = Typography.fontSm
                    fontFamily = Typography.defaultFontFamily
                    color = "var(--color-white)"
                },

                "[$HEADER_TAG] span" to {
                    fontSize = Typography.fontXs
                    fontFamily = Typography.defaultFontFamily
                    color = "var(--color-medium-transparent)"
                },

                "[$AUTHOR_TAG]" to {
                    fontSize = Typography.fontXs
                    fontFamily = Typography.defaultFontFamily
                    color = "var(--color-medium-transparent)"
                }
            )
        }
    }
}
