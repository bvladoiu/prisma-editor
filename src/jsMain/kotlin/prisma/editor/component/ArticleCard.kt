package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.create
import org.w3c.dom.HTMLElement
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography

class ArticleCard(
    // Initial properties can be set via constructor
    initialTitle: String = "",
    initialAuthor: String = "",
    initialDate: String = ""
) {
    var title: String = initialTitle
        private set // Properties are updated via set() method
    var author: String = initialAuthor
        private set
    var date: String = initialDate
        private set

    private var rootElement: HTMLElement? = null

    init {
        // Load initial data if necessary
        load()
    }

    /**
     * Turns the component into an editable state.
     * @return The editable HTMLElement
     */
    fun edit(): HTMLElement {
        val element = buildHtml()

        // Make the title editable
        val titleElement = element.querySelector("h3")
        titleElement?.setAttribute("contenteditable", "true")

        // Make the author editable
        val authorElement = element.querySelector("[data-component-tag='$AUTHOR_TAG']")
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

    // Generates the HTML structure for the component based on current properties
    private fun buildHtml(): HTMLElement {
        return document.create.li {
            attributes["data-component-tag"] = TAG
            // Store a reference to the Kotlin component instance on the DOM element
            this@li.asDynamic().kotlinInstance = this@ArticleCard

            header {
                attributes["data-component-tag"] = HEADER_TAG

                h3 {
                    attributes[Typography.HEADLINE] = ""
                    +title
                }

                time {
                    attributes[Typography.SMALL_TEXT] = ""
                    +date
                }
            }

            footer {
                attributes["data-component-tag"] = AUTHOR_TAG
                attributes[Typography.CAPTION] = ""
                +"By $author"
            }
        }
    }

    /**
     * Renders the component into the given parentElement.
     * If the component was already rendered elsewhere, it will be moved.
     * If called on an already rendered component within the same parent, it effectively refreshes it.
     */
    fun renderTo(parentElement: HTMLElement): HTMLElement {
        val newElement = buildHtml()

        // If this component instance is already associated with a DOM element,
        // remove it from its current parent before appending to the new parent.
        rootElement?.remove()

        parentElement.appendChild(newElement)
        rootElement = newElement
        return newElement
    }

    /**
     * Returns a preview of the component without rendering it to the DOM.
     * This is useful for previewing the component before committing it.
     */
    fun preview(): HTMLElement {
        return buildHtml()
    }

    /**
     * Placeholder for committing data, e.g., saving to a backend.
     */
    fun commit() {
        val data = mapOf(
            "title" to title,
            "author" to author,
            "date" to date
        )
        val jsonData = JSON.stringify(data)
        // In a real app, this might be an API call
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    /**
     * Placeholder for loading initial data.
     * This might involve fetching data and then calling set() to update the component.
     */
    fun load() {
        // Example: Fetch data and then call set.
        // val fetchedData = MyApi.fetchArticleData(Config.currentSite, Config.currentLanguage)
        // set(fetchedData)
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG: (Simulated - component created with initial or default values)")
    }

    /**
     * Updates the component's data and refreshes its DOM representation if it's already rendered.
     * @param data A dynamic object expected to have optional title, author, and date properties.
     */
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

    /**
     * Refreshes the DOM of the component in place if it has been rendered and is still in the DOM.
     * This is typically called after properties are updated via set().
     */
    fun refreshDOM() {
        val currentElement = rootElement ?: return // Not rendered yet
        val parent = currentElement.parentNode ?: return // Rendered but detached from DOM

        val newElement = buildHtml()
        parent.replaceChild(newElement, currentElement)
        rootElement = newElement // Update the stored reference to the new element
    }

    /**
     * Removes the component's DOM element from the document.
     */
    fun detach() {
        rootElement?.remove()
        rootElement = null
    }

    companion object {
        // TAG is used for styling via data-component-tag and potentially for logging/identification.
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
