package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.create
import org.w3c.dom.HTMLElement
import kotlinx.html.stream.createHTML
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography

class ArticleCard(
    initialTitle: String = "",
    initialAuthor: String = "",
    initialDate: String = ""
) {
    var title: String = initialTitle
        private set
    var author: String = initialAuthor
        private set
    var date: String = initialDate
        private set

    private var rootElement: HTMLElement? = null

    init {
        load()
    }

    /**
     * Generates the HTML structure for the editor's live view.
     * Returns an HTMLElement using kotlinx.html.dom.
     * Includes editor-specific elements like contenteditable attributes and a delete button.
     */
 fun buildEditorDom(): HTMLElement {
 @Suppress("MoveVariableDeclarationIntoWhen") // Keeping variable declarations outside for clarity in buildEditorDom
        val element = document.create.li {
            attributes["data-component-tag"] = TAG
            this@li.asDynamic().kotlinInstance = this@ArticleCard

            header {
                attributes["data-component-tag"] = HEADER_TAG

                h3 {
                    attributes[Typography.HEADLINE] = ""
                    +title
                }

                time {
                    attributes[Typography.SMALL_TEXT] = ""
                    attributes["contenteditable"] = "true"
                    +date
                }
            }

            footer {
                attributes["data-component-tag"] = AUTHOR_TAG
                attributes[Typography.CAPTION] = "" // Editor-specific
                +"By $author"
            }
        }

 val titleElement = element.querySelector("h3")
 titleElement?.setAttribute("contenteditable", "true")

 val authorElement = element.querySelector("[data-component-tag='$AUTHOR_TAG']")
 authorElement?.setAttribute("contenteditable", "true")

 val dateElement = element.querySelector("time")
 dateElement?.setAttribute("contenteditable", "true")

 val deleteButton = document.create.button {
 attributes["class"] = "delete-button"
 attributes["onclick"] = "this.parentElement.remove()" // This should ideally call a @JsName function
 attributes["title"] = "Delete this article"
            +"-"
        }
 element.insertBefore(deleteButton, element.firstChild)

        return element
    }

    /**
     * Generates the clean, static HTML string for client-side export.\n
     * Returns a String using kotlinx.html.stream.createHTML.\n
     * Excludes editor-specific elements and attributes.\n
     */
    fun buildStaticHtml(): String = createHTML().li {
        attributes["data-component-tag"] = TAG

        header {
            h3 { +title }
            time { +date }
        }
        footer { +"By $author" }
    }

    fun renderTo(parentElement: HTMLElement): HTMLElement {
        val newElement = buildEditorDom()

        rootElement?.remove()

        parentElement.appendChild(newElement)
        rootElement = newElement
        return newElement
    }

    fun commit() {
        val data = mapOf(
            "title" to title,
            "author" to author,
            "date" to date
        )
        val jsonData = JSON.stringify(data)
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG: (Simulated - component created with initial or default values)")
    }

    fun set(data: dynamic) {
        var changed = false

        val newTitle = data.title as? String
        if (newTitle != null && newTitle != title) {
            title = newTitle
            changed = true
        }

        val newAuthor = data.author as? String
        if (newAuthor != null && newAuthor != author) {
            author = newAuthor
            changed = true
        }

        val newDate = data.date as? String
        if (newDate != null && newDate != date) {
            date = newDate
            changed = true
        }

        if (changed) {
            refreshDOM()
        }
    }

    fun refreshDOM() {
        val currentElement = rootElement ?: return
        val parent = currentElement.parentNode ?: return

        val newElement = buildEditorDom()
        parent.replaceChild(newElement, currentElement)
        rootElement = newElement
    }

    fun detach() {
        rootElement?.remove()
        rootElement = null
    }

    companion object {
        const val TAG = "article-card"
        const val HEADER_TAG = "article-header"
        const val AUTHOR_TAG = "article-author"

        fun cssRules(): List<CssRuleDefinition> {
            val selectorBase = "[data-component-tag='$TAG']"
            return listOf(
                selectorBase to {
                    padding = "${Theme.spacing} 0"
                    borderBottom = "1px solid ${Theme.lightTransparent}"
                },

                "[data-component-tag='$HEADER_TAG']" to {
                    display = "flex"
                    justifyContent = "space-between"
                    alignItems = "center"
                    marginBottom = Theme.spacing
                },

                "[data-component-tag='$HEADER_TAG'] h3" to {
                    margin = "0"
                    color = Theme.white
                },

                "[data-component-tag='$HEADER_TAG'] span" to {
                    color = Theme.mediumTransparent
                },

                "[data-component-tag='$AUTHOR_TAG']" to {
                    color = Theme.mediumTransparent
                }
            )
        }
    }
}
