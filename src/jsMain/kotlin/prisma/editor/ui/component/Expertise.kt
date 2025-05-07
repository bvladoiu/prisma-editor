package prisma.editor.ui.component

import kotlinx.html.*
import prisma.editor.styles.ExpertiseItemStyles

/**
 * Creates an expertise item.
 */
fun FlowContent.Expertise(
    title: String,
    description: String
) {
    div {
        consumer.onTagContentUnsafe { 
            ExpertiseItemStyles.applyContainerStyle(it)
        }

        h3 {
            consumer.onTagContentUnsafe { 
                ExpertiseItemStyles.applyTitleStyle(it)
            }
            +title
        }

        p {
            consumer.onTagContentUnsafe { 
                ExpertiseItemStyles.applyDescriptionStyle(it)
            }
            +description
        }
    }
}
