package prisma.editor.ui.component

import kotlinx.html.*

/**
 * Creates a keyword strip.
 */
fun FlowContent.KeywordStrip(
    keywords: List<String>
) {
    div {
        attributes["style"] = """
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
            justify-content: center;
        """

        keywords.forEach { keyword ->
            span {
                attributes["style"] = """
                    background-color: rgba(255, 255, 255, 0.1);
                    color: #56b2f0;
                    padding: 4px 12px;
                    border-radius: 16px;
                    font-size: 14px;
                """
                +keyword
            }
        }
    }
}
