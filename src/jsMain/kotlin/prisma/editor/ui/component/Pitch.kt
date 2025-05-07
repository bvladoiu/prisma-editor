package prisma.editor.ui.component

import kotlinx.html.*

/**
 * Creates a reason item.
 */
fun FlowContent.Pitch(
    title: String,
    description: String
) {
    div {
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
