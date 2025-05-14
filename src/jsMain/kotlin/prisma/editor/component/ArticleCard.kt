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

    /**
     * Turns the component into an editable state.
     * @return The editable HTMLElement
     */
    fun edit(): HTMLElement {
        val element = preview()

        // Make the title editable
        val titleElement = element.querySelector("h3")
        titleElement?.setAttribute("contenteditable", "true")

        // Make the author editable
        val authorElement = element.querySelector("[$AUTHOR_TAG]")
        authorElement?.setAttribute("contenteditable", "true")

        // Make the date editable
        val dateElement = element.querySelector("time")
        dateElement?.setAttribute("contenteditable", "true")

        // Add delete button
        val deleteButton = document.create.button {
            attributes["class"] = "delete-button"
            attributes["onclick"] = "this.parentElement.remove()"
            attributes["title"] = "Delete this article"
            +"-"
        }
        element.insertBefore(deleteButton, element.firstChild)

        return element
    }

    fun preview(): HTMLElement {
        return document.create.li {
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@ArticleCard

            header {
                attributes[HEADER_TAG] = ""

                h3 {
                    +title
                }

                time {
                    +date
                }
            }

            footer {
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
                    marginBottom = Theme.spacing
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
