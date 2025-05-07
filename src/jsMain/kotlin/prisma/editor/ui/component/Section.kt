package prisma.editor.ui.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import web.html.HTMLElement
import org.w3c.dom.HTMLElement as W3CHTMLElement

/**
 * Creates a section with a title and content.
 */
class Section : HTMLElement {
    fun create(
        title: String? = null,
        isDivider: Boolean = false,
        marginTop: String = "2rem",
        content: String = ""
    ): W3CHTMLElement {
        return document.create.section {
            attributes["style"] = """
                margin-top: $marginTop;
                margin-bottom: 2rem;
                padding: 0 16px;
                max-width: 1200px;
                margin-left: auto;
                margin-right: auto;
            """

            article {
                attributes["style"] = """
                    padding: 16px;
                    background-color: rgba(255, 255, 255, 0.03);
                    border-radius: 4px;
                """

                if (title != null) {
                    if (isDivider) {
                        header {
                            attributes["style"] = """
                                font-size: 24px;
                                font-weight: bold;
                                font-family: 'Poppins', sans-serif;
                                margin-bottom: 16px;
                                padding-bottom: 8px;
                                border-bottom: 1px solid #666666;
                            """
                            h2 { +title }
                        }
                    } else {
                        header {
                            attributes["style"] = "margin-bottom: 16px;"
                            h2 {
                                attributes["style"] = """
                                    font-size: 24px;
                                    font-weight: bold;
                                    font-family: 'Poppins', sans-serif;
                                    margin: 0;
                                """
                                +title
                            }
                        }
                    }
                }

                // Content
                div {
                    +content
                }
            }
        }
    }
}
