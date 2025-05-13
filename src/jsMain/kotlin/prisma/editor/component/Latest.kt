package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import kotlin.js.JSON


class Latest(var articles: List<ArticleCard> = emptyList()) {
    init {
        load()
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
        console.log("save:$TAG", jsonData)
    }

    fun load() {
        console.log("load:$TAG")
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
