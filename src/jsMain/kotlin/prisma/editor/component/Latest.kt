package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.button
import kotlinx.html.dom.create
import kotlinx.html.ul
import org.w3c.dom.HTMLElement
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme


class Latest(
    // Initial properties can be set via constructor
    initialArticles: List<ArticleCard> = emptyList()
) {
    var articles: List<ArticleCard> = initialArticles
        private set // Properties are updated via set() method

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

        // Convert all article cards to editable mode
        val articleElements = element.querySelectorAll("[data-component-tag='${ArticleCard.TAG}']")
        for (i in 0 until articleElements.length) {
            val articleElement = articleElements.item(i) ?: continue
            val article = articleElement.asDynamic().kotlinInstance as? ArticleCard
            if (article != null) {
                val editableArticle = article.edit()
                articleElement.parentElement?.replaceChild(editableArticle, articleElement)
            }
        }

        // Add "+" button to add new articles
        val addButton = document.create.button {
            attributes["class"] = "add-button"
            attributes["onclick"] = "prisma.editor.component.Latest.addNewArticle(this)"
            attributes["title"] = "Add new article"
            +"+"
        }
        element.appendChild(addButton)

        return element
    }

    // Generates the HTML structure for the component based on current properties
    fun buildHtml(): HTMLElement {
        val ul = document.create.ul {
            attributes["data-component-tag"] = TAG
            // Store a reference to the Kotlin component instance on the DOM element
            this@ul.asDynamic().kotlinInstance = this@Latest
        }

        articles.forEach { article ->
            ul.appendChild(article.buildHtml())
        }

        return ul
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
     * Placeholder for committing data, e.g., saving to a backend.
     */
    fun commit() {
        val data = mapOf(
            "articles" to articles
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
        // val fetchedData = MyApi.fetchLatestData(Config.currentSite, Config.currentLanguage)
        // set(fetchedData)
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG: (Simulated - component created with initial or default values)")
    }

    /**
     * Updates the component's data and refreshes its DOM representation if it's already rendered.
     * @param data A dynamic object expected to have optional articles property.
     */
    fun set(data: dynamic) {
        var changed = false

        val newArticles = data.articles?.unsafeCast<List<ArticleCard>>()
        if (newArticles != null && newArticles != articles) {
            articles = newArticles
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
        const val TAG = "latest-updates"

        /**
         * Adds a new article to the list.
         * This is called from the "+" button in the list.
         */
        @JsName("addNewArticle")
        fun addNewArticle(button: dynamic) {
            val listElement = button.parentElement
            val article = ArticleCard(
                initialTitle = "New Article Title",
                initialAuthor = "Author Name",
                initialDate = "Date"
            )
            val articleElement = article.edit()
            listElement.insertBefore(articleElement, button)
        }

        fun cssRules(): List<CssRuleDefinition> {
            val selectorBase = "[data-component-tag='$TAG']"
            return listOf(
                selectorBase to {
                    display = "flex"
                    flexDirection = "column"
                    setProperty("gap", Theme.spacing)
                    listStyleType = "none"
                    padding = "0"
                    margin = "0"
                }
            )
        }
    }
}
