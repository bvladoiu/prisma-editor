package prisma.editor.ui.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement


class Expertise(var name: String, var description: String){

    fun preview(): HTMLElement {
        return document.create.div {
            {
                attributes["expertise"] = ""
                h3 {
                    +name
                }
                p {
                    +description
                }
            }
        }
    }
}

