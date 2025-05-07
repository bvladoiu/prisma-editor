package prisma.editor.ui.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import web.html.HTMLElement
import org.w3c.dom.HTMLElement as W3CHTMLElement

/**
 * Creates a reason item.
 */
class Pitch : HTMLElement {
    fun create(
        title: String,
        description: String
    ): W3CHTMLElement {
        return document.create.div {
            attributes["style"] = """
                background-color: rgba(255, 255, 255, 0.05);
                padding: 16px;
                border-radius: 4px;
            """

            h3 {
                attributes["style"] = """
                    font-size: 20px;
                    margin-top: 0;
                    margin-bottom: 8px;
                    color: #56b2f0;
                """
                +title
            }

            p {
                attributes["style"] = "margin: 0;"
                +description
            }
        }
    }
}
