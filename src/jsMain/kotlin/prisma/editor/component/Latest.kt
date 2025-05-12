package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme

/**
 * Creates an updates list.
 */
class Latest(var articles: List<ArticleCard> = emptyList()) {
    fun preview(): HTMLElement {
        val ul = document.create.ul {
            attributes["updates-list"] = ""
        }

        articles.forEach { article ->
            ul.appendChild(article.preview())
        }

        return ul
    }

    companion object {
        const val TAG = "updates-list"

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[updates-list]" to {
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
