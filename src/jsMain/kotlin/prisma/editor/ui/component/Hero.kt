package prisma.editor.ui.component

import kotlinx.html.*
import prisma.editor.styles.HeroStyles

/**
 * Creates a hero content section.
 */
fun FlowContent.Hero(
    title: String,
    description: String,
    buttonText: String
) {
    div {
        consumer.onTagContentUnsafe { 
            HeroStyles.applyContainerStyle(it)
        }

        h1 {
            consumer.onTagContentUnsafe { 
                HeroStyles.applyTitleStyle(it)
            }
            +title
        }

        p {
            consumer.onTagContentUnsafe { 
                HeroStyles.applyDescriptionStyle(it)
            }
            +description
        }

        button {
            consumer.onTagContentUnsafe { 
                HeroStyles.applyButtonStyle(it)
            }
            +buttonText
        }
    }
}
