package prisma.editor.ui.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import web.html.HTMLElement
import org.w3c.dom.HTMLElement as W3CHTMLElement
import prisma.editor.styles.NavigationMenuStyles

/**
 * Creates a navigation menu with the 'navigation-menu' attribute.
 */
class NavigationMenu(
    var brand: String = "Prisma-Software",
    var items: List<String> = listOf("Home", "News", "Blog", "Company")
) : HTMLElement {
    fun create(): W3CHTMLElement {
        return document.create.nav {
            // Add the 'navigation-menu' attribute for CSS targeting
            attributes["navigation-menu"] = ""

            div {
                a {
                    href = "#"
                    // Add the 'brand' class for CSS targeting
                    classes = setOf("brand")
                    +brand
                }

                ul {
                    items.forEach { item ->
                        li {
                            a {
                                href = "#"
                                +item
                            }
                        }
                    }
                }
            }
        }
    }
}
