package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import kotlin.js.JSON


class Latest(var articles: List<ArticleCard> = emptyList()) {
    init {
        load()
    }

    /**
     * Turns the component into an editable state.
     * @return The editable HTMLElement
     */
    fun edit(): HTMLElement {
        val element = preview()

        // Convert all article cards to editable mode
        val articleElements = element.querySelectorAll("[${ArticleCard.TAG}]")
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

    fun preview(): HTMLElement {
        val ul = document.create.ul {
            attributes[TAG] = ""
            asDynamic().kotlinInstance = this@Latest
        }

        articles.forEach { article ->
            ul.appendChild(article.preview())
        }

        return ul
    }

    fun commit() {
        val data = mapOf(
            "articles" to articles
        )
        val jsonData = JSON.stringify(data)
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG")
    }

    fun set(data: dynamic) {
        articles = data.articles?.unsafeCast<List<ArticleCard>>() ?: articles
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
        const val TAG = "updates-list"

        /**
         * Adds a new article to the list.
         * This is called from the "+" button in the list.
         */
        @JsName("addNewArticle")
        fun addNewArticle(button: dynamic) {
            val listElement = button.parentElement
            val article = ArticleCard("New Article Title", "Author Name", "Date")
            val articleElement = article.edit()
            listElement.insertBefore(articleElement, button)
        }

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
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
