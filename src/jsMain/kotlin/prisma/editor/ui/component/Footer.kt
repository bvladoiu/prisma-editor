package prisma.editor.ui.component

import kotlinx.html.*

/**
 * Creates a footer.
 */
fun FlowContent.Footer(
    copyright: String = "Prisma-Software © 2024, All rights reserved.",
    links: List<String> = listOf("Privacy Policy", "Terms of Service")
) {
    footer {
        attributes["style"] = """
            margin-top: 32px;
            padding-top: 16px;
            border-top: 1px solid rgba(255, 255, 255, 0.1);
            text-align: center;
            font-size: 14px;
            color: #cccccc;
        """

        p { +copyright }

        div {
            attributes["style"] = """
                display: flex;
                justify-content: center;
                gap: 16px;
                margin-top: 8px;
            """

            links.forEach { link ->
                a {
                    href = "#"
                    attributes["style"] = "color: #56b2f0; text-decoration: none;"
                    +link
                }
            }
        }
    }
}
