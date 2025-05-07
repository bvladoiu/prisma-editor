package prisma.editor.ui.component

import kotlinx.html.*

/**
 * Creates a section with a title and content.
 */
fun FlowContent.Section(
    title: String? = null,
    isDivider: Boolean = false,
    marginTop: String = "2rem",
    content: FlowContent.() -> Unit
) {
    section {
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
            content()
        }
    }
}

/**
 * Creates a page with content.
 * This follows the compose-style naming convention.
 */
fun FlowContent.Page(
    content: FlowContent.() -> Unit
) {
    div {
        attributes["style"] = """
            max-width: 1200px;
            margin: 0 auto;
            padding: 16px;
        """
        content()
    }
}
