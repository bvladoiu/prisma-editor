package prisma.editor.ui.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import web.html.HTMLElement
import org.w3c.dom.HTMLElement as W3CHTMLElement
import prisma.editor.styles.KeywordStripStyles

/**
 * Creates a keyword strip with the 'keyword-strip' attribute.
 */
class KeywordStrip : HTMLElement {
    fun create(
        keywords: List<String>
    ): W3CHTMLElement {
        return document.create.div {
            // Add the 'keyword-strip' attribute for CSS targeting
            attributes["keyword-strip"] = ""

            keywords.forEach { keyword ->
                span {
                    +keyword
                }
            }
        }
    }
}
