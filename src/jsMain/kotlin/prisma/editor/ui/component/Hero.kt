package prisma.editor.ui.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import web.html.HTMLElement
import org.w3c.dom.HTMLElement as W3CHTMLElement
import prisma.editor.styles.HeroStyles

/**
 * Creates a hero content section with the 'hero' attribute.
 */
class Hero(
    var name: String = "",
    var description: String = "",
    var buttonText: String = ""
) : HTMLElement {
    fun create(): W3CHTMLElement {
        return document.create.div {
            // Add the 'hero' attribute for CSS targeting
            attributes["hero"] = ""

            h1 {
                +name
            }

            p {
                +description
            }

            button {
                +buttonText
            }
        }
    }
}
