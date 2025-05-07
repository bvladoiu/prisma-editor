package prisma.editor.ui.component

import kotlinx.html.*
import prisma.editor.model.Update

/**
 * Creates an updates list.
 */
fun FlowContent.Updates(
    updates: List<Update>
) {
    ul {
        attributes["style"] = """
            list-style-type: none;
            padding: 0;
            margin: 0;
        """

        updates.forEach { update ->
            li {
                attributes["style"] = """
                    margin-bottom: 16px;
                    padding-bottom: 16px;
                    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
                """

                div {
                    attributes["style"] = """
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        margin-bottom: 8px;
                    """

                    h3 {
                        attributes["style"] = """
                            font-size: 18px;
                            margin: 0;
                            color: #56b2f0;
                        """
                        +update.title
                    }

                    span {
                        attributes["style"] = """
                            font-size: 14px;
                            color: #cccccc;
                        """
                        +update.date
                    }
                }

                div {
                    attributes["style"] = """
                        font-size: 14px;
                        color: #cccccc;
                    """
                    +"By ${update.author}"
                }
            }
        }
    }
}
